package xscript.object;

import java.io.IOException;
import java.io.ObjectOutput;

import xscript.values.XValue;
import xscript.values.XValueNull;

public class XObjectDataWeakRef implements XObjectData {
	
	private XValue value;
	
	public XObjectDataWeakRef(XValue value){
		this.value = value;
	}
	
	@Override
	public void delete(XRuntime runtime, boolean cleanup) {
		XObject obj = runtime.getObject(value);
		if(obj!=null){
			obj.releaseWeakRef();
		}
	}

	@Override
	public void setVisible(XRuntime runtime) {}

	@Override
	public void save(ObjectOutput out) throws IOException {
		XValue.write(out, value);
	}

	public XValue getRef(){
		return value;
	}

	public void release() {
		value = XValueNull.NULL;
	}
	
	@Override
	public Object toJava(XRuntime runtime, XObject object) {
		return value.toJava(runtime);
	}
	
}
