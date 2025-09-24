package xscript.compiler;

import java.util.ArrayList;
import java.util.List;

import xscript.XOpcode;
import xscript.compiler.tree.XTree;

public class XJump {

	private XJumpTarget target;
	private List<XJumpTarget> finallies = new ArrayList<XJumpTarget>();
	private int pops = 0;
	
	public void gen(XTree t, XCodeGen code) {
		if(pops>0){
			code.addInstructionB(t, XOpcode.POP, pops);
		}
		if(finallies.isEmpty()){
			code.addInstruction(t, XOpcode.JUMP, target);
		}else{
			code.addInstruction(t, XOpcode.LOADS, target);
			for(int i=finallies.size()-1; i>=1; i--){
				code.addInstruction(t, XOpcode.LOADS, finallies.get(i));
			}
			code.addInstruction(t, XOpcode.MAKE_TUPLE, finallies.size());
			code.addInstruction(t, XOpcode.LOADI_1);
			code.addInstruction(t, XOpcode.JUMP, finallies.get(0));
		}
	}
	
	public void genRet(XTree t, XCodeGen code) {
		if(finallies.isEmpty()){
			code.addInstruction(t, XOpcode.RET);
		}else{
			for(int i=finallies.size()-1; i>=1; i--){
				code.addInstruction(t, XOpcode.LOADS, finallies.get(i));
			}
			code.addInstruction(t, XOpcode.MAKE_TUPLE, finallies.size());
			code.addInstruction(t, XOpcode.LOADI_2);
			code.addInstruction(t, XOpcode.JUMP, finallies.get(0));
		}
	}

	public void addFinally(XJumpTarget target) {
		finallies.add(target);
	}

	public void setTarget(XJumpTarget target){
		this.target = target;
	}
	
	public int getPops() {
		return pops;
	}

	public void addPops(int pops) {
		this.pops+=pops;
	}
	
}
