package xscript.compiler;

import xscript.XOpcode;
import xscript.compiler.tree.XTree;


public class XCompiledPartCodeExpr extends XCompiledPart {

	private XCodeGen codeGen = new XCodeGen();
	
	public XCompiledPartCodeExpr(XTree t) {
		super(t);
	}
	
	@Override
	public XCompiledPartCodeExpr asStatement(XTreeCompiler compiler, XCodeGen codeGen) {
		setupAndGet(compiler, codeGen);
		codeGen.addInstruction(t, XOpcode.POP_1);
		return this;
	}
	
	@Override
	public XCompiledPart get(XTreeCompiler compiler, XCodeGen codeGen) {
		codeGen.addInstructions(this.codeGen);
		return this;
	}

	public XCodeGen getCode() {
		return codeGen;
	}

}
