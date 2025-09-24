/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.primitives;

import java.io.*;
import java.nio.*;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

import malictus.klang.KlangConstants;

/**
 * Primitive representing a four-byte ASCII 'chunk' designation ('data', 'COMM', etc.)
 * @author Jim Halliday
 */
public class FourCC implements Primitive, PrimitiveFixedByte {
	
	private String value;
	
	/**
	 * Empty constructor. The actual value in this case must be initialized later, 
	 * before the class will return a valid value.
	 */
	public FourCC() { 
	}
	
	/**
	 * Constructor from a String value.
	 * @param value the string that this object should represent
	 * @throws BadValueException if the string is an incorrect length or cannot be represented
	 */
	public FourCC(String value) throws BadValueException {
		setValueFromString(value);
	}
	
	/**
	 * Constructor from a file reference. The value will be read from the file at
	 * the current file pointer, and the file pointer will be advanced to 
	 * just beyond the value.
	 * @param raf a RandomAccessFile object that points to a valid file, queued for reading
	 * @throws IOException if the file cannot be read properly
	 * @throws BadValueException if value is incorrect
	 */
	public FourCC(RandomAccessFile raf) throws IOException, BadValueException {
		setValueFromFile(raf);
	}
	
	/**
	 * Get the number of bytes that this value (always) occupies
	 * @return the fixed length in bytes
	 */
	public int getFixedLength() {
		return 4;
	}
	
	/**
	 * Set the value for this object from the specified file pointer.
	 * @param raf the file object to read the value from
	 * @throws IOException if the file cannot be read properly
	 * @throws BadValueException if the value is out of range
	 * @throws CharacterCodingException if the value cannot be expressed in the given encoding
	 */
	public void setValueFromFile(RandomAccessFile raf) throws IOException, BadValueException, CharacterCodingException {
		byte[] buf = new byte[4];
		buf[0] = raf.readByte();
		buf[1] = raf.readByte();
		buf[2] = raf.readByte();
		buf[3] = raf.readByte();
		
		CharsetDecoder dec = Charset.forName("US-ASCII").newDecoder();
		dec.onMalformedInput(CodingErrorAction.REPORT);
		dec.onUnmappableCharacter(CodingErrorAction.REPORT);
		try {
			value = dec.decode(ByteBuffer.wrap(buf)).toString();
		} catch (Exception err) {
			throw new BadValueException();
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
		//make adjustments to make it exactly 4 characters
		if (s.length() > 4) {
			s = s.substring(0, 4);
		}
		while (s.length() < 4) {
			s = s + " ";
		}
		if (!testCandidate(s)) {
			throw new BadValueException(s);
		}
		this.value = s;
	}
	
	/**
	 * Get this object's value.
	 * @return the current value; note that this will return null if the value was never initialized
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Write this value to the specified file, at the current file pointer.
	 * @param raf the file object to write the value to
	 * @throws IOException if the file cannot be written to properly
	 * @throws BadValueException if the value has not been initialized
	 * @throws CharacterCodingException if the value cannot be expressed in the given encoding
	 */
	public void writeValueToFile(RandomAccessFile raf) throws IOException, BadValueException, CharacterCodingException {
		if (value == null) {
			throw new BadValueException();
		}
		
		CharsetEncoder enc = Charset.forName("US-ASCII").newEncoder();
		enc.onMalformedInput(CodingErrorAction.REPORT);
		enc.onUnmappableCharacter(CodingErrorAction.REPORT);
		byte[] theBytes = enc.encode(CharBuffer.wrap(value)).array();
		
		if (theBytes.length != 4) {
			throw new BadValueException(value);
		}
		raf.write(theBytes);
	}
	
	/**
	 * Write the current value of this object to a string.
	 * @throws BadValueException if the value has not been initialized
	 * @return a string representation of this value
	 */
	public String writeValueToString() throws BadValueException {
		if (value == null) {
			throw new BadValueException();
		}
		return value;
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
	 * Used internally to see if a given value is within range for this object.
	 * @param value the String object to test
	 * @return true if candidate is in range, and false otherwise
	 */
	private boolean testCandidate(String value) {
		if (value.length() != 4) {
			return false;
		}
		try {
			CharsetEncoder enc = Charset.forName("US-ASCII").newEncoder();
			enc.onMalformedInput(CodingErrorAction.REPORT);
			enc.onUnmappableCharacter(CodingErrorAction.REPORT);
			byte[] theBytes = enc.encode(CharBuffer.wrap(value)).array();
			
			if (theBytes.length != 4) {
				return false;
			}
		} catch (CharacterCodingException err) {
			return false;
		}
		return true;
	}
	
}
