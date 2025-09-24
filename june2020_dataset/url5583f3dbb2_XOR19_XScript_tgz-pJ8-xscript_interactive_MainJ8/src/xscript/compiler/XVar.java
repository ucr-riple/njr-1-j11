package xscript.compiler;

import xscript.compiler.tree.XTree;

public class XVar {

	public XTree t;
	
	public String name;

	public int position;

	public boolean usedInClosure;
	
	public XVar(XTree t, String name) {
		this.t = t;
		this.name = name;
	}

	@Override
	public String toString() {
		return "XVar [name=" + name + ", position=" + position + "]";
	}
	
}
