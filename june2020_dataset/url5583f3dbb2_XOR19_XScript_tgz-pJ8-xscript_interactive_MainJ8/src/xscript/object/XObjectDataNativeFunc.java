package xscript.object;

import java.io.IOException;
import java.io.ObjectOutput;

public class XObjectDataNativeFunc implements XObjectData {

	private String name;
	
	private XFunctionData function;
	
	public XObjectDataNativeFunc(String name, XFunctionData function){
		if(function==null)
			throw new NullPointerException();
		this.name = name;
		this.function = function;
	}
	
	@Override
	public void delete(XRuntime runtime, boolean cleanup) {}

	@Override
	public void setVisible(XRuntime runtime) {}

	@Override
	public void save(ObjectOutput out) throws IOException {
		out.writeUTF(name);
	}

	public String getName(){
		return name;
	}
	
	public XFunction getFunction(){
		return function.function;
	}

	public String[] getParamNames() {
		return function.paramNames;
	}

	public int getDefStart() {
		return function.defaultStart;
	}

	public boolean getUseList() {
		return function.useList;
	}

	public boolean getUseMap() {
		return function.useMap;
	}
	
	@Override
	public Object toJava(XRuntime runtime, XObject object) {
		return null;
	}
	
}
