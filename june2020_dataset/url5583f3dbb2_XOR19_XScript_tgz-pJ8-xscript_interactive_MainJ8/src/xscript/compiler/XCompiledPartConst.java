package xscript.compiler;

import xscript.compiler.inst.XInstConst;
import xscript.compiler.tree.XTree;


public class XCompiledPartConst extends XCompiledPart {
	
	private Object _const;
	
	public XCompiledPartConst(XTree t, Object _const){
		super(t);
		this._const = _const;
	}
	
	@Override
	public XCompiledPartConst get(XTreeCompiler compiler, XCodeGen codeGen){
		codeGen.addInstruction(new XInstConst(t.position.position.line, _const));
		return this;
	}
	
	@Override
	public boolean isConstValue() {
		return true;
	}

	@Override
	public Object getConst(){
		return _const;
	}

}
