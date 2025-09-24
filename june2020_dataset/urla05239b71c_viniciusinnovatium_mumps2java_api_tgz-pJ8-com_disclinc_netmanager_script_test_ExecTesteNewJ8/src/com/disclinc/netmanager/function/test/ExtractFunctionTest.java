package com.disclinc.netmanager.function.test;

import static mLibrary.mFnc.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ExtractFunctionTest {

	private final String stringTest = "1234Alabama567";

	@Test
	public void testExtractDefault() {
		assertEquals("Fail to extract string as default value", "1",  $extract(stringTest));
	}
	
	@Test
	public void testExtractSomeElement() {
		assertEquals("Fail to extract string as default value", "A",  $extract(stringTest, 5));
	}
	
	@Test
	public void testExtractIntervalElement() {
		assertEquals("Fail to extract string as default value", "Alabama",  $extract(stringTest, 5, 11));
	}
	
	@Test
	public void testExtractNegativeStart() {
		assertEquals("Fail to extract string as default value", "1234Alabama",  $extract(stringTest, -5, 11));
	}
	
	@Test
	public void testExtractNegativeStartAndEnd() {
		assertEquals("Fail to extract string as default value", "",  $extract(stringTest, -5, -11));
	}
	
	@Test
	public void testExtractInvalidInterval() {
		assertEquals("Fail to extract string as default value", "",  $extract(stringTest, 50, 11));
	}
	
	@Test
	public void testNonPresenteElement() {
		assertEquals("Fail to extract string a non present element", "",  $extract(stringTest, 50));
	}
	
	@Test
	public void testNonPresenteElementWithNegativeIndex() {
		assertEquals("Fail to extract string a non present element", "",  $extract(stringTest, -9));
	}
	
	@Test
	public void testEndIndexLargerThantValueLength() {
		String x = "x";
		assertEquals("Fail to extract string a non present element", "x",  $extract(x, 1, 10));
	}
	
	@Test
	public void testEndIndexEqualValueLength() {
		String x = "x";
		assertEquals("Fail to extract string a non present element", "x",  $extract(x, 1, 1));
	}
}
