package xscript.compiler.inst;

import java.util.List;

import xscript.XOpcode;
import xscript.compiler.XCodeGen;
import xscript.compiler.XDataOutput;

public class XInst {

	protected XOpcode opcode;
	
	public int line;
	
	public XInst(int line, XOpcode opcode) {
		this.line = line;
		this.opcode = opcode;
	}

	public void delInst(XCodeGen codeGen, List<XInst> instructions, XInst inst) {
		if(pointingTo(inst)){
			int i = instructions.indexOf(inst);
			if(i==-1)
				throw new AssertionError();
			i++;
			if(i>=instructions.size()){
				replace(codeGen, inst, null, instructions);
			}else{
				replace(codeGen, inst, instructions.get(i), instructions);
			}
		}
	}

	public boolean pointingTo(XInst inst) {
		return false;
	}

	public void resolve(XCodeGen codeGen, List<XInst> instructions) {
		
	}

	public XInst replaceWith(XCodeGen codeGen, List<XInst> instructions) {
		return this;
	}

	public void replace(XCodeGen codeGen, XInst instruction, XInst with, List<XInst> instructions) {
		
	}

	public void resolvePost(XCodeGen xCodeGen, List<XInst> instructions) {
		
	}
	
	public boolean isNormalJump() {
		return false;
	}

	public boolean isAlwaysJump() {
		return opcode==XOpcode.RET;
	}

	public int getStackChange() {
		return opcode.getStackChange();
	}

	public int tryWay(XCodeGen codeGen, int programPointer, int stackSize, int[] sizes) {
		if(opcode==XOpcode.RET)
			return 0xFFFF;
		return programPointer;
	}

	public void toCode(XDataOutput dataOutput) {
		dataOutput.writeByte(opcode.ordinal());
	}

	@Override
	public String toString() {
		return ""+opcode;
	}

	public XOpcode getOpcode(){
		return opcode;
	}

	public int getSize() {
		return 1;
	}

	public void compileSubparts() {
		
	}
	
}
