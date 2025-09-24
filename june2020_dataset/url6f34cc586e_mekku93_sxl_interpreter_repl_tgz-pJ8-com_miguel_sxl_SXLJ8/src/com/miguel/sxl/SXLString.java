package com.miguel.sxl;

public class SXLString implements SXLValue {

	private String value;
	
	public SXLString(String s) {
		value = s;
	}
	
	/**
	 * Returns the String value
	 */
	public String strValue() {
		return value;
	}
	
	@Override
	public String getType() {
		return "string";
	}
	
	@Override
	public String toString() {
		return value + "";
	}
	
	@Override
	public void setValue(SXLValue other) {
		value = ((SXLString)other).strValue();
	}
	
	
	// OPERATIONS
	

	/**
	 * Appends the "other" string to the the value, and returns it as a new SXLString.
	 */
	@Override
	public SXLValue add(SXLValue other) {
		return new SXLString( value + ((SXLString)other).strValue() );
	}

	
	/** UNSUPPORTED OPERATION */
	@Override
	public SXLValue unary(String op) {
		return null;
	}
	
	/** UNSUPPORTED OPERATION */
	@Override
	public SXLValue subtract(SXLValue other) {
		return null;
	}

	/** UNSUPPORTED OPERATION */
	@Override
	public SXLValue multiply(SXLValue other) {
		return null;
	}

	/** UNSUPPORTED OPERATION */
	@Override
	public SXLValue divide(SXLValue other) {
		return null;
	}

	/** UNSUPPORTED OPERATION */
	@Override
	public SXLValue and(SXLValue other) {
		return null;
	}

	/** UNSUPPORTED OPERATION */
	@Override
	public SXLValue or(SXLValue other) {
		return null;
	}

	/** UNSUPPORTED OPERATION */
	@Override
	public SXLValue not() {
		return null;
	}

	/** UNSUPPORTED OPERATION */
	@Override
	public SXLValue isGreaterThan(SXLValue other) {
		return null;
	}

	/** UNSUPPORTED OPERATION */
	@Override
	public SXLValue isLessThan(SXLValue other) {
		return null;
	}
	
	
	/**
	 * Returns an SXLBoolean, that has a value of true if the strings are equal, and false
	 * if not. Equality evaluates as true if the strings contain the exact same sequence of
	 * characters. Case-sensitive.
	 */
	@Override
	public SXLValue isEqualTo(SXLValue other) {
		return new SXLBoolean( value.equals( ((SXLString)other).strValue() ) );
	}

	
	
	@Override
	public void fromString(String s) throws Exception {
		value = s;
	}
	
	
	
	@Override
	public SXLValue castInto(String type) throws Exception {
		switch( type ) {
			case "string": return new SXLString( value );
			case "integer": return new SXLInteger( value );
			case "real" : return new SXLReal( value );
			case "boolean" : return new SXLBoolean( value );
			default: throw new Exception("Cannot cast from string to " + type);
		}
	}

}
