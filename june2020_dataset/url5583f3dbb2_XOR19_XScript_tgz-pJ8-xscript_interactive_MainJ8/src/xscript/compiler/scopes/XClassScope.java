package xscript.compiler.scopes;

import xscript.compiler.XClassAttr;
import xscript.compiler.XVar;
import xscript.compiler.tree.XTree;

public class XClassScope extends XScope {
	
	private XVar __class;
	
	public XClassScope(XScope parent, XVar __class) {
		super(parent, true);
		this.__class = __class;
	}
	
	public XClassScope(XClassScope thiz) {
		super(thiz);
		__class = null;
	}

	protected XVar create(XTree t, String name){
		return new XClassAttr(t, name, __class);
	}

	public XVar getClasz() {
		return __class;
	}

	@Override
	public XScope lock() {
		return new XClassScope(this);
	}
	
}
