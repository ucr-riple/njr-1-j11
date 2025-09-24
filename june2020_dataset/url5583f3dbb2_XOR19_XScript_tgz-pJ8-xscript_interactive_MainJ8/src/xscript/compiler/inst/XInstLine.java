package xscript.compiler.inst;

import xscript.XOpcode;
import xscript.compiler.XDataOutput;

public class XInstLine extends XInst {
	
	public XInstLine(int line) {
		super(line, XOpcode.LINE1);
	}
	
	@Override
	public void toCode(XDataOutput dataOutput) {
		if(line<=0xFF){
			dataOutput.writeByte(XOpcode.LINE1.ordinal());
			dataOutput.writeByte((int)line);
		}else if(line<=0xFFFF){
			dataOutput.writeByte(XOpcode.LINE2.ordinal());
			dataOutput.writeShort((int)line);
		}else{
			dataOutput.writeByte(XOpcode.LINE4.ordinal());
			dataOutput.writeInt((int)line);
		}
	}
	
	@Override
	public String toString() {
		return "LINE "+line;
	}
	
	@Override
	public int getSize() {
		return line<=0xFF?2:line<=0xFFFF?3:5;
	}
	
}
