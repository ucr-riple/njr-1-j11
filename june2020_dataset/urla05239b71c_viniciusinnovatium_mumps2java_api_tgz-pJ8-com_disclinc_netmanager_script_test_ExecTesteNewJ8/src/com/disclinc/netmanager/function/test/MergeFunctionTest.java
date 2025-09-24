package com.disclinc.netmanager.function.test;

import static junit.framework.Assert.*;
import mLibrary.mContext;

import org.junit.Test;

public class MergeFunctionTest {

	@Test
	public void testMergingTwoVariableWithoutSubscripts() {
		mContext m$ = new mContext();
		m$.var("x").set(2);
		m$.var("y").set(1);
		m$.merge(m$.var("y"), m$.var("x"));
		
		assertEquals("The value of the merged variable must be the same", m$.var("y").get(), m$.var("x").get());
	}
	
	@Test
	public void testMergingTwoVariableWithSubscripts() {
		mContext m$ = new mContext();
		m$.var("x").set(2);
		m$.var("x", "1").set(2);
		m$.var("y").set(1);
		m$.merge(m$.var("y"), m$.var("x"));
		
		assertEquals("The value of the merged variable must be the same", m$.var("y").get(), m$.var("x").get());
		
		assertEquals("The value of existente subscript must be the same", m$.var("y", "1").get(), m$.var("x", "1").get());
	}
	
	@Test
	public void testMergingWithUndefinedVariableWithSubscript() {
		mContext m$ = new mContext();
		// The x variable has no value, so he is undefined.
		m$.var("x", "1").set("2");
		m$.var("y").set("1");
		m$.merge(m$.var("y"), m$.var("x"));
		
		assertEquals("The value of the merged variable must be unchanged because the x variable is undefined", "1", m$.var("y").get());
		
		assertEquals("The value of existente subscript must be the same", m$.var("y", "1").get(), m$.var("x", "1").get());
	}
	
	@Test
	public void testMergingTwoVariableWithSubscriptsConflicting() {
		mContext m$ = new mContext();
		m$.var("a", "1").set("1");
		m$.var("a", "1", "1").set("11");
		m$.var("a", "2").set("2");
		m$.var("a", "2", "1").set("21");
		
		m$.var("b").set("10");
		m$.var("b", "1", "1").set("33");
		m$.var("b", "1").set("3");
		m$.merge(m$.var("b"), m$.var("a"));
		
		assertEquals("The value of the merged variable must be unchanged because the x variable is undefined", "10", m$.var("b").get());
		
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "1", "1").get(), m$.var("a", "1", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "1").get(), m$.var("a", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2").get(), m$.var("a", "2").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2", "1").get(), m$.var("a", "2", "1").get());
		
	}
	
	@Test
	public void testMergingTwiceBetweenTheSameTarget() {
		mContext m$ = new mContext();
		m$.var("a", "1").set("1");
		m$.var("a", "1", "1").set("11");
		m$.var("a", "2").set("2");
		m$.var("a", "2", "1").set("21");
		
		m$.var("b").set("10");
		m$.var("b", "1", "1").set("33");
		m$.var("b", "1").set("3");
		m$.merge(m$.var("b"), m$.var("a"));
		m$.merge(m$.var("b", "2", "1"), m$.var("a"));
		
		assertEquals("The value of the merged variable must be unchanged because the x variable is undefined", "10", m$.var("b").get());
		
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "1", "1").get(), m$.var("a", "1", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "1").get(), m$.var("a", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2").get(), m$.var("a", "2").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2", "1").get(), m$.var("a", "2", "1").get());
		
		assertEquals("This variable value was not changed because the a variable is undefined", "21", m$.var("b", "2", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2", "1", "1").get(), m$.var("a", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2", "1", "1", "1").get(), m$.var("a", "1", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2", "1", "2", "1").get(), m$.var("a", "2", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2", "1", "2", "2").get(), m$.var("a", "2", "2").get());
	}

	@Test 
	public void testMergeTwoHeadsOfDifferentTypesOfVariables(){
		mContext m$ = new mContext();
		m$.var("%a").set("1");
		m$.var("b").set("10");
		m$.merge(m$.var("b"), m$.var("%a"));
		
		assertEquals("This subscripts was merged and must be the same", m$.var("b").get(), m$.var("%a").get());
	}
	
	@Test 
	public void testSimlpeMergeBetweenTwoDifferentTypesOfVariablesWithoutHead(){
		mContext m$ = new mContext();
		m$.var("%a", "1", "1").set("1");
		m$.var("b").set("10");
		m$.merge(m$.var("b"), m$.var("%a"));
		
		assertEquals("This subscripts was not merged and must be the same before the merge", "10", m$.var("b").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "1", "1").get(), m$.var("%a", "1", "1").get());
		assertEquals("This subscripts was not merged and should be undefined, i.e, does have anyone value", null, m$.var("b", "1").get());
	}
	
	@Test 
	public void testDoubleMergeBetweenTwoDifferentKindsOfVariables(){
		mContext m$ = new mContext();
		m$.var("%a", "1").set("1");
		m$.var("%a", "1", "1").set("11");
		m$.var("%a", "2").set("2");
		m$.var("%a", "2", "1").set("21");
		
		m$.var("b").set("10");
		m$.var("b", "1", "1").set("33");
		m$.var("b", "1").set("3");
		m$.merge(m$.var("b"), m$.var("%a"));
		m$.merge(m$.var("b", "2", "1"), m$.var("%a"));
		

		assertEquals("The value of the merged variable must be unchanged because the x variable is undefined", "10", m$.var("b").get());
		
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "1", "1").get(), m$.var("%a", "1", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "1").get(), m$.var("%a", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2").get(), m$.var("%a", "2").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2", "1").get(), m$.var("%a", "2", "1").get());
		
		assertEquals("This variable value was not changed because the a variable is undefined", "21", m$.var("b", "2", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2", "1", "1").get(), m$.var("%a", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2", "1", "1", "1").get(), m$.var("%a", "1", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2", "1", "2", "1").get(), m$.var("%a", "2", "1").get());
		assertEquals("This subscripts was merged and must be the same", m$.var("b", "2", "1", "2", "2").get(), m$.var("%a", "2", "2").get());
	}
}
