/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.primitives;

import java.io.*;
import java.nio.*;
import java.nio.charset.*;

import malictus.klang.KlangConstants;

/**
 * Primitive representing a plain, unformatted string.
 * @author Jim Halliday
 */
public class PlainString implements Primitive, StringPrim {
	
	private String value;
	private String textEncoding;
	private boolean canChangeEncoding;
	
	/**
	 * Constructor from a file reference. The value will be read from the file at
	 * the current file pointer, and the file pointer will be advanced to 
	 * just beyond the string. Note that this is currently the ONLY way to initialize this
	 * value while specifying a stop point other than the end of the file.
	 * 
	 * @param raf a RandomAccessFile object that points to a valid file, queued for reading
	 * @param stoppoint the byte position to stop reading data, or -1 if should read to file end
	 * @param textEncoding the text encoding for this string
	 * @param canChangeEncoding true if text encoding can change, and false otherwise
	 * @throws IOException if the file cannot be read properly
	 * @throws BadValueException if value is incorrect
	 * @throws CharacterCodingException if a character encoding problem occurs
	 */
	public PlainString(RandomAccessFile raf, long stoppoint, String textEncoding, boolean canChangeEncoding) throws IOException, BadValueException, CharacterCodingException {
		this.textEncoding = textEncoding;
		this.canChangeEncoding = canChangeEncoding;
		if (stoppoint != -1) {
			if (raf.getFilePointer() >= stoppoint) {
				value = "";
				return;
			}
		}
		//check for pad byte at end
		long pos = raf.getFilePointer();
		raf.seek(stoppoint - 1);
		byte x = raf.readByte();
		if (x == 0) {
			stoppoint = stoppoint - 1;
		}
		raf.seek(pos);
		byte[] byt = new byte[(int)(stoppoint - raf.getFilePointer())];
		raf.read(byt);
		try {
			value = new String(byt, textEncoding);
		} catch (Exception err) {
			err.printStackTrace();
			throw new BadValueException();
		}
	}
	
	/**
	 * Constructor from a String value.
	 * @param value the string that this object should represent
	 * @param textEncoding the text encoding for this string
	 * @param canChangeEncoding true if text encoding can change, and false otherwise
	 * @throws BadValueException if the string is an incorrect length or cannot be represented
	 */
	public PlainString(String value, String textEncoding, boolean canChangeEncoding) throws BadValueException {
		this.textEncoding = textEncoding;
		this.canChangeEncoding = canChangeEncoding;
		setValueFromString(value);
	}
	
	/**
	 * Tells whether this string text encoding can change.
	 * @return true if the encoding can change, and false otherwise
	 */
	public boolean canChangeEncoding() {
		return canChangeEncoding;
	}
	
	/**
	 * Retrieval method for this primitive's text encoding
	 * @return the text encoding for this primitive
	 */
	public String getTextEncoding() {
		return textEncoding;
	}
	
	/**
	 * Set the text encoding for this primitive
	 * @param textEncoding the text encoding
	 * @throws BadValueException if the encoding cannot be changed
	 */
	public void setTextEncoding(String textEncoding) throws BadValueException {
		if (!this.canChangeEncoding) {
			throw new BadValueException();
		}
		this.textEncoding = textEncoding;
	}
	
	/**
	 * Set the value for this object from the specified file pointer. Note that this
	 * is NOT usually the preferred method, since this will simply read the entire file
	 * from this point on!
	 * @param raf the file object to read the value from
	 * @throws IOException if the file cannot be read properly
	 * @throws BadValueException if the value is out of range
	 */
	public void setValueFromFile(RandomAccessFile raf) throws IOException, BadValueException {
		byte[] byt = new byte[(int)(raf.length() - raf.getFilePointer())];
		raf.read(byt);
		try {
			value = new String(byt, textEncoding);
		} catch (Exception err) {
			err.printStackTrace();
			throw new BadValueException();
		}
	}
	
	/**
	 * Set the value for this object from the specified string.
	 * @param s the string to read
	 * @throws BadValueException if the string cannot be parsed into a value that is within range
	 */
	public void setValueFromString(String s) throws BadValueException {
		//verify that the string is encoded correctly
		try {			
			CharsetEncoder enc = Charset.forName(textEncoding).newEncoder();
			enc.onMalformedInput(CodingErrorAction.REPORT);
			enc.onUnmappableCharacter(CodingErrorAction.REPORT);
			enc.encode(CharBuffer.wrap(s));
		} catch (CharacterCodingException err) {
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
	 * @throws CharacterCodingException if the value can't be expressed in the appropriate character set
	 */
	public void writeValueToFile(RandomAccessFile raf) throws IOException, BadValueException, CharacterCodingException {
		if (value == null) {
			throw new BadValueException();
		}
		byte[] theBytes;
		try {
			theBytes = value.getBytes(textEncoding);
			
		} catch (Exception err) {
			err.printStackTrace();
			throw new BadValueException();
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
	
}
