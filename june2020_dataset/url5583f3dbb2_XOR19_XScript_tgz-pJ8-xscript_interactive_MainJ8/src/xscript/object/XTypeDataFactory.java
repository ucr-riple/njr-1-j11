package xscript.object;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.HashMap;
import java.util.List;

import xscript.XUtils;
import xscript.values.XValue;
import xscript.values.XValueNull;

public abstract class XTypeDataFactory {

	private static final HashMap<String, XTypeDataFactory> factories = new HashMap<String, XTypeDataFactory>();
	
	static{
		
	}
	
	static XTypeData createData(XRuntime runtime, XObject obj, Object[] args) {
		if(args[0] instanceof XTypeDataFactory){
			return ((XTypeDataFactory)args[0]).create(runtime, obj, null, args);
		}
		String name = (String)args[0];
		XTypeDataFactory factory = factories.get(name);
		if(factory==null){
			XValue base = (XValue)args[1];
			if(base==XValueNull.NULL){
				return new XTypeData(runtime, obj, name);
			}else if(XUtils.isInstanceOf(runtime, base, runtime.getBaseType(XUtils.TYPE))){
				return new XTypeData(runtime, obj, name, (XValue)args[1]);
			}else{
				List<XValue> bases = XUtils.asList(runtime, base);
				return new XTypeData(runtime, obj, name, bases);
			}
		}
		return factory.create(runtime, obj, name, args);
	}

	static XObjectData loadData(XRuntime runtime, XObject obj, ObjectInput in) throws IOException {
		String name = in.readUTF();
		XTypeDataFactory factory = factories.get(name);
		if(factory==null){
			return new XTypeData(runtime, obj, name, in);
		}
		return factory.load(runtime, obj, name, in);
	}
	
	protected abstract XTypeData create(XRuntime runtime, XObject obj, String name, Object[] args);
	
	protected abstract XTypeData load(XRuntime runtime, XObject obj, String name, ObjectInput in) throws IOException;

}
