package xscript.compiler;

public class XVarUse {

	private static final Object UNKNOWN_STATE = new Object();
	
	private static final Object UNKNOWN_STATE_CHANGEABLE = new Object();
	
	private XVar var;
	
	private Object _const;
	
	private boolean initialized;
	
	//private XVarWrite lastWrite;
	
	//private int reads;
	
	public XVarUse(XVar var){
		this.var = var;
	}
	
	public XVarUse(XVarUse outer){
		this.var = outer.var;
		this._const = outer._const;
		this.initialized = outer.initialized;
	}
	
	public void setConst(Object _const){
		this.initialized = true;
		if(this._const!=UNKNOWN_STATE_CHANGEABLE)
			this._const = _const;
	}
	
	public void setUnknownStateChangeable(){
		//this.lastWrite = null;
		this._const = UNKNOWN_STATE_CHANGEABLE;
	}
	
	public void setUnknownState(){
		initialized = true;
		this._const = UNKNOWN_STATE;
	}
	
	public boolean isConst(){
		return _const!=UNKNOWN_STATE&&_const!=UNKNOWN_STATE_CHANGEABLE;
	}
	
	public Object getConst(){
		return _const==UNKNOWN_STATE||_const==UNKNOWN_STATE_CHANGEABLE?null:_const;
	}
	
	public boolean isInitialized(){
		return initialized;
	}
	
	public void combine(XVarUse use){
		
	}
	
	public void combine(XVarUse use1, XVarUse use2){
		initialized = use1.initialized && use2.initialized;
	}
	
}
