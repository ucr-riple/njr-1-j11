package xscript.compiler;

import xscript.XOpcode;
import xscript.compiler.tree.XTree;

public class XCompiledPart {
	
	private boolean dead;
	
	protected XTree t;
	
	public XCompiledPart(XTree t){
		this.t = t;
	}
	
	public XCompiledPart asStatement(XTreeCompiler compiler, XCodeGen codeGen){
		return this;
	}
	
	public int setup(XTreeCompiler compiler, XCodeGen codeGen){
		return 0;
	}
	
	public int setupAndGetThis(XTreeCompiler compiler, XCodeGen codeGen){
		codeGen.addInstruction(t, XOpcode.LOADN);
		return setup(compiler, codeGen);
	}
	
	public XCompiledPart setupAndGet(XTreeCompiler compiler, XCodeGen codeGen){
		setup(compiler, codeGen);
		get(compiler, codeGen);
		return this;
	}
	
	public XCompiledPart get(XTreeCompiler compiler, XCodeGen codeGen){
		compiler.addDiagnostic(t, "unexpected.get");
		codeGen.addInstruction(t, XOpcode.LOADN);
		return this;
	}
	
	public XCompiledPart set(XTreeCompiler compiler, XCodeGen codeGen){
		compiler.addDiagnostic(t, "unexpected.set");
		codeGen.addInstruction(t, XOpcode.POP_1);
		return this;
	}
	
	public XCompiledPart del(XTreeCompiler compiler, XCodeGen codeGen){
		compiler.addDiagnostic(t, "unexpected.del");
		return this;
	}
	
	public boolean isConstValue() {
		return false;
	}

	public Object getConst(){
		return null;
	}
	
	public boolean isFalse() {
		return !isConstValue() || !XWrapper.asBool(getConst());
	}

	public boolean isDead() {
		return dead;
	}
	
	public XCompiledPart setDead(boolean dead) {
		this.dead = dead;
		return this;
	}

}
