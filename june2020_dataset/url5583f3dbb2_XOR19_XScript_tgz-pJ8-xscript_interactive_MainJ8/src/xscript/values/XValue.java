package xscript.values;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.Serializable;

import xscript.object.XRuntime;

public abstract class XValue extends Number implements Serializable, Cloneable {

	public static final int REF_NONE = 0;
	public static final int REF_INTERN = 1;
	public static final int REF_EXTERN = 2;
	
	public static XValue incRef(XRuntime runtime, XValue value, int addRef){
		if(value==null)
			return null;
		switch(addRef){
		case REF_INTERN:
			break;
		case REF_EXTERN:
			value.incExtRef(runtime);
		default:
			value.decRef(runtime);
		}
		return value;
	}
	
	public static XValue incRefNN(XRuntime runtime, XValue value, int addRef){
		switch(addRef){
		case REF_INTERN:
			break;
		case REF_EXTERN:
			value.incExtRef(runtime);
		default:
			value.decRef(runtime);
		}
		return value;
	}
	
	public static XValue unpackContainer(XValue value){
		while(value instanceof XContainer){
			value = ((XContainer)value).getValue();
		}
		return value;
	}
	
	public abstract XValue getType(XRuntime runtime);
	
	public XValue incRef(XRuntime runtime){
		return this;
	}
	
	public boolean decRef(XRuntime runtime){
		return true;
	}
	
	public XValue incExtRef(XRuntime runtime){
		return this;
	}
	
	public boolean decExtRef(XRuntime runtime){
		return true;
	}
	
	public void setVisible(XRuntime runtime){}
	
	public abstract boolean noneZero();
	
	@Override
	public abstract boolean equals(Object obj);
	
	@Override
	public abstract int hashCode();
	
	@Override
	public XValue clone(){
		return this;
	}
	
	public abstract Object toJava(XRuntime runtime);
	
	public XValue getRaw(XRuntime runtime, String attr){
		return null;
	}
	
	public XValue setRaw(XRuntime runtime, String attr, XValue value){
		return null;
	}
	
	public XValue delRaw(XRuntime runtime, String attr){
		return null;
	}
	
	private Object writeReplace() throws ObjectStreamException {
		return new XValueSerialized(this);
	}
	
	public static void write(ObjectOutput out, XValue value) throws IOException{
		new XValueSerialized(value).writeExternal(out);
	}
	
	public static XValue read(ObjectInput in) throws IOException {
		XValueSerialized serialized = new XValueSerialized();
		serialized.readExternal(in);
		return serialized.getValue();
	}

	public long getInt(){
		return 1;
	}
	
	public double getFloat(){
		return 1;
	}
	
	public boolean getBool(){
		return true;
	}
	
	public boolean isNumber() {
		return false;
	}
	
	public boolean isFloat() {
		return false;
	}
	
	public boolean isInt() {
		return false;
	}
	
	public boolean isBool() {
		return false;
	}

	public boolean isObject() {
		return false;
	}

	@Override
	public int intValue() {
		return (int)getInt();
	}

	@Override
	public long longValue() {
		return getInt();
	}

	@Override
	public float floatValue() {
		return (float)doubleValue();
	}

	@Override
	public double doubleValue() {
		return getFloat();
	}
	
}
