/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.primitives;

import java.io.*;

import malictus.klang.KlangConstants;

/**
 * Primitive representing an unsigned 4-byte (32 bit) little-endian integer value.
 * Even though a 4-byte value corresponds to an 'int' in Java, we need a long
 * to represent this value since Java is always signed.
 * 
 * @author Jim Halliday
 */
public class Int4ByteUnsignedLE implements Primitive, PrimitiveInt, PrimitiveFixedByte {

	private Long value;
	
	/**
	 * Empty constructor. The actual value must be initialized later, 
	 * before the class will return a valid value.
	 */
	public Int4ByteUnsignedLE() {}
	
	/**
	 * Constructor from a Long value. Checks will ensure that the Long value is
	 * within range for 4-byte unsigned values.
	 * @param value the Long that this object should represent
	 * @throws BadValueException if given value is out of range
	 */
	public Int4ByteUnsignedLE(Long value) throws BadValueException {
		if (!testCandidate(value)) {
			throw new BadValueException(value.toString());
		}
		this.value = value;
	}
	
	/**
	 * Constructor from an Integer value. This method is here for convenience,
	 * although not all valid values for this object can be expressed as Integer objects.
	 * @param value the Integer that this object should represent
	 * @throws BadValueException if given value is out of range
	 */
	public Int4ByteUnsignedLE(Integer value) throws BadValueException {
		if (!testCandidate(new Long(value.intValue()))) {
			throw new BadValueException(value.toString());
		}
		this.value = new Long(value.intValue());
	}

	/**
	 * Constructor from a file reference. The value will be read from the file at
	 * the current file pointer, and the file pointer will be advanced to 
	 * just beyond the value.
	 * @param raf a RandomAccessFile object that points to a valid file, queued for reading
	 * @throws IOException if the file cannot be read properly
	 * @throws BadValueException if the value read from file is incorrect
	 */
	public Int4ByteUnsignedLE(RandomAccessFile raf) throws IOException, BadValueException {
		setValueFromFile(raf);
	}
	
	/**
	 * Constructor from a string that represents the value.
	 * @param val the string to read from
	 * @throws BadValueException if the string cannot be converted properly into a matching value
	 */
	public Int4ByteUnsignedLE(String val) throws BadValueException {
		setValueFromString(val);
	}
	
	/**
	 * Get the number of bytes that this value (always) occupies
	 * @return the fixed length in bytes
	 */
	public int getFixedLength() {
		return 4;
	}
	
	/**
	 * Set the current value to the specified Long object.
	 * @param value the new Long value
	 * @throws BadValueException if the Long is out of range
	 */
	public void setValue(Long value) throws BadValueException {
		if (!testCandidate(value)) {
			throw new BadValueException(value.toString());
		}
		this.value = value;
	}
	
	/**
	 * Set the current value to the specified Integer object. This method is here for convenience,
	 * although not all valid values for this object can be expressed as Integer objects.
	 * @param value the new Integer value
	 * @throws BadValueException if the value is out of range
	 */
	public void setValue(Integer value) throws BadValueException {
		if (!testCandidate(new Long(value.intValue()))) {
			throw new BadValueException(value.toString());
		}
		this.value = new Long(value.intValue());
	}
	
	/**
	 * Set the value for this object from the specified file pointer.
	 * @param raf the file object to read the value from
	 * @throws IOException if the file cannot be read properly
	 * @throws BadValueException if the value is out of range
	 */
	public void setValueFromFile(RandomAccessFile raf) throws IOException, BadValueException {
		int val = raf.readInt();
		val = Integer.reverseBytes(val);
		long output = val & 0xffffffffL;		
		if (!testCandidate(output)) {
			throw new BadValueException(new Long(output).toString());
		}
		value = output;
	}
	
	/**
	 * Set the value for this object from the specified string.
	 * @param s the string to read
	 * @throws BadValueException if the string cannot be parsed into a integer that is within range
	 */
	public void setValueFromString(String s) throws BadValueException {
		if (s == null) {
			value = null;
			return;
		}
		s = s.trim();
		try {
			Long x = Long.decode(s);
			if (!testCandidate(x)) {
				throw new BadValueException(x.toString());
			}
			value = x;
		} catch (Exception err) {
			throw new BadValueException(s);
		}
	}
	
	/**
	 * Get this object's value.
	 * @return the current value; note that this will return null if the value was never initialized
	 */
	public Long getValue() {
		return value;
	}
	
	/**
	 * Get this object's value as a long.
	 * @return the current value; note that this will return null if the value was never initialized
	 */
	public Long getValueAsLong() {
		if (value == null) {
			return null;
		} else {
			return value.longValue();
		}
	}
	
	/**
	 * Write this value to the specified file, at the current file pointer.
	 * @param raf file object to write the value to
	 * @throws IOException if the file cannot be written to properly
	 * @throws BadValueException if the value has not been initialized
	 */
	public void writeValueToFile(RandomAccessFile raf) throws IOException, BadValueException {
		if (value == null) {
			throw new BadValueException();
		}
		int output = (int)(value & 0xffffffffL);
		output = Integer.reverseBytes(output);
		raf.writeInt(output);
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
	 * Used internally to see if a given value is within range for this object
	 * @param value the long value to test
	 * @return true if candidate is in range, and false otherwise
	 */
	private boolean testCandidate(Long value) {
		if ((value < 0) || (value > 4294967295L)) {
			return false;
		}
		return true;
	}
	
}
