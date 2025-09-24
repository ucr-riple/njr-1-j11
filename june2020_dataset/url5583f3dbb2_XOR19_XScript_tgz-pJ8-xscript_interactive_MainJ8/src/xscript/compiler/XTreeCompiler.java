package xscript.compiler;

import java.util.ArrayList;
import java.util.List;

import javax.tools.DiagnosticListener;

import xscript.XOpcode;
import xscript.compiler.inst.XInstCall;
import xscript.compiler.inst.XInstConst;
import xscript.compiler.inst.XInstMakeFunc;
import xscript.compiler.inst.XInstSwitch;
import xscript.compiler.scopes.XBaseScope;
import xscript.compiler.scopes.XClassScope;
import xscript.compiler.scopes.XFinallyScope;
import xscript.compiler.scopes.XModuleScope;
import xscript.compiler.scopes.XScope;
import xscript.compiler.scopes.XScope.Result;
import xscript.compiler.tree.XTree;
import xscript.compiler.tree.XTree.XTreeAssert;
import xscript.compiler.tree.XTree.XTreeBreak;
import xscript.compiler.tree.XTree.XTreeCall;
import xscript.compiler.tree.XTree.XTreeCase;
import xscript.compiler.tree.XTree.XTreeCatch;
import xscript.compiler.tree.XTree.XTreeClass;
import xscript.compiler.tree.XTree.XTreeConstant;
import xscript.compiler.tree.XTree.XTreeContinue;
import xscript.compiler.tree.XTree.XTreeDelete;
import xscript.compiler.tree.XTree.XTreeDo;
import xscript.compiler.tree.XTree.XTreeEmpty;
import xscript.compiler.tree.XTree.XTreeError;
import xscript.compiler.tree.XTree.XTreeExpr;
import xscript.compiler.tree.XTree.XTreeFor;
import xscript.compiler.tree.XTree.XTreeForeach;
import xscript.compiler.tree.XTree.XTreeFunc;
import xscript.compiler.tree.XTree.XTreeGroup;
import xscript.compiler.tree.XTree.XTreeIdent;
import xscript.compiler.tree.XTree.XTreeIf;
import xscript.compiler.tree.XTree.XTreeImport;
import xscript.compiler.tree.XTree.XTreeImportEntry;
import xscript.compiler.tree.XTree.XTreeImportFrom;
import xscript.compiler.tree.XTree.XTreeIndex;
import xscript.compiler.tree.XTree.XTreeInstanceof;
import xscript.compiler.tree.XTree.XTreeIssubclass;
import xscript.compiler.tree.XTree.XTreeLabel;
import xscript.compiler.tree.XTree.XTreeMakeList;
import xscript.compiler.tree.XTree.XTreeMakeMap;
import xscript.compiler.tree.XTree.XTreeMakeTuple;
import xscript.compiler.tree.XTree.XTreeMapEntry;
import xscript.compiler.tree.XTree.XTreeModule;
import xscript.compiler.tree.XTree.XTreeOperator;
import xscript.compiler.tree.XTree.XTreeOperatorIf;
import xscript.compiler.tree.XTree.XTreeOperatorPrefixSuffix;
import xscript.compiler.tree.XTree.XTreeReturn;
import xscript.compiler.tree.XTree.XTreeScope;
import xscript.compiler.tree.XTree.XTreeStatement;
import xscript.compiler.tree.XTree.XTreeSuper;
import xscript.compiler.tree.XTree.XTreeSwitch;
import xscript.compiler.tree.XTree.XTreeSynchronized;
import xscript.compiler.tree.XTree.XTreeThis;
import xscript.compiler.tree.XTree.XTreeThrow;
import xscript.compiler.tree.XTree.XTreeTry;
import xscript.compiler.tree.XTree.XTreeTypeof;
import xscript.compiler.tree.XTree.XTreeVarDecl;
import xscript.compiler.tree.XTree.XTreeVarDeclEntry;
import xscript.compiler.tree.XTree.XTreeWhile;
import xscript.compiler.tree.XTree.XTreeYield;
import xscript.compiler.tree.XVisitor;

public class XTreeCompiler implements XVisitor {

	private DiagnosticListener<String> diagnosticListener;
	private XCompiledPart treePart;
	private XScope scope;
	private List<XLabel> labels;
	private byte[] module;
	private XCompilerOptions options;

	public XTreeCompiler(DiagnosticListener<String> diagnosticListener, XCompilerOptions options) {
		this.diagnosticListener = diagnosticListener;
		this.options = options;
	}

	private XCompiledPart visit(XTree tree) {
		if (tree == null)
			return null;
		XCompiledPart tp = treePart;
		treePart = null;
		tree.accept(this);
		XCompiledPart ret = treePart;
		treePart = tp;
		return ret;
	}

	private XCompiledPart visitInScope(XTree tree) {
		if (tree == null)
			return null;
		scope = new XScope(scope, false);
		XCompiledPart tp = treePart;
		treePart = null;
		tree.accept(this);
		XCompiledPart ret = treePart;
		treePart = tp;
		scope = scope.getParent();
		return ret;
	}

	private XCompiledPartCode visit(XTree t, List<? extends XTree> trees) {
		if (trees == null)
			return null;
		XCompiledPart tp = treePart;
		XCompiledPartCode ret = new XCompiledPartCode(t);
		XCodeGen code = ret.getCode();
		for (XTree tree : trees) {
			if (treePart != null && treePart.isDead()) {
				XTree last = trees.get(trees.size() - 1);
				diagnosticListener.report(new XDiagnostic(tree.position.position, tree.position.start, last.position.end, "dead.code"));
			}
			treePart = null;
			tree.accept(this);
			treePart.asStatement(this, code);
		}
		treePart = tp;
		return ret;
	}

	private XCodeGen getCode(XTree t) {
		XCompiledPartCode code = new XCompiledPartCode(t);
		treePart = code;
		return code.getCode();
	}

	private XCodeGen getCodeExpr(XTree t) {
		XCompiledPartCodeExpr code = new XCompiledPartCodeExpr(t);
		treePart = code;
		return code.getCode();
	}

	@Override
	public void visitError(XTreeError error) {
	}

