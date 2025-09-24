package xscript.object;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xscript.XExec;
import xscript.XUtils;
import xscript.values.XValue;
import xscript.values.XValueInt;
import xscript.values.XValueNull;

public class XTypeDataMap extends XTypeData {

	public static final XTypeDataFactory FACTORY = new XTypeDataFactory(){

		@Override
		protected XTypeData create(XRuntime runtime, XObject obj, String name, Object[] args) {
			return new XTypeDataMap(runtime, obj);
		}

		@Override
		protected XTypeData load(XRuntime runtime, XObject obj, String name, ObjectInput in) throws IOException {
			return new XTypeDataMap(runtime, obj);
		}
		
	};
	
	private static final String[] METHODS = {"__getIndex__(index)", "length", "__setIndex__(index,value)", "__delIndex__(index)", "__getItem__(item)", "keys"};
	
	public XTypeDataMap(XRuntime runtime, XObject obj){
		super(runtime, obj, "Map");
	}
	
	@Override
	public XObjectData loadData(XRuntime runtime, XObject obj, ObjectInput in) throws IOException {
		int size = in.readInt();
		Map<String, XValue> map = new HashMap<String, XValue>();
		while(size>0){
			String key = in.readUTF();
			XValue value = XValue.read(in);
			map.put(key, value);
			size--;
		}
		return new XObjectDataMap(map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public XObjectData createData(XRuntime runtime, XObject obj, Object[] args) {
		return new XObjectDataMap((Map<String, XValue>)args[0]);
	}

	@Override
	public XValue invoke(XRuntime runtime, XExec exec, int id, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map){
		XObjectDataMap m = (XObjectDataMap)runtime.getObject(thiz).getData();
		switch(id){
		case 0:
			return getIndex(runtime, m, params[0]);
		case 1:
			return length(m);
		case 2:
			return setIndex(runtime, m, params[0], params[1]);
		case 3:
			return delIndex(runtime, m, params[0]);
		case 4:
			return getItem(runtime, m, thiz, params[0]);
		case 5:
			return getKeys(runtime, m);
		}
		return super.invoke(runtime, exec, id, thiz, params, list, map);
	}

	private XValue getKeys(XRuntime runtime, XObjectDataMap m) {
		List<XValue> list = new ArrayList<XValue>(m.size());
		for(String s:m.keySet()){
			list.add(runtime.alloc(s));
		}
		return runtime.createTuple(list);
	}

	private XValue getIndex(XRuntime rt, XObjectDataMap thiz, XValue index){
		return thiz.get(XUtils.getString(rt, index));
	}
	
	private XValue length(XObjectDataMap thiz){
		return XValueInt.valueOf(thiz.size());
	}
	
	private XValue setIndex(XRuntime rt, XObjectDataMap thiz, XValue index, XValue value){
		thiz.put(XUtils.getString(rt, index), value);
		return value;
	}
	
	private XValue getItem(XRuntime rt, XObjectDataMap thiz, XValue obj, XValue index){
		String attr = XUtils.getString(rt, index);
		XValue v = XUtils.lookupTry(rt, obj, attr, XValue.REF_NONE);
		if(v!=null)
			return v;
		return thiz.get(attr);
	}
	
	private XValue delIndex(XRuntime rt, XObjectDataMap thiz, XValue index){
		thiz.remove(XUtils.getString(rt, index));
		return XValueNull.NULL;
	}

	@Override
	public String[] getMethods() {
		return METHODS;
	}
	
}
