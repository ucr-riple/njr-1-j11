package com.github.lindenb.dataindexer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/** everything needed to 
 * read/write an object from/to a DataStream
 * @param <T> the type of object
 */
public interface TupleBinding<T>
	{
	/** write the object to the datastream */
	public void writeObject(final T o,DataOutputStream out) throws IOException;
	/** read a new object from the datastream */
	public T readObject(DataInputStream in) throws IOException;
	}
