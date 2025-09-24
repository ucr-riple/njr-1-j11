package xscript.object;

import java.io.IOException;
import java.io.ObjectOutput;

public interface XConstPool {

	public int getIntP(int index);
	
	public long getLongP(int index);
	
	public float getFloatP(int index);
	
	public double getDoubleP(int index);
	
	public String getStringP(int index);

	public byte[] getBytes(int index);

	public void save(ObjectOutput out) throws IOException;
	
}
