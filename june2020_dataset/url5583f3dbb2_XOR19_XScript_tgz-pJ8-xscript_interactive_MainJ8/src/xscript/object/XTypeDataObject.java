package xscript.object;

import java.util.List;
import java.util.Map;

import xscript.XExec;
import xscript.XUtils;
import xscript.values.XValue;
import xscript.values.XValueBool;


public class XTypeDataObject extends XTypeData {
	
	private static final String[] ATTRIBUTES = {"__type__"};
	
	private static final String[] METHODS = {"__weakref__", "__hasAttr__(attr)", "__str__"};
	
	public XTypeDataObject(XRuntime runtime, XObject obj) {
		super(runtime, obj);
	}

	@Override
	public XValue getAttr(XRuntime runtime, XValue value, XObject obj, int attrID) {
		switch(attrID){
		case 0:
			return value.getType(runtime);
		}
		return super.getAttr(runtime, value, obj, attrID);
	}

	@Override
	public XValue setAttr(XRuntime runtime, XValue value, XObject obj, int attrID, XValue v) {
		switch(attrID){
		case 0:
			return obj.setType(runtime, v);
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
		XObject obj = runtime.getObject(thiz);
		switch(id){
		case 0:
			return obj.getWeakRef(runtime);
		case 1:
			return hasAttr(runtime, thiz, params[0]);
		case 2:
			return asString(runtime, obj, thiz);
		}
		return super.invoke(runtime, exec, id, thiz, params, list, map);
	}

	private XValue asString(XRuntime runtime, XObject obj, XValue value){
		String s = XUtils.getDataAs(runtime, obj.getType(), XTypeData.class).getName();
		s+="@"+Integer.toHexString(value.hashCode());
		return runtime.alloc(s);
	}
	
	private XValue hasAttr(XRuntime runtime, XValue value, XValue attr){
		String a = XUtils.getString(runtime, attr);
		XValue v = XUtils.lookupTry(runtime, value, a, XValue.REF_NONE);
		return v==null?XValueBool.FALSE:XValueBool.TRUE;
	}
	
	@Override
	public String[] getMethods() {
		return METHODS;
	}
	
}
