package com.miguel.sxl;

/**
 * Custom integer wrapper class, that implements SXLValue
 */
public class SXLInteger implements SXLValue {
	
	private int value;
	
	/**
	 * Constructs the SXLInteger with the value equal to the integer parameter.
	 */
	public SXLInteger(int i) {
		value = i;
	}
	/**
	 * Constructs the SXLInteger by attempting to parse the given string into an integer.
	 */
	public SXLInteger(String s) throws Exception {
		this.fromString(s);
	}

	
	/**
	 * Returns the integer value
	 */
	public int intValue() {
		return value;
	}
	
	@Override
	public String getType() {
		return "integer";
	}
	
	@Override
	public String toString() {
		return value + "";
	}
	
	
	@Override
	public void setValue(SXLValue other) {
		value = ((SXLInteger)other).intValue();
	}
	
	
	// OPERATIONS
	
	
	@Override
	public SXLValue add(SXLValue other) {
		return new SXLInteger( value + ((SXLInteger)other).intValue() );
	}

	@Override
	public SXLValue subtract(SXLValue other) {
		return new SXLInteger( value - ((SXLInteger)other).intValue() );
	}

	@Override
	public SXLValue multiply(SXLValue other) {
		return new SXLInteger( value * ((SXLInteger)other).intValue() );
	}

	@Override
	public SXLValue divide(SXLValue other) {
		return new SXLInteger( value / ((SXLInteger)other).intValue() );
	}
	
	
	@Override
	public SXLValue unary(String op) {
		switch( op ) {
			case "+":	return new SXLInteger( +this.value );
			case "-":	return new SXLInteger( -this.value );
			default:	return null;
		}
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
		return new SXLBoolean( value > ((SXLInteger)other).intValue() );
	}
	@Override
	public SXLValue isLessThan(SXLValue other) {
		return new SXLBoolean( value < ((SXLInteger)other).intValue() );
	}
	@Override
	public SXLValue isEqualTo(SXLValue other) {
		return new SXLBoolean( value == ((SXLInteger)other).intValue() );
	}
	
	
	
	
	@Override
	public void fromString(String s) throws Exception {
		value = Integer.parseInt(s);
	}
	
	
	
	@Override
	public SXLValue castInto(String type) throws Exception {
		switch( type ) {
			case "string": return new SXLString( value + "" );
			case "real" : return new SXLReal( (double)value );
			case "integer": return new SXLInteger( value );
			case "character" : return new SXLCharacter( (char)value );
			default: throw new Exception("Cannot cast from integer to " + type);
		}
	}
	
}