	@Override
	public void visitScope(XTreeScope scope) {
		XJumpTarget _break = new XJumpTarget();
		this.scope = new XScope(this.scope, true, _break, labels, 0);
		labels = null;
		XCodeGen code = getCode(scope);
		XCompiledPartCode part = visit(scope, scope.statements);
		part.asStatement(this, code);
		if (part.isDead() && _break.jumps() == 0)
			treePart.setDead(true);
		code.addInstructionB(scope, XOpcode.POP, this.scope.getLocalsCount());
		this.scope = this.scope.getParent();
		code.addInstruction(scope, _break);
	}

	@Override
	public void visitClass(XTreeClass _class) {
		XCodeGen code = getCodeExpr(_class);
		XCompiledPart part = null;
		if (_class.name != null) {
			part = new XCompiledPartVar(_class.name, scope, _class.name, _class.access);
			part.setup(this, code);
		}
		if (_class.superClasses == null) {
			code.addInstruction(_class, XOpcode.LOADN);
		} else {
			visit(_class.superClasses).setupAndGet(this, code);
		}
		code.addInstruction(_class, XOpcode.MAKE_CLASS, _class.name == null ? "<anonymouse>" : _class.name.name);
		XVar __class = new XVar(_class, "<class>");
		code.addInstruction(_class, __class);
		this.scope = new XClassScope(this.scope, __class);
		boolean dead = false;
		for (XTreeStatement statement : _class.init.statements) {
			if (dead) {
				addDiagnostic(statement, "dead.code");
			} else {
				XCompiledPart p = visit(statement);
				if (p.isDead()) {
					dead = true;
				}
				p.asStatement(this, code);
			}
		}
		treePart.setDead(dead);
		this.scope = this.scope.getParent();
		if (part != null) {
			part.set(this, code);
		}
	}

	@Override
	public void visitFunc(XTreeFunc func) {
		XCodeGen code = getCodeExpr(func);
		String[] paramNames = new String[func.params.size()];
		int i = 0;
		for (XTreeIdent ident : func.params) {
			paramNames[i++] = ident.name;
		}
		XCompiledPart part = null;
		if (func.name != null) {
			part = new XCompiledPartVar(func.name, scope, func.name, func.access);
			part.setup(this, code);
		}
		int defStart = -1;
		i = 0;
		int nums = 0;
		for (XTreeExpr defExpr : func.defaults) {
			if (defExpr != null) {
				if (defStart == -1)
					defStart = i;
				visit(defExpr).setupAndGet(this, code);
				nums++;
			}
			i++;
		}
		if (nums == 0) {
			code.addInstruction(func, XOpcode.LOADN);
		} else {
			code.addInstruction(func, XOpcode.MAKE_TUPLE, nums);
		}
		XVar _class = scope.getLastClass();
		XBaseScope funcScope = new XBaseScope(this.scope, true);
		Result result = funcScope.get(func, "this", XVarAccess.LOCAL);
		result.var.position = 0;
		i = 1;
		for (String paramName : paramNames) {
			result = funcScope.get(func, paramName, XVarAccess.LOCAL);
			result.var.position = i++;
		}
		if (_class == null) {
			code.addInstruction(new XInstMakeFunc(func.position.position.line, XOpcode.MAKE_FUNC, func.body, func.name == null ? null : func.name.name, paramNames, func.kwParam, func.listParam, defStart, funcScope, this));
		} else {
			code.addInstruction2(func, XOpcode.GETBOTTOM1, _class);
			code.addInstruction(new XInstMakeFunc(func.position.position.line, XOpcode.MAKE_METH, func.body, func.name == null ? null : func.name.name, paramNames, func.kwParam, func.listParam, defStart, funcScope, this));
		}
		if (part != null) {
			part.set(this, code);
		}
	}

	@Override
	public void visitModule(XTreeModule module) {
		scope = new XModuleScope();
		XCodeGen code = new XCodeGen(1);
		visit(module, module.body.statements).asStatement(this, code);
		code.addInstructionB(module, XOpcode.POP, this.scope.getLocalsCount());
		XDataOutput dataOutput = new XDataOutput();
		this.module = dataOutput.toByteArray(code, options);
	}

	@Override
	public void visitImportEntry(XTreeImportEntry importEntry) {
		throw new AssertionError();
	}

	@Override
	public void visitImport(XTreeImport _import) {
		XCodeGen code = getCode(_import);
		for (XTreeImportEntry importEntry : _import.imports) {
			String importName = importEntry._import.name;
			XTreeIdent name = importEntry.as == null ? importEntry._import : importEntry.as;
			String asName = name.name;
			int index = asName.indexOf('.');
			if (index != -1) {
				name = new XTreeIdent(name.position, asName.substring(0, index));
			}
			XCompiledPart parts = new XCompiledPartVar(importEntry, scope, name, this.scope.isGloabl() ? XVarAccess.GLOBAL : XVarAccess.LOCAL);
			parts.setup(this, code);
			code.addInstruction(importEntry, XOpcode.IMPORT, importName);
			if (importEntry.as != null)
				importName = importEntry.as.name;
			if (index == -1) {
				parts.set(this, code);
				code.addInstruction(importEntry, XOpcode.POP_1);
			} else {
				parts.get(this, code);
				code.addInstruction(importEntry, XOpcode.DUP);
				code.addInstruction(importEntry, XOpcode.LOADN);
				code.addInstruction(importEntry, XOpcode.SAME);
				XJumpTarget target = new XJumpTarget();
				code.addInstruction(importEntry, XOpcode.JUMP_IF_ZERO, target);
				code.addInstruction(importEntry, XOpcode.POP_1);
				code.addInstruction(importEntry, XOpcode.MAKE_MAP, 0);
				parts.set(this, code);
				code.addInstruction(importEntry, target);
				code.addInstruction(importEntry, XOpcode.IMPORT_SAVE, asName.substring(index + 1));
			}
		}
	}

	@Override
	public void visitImportFrom(XTreeImportFrom importFrom) {
		XCodeGen code = getCode(importFrom);
		String importName = importFrom.from.name;
		// TODO from xxx import *;
		XCompiledPart[] parts = new XCompiledPart[importFrom.imports.size()];
		int i = 0;
		XVarAccess access = this.scope.isGloabl() ? XVarAccess.GLOBAL : XVarAccess.LOCAL;
		for (XTreeImportEntry importEntry : importFrom.imports) {
			XTreeIdent name = importEntry.as == null ? importEntry._import : importEntry.as;
			(parts[i++] = new XCompiledPartVar(importEntry, scope, name, access)).setup(this, code);
		}
		code.addInstruction(importFrom, XOpcode.IMPORT, importName);
		i = 0;
		for (XTreeImportEntry importEntry : importFrom.imports) {
			code.addInstruction(importEntry, XOpcode.DUP);
			code.addInstruction(importEntry, XOpcode.GET_ATTR, importEntry._import.name);
			parts[i++].set(this, code);
			code.addInstruction(importEntry, XOpcode.POP_1);
		}
	}

