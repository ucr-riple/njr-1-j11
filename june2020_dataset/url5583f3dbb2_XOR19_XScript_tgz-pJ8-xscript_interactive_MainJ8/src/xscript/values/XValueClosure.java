package xscript.values;

import xscript.XClosure;
import xscript.object.XRuntime;

public class XValueClosure extends XValue implements XContainer{

	public final int stackptr;
	
	public final XClosure closure;
	
	public XValue value;
	
	public XValueClosure(int stackptr, XValue value, XClosure closure) {
		this.stackptr = stackptr;
		this.value = value;
		this.closure = closure;
	}

	@Override
	public XValue getType(XRuntime runtime) {
		return value.getType(runtime);
	}

	@Override
	public XValue incRef(XRuntime runtime) {
		return value.incRef(runtime);
	}

	@Override
	public boolean decRef(XRuntime runtime) {
		return value.decRef(runtime);
	}

	@Override
	public XValue incExtRef(XRuntime runtime) {
		return value.incExtRef(runtime);
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

	@Override
	public boolean equals(Object obj) {
		return value.equals(obj);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public XValue clone() {
		return value.clone();
	}

	@Override
	public Object toJava(XRuntime runtime) {
		return value.toJava(runtime);
	}

	@Override
	public XValue getRaw(XRuntime runtime, String attr) {
		return value.getRaw(runtime, attr);
	}

	@Override
	public XValue setRaw(XRuntime runtime, String attr, XValue value) {
		return value.setRaw(runtime, attr, value);
	}

	@Override
	public XValue delRaw(XRuntime runtime, String attr) {
		return value.delRaw(runtime, attr);
	}

	@Override
	public long getInt() {
		return value.getInt();
	}

	@Override
	public double getFloat() {
		return value.getFloat();
	}

	@Override
	public boolean getBool() {
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
	public boolean isObject() {
		return value.isObject();
	}

	@Override
	public XValue getValue() {
		return value;
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
