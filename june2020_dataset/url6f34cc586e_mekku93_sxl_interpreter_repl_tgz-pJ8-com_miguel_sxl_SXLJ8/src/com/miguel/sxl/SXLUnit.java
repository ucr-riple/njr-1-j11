package com.miguel.sxl;

/**
 * Wrapper class for the SXL unit type. This type supports no operations.
 * 
 * @author Miguel Muscat
 */
public class SXLUnit implements SXLValue {
	
	@Override
	public void setValue(SXLValue other) {
	}
	
	@Override
	public String getType() {
		return "unit";
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
	public SXLValue and(SXLValue other) {
		return null;
	}

	@Override
	public SXLValue or(SXLValue other) {
		return null;
	}

	@Override
	public SXLValue not() {
		return null;
	}
	
	@Override
	public SXLValue unary(String op) {
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
	}

	
	
	@Override
	public SXLValue castInto(String type) throws Exception {
		throw new Exception("Cannot cast from unit to " + type);
	}
	
}