	@Override
	public void visitIf(XTreeIf _if) {
		XCodeGen code = getCode(_if);
		XJumpTarget _break = new XJumpTarget();
		scope = new XScope(scope, true, _break, labels, 0);
		labels = null;
		XCompiledPart part = visit(_if.condition);
		if (part.isConstValue()) {
			if (part.isFalse()) {
				addDiagnostic(_if.onTrue, "dead.code");
				if (_if.onFalse != null) {
					visitInScope(_if.onFalse).asStatement(this, code);
				}
			} else {
				visitInScope(_if.onTrue).asStatement(this, code);
				if (_if.onFalse != null) {
					addDiagnostic(_if.onFalse, "dead.code");
				}
			}
		} else {
			part.setupAndGet(this, code);
			XJumpTarget onFalse = new XJumpTarget();
			code.addInstruction(_if, XOpcode.JUMP_IF_ZERO, onFalse);
			visitInScope(_if.onTrue).asStatement(this, code);
			if (_if.onFalse == null) {
				code.addInstruction(_if, onFalse);
			} else {
				code.addInstruction(_if, XOpcode.JUMP, _break);
				code.addInstruction(_if, onFalse);
				visitInScope(_if.onFalse).asStatement(this, code);
			}
		}
		code.addInstructionB(_if, XOpcode.POP, this.scope.getLocalsCount());
		scope = scope.getParent();
		code.addInstruction(_if, _break);
	}

	@Override
	public void visitFor(XTreeFor _for) {
		XCodeGen code = getCode(_for);
		XJumpTarget _break = new XJumpTarget();
		XJumpTarget stop = new XJumpTarget();
		XJumpTarget _continue = new XJumpTarget();
		XJumpTarget start = new XJumpTarget();
		scope = new XScope(scope, true, _break, _continue, labels, 0);
		labels = null;
		visit(_for.init).asStatement(this, code);
		code.addInstruction(_for, start);
		XCompiledPart part = visit(_for.condition);
		if (part.isConstValue()) {
			if (part.isFalse()) {
				addDiagnostic(_for.inc, "dead.code");
				addDiagnostic(_for.body, "dead.code");
			} else {
				visitInScope(_for.body).asStatement(this, code);
				code.addInstruction(_for, _continue);
				visit(_for.inc).asStatement(this, code);
				code.addInstruction(_for, XOpcode.JUMP, start);
				if (_break.jumps() == 0)
					treePart.setDead(true);
			}
		} else {
			part.setupAndGet(this, code);
			code.addInstruction(_for, XOpcode.JUMP_IF_ZERO, stop);
			visitInScope(_for.body).asStatement(this, code);
			code.addInstruction(_for, _continue);
			visit(_for.inc).asStatement(this, code);
			code.addInstruction(_for, XOpcode.JUMP, start);
		}
		code.addInstruction(_for, stop);
		code.addInstructionB(_for, XOpcode.POP, this.scope.getLocalsCount());
		scope = scope.getParent();
		code.addInstruction(_for, _break);
	}

	@Override
	public void visitForeach(XTreeForeach foreach) {
		XCodeGen code = getCode(foreach);
		XJumpTarget _break = new XJumpTarget();
		XJumpTarget end = new XJumpTarget();
		XJumpTarget _continue = new XJumpTarget();
		scope = new XScope(scope, true, _break, _continue, labels, 1);
		labels = null;
		visit(foreach.in).setupAndGet(this, code);
		code.addInstruction(foreach, XOpcode.DUP);
		code.addInstruction(foreach, XOpcode.GET_ATTR, "iter");
		code.addInstruction(foreach, null, -1, -1, 0);
		code.addInstruction(foreach, _continue);
		code.addInstruction(foreach, XOpcode.DUP);
		code.addInstruction(foreach, XOpcode.DUP);
		code.addInstruction(foreach, XOpcode.GET_ATTR, "hasNext");
		code.addInstruction(foreach, null, -1, -1, 0);
		code.addInstruction(foreach, XOpcode.JUMP_IF_ZERO, end);
		code.addInstruction(foreach, XOpcode.DUP);
		code.addInstruction(foreach, XOpcode.DUP);
		code.addInstruction(foreach, XOpcode.GET_ATTR, "next");
		code.addInstruction(foreach, null, -1, -1, 0);
		XCompiledPart part = visit(foreach.var);
		part.setup(this, code);
		part.set(this, code);
		visitInScope(foreach.body).asStatement(this, code);
		code.addInstruction(foreach, XOpcode.JUMP, _continue);
		code.addInstructionB(foreach, XOpcode.POP, this.scope.getLocalsCount());
		scope = scope.getParent();
		code.addInstruction(foreach, end);
		code.addInstruction(foreach, XOpcode.POP_1);
		code.addInstruction(foreach, _break);
	}

	@Override
	public void visitWhile(XTreeWhile _while) {
		XCodeGen code = getCode(_while);
		XJumpTarget _break = new XJumpTarget();
		XJumpTarget _continue = new XJumpTarget();
		scope = new XScope(scope, true, _break, _continue, labels, 0);
		labels = null;
		code.addInstruction(_while, _continue);
		XCompiledPart part = visit(_while.condition);
		if (part.isConstValue()) {
			if (part.isFalse()) {
				addDiagnostic(_while.body, "dead.code");
			} else {
				visitInScope(_while.body).asStatement(this, code);
				code.addInstruction(_while, XOpcode.JUMP, _continue);
				if (_break.jumps() == 0)
					treePart.setDead(true);
			}
		} else {
			part.setupAndGet(this, code);
			code.addInstruction(_while, XOpcode.JUMP_IF_ZERO, _break);
			visitInScope(_while.body).asStatement(this, code);
			code.addInstruction(_while, XOpcode.JUMP, _continue);
		}
		code.addInstructionB(_while, XOpcode.POP, this.scope.getLocalsCount());
		scope = scope.getParent();
		code.addInstruction(_while, _break);
	}

