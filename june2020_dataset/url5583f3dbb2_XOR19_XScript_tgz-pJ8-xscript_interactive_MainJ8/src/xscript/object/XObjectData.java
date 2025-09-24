package xscript.object;

import java.io.IOException;
import java.io.ObjectOutput;

public interface XObjectData {
	
	void delete(XRuntime runtime, boolean cleanup);

	void setVisible(XRuntime runtime);

	void save(ObjectOutput out) throws IOException;

	Object toJava(XRuntime runtime, XObject object);
	
}
