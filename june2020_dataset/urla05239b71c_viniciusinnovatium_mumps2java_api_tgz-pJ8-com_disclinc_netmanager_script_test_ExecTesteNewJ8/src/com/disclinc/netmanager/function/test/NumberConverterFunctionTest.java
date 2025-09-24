package com.disclinc.netmanager.function.test;

import static mLibrary.mFnc.*;
import static org.junit.Assert.*;
import mLibrary.mFnc;
import mLibrary.mFncUtil;

import org.junit.Test;

public class NumberConverterFunctionTest {

	@Test
	public void testFormatInteger() {
		Object obj = "12345";
		Double dbl = mFncUtil.numberConverter(obj);
		Double expected = 12345d;
		assertEquals("Fail to generate number from a string",expected , dbl);
	}
	
	@Test
	public void testFormatDecimal() {
		Object obj = "+00049.95";
		Double dbl = mFncUtil.numberConverter(obj);
		Double expected = 49.95d;
		assertEquals("Fail to generate number from a string",expected , dbl);
	}	
	
	@Test
	public void testFormatAlpha() {
		Object obj = "-TEST";
		Double dbl = mFncUtil.numberConverter(obj);
		Double expected = 0D;
		assertEquals("Fail to generate number from a string",expected , dbl);
	}		
	
	@Test
	public void testFormatAlphaNum() {
		Object obj = "TESTE123.5";
		Double dbl = mFncUtil.numberConverter(obj);
		Double expected = 0D;
		assertEquals("Fail to generate number from a string",expected , dbl);
	}			
	
	@Test
	public void testFormatNumAlpha() {
		Object obj = "34.3ABCD";
		Double dbl = mFncUtil.numberConverter(obj);
		Double expected = 34.3D;
		assertEquals("Fail to generate number from a string",expected , dbl);
	}		

	@Test
	public void testFormatDoublePoints() {
		Object obj = "34.34.666";
		Double dbl = mFncUtil.numberConverter(obj);
		Double expected = 34.34D;
		assertEquals("Fail to generate number from a string",expected , dbl);
	}			
	
	@Test
	public void testFormatSign() {
		Object obj = "+-+-++---+34.34xxx";
		Double dbl = mFncUtil.numberConverter(obj);
		Double expected = -34.34D;
		assertEquals("Fail to generate number from a string",expected , dbl);
	}	
	
	@Test
	public void testFormatStartWithZero() {
		Object obj = "0000034.34xxx";
		Double dbl = mFncUtil.numberConverter(obj);
		Double expected = 34.34D;
		assertEquals("Fail to generate number from a string",expected , dbl);
	}			
}