	@Override
	public void visitDo(XTreeDo _do) {
		XCodeGen code = getCode(_do);
		XJumpTarget _break = new XJumpTarget();
		XJumpTarget _continue = new XJumpTarget();
		XJumpTarget start = new XJumpTarget();
		scope = new XScope(scope, true, _break, _continue, labels, 0);
		labels = null;
		code.addInstruction(_do, start);
		visitInScope(_do.body).asStatement(this, code);
		code.addInstruction(_do, _continue);
		XCompiledPart part = visit(_do.condition);
		if (part.isConstValue()) {
			if (!part.isFalse()) {
				code.addInstruction(_do, XOpcode.JUMP, start);
				if (_break.jumps() == 0)
					treePart.setDead(true);
			}
		} else {
			part.setupAndGet(this, code);
			code.addInstruction(_do, XOpcode.JUMP_IF_NON_ZERO, start);
		}
		code.addInstructionB(_do, XOpcode.POP, this.scope.getLocalsCount());
		scope = scope.getParent();
		code.addInstruction(_do, _break);
	}

	@Override
	public void visitSynchronized(XTreeSynchronized _synchronized) {
		XCodeGen code = getCode(_synchronized);
		visit(_synchronized.sync).asStatement(this, code);
		visit(_synchronized.body).asStatement(this, code);
		addDiagnostic(_synchronized, "synchronized.not.implemented");
	}

	@Override
	public void visitBreak(XTreeBreak _break) {
		XCodeGen code = getCode(_break);
		XJump jump = new XJump();
		if (!scope.getJump(_break.lable == null ? null : _break.lable.name, 0, jump)) {
			addDiagnostic(_break, "break.not.found");
		} else {
			jump.gen(_break, code);
		}
		treePart.setDead(true);
	}

	@Override
	public void visitContinue(XTreeContinue _continue) {
		XCodeGen code = getCode(_continue);
		XJump jump = new XJump();
		if (!scope.getJump(_continue.lable == null ? null : _continue.lable.name, 1, jump)) {
			addDiagnostic(_continue, "continue.not.found");
		} else {
			jump.gen(_continue, code);
		}
		treePart.setDead(true);
	}

	@Override
	public void visitReturn(XTreeReturn _return) {
		XCodeGen code = getCode(_return);
		XJump jump = new XJump();
		scope.getJump(null, 2, jump);
		if (_return._return == null) {
			code.addInstruction(_return, XOpcode.LOADN);
		} else {
			visit(_return._return).setupAndGet(this, code);
		}
		jump.genRet(_return, code);
		treePart.setDead(true);
	}

	@Override
	public void visitYield(XTreeYield yield) {
		XCodeGen code = getCodeExpr(yield);
		if (yield._return == null) {
			code.addInstruction(yield, XOpcode.LOADN);
		} else {
			visit(yield._return).get(this, code);
		}
		code.addInstruction(yield, XOpcode.YIELD);
	}

	@Override
	public void visitThis(XTreeThis _this) {
		XCodeGen code = getCodeExpr(_this);
		code.addInstructionB(_this, XOpcode.GETBOTTOM1, 0);
	}

	@Override
	public void visitLabel(XTreeLabel label) {
		if (labels == null) {
			labels = new ArrayList<XLabel>();
		}
		XLabel l = new XLabel(label.label.name);
		labels.add(l);
		treePart = visit(label.statement);
		if (l.uses == 0) {
			addDiagnostic(label, "label.unused");
		}
		labels = null;
	}

	@Override
	public void visitSuper(XTreeSuper _super) {
		XCodeGen code = getCodeExpr(_super);
		code.addInstruction(_super, XOpcode.SUPER);
	}

	@Override
	public void visitThrow(XTreeThrow _throw) {
		XCodeGen code = getCode(_throw);
		visit(_throw._throw).setupAndGet(this, code);
		code.addInstruction(_throw, XOpcode.THROW);
		treePart.setDead(true);
	}

	@Override
	public void visitAssert(XTreeAssert _assert) {
		XCodeGen code = getCode(_assert);
		XCompiledPart part = visit(_assert.condition);
		if (part.isConstValue()) {
			if (part.isFalse() && !options.removeAsserts) {
				code.addInstruction(_assert, XOpcode.LOADN);
				code.addInstruction(_assert, XOpcode.IMPORT, "sys");
				code.addInstruction(_assert, XOpcode.GET_ATTR, "AssertionError");
				if (_assert.message == null) {
					code.addInstruction(new XInstCall(_assert.position.position.line, null, -1, -1, 0));
				} else {
					visit(_assert.message).setupAndGet(this, code);
					code.addInstruction(new XInstCall(_assert.position.position.line, null, -1, -1, 1));
				}
				treePart.setDead(true);
			}
		} else if (options.removeAsserts) {
			part.asStatement(this, code);
		} else {
			part.setupAndGet(this, code);
			XJumpTarget end = new XJumpTarget();
			code.addInstruction(_assert, XOpcode.JUMP_IF_NON_ZERO, end);
			code.addInstruction(_assert, XOpcode.LOADN);
			code.addInstruction(_assert, XOpcode.IMPORT, "sys");
			code.addInstruction(_assert, XOpcode.GET_ATTR, "AssertionError");
			if (_assert.message == null) {
				code.addInstruction(new XInstCall(_assert.position.position.line, null, -1, -1, 0));
			} else {
				visit(_assert.message).setupAndGet(this, code);
				code.addInstruction(new XInstCall(_assert.position.position.line, null, -1, -1, 1));
			}
			code.addInstruction(_assert, XOpcode.THROW);
			code.addInstruction(_assert, end);
		}
	}

	@Override
	public void visitCatch(XTreeCatch _catch) {
		throw new AssertionError();
	}

