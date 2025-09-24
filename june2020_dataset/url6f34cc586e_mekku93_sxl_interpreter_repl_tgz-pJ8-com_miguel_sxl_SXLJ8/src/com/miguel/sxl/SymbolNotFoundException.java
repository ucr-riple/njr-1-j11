package com.miguel.sxl;

public class SymbolNotFoundException extends Exception {
	private static final long serialVersionUID = 5730881356890611104L;
	
	private String identifier;
	
	public SymbolNotFoundException(String identifier) {
		this.identifier = identifier;
	}
	
	public String getMessage() {
		return this.identifier + " cannot be resolved to a variable";
	}
	
}