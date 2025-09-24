package xscript.compiler;

import xscript.XOpcode;
import xscript.compiler.inst.XInstCall;
import xscript.compiler.tree.XTree;



public class XCompiledPartCall extends XCompiledPart {
	
	private XCompiledPart method;
	private XCompiledPart[] params;
	private String[] kws;
	private int unpackList;
	private int unpackMap;
	
	public XCompiledPartCall(XTree t, XCompiledPart method, XCompiledPart[] params, String[] kws, int unpackList, int unpackMap) {
		super(t);
		this.method = method;
		this.params = params;
		this.kws = kws;
		this.unpackList = unpackList;
		this.unpackMap = unpackMap;
	}

	@Override
	public XCompiledPart asStatement(XTreeCompiler compiler, XCodeGen codeGen) {
		setup(compiler, codeGen);
		get(compiler, codeGen);
		codeGen.addInstruction(t, XOpcode.POP_1);
		return this;
	}
	
	@Override
	public int setup(XTreeCompiler compiler, XCodeGen codeGen) {
		method.setupAndGetThis(compiler, codeGen);
		method.get(compiler, codeGen);
		for(XCompiledPart param:params){
			param.setupAndGet(compiler, codeGen);
		}
		return params.length+2;
	}

	@Override
	public XCompiledPart get(XTreeCompiler compiler, XCodeGen codeGen) {
		codeGen.addInstruction(new XInstCall(t.position.position.line, kws, unpackList, unpackMap, params.length));
		return this;
	}
	
	@Override
	public XCompiledPart set(XTreeCompiler compiler, XCodeGen codeGen) {
		//TODO 
		return super.set(compiler, codeGen);
	}

	@Override
	public XCompiledPart del(XTreeCompiler compiler, XCodeGen codeGen) {
		//TODO
		return super.get(compiler, codeGen);
	}

}
