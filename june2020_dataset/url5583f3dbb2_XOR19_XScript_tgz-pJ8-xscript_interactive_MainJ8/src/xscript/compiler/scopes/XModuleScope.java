package xscript.compiler.scopes;

import java.util.HashMap;
import java.util.Map;

import xscript.compiler.XGlobal;
import xscript.compiler.XJump;
import xscript.compiler.XVar;
import xscript.compiler.tree.XTree;


public class XModuleScope extends XBaseScope{

	private Map<String, XGlobal> globals = new HashMap<String, XGlobal>();
	
	public XModuleScope(){
		globals = new HashMap<String, XGlobal>();
	}
	
	private XModuleScope(XModuleScope thiz){
		super(thiz);
		globals = thiz.globals;
	}
	
	protected XVar create(XTree t, String name){
		return new XVar(t, name);
	}
	
	public XGlobal getOrCreateGlobal(XTree t, String name){
		XGlobal var = globals.get(name);
		if(var!=null)
			return var;
		var = new XGlobal(t, name);
		globals.put(name, var);
		return var;
	}
	
	public boolean getJump(String label, int type, XJump jump) {
		XScope scope = parent;
		while(scope!=null){
			jump.addPops(scope.pops);
			if(scope.getJump(label, type, jump)){
				return true;
			}
			if(scope == base){
				break;
			}
			scope = scope.parent;
		}
		return false;
	}
	
	public int getLocalsCount() {
		return 0;
	}
	
	public XScope lock() {
		return new XModuleScope(this);
	}
	
}
