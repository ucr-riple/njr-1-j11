package xscript.values;

import xscript.object.XRuntime;

public class XValueAccess extends XValue {

	private final XValueObj value;

	public XValueAccess(XValueObj value){
		this.value = value;
	}
	
	@Override
	public XValue getType(XRuntime runtime) {
		return value.getType(runtime);
	}

	@Override
	public void setVisible(XRuntime runtime) {
		value.setVisible(runtime);
	}
	
	@Override
	public boolean noneZero() {
		return value.noneZero();
	}

	@Override
	public boolean equals(Object obj) {
		return value.equals(obj);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public Object toJava(XRuntime runtime) {
		return value.toJava(runtime);
	}

	@Override
	public boolean isObject() {
		return value.isObject();
	}
	
	public XValueObj getHolding() {
		return value;
	}

}
