package xscript.compiler;

import xscript.XOpcode;
import xscript.compiler.tree.XTree;



public class XCompiledPartIndex extends XCompiledPart {
	
	private XCompiledPart array;
	private XCompiledPart index;
	
	public XCompiledPartIndex(XTree t, XCompiledPart array, XCompiledPart index) {
		super(t);
		this.array = array;
		this.index = index;
	}

	@Override
	public XCompiledPart asStatement(XTreeCompiler compiler, XCodeGen codeGen) {
		setup(compiler, codeGen);
		get(compiler, codeGen);
		codeGen.addInstruction(t, XOpcode.POP_1);
		return this;
	}
	
	@Override
	public int setupAndGetThis(XTreeCompiler compiler, XCodeGen codeGen) {
		array.setupAndGet(compiler, codeGen);
		codeGen.addInstruction(t, XOpcode.DUP);
		index.setupAndGet(compiler, codeGen);
		return 2;
	}

	@Override
	public int setup(XTreeCompiler compiler, XCodeGen codeGen) {
		array.setupAndGet(compiler, codeGen);
		index.setupAndGet(compiler, codeGen);
		return 2;
	}

	@Override
	public XCompiledPart get(XTreeCompiler compiler, XCodeGen codeGen) {
		codeGen.addInstruction(t, XOpcode.GET_INDEX);
		return this;
	}

	@Override
	public XCompiledPart set(XTreeCompiler compiler, XCodeGen codeGen) {
		codeGen.addInstruction(t, XOpcode.SET_INDEX);
		return this;
	}
	
	@Override
	public XCompiledPart del(XTreeCompiler compiler, XCodeGen codeGen) {
		codeGen.addInstruction(t, XOpcode.DEL_INDEX);
		return this;
	}

}
