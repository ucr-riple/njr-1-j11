package xscript.compiler;

import xscript.XOpcode;
import xscript.compiler.tree.XTree;



public class XCompiledPartTupel extends XCompiledPart {
	
	private XCompiledPart[] parts;
	
	public XCompiledPartTupel(XTree t, XCompiledPart[] parts) {
		super(t);
		this.parts = parts;
	}

	@Override
	public XCompiledPart asStatement(XTreeCompiler compiler, XCodeGen codeGen) {
		for(XCompiledPart part:parts){
			part.asStatement(compiler, codeGen);
		}
		return this;
	}

	@Override
	public XCompiledPart get(XTreeCompiler compiler, XCodeGen codeGen) {
		for(XCompiledPart part:parts){
			part.setupAndGet(compiler, codeGen);
		}
		codeGen.addInstruction(t, XOpcode.MAKE_TUPLE, parts.length);
		return this;
	}

}
