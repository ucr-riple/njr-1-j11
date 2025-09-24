package com.miguel.sxl;

public interface SXLValue {
	
	public String getType();
	
	public void setValue(SXLValue other);
	
	public SXLValue add(SXLValue other);
	public SXLValue subtract(SXLValue other);
	public SXLValue multiply(SXLValue other);
	public SXLValue divide(SXLValue other);
	
	public SXLValue and(SXLValue other);
	public SXLValue or(SXLValue other);
	public SXLValue not();
	public SXLValue unary(String op);
	
	public SXLValue isGreaterThan(SXLValue other);
	public SXLValue isLessThan(SXLValue other);
	public SXLValue isEqualTo(SXLValue other);
	
	public void fromString(String s) throws Exception;
	public SXLValue castInto(String type) throws Exception;
}
