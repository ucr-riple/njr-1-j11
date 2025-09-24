package xscript.object;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.List;
import java.util.Map;

import xscript.XExec;
import xscript.XUtils;
import xscript.values.XValue;

public class XTypeDataModule extends XTypeData {

private static final String[] ATTRIBUTES = {"__name__"};
	
	private static final String[] METHODS = {"__str__"};
	
	public static final XTypeDataFactory FACTORY = new XTypeDataFactory(){

		@Override
		protected XTypeData create(XRuntime runtime, XObject obj, String name, Object[] args) {
			return new XTypeDataModule(runtime, obj);
		}

		@Override
		protected XTypeData load(XRuntime runtime, XObject obj, String name, ObjectInput in) throws IOException {
			return new XTypeDataModule(runtime, obj);
		}
		
	};
	
	public XTypeDataModule(XRuntime runtime, XObject obj) {
		super(runtime, obj, "Module");
	}

	@Override
	public XObjectData loadData(XRuntime runtime, XObject obj, ObjectInput in) throws IOException {
		XValue constPool = XValue.read(in);
		String name = in.readUTF();
		return new XObjectDataModule(runtime, constPool, name);
	}

	@Override
	public XObjectData createData(XRuntime runtime, XObject obj, Object[] args) {
		XValue sys = runtime.getModule("sys");
		if(sys!=null){
			obj.setRaw("dir", sys.getRaw(runtime, "dir"));
			obj.setRaw("println", sys.getRaw(runtime, "println"));
			obj.setRaw("readln", sys.getRaw(runtime, "readln"));
			obj.setRaw("sleep", sys.getRaw(runtime, "sleep"));
			obj.setRaw("exit", sys.getRaw(runtime, "exit"));
			obj.setRaw("exec", sys.getRaw(runtime, "exec"));
			obj.setRaw("print", sys.getRaw(runtime, "print"));
			obj.setRaw("printerrln", sys.getRaw(runtime, "printerrln"));
			obj.setRaw("printerr", sys.getRaw(runtime, "printerr"));
		}
		return new XObjectDataModule(runtime, (XValue) args[0], (String)args[1]);
	}
	
	@Override
	public XValue getAttr(XRuntime runtime, XValue value, XObject obj, int attrID) {
		XObjectDataModule data = XUtils.getDataAs(runtime, value, XObjectDataModule.class);
		switch(attrID){
		case 0:
			return runtime.alloc(data.getName());
		}
		return super.getAttr(runtime, value, obj, attrID);
	}

	@Override
	public XValue setAttr(XRuntime runtime, XValue value, XObject obj, int attrID, XValue v) {
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
		XObjectDataModule data = XUtils.getDataAs(runtime, thiz, XObjectDataModule.class);
		switch(id){
		case 0:
			return runtime.alloc("module<"+data.getName()+">");
		}
		return super.invoke(runtime, exec, id, thiz, params, list, map);
	}
	
	@Override
	public String[] getMethods() {
		return METHODS;
	}
	
}
