package xscript.object;

import java.io.IOException;
import java.io.ObjectOutput;

public class XObjectDataConstPool implements XObjectData {
	
	private XConstPool constPool;
	
	public XObjectDataConstPool(XConstPool constPool){
		if(constPool==null)
			throw new NullPointerException();
		this.constPool = constPool;
	}
	
	@Override
	public void delete(XRuntime runtime, boolean cleanup) {}

	@Override
	public void setVisible(XRuntime runtime) {}

	@Override
	public void save(ObjectOutput out) throws IOException {
		constPool.save(out);
	}

	public XConstPool getConstPool(){
		return constPool;
	}

	@Override
	public Object toJava(XRuntime runtime, XObject object) {
		return null;
	}
	
}
