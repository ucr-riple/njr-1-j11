package com.disclinc.netmanager.function.test;

import static mLibrary.mFnc.$select;
import static org.junit.Assert.assertEquals;
import mLibrary.BooleanObject;
import mLibrary.mFnc;

import org.junit.Test;

public class Select2FunctionTest {

	@Test
	public void testSelectSomeValidConditional() {
		Object bool1 = false;
		Object var1 = "amarelo";
		Object bool2 = false;
		Object var2 = "vermelho";
		Object bool3 = true;
		Object var3 = "azul";		
		
		String expected = "azul";
		Object actual = mFnc.$select(bool1, var1, bool2, var2, bool3, var3);
		
		assertEquals("Fail on select a valid condition", expected, actual);
	}

}
