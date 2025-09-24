/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.primitives;

import java.util.*;

import malictus.klang.KlangConstants;

/**
 * A PrimitiveData object represents a primitive, a description of what it
 * represents, and optionally a byte location in a file.
 * @author Jim Halliday
 */
public class PrimitiveData {

	private Primitive primitive;
	private String name;
	private Long startPos;
	private Long endPos;
	
	/**
	 * Initialize a PrimitiveData object.
	 * @param primitive a primitive object. The value for this primitive may not be
	 * set when the object is first initialized.
	 * @param name the name of the object, from the KlangConstants PRIMITIVE_DATA_ values
	 * @param startPos start byte location of this primitive within the file; set to NULL if not applicable
	 * @param endPos end byte location of this primitive within the file (the first byte after this primitive); 
	 * 		set to NULL if not applicable
	 */
	public PrimitiveData(Primitive primitive, String name, Long startPos, Long endPos) {
		this.primitive = primitive;
		this.name = name;
		this.startPos = startPos;
		this.endPos = endPos;
	}
	
	/**
	 * Retrieval method for the actual primitive
	 * @return the primitive that this object represents
	 */
	public Primitive getPrimitive() {
		return primitive;
	}
	
	/**
	 * Return the name associated with this primitive, from the KlangConstants PRIMITIVE_DATA_ values
	 * @return the name associated with this primitive
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Return the byte position that this primitive begins at (or null if not applicable)
	 * @return the byte position that this primitive begins at (or null if not applicable)
	 */
	public Long getStartBytePosition() {
		return startPos;
	}
	
	/**
	 * Return the byte position that this primitive ends at (or null if not applicable)
	 * @return the byte position that this primitive ends at (or null if not applicable)
	 */
	public Long getEndBytePosition() {
		return endPos;
	}
	
	/**
	 * Return a description of this primitive data type
	 * @return a description of this primitive data type
	 */
	public String getDescription() {
		return KlangConstants.getPrimitiveDataDescriptionFor(name);
	}
	
	/**
	 * Some data types are an integer or short string value, with a longer 
	 * string description. This method returns a LinkedHashMap that maps the 
	 * numerical value with the string description, or null if no mapping exists
	 * @return a LinkedHashMap that maps values to descriptions, if one exists
	 */
	public LinkedHashMap<String, String> getValueStrings() {
		return KlangConstants.getValueStringsFor(name);
	}
	
	/**
	 * If a value string LinkedHashMap exists, this method will return the String associated
	 * with the current primitive value. If there is no mapping, or the primitive doesn't
	 * map to a value in the table, or the primitive does not yet have a value, this
	 * method returns null.
	 * @return a string that represents the current value, if a mapping to a string exists
	 */
	public String getCurrentValueString() {
		return KlangConstants.getCurrentValueStringFor(name, primitive);
	}
	
}
