package xscript.compiler.inst;

import xscript.XOpcode;
import xscript.compiler.XDataOutput;

public class XInst1S extends XInst {

	private String s;
	
	public XInst1S(int line, XOpcode opcode, String s) {
		super(line, opcode);
		this.s = s;
	}
	
	@Override
	public void toCode(XDataOutput dataOutput) {
		super.toCode(dataOutput);
		dataOutput.writeUTF(s);
	}
	
	@Override
	public String toString() {
		return super.toString()+" "+s;
	}
	
	@Override
	public int getSize() {
		return 3;
	}
	
}