	@Override
	public void visitTry(XTreeTry _try) {
		XCodeGen code = getCode(_try);

		XJumpTarget onFinallyExc = null;
		XJumpTarget onExc = null;
		if (_try._finally != null) {
			onFinallyExc = new XJumpTarget();
			code.addInstruction(_try, XOpcode.START_TRY, onFinallyExc);
		}
		if (_try.catches != null) {
			onExc = new XJumpTarget();
			code.addInstruction(_try, XOpcode.START_TRY, onExc);
		}
		if (_try.resources != null) {
			addDiagnostic(_try.resources, "try.resources.not.implemented");
			visit(_try.resources).asStatement(this, code);
		}
		XJumpTarget _break = new XJumpTarget();
		this.scope = new XScope(this.scope, false, _break, labels, 0);
		labels = null;
		XJumpTarget _finally = null;
		if (_try._finally == null) {
			this.scope = new XScope(this.scope, true);
		} else {
			_finally = new XJumpTarget();
			this.scope = new XFinallyScope(this.scope, _finally);
		}
		visit(_try.body).asStatement(this, code);
		code.addInstructionB(_try, XOpcode.POP, this.scope.getLocalsCount());
		this.scope = this.scope.getParent();
		code.addInstruction(_try, XOpcode.END_TRY);
		if (_finally == null) {
			code.addInstruction(_try, XOpcode.JUMP, _break);
		} else {
			code.addInstruction(_try, XOpcode.LOADN);
			code.addInstruction(_try, XOpcode.LOADI_1);
			code.addInstruction(_try, XOpcode.JUMP, _finally);
		}
		if (_try.catches != null) {
			code.addInstruction(_try, onExc);
			for (XTreeCatch _catch : _try.catches) {
				XJumpTarget not = new XJumpTarget();
				if (_catch.excType.size() == 1) {
					code.addInstruction(_catch, XOpcode.DUP);
					XCompiledPart part = visit(_catch.excType.get(0));
					part.setupAndGet(this, code);
					code.addInstruction(_catch, XOpcode.INSTANCEOF);
					code.addInstruction(_catch, XOpcode.JUMP_IF_ZERO, not);
				} else {
					XJumpTarget is = new XJumpTarget();
					for (XTreeExpr type : _catch.excType) {
						code.addInstruction(_catch, XOpcode.DUP);
						XCompiledPart part = visit(type);
						part.setupAndGet(this, code);
						code.addInstruction(_catch, XOpcode.INSTANCEOF);
						code.addInstruction(_catch, XOpcode.JUMP_IF_NON_ZERO, is);
					}
					code.addInstruction(_catch, XOpcode.JUMP, not);
					code.addInstruction(_catch, is);
				}
				this.scope = new XScope(this.scope, true);
				if (_catch.varName == null) {
					code.addInstruction(_catch, XOpcode.POP_1);
				} else {
					XCompiledPartVar part = new XCompiledPartVar(_catch, scope, _catch.varName, XVarAccess.LOCAL);
					if (part.setup2(this, code)) {
						throw new AssertionError();
					}
					part.set(this, code);
				}
				XCompiledPart part = visit(_catch.body);
				part.asStatement(this, code);
				code.addInstructionB(_catch, XOpcode.POP, this.scope.getLocalsCount());
				this.scope = this.scope.getParent();
				if (_finally == null) {
					code.addInstruction(_try, XOpcode.JUMP, _break);
				} else {
					code.addInstruction(_try, XOpcode.LOADN);
					code.addInstruction(_try, XOpcode.LOADI_1);
					code.addInstruction(_try, XOpcode.JUMP, _finally);
				}
				code.addInstruction(_catch, not);
			}
			if (_finally == null) {
				code.addInstruction(_try, XOpcode.POP_1);
			}
		}
		if (_finally != null) {
			code.addInstruction(_try, onFinallyExc);
			code.addInstruction(_try, XOpcode.LOADI_0);
			code.addInstruction(_try, _finally);
			this.scope = new XScope(this.scope, true);
			visit(_try._finally).asStatement(this, code);
			code.addInstructionB(_try, XOpcode.POP, this.scope.getLocalsCount());
			this.scope = this.scope.getParent();
			code.addInstruction(_try, XOpcode.END_FINALLY);
		}
		this.scope = this.scope.getParent();
		code.addInstruction(_try, _break);
	}

	@Override
	public void visitGroup(XTreeGroup group) {
		group.group.accept(this);
	}

