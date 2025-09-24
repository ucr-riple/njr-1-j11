package xscript.values;

import xscript.object.XRuntime;

public final class XValueObjSuper extends XValue implements XContainer {

	private XValue value;
	
	private int type;
	
	public XValueObjSuper(XValue value, int type) {
		this.value = value;
		this.type = type;
	}
	
	public int getCastToType(){
		return type;
	}

	@Override
	public XValue getType(XRuntime runtime){
		return value.getType(runtime);
	}
	
	@Override
	public XValue incRef(XRuntime runtime) {
		value.incRef(runtime);
		return this;
	}

	@Override
	public boolean decRef(XRuntime runtime) {
		return value.decRef(runtime);
	}

	@Override
	public XValue incExtRef(XRuntime runtime) {
		value.incExtRef(runtime);
		return this;
	}

	@Override
	public boolean decExtRef(XRuntime runtime) {
		return value.decExtRef(runtime);
	}

	@Override
	public void setVisible(XRuntime runtime) {
		value.setVisible(runtime);
	}
	
	@Override
	public boolean noneZero() {
		return value.noneZero();
	}
	
	public XValue getRaw(XRuntime runtime, String attr){
		return value.getRaw(runtime, attr);
	}
	
	public XValue setRaw(XRuntime runtime, String attr, XValue value){
		return value.setRaw(runtime, attr, value);
	}
	
	public XValue delRaw(XRuntime runtime, String attr){
		return value.delRaw(runtime, attr);
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return value.equals(obj);
	}

	@Override
	public Object toJava(XRuntime runtime) {
		return value.toJava(runtime);
	}

	@Override
	public long getInt(){
		return value.getInt();
	}
	
	@Override
	public double getFloat(){
		return value.getFloat();
	}
	
	@Override
	public boolean getBool(){
		return value.getBool();
	}
	
	@Override
	public boolean isNumber() {
		return value.isNumber();
	}
	
	@Override
	public boolean isFloat() {
		return value.isFloat();
	}
	
	@Override
	public boolean isInt() {
		return value.isInt();
	}
	
	@Override
	public boolean isBool() {
		return value.isBool();
	}

	@Override
	public boolean isObject() {
		return value.isObject();
	}

	@Override
	public XValue getValue() {
		return value;
	}

	@Override
	public int intValue() {
		return value.intValue();
	}

	@Override
	public long longValue() {
		return value.longValue();
	}

	@Override
	public float floatValue() {
		return value.floatValue();
	}

	@Override
	public double doubleValue() {
		return value.doubleValue();
	}
	
}
