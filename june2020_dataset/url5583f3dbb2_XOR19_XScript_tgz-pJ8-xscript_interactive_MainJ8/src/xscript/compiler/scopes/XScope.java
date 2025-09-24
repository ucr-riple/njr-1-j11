package xscript.compiler.scopes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xscript.compiler.XClosureVar;
import xscript.compiler.XGlobal;
import xscript.compiler.XJump;
import xscript.compiler.XJumpTarget;
import xscript.compiler.XLabel;
import xscript.compiler.XVar;
import xscript.compiler.XVarAccess;
import xscript.compiler.scopes.XScope.Result.R;
import xscript.compiler.tree.XTree;

public class XScope {

	protected final XModuleScope module;

	protected final XBaseScope base;

	protected final XScope parent;

	protected final XClassScope lastClassScope;

	protected final Map<String, XVar> locals;

	protected final Map<String, XVar> accessed;

	protected final boolean localsAllowed;

	protected final XJumpTarget _break;

	protected final XJumpTarget _continue;

	protected final List<XLabel> labels;

	protected final int pops;

	protected final boolean breakOnlyByName;

	XScope(XScope thiz) {
		if (thiz.parent == null) {
			parent = null;
			this.module = (XModuleScope) this;
		} else {
			parent = thiz.parent.lock();
			this.module = parent.module;
		}
		if (this instanceof XBaseScope) {
			base = (XBaseScope) this;
			lastClassScope = null;
		} else {
			base = parent.base;
			if (this instanceof XClassScope) {
				lastClassScope = (XClassScope) this;
			} else {
				lastClassScope = parent.lastClassScope;
			}
		}
		locals = Collections.unmodifiableMap(thiz.locals);
		accessed = Collections.unmodifiableMap(thiz.accessed);
		localsAllowed = false;
		_break = null;
		_continue = null;
		labels = null;
		pops = 0;
		breakOnlyByName = false;
	}

	public XScope(XScope parent, boolean localsAllowed, XJumpTarget _break, XJumpTarget _continue, List<XLabel> labels, int pops) {
		locals = new HashMap<String, XVar>();
		accessed = new HashMap<String, XVar>();
		this.module = parent.module;
		if (this instanceof XBaseScope) {
			base = (XBaseScope) this;
			lastClassScope = null;
		} else {
			base = parent.base;
			if (this instanceof XClassScope) {
				lastClassScope = (XClassScope) this;
			} else {
				lastClassScope = parent.lastClassScope;
			}
		}
		this.parent = parent;
		this.localsAllowed = localsAllowed;
		this._break = _break;
		this._continue = _continue;
		this.labels = labels;
		this.pops = pops;
		breakOnlyByName = false;
	}

	public XScope(XScope parent, boolean localsAllowed, XJumpTarget _break, List<XLabel> labels, int pops) {
		locals = new HashMap<String, XVar>();
		accessed = new HashMap<String, XVar>();
		this.module = parent.module;
		if (this instanceof XBaseScope) {
			base = (XBaseScope) this;
			lastClassScope = null;
		} else {
			base = parent.base;
			if (this instanceof XClassScope) {
				lastClassScope = (XClassScope) this;
			} else {
				lastClassScope = parent.lastClassScope;
			}
		}
		this.parent = parent;
		this.localsAllowed = localsAllowed;
		this._break = _break;
		this._continue = null;
		this.labels = labels;
		this.pops = pops;
		breakOnlyByName = true;
	}

	public XScope(XScope parent, boolean localsAllowed) {
		locals = new HashMap<String, XVar>();
		accessed = new HashMap<String, XVar>();
		this.module = parent.module;
		if (this instanceof XBaseScope) {
			base = (XBaseScope) this;
			lastClassScope = null;
		} else {
			base = parent.base;
			if (this instanceof XClassScope) {
				lastClassScope = (XClassScope) this;
			} else {
				lastClassScope = parent.lastClassScope;
			}
		}
		this.parent = parent;
		this.localsAllowed = localsAllowed;
		_break = null;
		_continue = null;
		labels = null;
		pops = 0;
		breakOnlyByName = false;
	}

	XScope() {
		locals = new HashMap<String, XVar>();
		accessed = new HashMap<String, XVar>();
		this.module = (XModuleScope) this;
		this.base = this.module;
		this.parent = null;
		this.localsAllowed = true;
		lastClassScope = null;
		_break = null;
		_continue = null;
		labels = null;
		pops = 0;
		breakOnlyByName = false;
	}

	public static class Result {
		public static enum R {
			FOUND, DUPLICATED, NOT_FOUND, DECLARED
		}

		public R r;
		public XVar var;

