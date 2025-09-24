/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.primitives;

/**
 * This interface is for any primitive that is always the same number of bytes.
 * @author Jim Halliday
 */
public interface PrimitiveFixedByte {
	
	/**
	 * Will return the byte length of ANY primitive of this type
	 * @return the byte length
	 */
	public int getFixedLength();

}
