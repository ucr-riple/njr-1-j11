package com.miguel.sxl;

public class SXLParam {

	public String identifier;
	public SXLValue type;
	
	/**
	 * Constructs the SXLParam using the identifier and the type given.
	 * 
	 * @param identifier The string identifier
	 * @param type The SXLValue that reflects the type. The actual value of the object is ignored.
	 */
	public SXLParam(String identifier, SXLValue type) {
		this.identifier = identifier;
		this.type = type;
	}
	
	
	
}
