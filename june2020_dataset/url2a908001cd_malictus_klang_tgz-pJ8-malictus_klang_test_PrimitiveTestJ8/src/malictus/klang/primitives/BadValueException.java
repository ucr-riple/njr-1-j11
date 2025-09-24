/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.primitives;

import malictus.klang.*;

/**
 * A BadValueException should be thrown any time an attempt is made to set a Primitive to an illegal value.
 * @author Jim Halliday
 */
public class BadValueException extends Exception {
	
	/**
	 * Initialize the BadValueException
	 * @param value string representation of the value that is incorrect
	 */
	public BadValueException(String value) {
		super(value + KlangConstants.ERROR_BAD_VALUE_EXCEPTION);
	}
	
	/**
	 * Initialize a BadValueException with no value
	 */
	public BadValueException() {
		super(KlangConstants.ERROR_BAD_VALUE_EXCEPTION_NOVALUE);
	}
	
	/**
	 * Overwrite of getMessage() to truncate message if string value is very long
	 * @return the message, truncated if the value is very long
	 */
	public String getMessage() {
		String mess = super.getMessage();
		if (mess.length() > (36 + KlangConstants.ERROR_BAD_VALUE_EXCEPTION.length())) {
			mess = mess.substring(0, 35) + "..." + KlangConstants.ERROR_BAD_VALUE_EXCEPTION;
		}
		return mess;
	}

}
