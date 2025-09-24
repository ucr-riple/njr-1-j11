package xscript.compiler.inst;

import java.util.List;

import xscript.XOpcode;
import xscript.compiler.XCodeGen;
import xscript.compiler.XDataOutput;
import xscript.compiler.XJumpTarget;

public class XInstJump extends XInst {

	public XJumpTarget target;
	public int resolved;
	public int resolved2;
	
	public XInstJump(int line, XOpcode opcode, XJumpTarget target) {
		super(line, opcode);
		this.target = target;
	}

	@Override
	public boolean pointingTo(XInst inst) {
		return target.is(inst);
	}

	@Override
	public void resolve(XCodeGen codeGen, List<XInst> instructions) {
		resolved = target.resolve(codeGen);
		resolved2 = target.resolve(instructions);
	}

	@Override
	public boolean isNormalJump() {
		return opcode==XOpcode.JUMP;
	}

	@Override
	public boolean isAlwaysJump() {
		return opcode==XOpcode.JUMP;
	}

	@Override
	public int tryWay(XCodeGen codeGen, int programPointer, int stackSize, int[] sizes) {
		if(isAlwaysJump()){
			return resolved2;
		}
		if(opcode==XOpcode.START_TRY){
			codeGen.tryWay(resolved2, stackSize+1, sizes);
		}else{
			codeGen.tryWay(resolved2, stackSize, sizes);
		}
		return programPointer;
	}

	@Override
	public void replace(XCodeGen codeGen, XInst instruction, XInst with, List<XInst> instructions) {
		target.replace(instruction, with);
	}

	@Override
	public void toCode(XDataOutput dataOutput) {
		super.toCode(dataOutput);
		if(resolved>0xFFFF)
			throw new AssertionError();
		dataOutput.writeShort(resolved);
	}

	@Override
	public String toString() {
		return super.toString()+" "+resolved;
	}
	
	@Override
	public int getSize() {
		return 3;
	}
	
}
