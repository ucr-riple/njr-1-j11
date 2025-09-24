package xscript.compiler;


public class XClosureVar extends XVar {
	
	public XVar base;
	public XVar var;

	public XClosureVar(XVar var) {
		super(var.t, var.name);
		this.var = var;
		var.usedInClosure = true;
		if(var instanceof XClosureVar){
			base = ((XClosureVar)var).base;
		}else{
			base = var;
		}
	}

	public int getPosition(){
		return var.position;
	}

	@Override
	public String toString() {
		return "XClosureVar [var=" + var + "]";
	}
	
}
