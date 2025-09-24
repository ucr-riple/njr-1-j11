/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.primitives;

/**
 * StringPrim is an interface for variable-byte strings.
 * @author Jim Halliday
 */
public interface StringPrim {
	
	/**
	 * Determines whether this string is set in a specific encoding 
	 * (due to the chunk specs, for instance) 
	 * @return true if the encoding can change, and false otherwise
	 */
	public boolean canChangeEncoding();
	
	/**
	 * Get the current encoding of this string
	 * @return the current encoding of this string
	 */
	public String getTextEncoding();
	
	/**
	 * Set the text encoding to the given encoding.
	 * @param newEncoding the new text encoding
	 */
	public void setTextEncoding(String newEncoding) throws BadValueException;

}
