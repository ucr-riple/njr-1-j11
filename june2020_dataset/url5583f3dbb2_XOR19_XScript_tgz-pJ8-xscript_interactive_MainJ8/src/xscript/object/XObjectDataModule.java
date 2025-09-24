package xscript.object;

import java.io.IOException;
import java.io.ObjectOutput;

import xscript.XUtils;
import xscript.values.XValue;

public class XObjectDataModule implements XObjectData {

	private XValue constPool;

	private String name;

	public XObjectDataModule(XRuntime rt, XValue constPool, String name) {
		XUtils.check(rt, constPool, XUtils.CONST_POOL);
		this.constPool = constPool;
		this.name = name;
	}

	@Override
	public void delete(XRuntime runtime, boolean cleanup) {
	}

	@Override
	public void setVisible(XRuntime runtime) {
	}

	@Override
	public void save(ObjectOutput out) throws IOException {
		XValue.write(out, constPool);
	}

	public XValue getConstPool() {
		return constPool;
	}

	public String getName() {
		return name;
	}

	@Override
	public Object toJava(XRuntime runtime, XObject object) {
		return null;
	}

}
