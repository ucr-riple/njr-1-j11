package xscript.values;

import xscript.object.XRuntime;

public final class XValueObj extends XValue {

	private final int pointer;
	
	public XValueObj(int pointer){
		this.pointer = pointer;
	}
	
	public int getPointer(){
		return pointer;
	}
	
	@Override
	public XValue getType(XRuntime runtime){
		return runtime.getObject(pointer).getType();
	}
	
	@Override
	public XValue incRef(XRuntime runtime) {
		runtime.getObject(pointer).incRef(runtime);
		return this;
	}

	@Override
	public boolean decRef(XRuntime runtime) {
		return runtime.getObject(pointer).decRef(runtime);
	}

	@Override
	public XValue incExtRef(XRuntime runtime) {
		runtime.getObject(pointer).incExtRef(runtime);
		return this;
	}

	@Override
	public boolean decExtRef(XRuntime runtime) {
		return runtime.getObject(pointer).decExtRef(runtime);
	}

	@Override
	public void setVisible(XRuntime runtime) {
		runtime.getObject(pointer).setVisible(runtime);
	}
	
	@Override
	public boolean noneZero() {
		return true;
	}
	
	public XValue getRaw(XRuntime runtime, String attr){
		return runtime.getObject(pointer).getRaw(attr);
	}
	
	public XValue setRaw(XRuntime runtime, String attr, XValue value){
		return runtime.getObject(pointer).setRaw(attr, value);
	}
	
	public XValue delRaw(XRuntime runtime, String attr){
		return runtime.getObject(pointer).delRaw(attr);
	}
	
	@Override
	public String toString() {
		return "@"+pointer;
	}
	
	@Override
	public int hashCode() {
		return 31 + (pointer^2189041);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof XValueObj))
			return false;
		XValueObj other = (XValueObj) obj;
		return pointer == other.pointer;
	}

	@Override
	public Object toJava(XRuntime runtime) {
		return runtime.getObject(pointer).toJava(runtime);
	}
	
	@Override
	public boolean isObject() {
		return true;
	}

	@Override
	public long getInt() {
		return pointer;
	}
	
}
