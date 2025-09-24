package xscript.object;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.List;
import java.util.Map;

import xscript.XExec;
import xscript.XUtils;
import xscript.values.XValue;

public class XTypeDataType extends XTypeData {

	private static final String[] ATTRIBUTES = {"__bases__", "__cro__", "__name__"};
	
	private static final String[] METHODS = {"__new__()lm", "__str__"};
	
	public XTypeDataType(XRuntime runtime, XObject obj) {
		super(runtime, obj, "Type");
	}

	@Override
	public XObjectData loadData(XRuntime runtime, XObject obj, ObjectInput in) throws IOException {
		return XTypeDataFactory.loadData(runtime, obj, in);
	}

	@Override
	public XObjectData createData(XRuntime runtime, XObject obj, Object[] args) {
		return XTypeDataFactory.createData(runtime, obj, args);
	}

	@Override
	public XValue getAttr(XRuntime runtime, XValue value, XObject obj, int attrID) {
		XTypeData data = (XTypeData)runtime.getObject(value).getData();
		switch(attrID){
		case 0:
			return runtime.createTuple(data.getBases());
		case 1:
			return runtime.createTuple(data.getCRO());
		case 2:
			return runtime.alloc(data.getName());
		}
		return super.getAttr(runtime, value, obj, attrID);
	}

	@Override
	public XValue setAttr(XRuntime runtime, XValue value, XObject obj, int attrID, XValue v) {
		XTypeData data = (XTypeData)obj.getData();
		switch(attrID){
		case 0:
			return runtime.createTuple(data.setBases(runtime, XUtils.asList(runtime, v)));
		case 1:
		case 2:
			throw new UnsupportedOperationException();
		}
		return super.setAttr(runtime, value, obj, attrID, v);
	}

	@Override
	public XValue delAttr(XRuntime runtime, XValue value, XObject obj, int attrID) {
		return super.delAttr(runtime, value, obj, attrID);
	}
	
	@Override
	public String[] getAttributes() {
		return ATTRIBUTES;
	}

	@Override
	public XValue invoke(XRuntime runtime, XExec exec, int id, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) {
		XTypeData data = (XTypeData)runtime.getObject(thiz).getData();
		switch(id){
		case 0:
			return _new(runtime, thiz, list, map);
		case 1:
			return runtime.alloc(data.getName());
		}
		return super.invoke(runtime, exec, id, thiz, params, list, map);
	}
	
	public XValue _new(XRuntime runtime, XValue type, List<XValue> list, Map<String, XValue> map){
		XTypeData t = XUtils.getDataAs(runtime, type, XTypeData.class);
		return t.alloc(runtime, type, list, map);
	}

	@Override
	public String[] getMethods() {
		return METHODS;
	}
	
}
