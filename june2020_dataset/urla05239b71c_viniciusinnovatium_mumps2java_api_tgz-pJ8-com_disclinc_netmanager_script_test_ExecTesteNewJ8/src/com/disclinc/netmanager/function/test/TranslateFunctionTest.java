package com.disclinc.netmanager.function.test;

import static org.junit.Assert.assertEquals;
import mLibrary.mFnc;

import org.junit.Test;

public class TranslateFunctionTest {
	@Test
	public void testJustifyTwoArgs() {

		Object string = "1,462,543";
		Object oldCharsequence	 = ",";	
		
		String expected = "1462543";
		Object actual = mFnc.$translate(string, oldCharsequence);
		
		assertEquals("Fail on translate with two arguments.", expected, actual);
	}

	@Test
	public void testJustifyThreeArgs() {

		Object string = "06-23-1993";
		Object oldCharsequenceA	 = "19";	
		String newCharsequenceA	 = "9";	
		
		Object actualA = mFnc.$translate(string, oldCharsequenceA, newCharsequenceA);

		Object oldCharsequence	 = "-";	
		String newCharsequence	 = "/";	
		
		String expected = "06/23/93";
		Object actual = mFnc.$translate(actualA, oldCharsequence, newCharsequence);		
		
		assertEquals("Fail on translate with Three arguments.", expected, actual);
	}
	
	
}
