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
 * Primitive representing a 1-byte (8 bit) signed integer value. 
 * This closely corresponds to java's 'byte' value.
 * @author Jim Halliday
 */
public class IntByteSigned implements Primitive, PrimitiveInt, PrimitiveFixedByte {
	
	private Byte value;
	
	/**
	 * Empty constructor. The actual value must be initialized later, 
	 * before the class will return a valid value.
	 */
	public IntByteSigned() { }
	
	/**
	 * Constructor from a byte value.
	 * @param value the byte that this object should represent
	 */
	public IntByteSigned(Byte value) {
		//in this case, initial value is always exactly within range that can be represented; no need for additional checks
		this.value = value;
	}
	
	/**
	 * Constructor from an Integer value. Checks will be made to ensure that
	 * number is within range.
	 * @param value the Integer that this object should represent
	 * @throws BadValueException if the Integer is out of range
	 */
	public IntByteSigned(Integer value) throws BadValueException {
		if (!testCandidate(value)) {
			throw new BadValueException(value.toString());
		}
		this.value = new Byte(value.byteValue());
	}
	
	/**
	 * Constructor from a file reference. The value will be read from the file at
	 * the current file pointer, and the file pointer will be advanced to 
	 * just beyond the value.
	 * @param raf a RandomAccessFile object that points to a valid file, queued for reading
	 * @throws IOException if the file cannot be read properly
	 * @throws BadValueException if the value read from file is incorrect
	 */
	public IntByteSigned(RandomAccessFile raf) throws IOException, BadValueException {
		setValueFromFile(raf);
	}
	
	/**
	 * Constructor from a string that represents the byte value.
	 * @param val the string to read from
	 * @throws BadValueException if the string cannot be converted properly into a matching value
	 */
	public IntByteSigned(String val) throws BadValueException {
		setValueFromString(val);
	}
	
	/**
	 * Get the number of bytes that this value (always) occupies
	 * @return the fixed length in bytes
	 */
	public int getFixedLength() {
		return 1;
	}
	
	/**
	 * Set the current value to the specified Byte object.
	 * @param value the new Byte value
	 * @throws BadValueException if the number is out of range
	 */
	public void setValue(Byte value) throws BadValueException {
		if (!testCandidate(value)) {
			throw new BadValueException(value.toString());
		}
		this.value = value;
	}
	
	/**
	 * Set the current value to the specified integer object.
	 * @param value the new Integer value
	 * @throws BadValueException if the Integer is out of range
	 */
	public void setValue(Integer value) throws BadValueException {
		if (!testCandidate(value)) {
			throw new BadValueException(value.toString());
		}
		this.value = new Byte(value.byteValue());
	}
	
	/**
	 * Set the value for this object from the specified file pointer.
	 * @param raf the file object to read the value from
	 * @throws IOException if the file cannot be read properly
	 * @throws BadValueException if the value is out of range
	 */
	public void setValueFromFile(RandomAccessFile raf) throws IOException, BadValueException {
		Byte val = raf.readByte();
		if (!testCandidate(val)) {
			throw new BadValueException(val.toString());
		}
		value = val;
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
			Byte x = Byte.decode(s);
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
	public Byte getValue() {
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
	 * @param raf the file object to write the value to
	 * @throws IOException if the file cannot be written to properly
	 * @throws BadValueException if the value has not been initialized
	 */
	public void writeValueToFile(RandomAccessFile raf) throws IOException, BadValueException {
		if (value == null) {
			throw new BadValueException();
		}
		raf.writeByte(value);
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
	 * @param value the Byte object to test
	 * @return true if candidate is in range, and false otherwise
	 */
	private boolean testCandidate(Byte value) {
		//works everytime
		return true;
	}
	
	/**
	 * Used internally to see if a given value is within range for this object.
	 * @param value the Integer object to test
	 * @return true if candidate is in range, and false otherwise
	 */
	private boolean testCandidate(Integer value) {
		//when testing int, have to see if it's within range first
		if ((value < -128) || (value > 127)) {
			return false;
		}
		return true;
	}
	
}
