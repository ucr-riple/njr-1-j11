package xscript.object;

import java.io.IOException;
import java.io.ObjectOutput;

import xscript.XClosure;
import xscript.XUtils;
import xscript.values.XValue;

public class XObjectDataFunc implements XObjectData {
	
	private String name;
	
	private String[] paramNames;
	
	private int kwParam;
	
	private int listParam;
	
	private int defStart;
	
	private XValue def;
	
	private XValue module;
	
	private XValue constPool;
	
	private XValue clasz;
	
	private int index;
	
	private XClosure[] closures;
	
	public XObjectDataFunc(XRuntime rt, String name, String[] paramNames, int kwParam,
			int listParam, int defStart, XValue def, XValue module, XValue constPool, XValue clasz, int index,
			XClosure[] closures) {
		XUtils.check(rt, def, XUtils.TUPLE);
		XUtils.check(rt, module, XUtils.MODULE);
		XUtils.check(rt, constPool, XUtils.CONST_POOL);
		XUtils.check(rt, clasz, XUtils.TYPE);
		this.name = name;
		this.paramNames = paramNames;
		this.kwParam = kwParam;
		this.listParam = listParam;
		this.defStart = defStart;
		this.def = def;
		this.module = module;
		this.constPool = constPool;
		this.clasz = clasz;
		this.index = index;
		this.closures = closures;
	}

	@Override
	public void delete(XRuntime runtime, boolean cleanup) {
		for(XClosure closure:closures){
			closure.decRefs();
		}
	}

	@Override
	public void setVisible(XRuntime runtime) {
		def.setVisible(runtime);
		module.setVisible(runtime);
		constPool.setVisible(runtime);
		for(XClosure closure:closures){
			closure.setVisible(runtime);
		}
	}

	@Override
	public void save(ObjectOutput out) throws IOException {
		
	}

	public String getName() {
		return name;
	}
	
	public String[] getParamNames() {
		return paramNames;
	}

	public int getKwParam() {
		return kwParam;
	}

	public int getListParam() {
		return listParam;
	}

	public int getDefStart() {
		return defStart;
	}

	public XValue getDef() {
		return def;
	}

	public XConstPool getConstPool(XRuntime rt) {
		XObjectDataConstPool m = XUtils.getDataAs(rt, constPool, XObjectDataConstPool.class);
		return m.getConstPool();
	}

	public byte[] getInstructions(XRuntime rt) {
		return getConstPool(rt).getBytes(index);
	}

	public String getFileName(XRuntime rt) {
		//TODO
		return "";
	}

	public String getFullPath(XRuntime rt) {
		//TODO
		return "";
	}

	public XClosure[] getClosures(){
		return closures;
	}
	
	public XValue getModule(){
		return module;
	}

	public XValue getDeclaringClass() {
		return clasz;
	}

	public XValue getConstPool() {
		return constPool;
	}
	
	@Override
	public Object toJava(XRuntime runtime, XObject object) {
		return null;
	}
	
}
