/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.primitives;

import java.io.*;

/**
 * Primitive is an interface for any sort of primitive value that klang can support 
 * in reading and writing values to/from disk and string.
 * @author Jim Halliday
 */
public interface Primitive {
	
	/**
	 * Write this value to the specified file, at the current file pointer.
	 * @param raf the file object to write the value to
	 * @throws IOException if the file cannot be written to properly
	 * @throws BadValueException if the value was never initialized
	 */
	public void writeValueToFile(RandomAccessFile raf) throws IOException, BadValueException;
	
	/**
	 * Set the value for this object from the specified file pointer.
	 * @param raf the file object to read the value from
	 * @throws IOException if the file cannot be read properly
	 * @throws BadValueException if the value is out of range
	 */
	public void setValueFromFile(RandomAccessFile raf) throws IOException, BadValueException;
	
	/**
	 * Write the current value of this object to a string.
	 * @return a string representation of this value
	 * @throws BadValueException if the value was never initialized
	 */
	public String writeValueToString() throws BadValueException;
	
	/**
	 * Set the value for this object from the specified string.
	 * @param s the string to read; NOTE: If string is null,
	 * 		this should set the value of the primitive back to null
	 * @throws BadValueException if the string cannot be parsed into a value that 
	 * is acceptable and within range for this object's type
	 */
	public void setValueFromString(String s) throws BadValueException;
	
	/**
	 * Retrieve a text-based description of this primitive.
	 * @return a text-based description of this primitive
	 */
	public String getPrimitiveDescription();
	
	/**
	 * This is the way to tell if a primitive has been initialized with a value or not.
	 * @return true if a primitive value exists, and false otherwise
	 */
	public boolean valueExists();
	
}
