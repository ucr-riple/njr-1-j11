package xscript.compiler;

import java.util.List;

import xscript.compiler.inst.XInst;

public class XJumpTarget {

	public XInst target;
	private int jumps;
	
	public void addJump(){
		jumps++;
	}
	
	public int jumps() {
		return jumps;
	}

	public boolean is(XInst inst) {
		return target==inst;
	}

	public int resolve(XCodeGen codeGen) {
		return codeGen.resolve(target);
	}

	public void replace(XInst instruction, XInst with) {
		if(target==instruction){
			target = with;
		}
	}

	public int resolve(List<XInst> instructions) {
		return target==null?instructions.size():instructions.indexOf(target);
	}

}
