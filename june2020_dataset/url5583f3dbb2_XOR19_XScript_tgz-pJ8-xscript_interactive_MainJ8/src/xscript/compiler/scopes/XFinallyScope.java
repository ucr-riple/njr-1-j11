package xscript.compiler.scopes;

import xscript.compiler.XJump;
import xscript.compiler.XJumpTarget;

public class XFinallyScope extends XScope {
	
	private XJumpTarget _finally;
	
	public XFinallyScope(){
		
	}
	
	private XFinallyScope(XFinallyScope thiz){
		super(thiz);
	}
	
	public XFinallyScope(XScope parent, XJumpTarget _finally){
		super(parent, true);
		this._finally = _finally;
	}
	
	@Override
	protected boolean getJumpTarget(String label, int type, XJump jump){
		jump.addFinally(_finally);
		return false;
	}

	@Override
	public XScope lock() {
		return new XFinallyScope(this);
	}
	
}
