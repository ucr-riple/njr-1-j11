package xscript.object;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.List;
import java.util.Map;

import xscript.XExec;
import xscript.XUtils;
import xscript.values.XValue;

public class XTypeDataWeakRef extends XTypeData {

	public static final XTypeDataFactory FACTORY = new XTypeDataFactory(){

		@Override
		protected XTypeData create(XRuntime runtime, XObject obj, String name, Object[] args) {
			return new XTypeDataWeakRef(runtime, obj);
		}

		@Override
		protected XTypeData load(XRuntime runtime, XObject obj, String name, ObjectInput in) throws IOException {
			return new XTypeDataWeakRef(runtime, obj);
		}
		
	};
	
	private static final String[] METHODS = {"get", "__str__"};
	
	public XTypeDataWeakRef(XRuntime runtime, XObject obj) {
		super(runtime, obj, "WeakRef");
	}
	
	@Override
	public XValue alloc(XRuntime runtime, XValue type, List<XValue> list, Map<String, XValue> map) {
		XValue obj;
		if(list.isEmpty()){
			obj = map.get("obj");
		}else{
			obj = list.get(0);
		}
		XObject o = runtime.getObject(obj);
		if(o==null){
			return obj;
		}
		return o.getWeakRef(runtime);
	}

	@Override
	public XObjectData loadData(XRuntime runtime, XObject obj, ObjectInput in) throws IOException {
		XValue value = XValue.read(in);
		return new XObjectDataWeakRef(value);
	}

	@Override
	public XObjectData createData(XRuntime runtime, XObject obj, Object[] args) {
		return new XObjectDataWeakRef((XValue)args[0]);
	}

	@Override
	public XValue invoke(XRuntime runtime, XExec exec, int id, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map){
		XObjectDataWeakRef weakRef = XUtils.getDataAs(runtime, thiz, XObjectDataWeakRef.class);
		switch(id){
		case 0:
			return weakRef.getRef();
		case 1:
			return runtime.alloc("weakref<ref@"+Integer.toHexString(weakRef.getRef().hashCode())+">");
		}
		return super.invoke(runtime, exec, id, thiz, params, list, map);
	}
	
	@Override
	public String[] getMethods() {
		return METHODS;
	}
	
}
