package xscript.compiler.inst;

import xscript.XOpcode;
import xscript.compiler.XDataOutput;
import xscript.compiler.XGlobal;
import xscript.compiler.XVar;

public class XInstVarLookup extends XInst {

	private XVar var;

	public XInstVarLookup(int line, XOpcode opcode, XVar var) {
		super(line, opcode);
		this.var = var;
	}

	@Override
	public void toCode(XDataOutput dataOutput) {
		super.toCode(dataOutput);
		if(var instanceof XGlobal){
			dataOutput.writeUTF(var.name);
		}else{
			dataOutput.writeByte(var.position);
		}
	}
	
	@Override
	public String toString() {
		return super.toString()+" "+var.name+"@"+Integer.toHexString(var.position)+" "+var;
	}
	
	@Override
	public int getSize() {
		return var instanceof XGlobal?3:2;
	}
	
}
