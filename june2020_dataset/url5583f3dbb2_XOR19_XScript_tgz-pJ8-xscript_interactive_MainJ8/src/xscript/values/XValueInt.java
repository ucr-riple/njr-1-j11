package xscript.values;

import xscript.XUtils;
import xscript.object.XRuntime;

public final class XValueInt extends XValueNumber {

	private static final XValueInt[] cache = new XValueInt[5];
	private static final int MIN = -1;
	private static final int MAX = 3;
	
	private final long value;
	
	static{
		for(int i=0; i<cache.length; i++){
			cache[i] = new XValueInt(i+MIN);
		}
	}
	
	public static XValueInt valueOf(long l){
		if(l>=MIN && l<=MAX){
			return cache[(int) (l-MIN)];
		}
		return new XValueInt(l);
	}
	
	public XValueInt(long value){
		this.value = value;
	}
	
	public long getValue(){
		return value;
	}

	@Override
	public XValue getType(XRuntime runtime){
		return runtime.getBaseType(XUtils.INT);
	}
	
	@Override
	public long getInt() {
		return value;
	}

	@Override
	public double getFloat() {
		return value;
	}

	@Override
	public boolean getBool() {
		return value!=0;
	}

	@Override
	public int hashCode() {
		return 31 + (int) (value ^ (value >>> 32));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof XValueInt))
			return false;
		XValueInt other = (XValueInt) obj;
		return value == other.value;
	}
	
	@Override
	public String toString() {
		return Long.toString(value);
	}

	@Override
	public Object toJava(XRuntime runtime) {
		return value;
	}
	
	@Override
	public boolean isInt() {
		return true;
	}
	
}
