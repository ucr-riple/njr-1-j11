package xscript.values;

import xscript.XUtils;
import xscript.object.XRuntime;

public final class XValueNull extends XValue {

	public static final XValueNull NULL = new XValueNull();
	
	private XValueNull(){
		
	}

	@Override
	public XValue getType(XRuntime runtime){
		return runtime.getBaseType(XUtils.NULL);
	}

	@Override
	public boolean noneZero() {
		return false;
	}

	@Override
	public String toString() {
		return "null";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj==this;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public Object toJava(XRuntime runtime) {
		return null;
	}
	
}