	@Override
	public void visitOperator(XTreeOperator operator) {
		XOperator o = operator.operators.get(0);
		if (o.priority == 0) {
			XCodeGen code = getCodeExpr(operator);
			XCompiledPart[] parts = new XCompiledPart[operator.exprs.size() - 1];
			for (int i = 0; i < parts.length; i++) {
				XCompiledPart part = visit(operator.exprs.get(i));
				int size = part.setup(this, code);
				parts[i] = part;
				o = operator.operators.get(i);
				if (o != XOperator.LET && o != XOperator.COPY) {
					for (int j = 0; j < size; j++) {
						code.addInstructionB(operator, XOpcode.GETTOP1, size);
					}
					part.get(this, code);
				}
			}
			visit(operator.exprs.get(operator.exprs.size() - 1)).setupAndGet(this, code);
			for (int i = parts.length - 1; i >= 0; i--) {
				o = operator.operators.get(i);
				if (o == XOperator.COPY) {
					code.addInstruction(operator, XOpcode.COPY);
				} else if (o != XOperator.LET) {
					code.addInstruction(operator, o.opcode);
				}
				parts[i].set(this, code);
			}
		} else if (o == XOperator.COMMA) {
			XCompiledPart[] parts = new XCompiledPart[operator.exprs.size()];
			for (int i = 0; i < operator.exprs.size(); i++) {
				XCompiledPart part = visit(operator.exprs.get(i));
				parts[i] = part;
			}
			treePart = new XCompiledPartTupel(operator, parts);
		} else if (o == XOperator.ELEMENT) {
			XCodeGen code = getCodeExpr(operator);
			visit(operator.exprs.get(0)).setupAndGet(this, code);
			for (int i = 1; i < operator.exprs.size() - 1; i++) {
				XTreeExpr expr = operator.exprs.get(i);
				if (expr instanceof XTreeIdent) {
					code.addInstruction(operator, XOpcode.GET_ATTR, ((XTreeIdent) expr).name);
				} else {
					addDiagnostic(expr, "element.expected.ident");
				}
			}
			XTreeExpr expr = operator.exprs.get(operator.exprs.size() - 1);
			if (expr instanceof XTreeIdent) {
				treePart = new XCompiledPartAttr(operator, treePart, ((XTreeIdent) expr).name);
			} else {
				addDiagnostic(expr, "element.expected.ident");
			}
		} else if (o.priority == XOperator.EQ.priority || o.priority == XOperator.SMA.priority) {
			XCodeGen code = getCodeExpr(operator);
			visit(operator.exprs.get(0)).setupAndGet(this, code);
			int i = 1;
			XJumpTarget target = new XJumpTarget();
			for (XOperator oo : operator.operators) {
				visit(operator.exprs.get(i++)).setupAndGet(this, code);
				if (i == operator.exprs.size()) {
					code.addInstruction(operator, oo.opcode);
				} else {
					code.addInstruction(operator, XOpcode.SWAP);
					code.addInstructionB(operator, XOpcode.GETTOP1, 1);
					code.addInstruction(operator, oo.opcode);
					code.addInstruction(operator, XOpcode.JUMP_IF_ZERO, target);
				}
			}
			if (operator.exprs.size() > 2) {
				XJumpTarget t = new XJumpTarget();
				code.addInstruction(operator, XOpcode.JUMP, t);
				code.addInstruction(operator, target);
				code.addInstruction(operator, XOpcode.POP_1);
				code.addInstruction(operator, XOpcode.LOAD_FALSE);
				code.addInstruction(operator, t);
			}
		} else if (o == XOperator.OR) {
			XCodeGen code = getCodeExpr(operator);
			XCompiledPart part = visit(operator.exprs.get(0));
			XJumpTarget target = new XJumpTarget();
			boolean hadSetup = false;
			for (int i = 1; i < operator.exprs.size(); i++) {
				if (part.isConstValue()) {
					if (part.isFalse()) {

					} else {
						// TODO
						treePart = new XCompiledPartConst(operator, true);
						return;
					}
				} else {
					if (hadSetup) {
						code.addInstruction(operator, XOpcode.JUMP_IF_NON_ZERO, target);
					}
					hadSetup = true;
					part.setupAndGet(this, code);
				}
				part = visit(operator.exprs.get(i));
			}
			if (part.isConstValue()) {
				if (!part.isFalse()) {
					// TODO
					treePart = new XCompiledPartConst(operator, true);
					return;
				}
			} else {
				if (hadSetup) {
					code.addInstruction(operator, XOpcode.JUMP_IF_NON_ZERO, target);
				}
				part.setupAndGet(this, code);
			}
			XJumpTarget t = new XJumpTarget();
			code.addInstruction(operator, XOpcode.JUMP, t);
			code.addInstruction(operator, target);
			code.addInstruction(operator, XOpcode.LOAD_TRUE);
			code.addInstruction(operator, t);
		} else if (o == XOperator.AND) {
			XCodeGen code = getCodeExpr(operator);
			XCompiledPart part = visit(operator.exprs.get(0));
			XJumpTarget target = new XJumpTarget();
			boolean hadSetup = false;
			for (int i = 1; i < operator.exprs.size(); i++) {
				if (part.isConstValue()) {
					if (part.isFalse()) {
						// TODO
						treePart = new XCompiledPartConst(operator, false);
						return;
					} else {

					}
				} else {
					if (hadSetup) {
						code.addInstruction(operator, XOpcode.JUMP_IF_ZERO, target);
					}
					hadSetup = true;
					part.setupAndGet(this, code);
				}
				part = visit(operator.exprs.get(i));
			}
			if (part.isConstValue()) {
				if (part.isFalse()) {
					// TODO
					treePart = new XCompiledPartConst(operator, false);
					return;
				}
			} else {
				if (hadSetup) {
					code.addInstruction(operator, XOpcode.JUMP_IF_ZERO, target);
				}
				part.setupAndGet(this, code);
			}
			XJumpTarget t = new XJumpTarget();
			code.addInstruction(operator, XOpcode.JUMP, t);
			code.addInstruction(operator, target);
			code.addInstruction(operator, XOpcode.LOAD_FALSE);
			code.addInstruction(operator, t);
		} else {
			XCodeGen code = getCodeExpr(operator);
			XCompiledPart part = visit(operator.exprs.get(0));
			int i = 1;
			Object _const = null;
			boolean isConst = part.isConstValue();
			if (isConst) {
				_const = part.getConst();
			} else {
				part.setupAndGet(this, code);
			}
			for (XOperator oo : operator.operators) {
				part = visit(operator.exprs.get(i++));
				if (isConst && part.isConstValue()) {
					try {
						_const = oo.calc(_const, part.getConst());
					} catch (UnsupportedOperationException e) {
						addDiagnostic(operator, "op.not.supported", oo, _const == null ? null : _const.getClass(), part.getConst() == null ? null : part.getConst().getClass());
						code.addInstruction(operator, XOpcode.LOADN);
						isConst = false;
					}
				} else {
					if (isConst) {
						code.addInstruction(new XInstConst(operator.position.position.line, _const));
						isConst = false;
					}
					part.setupAndGet(this, code);
					code.addInstruction(operator, oo.opcode);
				}
			}
			if (isConst) {
				treePart = new XCompiledPartConst(operator, _const);
			}
		}
	}

	@Override
	public void visitOperatorIf(XTreeOperatorIf operatorIf) {
		XCodeGen code = getCodeExpr(operatorIf);
		XCompiledPart part = visit(operatorIf.condition);
		if (part.isConstValue()) {
			if (part.isFalse()) {
				addDiagnostic(operatorIf.onTrue, "dead.code");
				visit(operatorIf.onFalse).setupAndGet(this, code);
			} else {
				visit(operatorIf.onTrue).setupAndGet(this, code);
				addDiagnostic(operatorIf.onFalse, "dead.code");
			}
		} else {
			part.setupAndGet(this, code);
			XJumpTarget onFalse = new XJumpTarget();
			code.addInstruction(operatorIf, XOpcode.JUMP_IF_ZERO, onFalse);
			visit(operatorIf.onTrue).setupAndGet(this, code);
			XJumpTarget end = new XJumpTarget();
			code.addInstruction(operatorIf, XOpcode.JUMP, end);
			code.addInstruction(operatorIf, onFalse);
			visit(operatorIf.onFalse).setupAndGet(this, code);
			code.addInstruction(operatorIf, end);
		}
	}

