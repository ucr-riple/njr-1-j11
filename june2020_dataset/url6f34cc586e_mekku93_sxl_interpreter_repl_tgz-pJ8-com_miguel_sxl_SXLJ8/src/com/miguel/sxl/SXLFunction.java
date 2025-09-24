package com.miguel.sxl;

import java.util.ArrayList;

public class SXLFunction implements SXLValue {

	private SXLValue returnType;
	private ArrayList<SXLParam> params;
	private ASTBlock node;
	
	
	public SXLFunction(SXLValue returnType, ArrayList<SXLParam> params, ASTBlock node) {
		this.returnType = returnType;
		this.params = params;
		this.node = node;
	}
	
	public SXLValue getReturnType() {
		return this.returnType;
	}
	
	public ArrayList<SXLParam> getParams() {
		return params;
	}
	
	public ASTBlock getNode() {
		return node;
	}
	
	@Override
	public String getType() {
		return "function";
	}
	
	@Override
	public void setValue(SXLValue other) {
	}

	
	@Override
	public String toString() {
		return Utils.generateFunctionScopeName("", params) + " : " + returnType.getType();
	}
	
	// NO OPERATIONS SUPPORTED
	
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
	public void fromString(String s) throws Exception {}
	
	@Override
	public SXLValue castInto(String type) throws Exception {
		throw new Exception("Function type cannot be casted into " + type);
	}

}
