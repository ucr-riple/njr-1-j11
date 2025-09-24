package com.miguel.sxl;

public class SXLReal implements SXLValue {

	private double value;
	
	/**
	 * Constructs the SXLReal with the value equal to the double parameter.
	 */
	public SXLReal(double i) {
		value = i;
	}
	/**
	 * Constructs the SXLReal by attempting to parse the given string into a double.
	 */
	public SXLReal(String s) throws Exception {
		this.fromString(s);
	}

	
	/**
	 * Returns the double value
	 */
	public double dblValue() {
		return value;
	}
	
	@Override
	public String getType() {
		return "real";
	}
	
	@Override
	public String toString() {
		return value + "";
	}
	
	@Override
	public void setValue(SXLValue other) {
		value = ((SXLReal)other).dblValue();
	}
	
	
	
	// OPERATIONS
	
	
	@Override
	public SXLValue add(SXLValue other) {
		return new SXLReal( value + ((SXLReal)other).dblValue() );
	}

	@Override
	public SXLValue subtract(SXLValue other) {
		return new SXLReal( value - ((SXLReal)other).dblValue() );
	}

	@Override
	public SXLValue multiply(SXLValue other) {
		return new SXLReal( value * ((SXLReal)other).dblValue() );
	}

	@Override
	public SXLValue divide(SXLValue other) {
		return new SXLReal( value / ((SXLReal)other).dblValue() );
	}
	
	
	@Override
	public SXLValue unary(String op) {
		switch( op ) {
			case "+":	return new SXLReal( +this.value );
			case "-":	return new SXLReal( -this.value );
			default:	return null;
		}
	}
	

	// UNSUPPORTED OPERATION
	@Override
	public SXLValue and(SXLValue other) {
		return null;
	}
	// UNSUPPORTED OPERATION
	@Override
	public SXLValue or(SXLValue other) {
		return null;
	}
	// UNSUPPORTED OPERATION
	@Override
	public SXLValue not() {
		return null;
	}
	
	
	
	
	@Override
	public SXLValue isGreaterThan(SXLValue other) {
		return new SXLBoolean( value > ((SXLReal)other).dblValue() );
	}
	@Override
	public SXLValue isLessThan(SXLValue other) {
		return new SXLBoolean( value < ((SXLReal)other).dblValue() );
	}
	@Override
	public SXLValue isEqualTo(SXLValue other) {
		return new SXLBoolean( value == ((SXLReal)other).dblValue() );
	}
	
	
	
	@Override
	public void fromString(String s) throws Exception {
		value = Double.parseDouble(s);
	}
	
	
	@Override
	public SXLValue castInto(String type) throws Exception {
		switch( type ) {
			case "string": return new SXLString( value + "" );
			case "integer": return new SXLInteger( (int)value );
			case "real": return new SXLReal( value );
			case "character" : return new SXLCharacter( (char)value );
			default: throw new Exception("Cannot cast from real to " + type);
		}
	}
	
}