	@Override
	public void visitOperatorPrefixSuffix(XTreeOperatorPrefixSuffix operatorPrefixSuffix) {
		XCompiledPart part = visit(operatorPrefixSuffix.expr);
		if (part.isConstValue()) {
			Object _const = part.getConst();
			if (operatorPrefixSuffix.suffix != null) {
				for (XOperator op : operatorPrefixSuffix.suffix) {
					try {
						_const = op.calc(_const, null);
					} catch (UnsupportedOperationException e) {
						addDiagnostic(operatorPrefixSuffix, "op.not.supported", op, _const == null ? null : _const.getClass());
						treePart = new XCompiledPartConst(operatorPrefixSuffix, null);
						return;
					}
				}
			}
			if (operatorPrefixSuffix.prefix != null) {
				for (int i = operatorPrefixSuffix.prefix.size() - 1; i >= 0; i--) {
					try {
						_const = operatorPrefixSuffix.prefix.get(i).calc(_const, null);
					} catch (UnsupportedOperationException e) {
						addDiagnostic(operatorPrefixSuffix, "op.not.supported", operatorPrefixSuffix.prefix.get(i), _const == null ? null : _const.getClass());
						treePart = new XCompiledPartConst(operatorPrefixSuffix, null);
						return;
					}
				}
			}
			treePart = new XCompiledPartConst(operatorPrefixSuffix, _const);
			return;
		}
		XCodeGen code = getCodeExpr(operatorPrefixSuffix);
		int i = -1;
		boolean backSave = false;
		boolean pop = false;
		if (operatorPrefixSuffix.prefix != null) {
			for (i = operatorPrefixSuffix.prefix.size() - 1; i >= 0; i--) {
				XOperator o = operatorPrefixSuffix.prefix.get(i);
				if (o != XOperator.INC && o != XOperator.DEC) {
					break;
				}
				if (!backSave) {
					int size = part.setup(this, code);
					for (int j = 0; j < size; j++) {
						code.addInstructionB(operatorPrefixSuffix, XOpcode.GETTOP1, size - 1);
					}
					part.get(this, code);
					backSave = true;
				}
				code.addInstruction(operatorPrefixSuffix, o.opcode);
			}
		}
		if (operatorPrefixSuffix.suffix != null) {
			if (operatorPrefixSuffix.suffix.size() > 0) {
				XVar tmp = new XVar(null, "<tmp>");
				code.addInstruction(operatorPrefixSuffix, XOpcode.LOADN);
				code.addInstruction(operatorPrefixSuffix, tmp);
				if (!backSave) {
					int size = part.setup(this, code);
					for (int j = 0; j < size; j++) {
						code.addInstructionB(operatorPrefixSuffix, XOpcode.GETTOP1, size - 1);
					}
					part.get(this, code);
					backSave = true;
					pop = true;
				}
				code.addInstruction(operatorPrefixSuffix, XOpcode.DUP);
				code.addInstruction2(operatorPrefixSuffix, XOpcode.SETBOTTOM1, tmp);
				for (XOperator o : operatorPrefixSuffix.suffix) {
					code.addInstruction(operatorPrefixSuffix, o.opcode);
				}
			}
		}
		if (backSave) {
			part.set(this, code);
			if (pop)
				code.addInstruction(operatorPrefixSuffix, XOpcode.POP_1);
		} else {
			part.setupAndGet(this, code);
		}
		for (; i >= 0; i--) {
			XOperator o = operatorPrefixSuffix.prefix.get(i);
			code.addInstruction(operatorPrefixSuffix, o.opcode);
		}
	}

	@Override
	public void visitIdent(XTreeIdent ident) {
		treePart = new XCompiledPartVar(ident, scope, ident, XVarAccess.NONE);
	}

	@Override
	public void visitIndex(XTreeIndex index) {
		XCompiledPart array = visit(index.array);
		XCompiledPart ind = visit(index.index);
		treePart = new XCompiledPartIndex(index, array, ind);
	}

	@Override
	public void visitInstanceof(XTreeInstanceof _instanceof) {
		XCodeGen code = getCodeExpr(_instanceof);
		visit(_instanceof.statement).setupAndGet(this, code);
		visit(_instanceof.type).setupAndGet(this, code);
		code.addInstruction(_instanceof, XOpcode.INSTANCEOF);

	}

	@Override
	public void visitIssubclass(XTreeIssubclass issubclass) {
		XCodeGen code = getCodeExpr(issubclass);
		visit(issubclass.statement).setupAndGet(this, code);
		visit(issubclass.type).setupAndGet(this, code);
		code.addInstruction(issubclass, XOpcode.ISDERIVEDOF);
	}

	@Override
	public void visitTypeof(XTreeTypeof typeof) {
		XCodeGen code = getCodeExpr(typeof);
		visit(typeof.statement).setupAndGet(this, code);
		code.addInstruction(typeof, XOpcode.TYPEOF);
	}

	@Override
	public void visitSwitch(XTreeSwitch _switch) {
		XCodeGen code = getCode(_switch);
		XJumpTarget _break = new XJumpTarget();
		this.scope = new XScope(this.scope, true, _break, null, labels, 0);
		labels = null;
		visit(_switch.statement).setupAndGet(this, code);
		XInstSwitch s = new XInstSwitch(_switch.position.position.line);
		code.addInstruction(s);
		for (XTreeCase _case : _switch.cases) {
			XJumpTarget jumpTo = new XJumpTarget();
			code.addInstruction(_switch, jumpTo);
			if (_case.key instanceof XTreeConstant) {
				XTreeConstant c = (XTreeConstant) _case.key;
				if (!s.putIfNonExist(c.value, jumpTo)) {
					addDiagnostic(_case.key, "case.duplicated.key", c.value);
				}
			} else if (_case.key == null) {
				if (s._default == null) {
					s._default = jumpTo;
				} else {
					addDiagnostic(_case.key, "case.duplicated.default");
				}
			} else {
				addDiagnostic(_case.key, "case.key.no.constant");
			}
			visit(_case, _case.block).asStatement(this, code);
		}
		if (s._default == null) {
			s._default = _break;
		}
		code.addInstructionB(_switch, XOpcode.POP, this.scope.getLocalsCount());
		code.addInstruction(_switch, _break);
		this.scope = this.scope.getParent();
	}

	@Override
	public void visitCase(XTreeCase _case) {
		throw new AssertionError();
	}

	@Override
	public void visitConstant(XTreeConstant constant) {
		treePart = new XCompiledPartConst(constant, constant.value);
	}

