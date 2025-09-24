package darwInvest.data.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DefaultSerializer implements Serializer {

	public Object readObject(File file) {
		Object result = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);
			result = in.readObject();
			in.close();
		}
		catch(IOException e) {} 
		catch (ClassNotFoundException e) {}
		return result;
	}

	public void writeObject(Serializable object, File file) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			file.createNewFile();
			fos = new FileOutputStream(file);
			
			out = new ObjectOutputStream(fos);
			out.writeObject(object);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