		public Result(XVar var) {
			this.var = var;
			this.r = R.FOUND;
		}

		public Result(XVar var, R r) {
			this.var = var;
			this.r = r;
		}
	}

	public Result get(XTree tree, String name, XVarAccess access) {
		XVar var = accessed.get(name);
		if (var != null) {
			if (access == XVarAccess.LOCAL && var.t == null) {
				var.t = tree;
				return new Result(var, R.FOUND);
			}
			return new Result(var, access == XVarAccess.NONE ? R.FOUND : R.DUPLICATED);
		}
		switch (access) {
		case GLOBAL:
			var = module.getOrCreateGlobal(tree, name);
			accessed.put(name, var);
			return new Result(var);
		case LOCAL:
			var = create(tree, name);
			locals.put(name, var);
			accessed.put(name, var);
			return new Result(var, R.DECLARED);
		default:
			XScope scope = this;
			while (scope != null) {
				var = scope.locals.get(name);
				if (var != null) {
					return new Result(var);
				}
				if (scope instanceof XClassScope) {
					var = scope.create(tree, name);
					scope.locals.put(name, var);
					accessed.put(name, var);
					return new Result(var, R.DECLARED);
				}
				if (scope == base)
					break;
				scope = scope.parent;
			}
		case NONLOCAL:
			if (base.parent == null) {
				if (base == module) {
					var = module.getOrCreateGlobal(tree, name);
					return new Result(var);
				}
				return new Result(null, R.NOT_FOUND);
			}
			var = base.parent.getNonLocal(tree, name, access);
			if (var instanceof XGlobal) {
				return new Result(var);
			}
			if (var == null)
				return new Result(null, R.NOT_FOUND);
			var = new XClosureVar(var);
			base.addClosure((XClosureVar) var);
			return new Result(var);
		}
	}

	protected XVar getNonLocal(XTree tree, String name, XVarAccess access) {
		XScope scope = this;
		XVar var;
		while (scope != null) {
			if (access == XVarAccess.NONE || !(scope instanceof XClassScope)) {
				var = scope.locals.get(name);
				if (var != null) {
					return var;
				}
			}
			if (scope == base)
				break;
			scope = scope.parent;
		}
		if (base.parent == null) {
			return base == module ? module.getOrCreateGlobal(tree, name) : null;
		}
		var = base.parent.getNonLocal(tree, name, access);
		if (var instanceof XGlobal)
			return var;
		var = new XClosureVar(var);
		base.addClosure((XClosureVar) var);
		return var;
	}

	protected XVar create(XTree tree, String name) {
		return new XVar(tree, name);
	}

	public XScope getParent() {
		return parent;
	}

	public boolean getJump(String label, int type, XJump jump) {
		XScope scope = this;
		while (scope != null) {
			jump.addPops(scope.pops + scope.locals.size());
			if (scope.getJumpTarget(label, type, jump)) {
				return true;
			}
			if (scope == base) {
				break;
			}
			scope = scope.parent;
		}
		return false;
	}

	protected boolean getJumpTarget(String label, int type, XJump jump) {
		if (type != 2 && hasLabel(label)) {
			if (type == 0 && _break != null) {
				jump.setTarget(_break);
				return true;
			} else if (type == 1 && _continue != null) {
				jump.setTarget(_continue);
				return true;
			}
		}
		return false;
	}

	private boolean hasLabel(String label) {
		if (label == null)
			return !breakOnlyByName;
		if (labels == null)
			return false;
		for (XLabel ll : labels) {
			if (ll.name.equals(label)) {
				ll.uses++;
				return true;
			}
		}
		return false;
	}

	public int getLocalsCount() {
		return locals.size();
	}

	public XVar getLastClass() {
		return lastClassScope == null ? null : lastClassScope.getClasz();
	}

	public Map<String, XVar> getLocals() {
		return Collections.unmodifiableMap(locals);
	}

	public XScope lock() {
		return new XScope(this);
	}

	public boolean isGloabl() {
		return base == module;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [module=" + getScopeString(module) + ", base=" + getScopeString(base) + ", parent=" + getScopeString(parent) + ", lastClassScope=" + getScopeString(lastClassScope) + ", locals=" + locals + ", accessed=" + accessed + ", localsAllowed=" + localsAllowed + ", _break=" + _break + ", _continue=" + _continue + ", labels=" + labels + ", pops=" + pops + ", breakOnlyByName=" + breakOnlyByName + "]";
	}

	private String getScopeString(XScope scope) {
		return scope==null?"null":scope.getClass().getSimpleName();// scope==this?"this":scope==null?"null":scope.toString();
	}

}
