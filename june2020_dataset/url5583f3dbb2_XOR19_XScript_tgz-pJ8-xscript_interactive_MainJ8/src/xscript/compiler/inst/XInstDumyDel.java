package xscript.compiler.inst;

import xscript.XOpcode;
import xscript.compiler.XDataOutput;

public class XInstDumyDel extends XInst {

	public XInstDumyDel() {
		super(-1, XOpcode.NOP);
	}
	
	@Override
	public void toCode(XDataOutput dataOutput) {
		throw new AssertionError();
	}
	
	@Override
	public int getSize() {
		return 0;
	}
	
}
