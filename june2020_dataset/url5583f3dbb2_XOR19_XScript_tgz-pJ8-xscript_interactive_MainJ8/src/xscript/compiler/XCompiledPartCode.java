package xscript.compiler;

import xscript.compiler.tree.XTree;


public class XCompiledPartCode extends XCompiledPart {

	private XCodeGen codeGen = new XCodeGen();
	
	public XCompiledPartCode(XTree t) {
		super(t);
	}
	
	@Override
	public XCompiledPartCode asStatement(XTreeCompiler compiler, XCodeGen codeGen) {
		codeGen.addInstructions(this.codeGen);
		return this;
	}

	public XCodeGen getCode() {
		return codeGen;
	}

}
