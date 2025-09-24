package com.disclinc.netmanager.function.test;

import static mLibrary.mFnc.$select;
import static org.junit.Assert.assertEquals;
import mLibrary.BooleanObject;
import mLibrary.mFnc;

import org.junit.Test;

public class FNumberFunctionTest {

	@Test
	public void testFNumber() {

		Object inumber = "6.25198";
		String format	 = "T+";	
		Object decimal	 = "3";	
		
		String expected = "6.252+";
		Object actual = mFnc.$fnumber(inumber, format, decimal);
		
		assertEquals("Fail on convert with two arguments.", expected, actual);
	}

	
}
