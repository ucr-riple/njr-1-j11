package xscript.object;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xscript.XExec;
import xscript.values.XValue;
import xscript.values.XValueInt;

public class XTypeDataTuple extends XTypeData {

	public static final XTypeDataFactory FACTORY = new XTypeDataFactory(){

		@Override
		protected XTypeData create(XRuntime runtime, XObject obj, String name, Object[] args) {
			return new XTypeDataTuple(runtime, obj);
		}

		@Override
		protected XTypeData load(XRuntime runtime, XObject obj, String name, ObjectInput in) throws IOException {
			return new XTypeDataTuple(runtime, obj);
		}
		
	};
	
	private static final String[] METHODS = {"__getIndex__(index)", "length"};
	
	public XTypeDataTuple(XRuntime runtime, XObject obj) {
		super(runtime, obj, "Tuple");
	}
	
	@Override
	public XObjectData loadData(XRuntime runtime, XObject obj, ObjectInput in) throws IOException {
		int size = in.readInt();
		List<XValue> list = new ArrayList<XValue>(size);
		while(size>0){
			list.add(XValue.read(in));
			size--;
		}
		return new XObjectDataTuple(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public XObjectData createData(XRuntime runtime, XObject obj, Object[] args) {
		return new XObjectDataTuple((List<XValue>)args[0]);
	}
	
	@Override
	public XValue invoke(XRuntime runtime, XExec exec, int id, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map){
		XObjectDataTuple tuple = (XObjectDataTuple)runtime.getObject(thiz).getData();
		switch(id){
		case 0:
			return getIndex(tuple, params[0]);
		case 1:
			return length(tuple);
		}
		return super.invoke(runtime, exec, id, thiz, params, list, map);
	}

	private XValue getIndex(XObjectDataTuple thiz, XValue index){
		return thiz.get((int)index.getInt());
	}
	
	private XValue length(XObjectDataTuple thiz){
		return XValueInt.valueOf(thiz.size());
	}
	
	@Override
	public String[] getMethods(){
		return METHODS;
	}
	
}
