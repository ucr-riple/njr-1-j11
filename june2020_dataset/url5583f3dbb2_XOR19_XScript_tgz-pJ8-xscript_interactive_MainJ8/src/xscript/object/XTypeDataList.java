package xscript.object;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xscript.XExec;
import xscript.values.XValue;
import xscript.values.XValueBool;
import xscript.values.XValueInt;

public class XTypeDataList extends XTypeData {

	public static final XTypeDataFactory FACTORY = new XTypeDataFactory(){

		@Override
		protected XTypeData create(XRuntime runtime, XObject obj, String name, Object[] args) {
			return new XTypeDataList(runtime, obj);
		}

		@Override
		protected XTypeData load(XRuntime runtime, XObject obj, String name, ObjectInput in) throws IOException {
			return new XTypeDataList(runtime, obj);
		}
		
	};
	
	private static final String[] METHODS = {"__getIndex__(index)", "length", "__setIndex__(index,value)", "add(value,#index)", "removeAt(index)", "isEmpty"};
	
	public XTypeDataList(XRuntime runtime, XObject obj){
		super(runtime, obj, "List");
	}
	
	@Override
	public XObjectData loadData(XRuntime runtime, XObject obj, ObjectInput in) throws IOException {
		int size = in.readInt();
		List<XValue> list = new ArrayList<XValue>(size);
		while(size>0){
			list.add(XValue.read(in));
			size--;
		}
		return new XObjectDataList(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public XObjectData createData(XRuntime runtime, XObject obj, Object[] args) {
		return new XObjectDataList((List<XValue>)args[0]);
	}

	@Override
	public XValue invoke(XRuntime runtime, XExec exec, int id, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map){
		XObjectDataList l = (XObjectDataList)runtime.getObject(thiz).getData();
		switch(id){
		case 0:
			return getIndex(l, params[0]);
		case 1:
			return length(l);
		case 2:
			return setIndex(l, params[0], params[1]);
		case 3:
			return add(l, params[0], params[1]);
		case 4:
			return removeAt(l, params[0]);
		case 5:
			return XValueBool.valueOf(l.isEmpty());
		}
		return super.invoke(runtime, exec, id, thiz, params, list, map);
	}

	private XValue getIndex(XObjectDataList thiz, XValue index){
		return thiz.get((int)index.getInt());
	}
	
	private XValue length(XObjectDataList thiz){
		return XValueInt.valueOf(thiz.size());
	}
	
	private XValue setIndex(XObjectDataList thiz, XValue index, XValue value){
		thiz.set((int)index.getInt(), value);
		return value;
	}
	
	private XValue add(XObjectDataList thiz, XValue value, XValue index){
		if(index==null){
			thiz.add(value);
		}else{
			thiz.add((int) index.getInt(), value);
		}
		return value;
	}
	
	private XValue removeAt(XObjectDataList thiz, XValue index){
		return thiz.remove((int)index.getInt());
	}
	
	@Override
	public String[] getMethods() {
		return METHODS;
	}
	
}
