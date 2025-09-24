package com.miguel.sxl;

public class SXLCharacter implements SXLValue {

	private char value;
	
	/**
	 * Constructs the SXLInteger with the value equal to the character parameter.
	 */
	public SXLCharacter(char c) {
		value = c;
	}
	
	/**
	 * Returns the character value
	 */
	public char charValue() {
		return value;
	}
	
	@Override
	public String getType() {
		return "character";
	}
	
	@Override
	public String toString() {
		return value + "";
	}

	
	@Override
	public void setValue(SXLValue other) {
		value = ((SXLCharacter)other).charValue();
	}
	
	
	
	// OPERATIONS
	
	@Override
	public SXLValue add(SXLValue other) {
		return new SXLString( "" + value + ((SXLCharacter)other).charValue() );
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

	@Override
	public SXLValue isGreaterThan(SXLValue other) {
		return new SXLBoolean( value > ((SXLCharacter)other).charValue() );
	}

	@Override
	public SXLValue isLessThan(SXLValue other) {
		return new SXLBoolean( value < ((SXLCharacter)other).charValue() );
	}

	@Override
	public SXLValue isEqualTo(SXLValue other) {
		return new SXLBoolean( value == ((SXLCharacter)other).charValue() );
	}

	
	@Override
	public void fromString(String s) throws Exception {
		char first = s.charAt(0);
		// check if the character is an escaped one
		if ( first == '\\' && s.length() == 2 ) {
			switch( s ) {
				case "\\\\": value = '\\'; break;
				case "\\'": value = '\''; break;
				case "\\n": value = '\n'; break;
				case "\\r": value = '\r'; break;
				case "\\f": value = '\f'; break;
				case "\\b": value = '\b'; break;
				case "\\t": value = '\t'; break;
				default: throw new Exception();
			}
		} else if ( s.length() == 1 ) {
			value = first;
		} else {
			throw new Exception();
		}
	}

	
	
	@Override
	public SXLValue castInto(String type) throws Exception {
		switch( type ) {
			case "string": return new SXLString( value + "" );
			case "integer": return new SXLInteger( (int)value );
			case "real" : return new SXLReal( (double)value );
			default: throw new Exception("Cannot cast boolean into " + type);
		}
	}
}
