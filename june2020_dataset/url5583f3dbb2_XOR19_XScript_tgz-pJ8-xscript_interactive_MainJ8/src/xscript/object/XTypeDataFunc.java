package xscript.object;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.List;
import java.util.Map;

import xscript.XClosure;
import xscript.XExec;
import xscript.XUtils;
import xscript.values.XValue;

public class XTypeDataFunc extends XTypeData {

	private static final String[] METHODS = {"__str__"};
	
	public static final XTypeDataFactory FACTORY = new XTypeDataFactory(){

		@Override
		protected XTypeData create(XRuntime runtime, XObject obj, String name, Object[] args) {
			return new XTypeDataFunc(runtime, obj);
		}

		@Override
		protected XTypeData load(XRuntime runtime, XObject obj, String name, ObjectInput in) throws IOException {
			return new XTypeDataFunc(runtime, obj);
		}
		
	};
	
	public XTypeDataFunc(XRuntime runtime, XObject obj) {
		super(runtime, obj, "Func");
	}

	@Override
	public XObjectData loadData(XRuntime runtime, XObject obj, ObjectInput in) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XObjectData createData(XRuntime runtime, XObject obj, Object[] args) {
		// TODO Auto-generated method stub
		return new XObjectDataFunc(runtime, (String)args[0], (String[])args[1], (Integer)args[2], (Integer)args[3], (Integer)args[4], (XValue)args[5], (XValue)args[6], (XValue)args[7], (XValue)args[8], (Integer)args[9], (XClosure[])args[10]);
	}

	@Override
	public XValue getAttr(XRuntime runtime, XValue value, XObject obj, int attrID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XValue setAttr(XRuntime runtime, XValue value, XObject obj, int attrID, XValue v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XValue delAttr(XRuntime runtime, XValue value, XObject obj, int attrID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public XValue invoke(XRuntime runtime, XExec exec, int id, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) {
		XObjectDataFunc data = XUtils.getDataAs(runtime, thiz, XObjectDataFunc.class);
		switch(id){
		case 0:
			return asString(runtime, data, thiz);
		}
		return super.invoke(runtime, exec, id, thiz, params, list, map);
	}
	
	private XValue asString(XRuntime runtime, XObjectDataFunc data, XValue thiz){
		String s = "method<"+data.getName()+"(";
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
