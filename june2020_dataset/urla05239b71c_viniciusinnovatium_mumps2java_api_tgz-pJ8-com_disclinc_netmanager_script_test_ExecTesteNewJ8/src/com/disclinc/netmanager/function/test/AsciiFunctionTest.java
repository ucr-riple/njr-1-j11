package com.disclinc.netmanager.function.test;

import static mLibrary.mFnc.$select;
import static org.junit.Assert.assertEquals;
import mLibrary.BooleanObject;
import mLibrary.mFnc;

import org.junit.Test;

public class AsciiFunctionTest {

	@Test
	public void testAsciiTwoArgs() {

		Object expression = "TEST";
		Object position	 = "3";	
		
		Integer expected = 83;
		Object actual = mFnc.$ascii(expression, position);
		
		assertEquals("Fail on convert with two arguments.", expected, actual);
	}

	@Test
	public void testAsciiOneArgs() {
		
		Object expression = "W";	
		
		Integer expected = 87;
		Object actual = mFnc.$ascii(expression);
		
		assertEquals("Fail on convert with one arguments.", expected, actual);
	}
	
}
