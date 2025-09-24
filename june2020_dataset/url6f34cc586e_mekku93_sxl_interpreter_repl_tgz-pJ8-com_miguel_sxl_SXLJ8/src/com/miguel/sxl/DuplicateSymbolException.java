package com.miguel.sxl;

public class DuplicateSymbolException extends Exception {
	private static final long serialVersionUID = 8456673741318498079L;
	
	private String identifier;
	
	public DuplicateSymbolException(String identifier) {
		this.identifier = identifier;
	}
	
	public String getMessage() {
		return "Cannot redeclare " + this.identifier;
	}
}
