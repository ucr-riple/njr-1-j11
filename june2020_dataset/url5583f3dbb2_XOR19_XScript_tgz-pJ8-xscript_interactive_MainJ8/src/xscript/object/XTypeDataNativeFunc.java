package xscript.object;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.List;
import java.util.Map;

import xscript.XExec;
import xscript.XUtils;
import xscript.values.XValue;

public class XTypeDataNativeFunc extends XTypeData {

	private static final String[] METHODS = {"__str__"};
	
	public XTypeDataNativeFunc(XRuntime runtime, XObject obj) {
		super(runtime, obj, "NativeFunc");
	}

	@Override
	public XObjectData loadData(XRuntime runtime, XObject obj, ObjectInput in) throws IOException {
		String name = in.readUTF();
		XFunctionData function = runtime.getFunction(name);
		return new XObjectDataNativeFunc(name, function);
	}

	@Override
	public XObjectData createData(XRuntime runtime, XObject obj, Object[] args) {
		String name = (String)args[0];
		XFunctionData function = runtime.getFunction(name);
		return new XObjectDataNativeFunc(name, function);
	}

	@Override
	public XValue invoke(XRuntime runtime, XExec exec, int id, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) {
		XObjectDataNativeFunc data = XUtils.getDataAs(runtime, thiz, XObjectDataNativeFunc.class);
		switch(id){
		case 0:
			return asString(runtime, data, thiz);
		}
		return super.invoke(runtime, exec, id, thiz, params, list, map);
	}
	
	private XValue asString(XRuntime runtime, XObjectDataNativeFunc data, XValue thiz){
		String s = "nativemethod<"+data.getName()+"(";
		String[] params = data.getParamNames();
		if(params.length>0){
			s+= params[0];
			for(int i=1; i<params.length; i++){
				s+=", "+params[i];
			}
		}
		return runtime.alloc(s+")@"+Integer.toHexString(thiz.hashCode())+">");
	}

	@Override
	public String[] getMethods() {
		return METHODS;
	}
	
}
