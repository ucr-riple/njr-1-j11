package de.codesourcery.dcpu16.codegeneration;

public enum Register 
{
	A("A"),B("B"),C("C"),I("I"),J("J"),X("X"),Y("Y"),Z("Z"),PC("PC"),SP("SP");
	
	private final String identifier;

	private Register(String identifier) {
		this.identifier = identifier;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	@Override
	public String toString() {
		return identifier;
	}
}
