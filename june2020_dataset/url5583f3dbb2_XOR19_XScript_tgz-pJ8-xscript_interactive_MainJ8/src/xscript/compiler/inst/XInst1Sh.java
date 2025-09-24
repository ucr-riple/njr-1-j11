package xscript.compiler.inst;

import xscript.XOpcode;
import xscript.compiler.XDataOutput;

public class XInst1Sh extends XInst {

	public int i;
	
	public XInst1Sh(int line, XOpcode opcode, int i) {
		super(line, opcode);
		this.i = i;
	}

	@Override
	public void toCode(XDataOutput dataOutput) {
		super.toCode(dataOutput);
		dataOutput.writeShort(i);
	}

	@Override
	public String toString() {
		return super.toString()+" "+i;
	}

	@Override
	public int getStackChange() {
		return opcode.getStackChange(i);
	}
	
	@Override
	public int getSize() {
		return 3;
	}
	
}
