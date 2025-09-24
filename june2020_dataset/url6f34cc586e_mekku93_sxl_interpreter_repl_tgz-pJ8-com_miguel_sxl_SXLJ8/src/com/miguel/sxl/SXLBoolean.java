package com.miguel.sxl;

public class SXLBoolean  implements SXLValue {

	private boolean value;
	
	/**
	 * Constructs the SXLBoolean with the value equal to the boolean parameter.
	 */
	public SXLBoolean(boolean i) {
		value = i;
	}
	/**
	 * Constructs the SXLBoolean by attempting to parse the given string into a boolean.
	 */
	public SXLBoolean(String s) throws Exception {
		this.fromString(s);
	}

	
	/**
	 * Returns the boolean value
	 */
	public boolean boolValue() {
		return value;
	}
	
	@Override
	public String getType() {
		return "boolean";
	}
	
	@Override
	public String toString() {
		return value + "";
	}
	
	
	@Override
	public void setValue(SXLValue other) {
		value = ((SXLBoolean)other).boolValue();
	}
	
	
	
	// OPERATIONS
	
	@Override
	public SXLValue and(SXLValue other) {
		return new SXLBoolean( value && ((SXLBoolean)other).boolValue() );
	}
	@Override
	public SXLValue or(SXLValue other) {
		return new SXLBoolean( value || ((SXLBoolean)other).boolValue() );
	}
	@Override
	public SXLValue not() {
		return new SXLBoolean( !value );
	}
	
	
	
	// UNSUPPORTED OPERATIONS
	@Override
	public SXLValue unary(String op) {
		return null;
	}
	
	@Override
	public SXLValue add(SXLValue other) {
		return null;
	}
	@Override
	public SXLValue subtract(SXLValue other) {
		return null;
	}
	@Override
	public SXLValue multiply(SXLValue other) {
		return null;
	}
	@Override
	public SXLValue divide(SXLValue other) {
		return null;
	}
	@Override
	public SXLValue isGreaterThan(SXLValue other) {
		return null;
	}
	@Override
	public SXLValue isLessThan(SXLValue other) {
		return null;
	}
	@Override
	public SXLValue isEqualTo(SXLValue other) {
		return null;
	}
	
	
	
	@Override
	public void fromString(String s) throws Exception {
		switch( s ) {
			case "true":
				value = true;
				break;
			case "false":
				value = false;
				break;
			default:
				throw new Exception();
		}
	}
	
	
	@Override
	public SXLValue castInto(String type) throws Exception {
		switch( type ) {
			case "string": return new SXLString( (value)? "true" : "false" );
			case "boolean": return new SXLBoolean( value );
			case "integer": return new SXLInteger( (value)? 1 : 0 );
			case "real" : return new SXLReal( (value)? 1.0 : 0.0 );
			case "character" : return new SXLCharacter( (value)? 't' :'f' );
			default: throw new Exception("Cannot cast boolean into " + type);
		}
	}
	
}
