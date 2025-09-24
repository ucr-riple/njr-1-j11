package xscript.object;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.List;
import java.util.Map;

import xscript.XExec;
import xscript.values.XValue;

public class XTypeDataBool extends XTypeData {

	private static final String[] METHODS = {"__str__"};
	
	public static final XTypeDataFactory FACTORY = new XTypeDataFactory(){

		@Override
		protected XTypeData create(XRuntime runtime, XObject obj, String name, Object[] args) {
			return new XTypeDataBool(runtime, obj);
		}

		@Override
		protected XTypeData load(XRuntime runtime, XObject obj, String name, ObjectInput in) throws IOException {
			return new XTypeDataBool(runtime, obj);
		}
		
	};
	
	public XTypeDataBool(XRuntime runtime, XObject obj) {
		super(runtime, obj, "Bool");
	}

	@Override
	public XValue alloc(XRuntime runtime, XValue type, List<XValue> list, Map<String, XValue> map) {
		if(list.isEmpty()){
			return map.get("b");
		}
		return list.get(0);
	}
	
	@Override
	public XValue invoke(XRuntime runtime, XExec exec, int id, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) {
		switch(id){
		case 0:
			return runtime.alloc(thiz.toString());
		}
		return super.invoke(runtime, exec, id, thiz, params, list, map);
	}
	
	@Override
	public String[] getMethods() {
		return METHODS;
	}
	
}
