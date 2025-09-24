package xscript.values;

import xscript.XUtils;
import xscript.object.XRuntime;

public class XValueFloat extends XValueNumber {

	private final double value;
	
	public XValueFloat(double value){
		this.value = value;
	}
	
	public double getValue(){
		return value;
	}

	@Override
	public XValue getType(XRuntime runtime){
		return runtime.getBaseType(XUtils.FLOAT);
	}
	
	@Override
	public long getInt() {
		return (long) value;
	}

	@Override
	public double getFloat() {
		return value;
	}

	@Override
	public boolean getBool() {
		return value!=0;
	}
	
	public boolean isFloat() {
		return true;
	}

	@Override
	public String toString() {
		return Double.toString(value);
	}
	
	@Override
	public int hashCode() {
		long temp = Double.doubleToLongBits(value);
		return 31 + (int) (temp ^ (temp >>> 32));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof XValueFloat))
			return false;
		XValueFloat other = (XValueFloat) obj;
		return value == other.value;
	}

	@Override
	public Object toJava(XRuntime runtime) {
		return value;
	}
	
}
