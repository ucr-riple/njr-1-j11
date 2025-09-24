package xscript.compiler;

import xscript.compiler.tree.XTree;


public class XClassAttr extends XVar {
	
	private XVar access;
	
	public XClassAttr(XTree t, String name, XVar access) {
		super(t, name);
		this.access = access;
	}
	
	public XVar getAccess(){
		return access;
	}

}
