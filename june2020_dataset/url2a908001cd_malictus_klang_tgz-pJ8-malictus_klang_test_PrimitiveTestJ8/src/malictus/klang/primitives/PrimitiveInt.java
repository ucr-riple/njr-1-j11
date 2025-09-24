/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.primitives;

/**
 * PrimitiveInt is any primitive that can be expressed as an integer.
 * This is useful for comparing primitive values on a generic level.
 * @author Jim Halliday
 */
public interface PrimitiveInt {
	
	/**
	 * Get the primitive value when expressed as a long.
	 * @return the primitive's value when expressed as a long
	 */
	public Long getValueAsLong();
	
}
