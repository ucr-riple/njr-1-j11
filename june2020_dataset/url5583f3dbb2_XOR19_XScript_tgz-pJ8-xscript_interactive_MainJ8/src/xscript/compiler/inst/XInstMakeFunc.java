package xscript.compiler.inst;

import java.util.Arrays;
import java.util.List;

import xscript.XOpcode;
import xscript.compiler.XClosureVar;
import xscript.compiler.XCodeGen;
import xscript.compiler.XDataOutput;
import xscript.compiler.XTreeCompiler;
import xscript.compiler.scopes.XBaseScope;
import xscript.compiler.tree.XTree;


public class XInstMakeFunc extends XInst {

	private XTree funcBody;
	private XCodeGen c;
	private String name;
	private String[] paramNames;
	private int kwParam;
	private int listParam;
	private int defStart;
	private XBaseScope scope;
	private XTreeCompiler comp;
	
	public XInstMakeFunc(int line, XOpcode opcode, XTree funcBody, String name, String[] paramNames, int kwParam, int listParam, int defStart, XBaseScope scope, XTreeCompiler comp) {
		super(line, opcode);
		this.funcBody = funcBody;
		this.name = name;
		this.paramNames = paramNames;
		this.kwParam = kwParam;
		this.listParam = listParam;
		this.defStart = defStart;
		this.scope = scope;
		this.comp = comp;
	}
	
	@Override
	public void compileSubparts() {
		c = new XCodeGen(1+paramNames.length);
		comp.compFuncBody(funcBody, scope).asStatement(comp, c);
	}

	@Override
	public void toCode(XDataOutput dataOutput) {
		super.toCode(dataOutput);
		if(name==null){
			dataOutput.writeUTF("<anonymous>");
		}else{
			dataOutput.writeUTF(name);
		}
		dataOutput.writeCode(c);
		if(paramNames.length>0xFF)
			throw new AssertionError();
		dataOutput.writeByte(paramNames.length);
		for(String s:paramNames){
			dataOutput.writeUTF(s);
		}
		dataOutput.writeByte(kwParam);
		dataOutput.writeByte(listParam);
		dataOutput.writeByte(defStart);
		List<XClosureVar> closures = scope.getClosures();
		dataOutput.writeByte(closures.size());
		for(XClosureVar closure:closures){
			if(closure.var instanceof XClosureVar){
				dataOutput.writeByte(0);
			}else{
				dataOutput.writeByte(1);
			}
			dataOutput.writeByte(closure.getPosition());
		}
	}

	@Override
	public String toString() {
		return super.toString()+" [name=" + name + ", paramNames="
				+ Arrays.toString(paramNames) + ", kwParam=" + kwParam
				+ ", listParam=" + listParam + ", defStart=" + defStart +", closures="+scope.getClosures()+"]";
	}
	
	@Override
	public int getSize() {
		return 10+paramNames.length*2+scope.getClosures().size()*2;
	}
	
}
