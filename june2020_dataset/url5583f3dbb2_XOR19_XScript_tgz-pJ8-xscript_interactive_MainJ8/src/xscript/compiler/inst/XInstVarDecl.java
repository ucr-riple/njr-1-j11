package xscript.compiler.inst;

import xscript.XOpcode;
import xscript.compiler.XCodeGen;
import xscript.compiler.XDataOutput;
import xscript.compiler.XVar;

public class XInstVarDecl extends XInst {

	private XVar var;
	
	public XInstVarDecl(int line, XVar var) {
		super(line, XOpcode.NOP);
		this.var = var;
	}

	@Override
	public int tryWay(XCodeGen codeGen, int programPointer, int stackSize, int[] sizes) {
		var.position = stackSize-1;
		return programPointer;
	}

	@Override
	public void toCode(XDataOutput dataOutput) {}

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public String toString() {
		return "DECL "+var.name+"@"+var.position;
	}
	
}