	@Override
	public void visitCall(XTreeCall call) {
		XCompiledPart method = visit(call.method);
		XCompiledPart[] params = new XCompiledPart[call.params.size()];
		int unpackList = -1;
		int unpackMap = -1;
		for (int i = 0; i < params.length; i++) {
			XTreeExpr expr = call.params.get(i);
			if (expr instanceof XTreeOperatorPrefixSuffix) {
				XTreeOperatorPrefixSuffix o = (XTreeOperatorPrefixSuffix) expr;
				if (o.prefix != null && !o.prefix.isEmpty()) {
					if (o.prefix.get(0) == XOperator.UNPACKLIST) {
						if (unpackList == -1) {
							if (unpackMap == -1) {
								unpackList = i;
								o.prefix.remove(0);
							} else {
								addDiagnostic(o, "call.unpack.list.after.map");
							}
						} else {
							addDiagnostic(o, "call.unpack.list.after.list");
						}
					} else if (o.prefix.get(0) == XOperator.UNPACKMAP) {
						if (unpackMap == -1) {
							unpackMap = i;
							o.prefix.remove(0);
						} else {
							addDiagnostic(o, "call.unpack.map.after.map");
						}
					}
					if (o.prefix.isEmpty()) {
						if (o.suffix == null || o.suffix.isEmpty()) {
							expr = o.expr;
						} else {
							o.prefix = null;
						}
					}
				}
			}
			params[i] = visit(expr);
		}
		String[] kws = new String[call.kws.size()];
		boolean needkw = false;
		boolean nn = false;
		for (int i = 0; i < kws.length; i++) {
			XTreeIdent kw = call.kws.get(i);
			String k = kw == null ? null : kw.name;
			if (i == unpackList || i == unpackMap) {
				if (k != null)
					addDiagnostic(kw, i == unpackList ? "unpack.list.with.keyword" : "unpack.map.with.keyword");
				needkw = true;
			} else if (needkw) {
				if (k == null)
					addDiagnostic(call.params.get(i), "keyword.needed");
			}
			if (k != null) {
				needkw = true;
				nn = true;
			}
			kws[i] = k;
		}
		treePart = new XCompiledPartCall(call, method, params, nn ? kws : null, unpackList, unpackMap);
	}

	@Override
	public void visitMakeTuple(XTreeMakeTuple makeTuple) {
		XCompiledPart[] parts = new XCompiledPart[makeTuple.exprs.size()];
		int i = 0;
		for (XTreeExpr expr : makeTuple.exprs) {
			parts[i++] = visit(expr);
		}
		treePart = new XCompiledPartTupel(makeTuple, parts);
	}

	@Override
	public void visitMakeList(XTreeMakeList makeList) {
		XCodeGen code = getCodeExpr(makeList);
		for (XTreeExpr expr : makeList.exprs) {
			visit(expr).get(this, code);
		}
		code.addInstruction(makeList, XOpcode.MAKE_LIST, makeList.exprs.size());

	}

	@Override
	public void visitMakeMap(XTreeMakeMap makeMap) {
		XCodeGen code = getCodeExpr(makeMap);
		for (XTreeMapEntry entry : makeMap.entries) {
			visit(entry.key).get(this, code);
			visit(entry.expr).get(this, code);
		}
		code.addInstruction(makeMap, XOpcode.MAKE_MAP, makeMap.entries.size());
	}

	@Override
	public void visitMapEntry(XTreeMapEntry mapEntry) {
		throw new AssertionError();
	}

	@Override
	public void visitEmpty(XTreeEmpty empty) {
		getCode(empty);
	}

	@Override
	public void visitVarDecl(XTreeVarDecl varDecl) {
		XCodeGen code = getCode(varDecl);
		for (XTreeVarDeclEntry entry : varDecl.entries) {
			XCodeGen cc = new XCodeGen();
			if (entry.def == null) {
				cc.addInstruction(varDecl, XOpcode.LOADN);
			} else {
				visit(entry.def).setupAndGet(this, cc);
			}
			Result result = this.scope.get(varDecl, entry.ident.name, varDecl.access);
			switch (result.r) {
			case DECLARED:
				code.addInstructions(cc);
				code.addInstruction(varDecl, result.var);
				break;
			case DUPLICATED:
				addDiagnostic(varDecl, "var.duplicated", entry.ident.name);
				break;
			case FOUND:
				if (result.var instanceof XClassAttr) {
					XVar var = ((XClassAttr) result.var).getAccess();
					XCompiledPartVar.getVar(varDecl, var, code);
				} else if (result.var instanceof XClosureVar) {
					XVar base = ((XClosureVar) result.var).base;
					if (base instanceof XClassAttr) {
						if (base == ((XClosureVar) result.var).var) {
							code.addInstructionB(varDecl, XOpcode.GETBOTTOM1, 0);
						} else {
							code.addInstruction2(varDecl, XOpcode.GET_CLOSURE, result.var);
						}
					}
				}
				code.addInstructions(cc);
				if (result.var instanceof XClassAttr) {
					code.addInstruction(varDecl, XOpcode.SET_ATTR, result.var.name);
				} else if (result.var instanceof XGlobal) {
					code.addInstruction(varDecl, XOpcode.DUP);
					code.addInstruction2(varDecl, XOpcode.SET_GLOBAL, result.var);
				} else if (result.var instanceof XClosureVar) {
					XVar base = ((XClosureVar) result.var).base;
					if (base instanceof XClassAttr) {
						code.addInstruction(varDecl, XOpcode.SET_ATTR, base.name);
					} else {
						code.addInstruction(varDecl, XOpcode.DUP);
						code.addInstruction2(varDecl, XOpcode.SET_CLOSURE, result.var);
					}
				} else {
					code.addInstruction(varDecl, XOpcode.DUP);
					code.addInstruction2(varDecl, XOpcode.SETBOTTOM1, result.var);
				}
				break;
			case NOT_FOUND:
				addDiagnostic(varDecl, "var.not.found", entry.ident.name);
				break;
			default:
				addDiagnostic(varDecl, "unknown.error");
				break;
			}
		}
	}

	@Override
	public void visitVarDeclEntry(XTreeVarDeclEntry varDeclEntry) {
		throw new AssertionError();
	}

	@Override
	public void visitDelete(XTreeDelete delete) {
		XCodeGen code = getCode(delete);
		visit(delete.expr).del(this, code);
	}

	void addDiagnostic(XTree t, String message, Object... args) {
		diagnosticListener.report(new XDiagnostic(t.position.position, t.position.start, t.position.end, message, args));
	}

	public byte[] getBytes() {
		return module;
	}

	public XCompiledPart compFuncBody(XTree funcBody, XBaseScope scope) {
		XScope oldScope = this.scope;
		this.scope = scope;
		XCompiledPart part = visit(funcBody);
		this.scope = oldScope;
		return part;
	}

}
