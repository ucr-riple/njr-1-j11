package darwInvest.data.utility;

import java.io.File;
import java.io.Serializable;

/**
 * The Serializer interface should be implemented by any
 * object-serialization procedure, in the event we may
 * want to use something different in the future
 * Makes the assumption that everything is kosher to simplify
 * code at the expense of safety.
 * @author Kevin Dolan
 */
public interface Serializer {

	/**
	 * Persist an object to a file
	 * @param object the object to persist
	 * @param file	 the file to persist the object to
	 */
	public void writeObject(Serializable object, File file);
	
	/**
	 * Read an object from the file, asserts the file is
	 * the right format
	 * @param file the file to load
	 * @return	   the unserialized object
	 */
	public Object readObject(File file);
	
}
