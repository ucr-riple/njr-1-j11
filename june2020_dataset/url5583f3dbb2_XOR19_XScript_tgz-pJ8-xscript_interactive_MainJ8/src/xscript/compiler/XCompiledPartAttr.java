package xscript.compiler;

import xscript.XOpcode;
import xscript.compiler.tree.XTree;



public class XCompiledPartAttr extends XCompiledPart {
	
	private String attribute;
	private XCompiledPart part;
	
	public XCompiledPartAttr(XTree t, XCompiledPart part, String attribute) {
		super(t);
		this.part = part;
		this.attribute = attribute;
	}

	@Override
	public XCompiledPartAttr asStatement(XTreeCompiler compiler, XCodeGen codeGen) {
		setupAndGet(compiler, codeGen);
		codeGen.addInstruction(t, XOpcode.POP_1);
		return this;
	}

	@Override
	public int setup(XTreeCompiler compiler, XCodeGen codeGen) {
		part.setupAndGet(compiler, codeGen);
		return 1;
	}
	
	@Override
	public int setupAndGetThis(XTreeCompiler compiler, XCodeGen codeGen) {
		part.setupAndGet(compiler, codeGen);
		codeGen.addInstruction(t, XOpcode.DUP);
		return 1;
	}

	@Override
	public XCompiledPartAttr get(XTreeCompiler compiler, XCodeGen codeGen) {
		codeGen.addInstruction(t, XOpcode.GET_ATTR, attribute);
		return this;
	}
	
	@Override
	public XCompiledPartAttr set(XTreeCompiler compiler, XCodeGen codeGen) {
		codeGen.addInstruction(t, XOpcode.SET_ATTR, attribute);
		return this;
	}
	
	@Override
	public XCompiledPartAttr del(XTreeCompiler compiler, XCodeGen codeGen) {
		codeGen.addInstruction(t, XOpcode.DEL_ATTR, attribute);
		return this;
	}

}
