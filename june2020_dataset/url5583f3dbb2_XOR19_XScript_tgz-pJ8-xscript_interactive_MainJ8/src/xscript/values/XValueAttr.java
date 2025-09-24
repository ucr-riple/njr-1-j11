package xscript.values;

import xscript.object.XRuntime;

public class XValueAttr extends XValue {

	private final int attribute;

	public XValueAttr(int attribute){
		this.attribute = attribute;
	}
	
	@Override
	public XValue getType(XRuntime runtime) {
		return null;
	}

	@Override
	public boolean noneZero() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		return this==obj;
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	@Override
	public Object toJava(XRuntime runtime) {
		throw new UnsupportedOperationException();
	}

	public int getAttrID() {
		return attribute;
	}

}
