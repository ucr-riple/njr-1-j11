/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.primitives;

import java.io.*;
import java.util.*;
import malictus.klang.KlangConstants;

/**
 * Primitive representing a Microsoft GUID (UUID). 
 * 
 * NOTE: At the current time, this implementation does not seem to work. At least
 * GUID's read from file using this method for extended WAV fmt chunks don't
 * match the expected values.
 * 
 * @author Jim Halliday
 */
public class GUID implements Primitive, PrimitiveFixedByte {
	
	private UUID value;
	
	/**
	 * Empty constructor. The actual value must be initialized later, 
	 * before the class will return a valid value.
	 */
	public GUID() { }
	
	/**
	 * Constructor from a file reference. The value will be read from the file at
	 * the current file pointer, and the file pointer will be advanced to 
	 * just beyond the value.
	 * @param raf a RandomAccessFile object that points to a valid file, queued for reading
	 * @throws IOException if the file cannot be read properly
	 * @throws BadValueException if the value read from file is incorrect
	 */
	public GUID(RandomAccessFile raf) throws IOException, BadValueException {
		setValueFromFile(raf);
	}
	
	/**
	 * Constructor from a string that represents the value.
	 * @param val the string to read from
	 * @throws BadValueException if the string cannot be converted properly into a matching value
	 */
	public GUID(String val) throws BadValueException {
		setValueFromString(val);
	}
	
	/**
	 * Get the number of bytes that this value (always) occupies
	 * @return the fixed length in bytes
	 */
	public int getFixedLength() {
		return 16;
	}
	
	/**
	 * Set the value for this object from the specified file pointer.
	 * @param raf the file object to read the value from
	 * @throws IOException if the file cannot be read properly
	 * @throws BadValueException if the value is out of range
	 */
	public void setValueFromFile(RandomAccessFile raf) throws IOException, BadValueException {
		long p1 = raf.readLong();
		long p2 = raf.readLong();
		value = new UUID(p1, p2);
	}
	
	/**
	 * Set the value for this object from the specified string.
	 * @param s the string to read
	 * @throws BadValueException if the string cannot be parsed into a value that is within range
	 */
	public void setValueFromString(String s) throws BadValueException {
		if (s == null) {
			value = null;
			return;
		}
		s = s.trim();
		try {
			value = UUID.fromString(s);
		} catch (Exception err) {
			throw new BadValueException(s);
		}
	}
	
	/**
	 * Tell if the primitive object has an initialized value or not.
	 * @return true if value has been initialized, and false otherwise
	 */
	public boolean valueExists() {
		if (value == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Write this value to the specified file, at the current file pointer.
	 * @param raf the file object to write the value to
	 * @throws IOException if the file cannot be written to properly
	 * @throws BadValueException if the value has not been initialized
	 */
	public void writeValueToFile(RandomAccessFile raf) throws IOException, BadValueException {
		if (value == null) {
			throw new BadValueException();
		}
		long x = value.getMostSignificantBits();
		//x = Long.reverseBytes(x);
		raf.writeLong(x);
		raf.writeLong(value.getLeastSignificantBits());
	}
	
	/**
	 * Write the current value of this object to a string.
	 * @throws BadValueException if the value has not been initialized
	 * @return a string representation of this integer
	 */
	public String writeValueToString() throws BadValueException {
		if (value == null) {
			throw new BadValueException();
		}
		return value.toString();
	}
	
	/**
	 * Retrieve a text-based description of this primitive.
	 * @return a text-based description of this primitive
	 */
	public String getPrimitiveDescription() {
		return KlangConstants.getPrimitiveDescriptionFor(this.getClass().getName());
	}
	
}
