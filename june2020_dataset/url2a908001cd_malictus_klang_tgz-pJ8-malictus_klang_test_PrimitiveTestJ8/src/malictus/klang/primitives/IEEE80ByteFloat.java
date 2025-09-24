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
 * Primitive representing an 80-byte float value, according to the IEEE standard.
 * IMPORTANT: This class takes several shortcuts, and assumes the value is actually a positive integer!
 * Currently, this primitive is only used to represent sample rate in AIFF files, which should always be a positive integer.
 * A more sophisticated process could deal with other cases.
 * @author Jim Halliday
 */
public class IEEE80ByteFloat implements Primitive, PrimitiveInt, PrimitiveFixedByte {
	
	private Integer value;
	
	/**
	 * Empty constructor. The actual value must be initialized later, 
	 * before the class will return a valid value.
	 */
	public IEEE80ByteFloat() { }
	
	/**
	 * Constructor from an Integer value.
	 * @param value the Integer that this object should represent
	 * @throws BadValueException if value is not within correct range (is negative)
	 */
	public IEEE80ByteFloat(Integer value) throws BadValueException {
		if (!testCandidate(value)) {
			throw new BadValueException(value.toString());
		}
		this.value = value;
	}
	
	/**
	 * Constructor from a file reference. The value will be read from the file at
	 * the current file pointer, and the file pointer will be advanced to 
	 * just beyond the value.
	 * @param raf a RandomAccessFile object that points to a valid file, queued for reading
	 * @throws IOException if the file cannot be read properly
	 * @throws BadValueException if the value read from file is incorrect
	 */
	public IEEE80ByteFloat(RandomAccessFile raf) throws IOException, BadValueException {
		setValueFromFile(raf);
	}
	
	/**
	 * Constructor from a string that represents the integer value.
	 * @param val the string to read from
	 * @throws BadValueException if the string cannot be converted properly into a matching value
	 */
	public IEEE80ByteFloat(String val) throws BadValueException {
		setValueFromString(val);
	}
	
	/**
	 * Get the number of bytes that this value (always) occupies
	 * @return the fixed length in bytes
	 */
	public int getFixedLength() {
		return 10;
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
		this.value = value;
	}
	
	/**
	 * Set the value for this object from the specified file pointer.
	 * @param raf the file object to read the value from
	 * @throws IOException if the file cannot be read properly
	 * @throws BadValueException if the value is out of range
	 */
	public void setValueFromFile(RandomAccessFile raf) throws IOException, BadValueException {
		//NOTE: Shortcuts being taken here because value should always be positive integer
		byte[] buf = new byte[10];
		raf.read(buf);
        int sign = buf[0] >> 7;
        if (sign != 0) {
        	throw new BadValueException();
        }
        int exp = (buf[0] << 8) | buf[1];
        exp = exp & 0x7fff;
        exp = exp - 16445;
        sign = buf[0] >> 7;
        int shift = 55;
        long mant = 0;
        for (int i = 2; i < 9; i++) {
            mant = mant | ((long) buf[i] & 0xffL) << shift;
            shift = shift - 8;
        }
        mant = mant | buf[9] >>> 1;
        double val = Math.pow (2, exp);
        val = val * mant;
        value = new Double((Math.floor(val))).intValue();
		if (!testCandidate(value)) {
			throw new BadValueException(value.toString());
		}
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
			Integer x = new Integer(s);
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
	public Integer getValue() {
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
		//NOTE: Shortcuts being taken here because value should always be positive integer
        int exp = 0;
        int x = value.intValue();
    	while ((x != 0) && (x & 0x80000000) == 0) {
    		exp = exp + 1;
    		x = x << 1;
    	}
    	raf.writeShort(16414 - exp);
    	raf.writeInt(x);  		
    	raf.writeInt(0);					
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
	 * Used internally to see if a given value is within range for this object.
	 * @param value the Integer object to test
	 * @return true if candidate is in range, and false otherwise
	 */
	private boolean testCandidate(Integer value) {
		if (value < 0) {
			return false;
		}
		return true;
	}
	
}
