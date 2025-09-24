package xscript.values;

import xscript.XUtils;
import xscript.object.XRuntime;

public final class XValueBool extends XValueNumber {

	public static final XValueBool TRUE = new XValueBool(true);
	public static final XValueBool FALSE = new XValueBool(false);
	
	private final boolean value;
	
	public static XValueBool valueOf(boolean b){
		return b?TRUE:FALSE;
	}
	
	private XValueBool(boolean value){
		this.value = value;
	}
	
	public boolean getValue(){
		return value;
	}
	
	@Override
	public XValue getType(XRuntime runtime){
		return runtime.getBaseType(XUtils.BOOL);
	}
	
	@Override
	public long getInt() {
		return value?-1:0;
	}

	@Override
	public double getFloat() {
		return value?-1:0;
	}

	@Override
	public boolean getBool() {
		return value;
	}

	@Override
	public int hashCode() {
		return value ? 1262 : 1268;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof XValueBool))
			return false;
		XValueBool other = (XValueBool) obj;
		return value == other.value;
	}
	
	@Override
	public XValueBool clone(){
		return this;
	}

	@Override
	public String toString() {
		return this==TRUE?"true":"false";
	}
	
	@Override
	public Object toJava(XRuntime runtime) {
		return value;
	}
	
	@Override
	public boolean isBool() {
		return true;
	}

}
