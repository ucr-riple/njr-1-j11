package xscript.object;

import java.io.IOException;
import java.io.ObjectOutput;

public class XObjectDataString implements XObjectData {

	private String string;
	
	public XObjectDataString(String string){
		this.string = string;
	}
	
	@Override
	public void delete(XRuntime runtime, boolean cleanup) {}

	@Override
	public void setVisible(XRuntime runtime) {}

	@Override
	public void save(ObjectOutput out) throws IOException {
		out.writeUTF(string);
	}
	
	public String getString(){
		return string;
	}
	
	@Override
	public Object toJava(XRuntime runtime, XObject object) {
		return string;
	}

}
