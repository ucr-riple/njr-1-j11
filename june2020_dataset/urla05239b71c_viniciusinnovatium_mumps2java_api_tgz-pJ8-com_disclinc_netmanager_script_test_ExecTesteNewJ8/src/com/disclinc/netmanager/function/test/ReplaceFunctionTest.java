package com.disclinc.netmanager.function.test;

import static mLibrary.mFnc.$replace;
import static mLibrary.mFnc.$translate;
import static org.junit.Assert.assertEquals;
import mLibrary.mFncUtil;

import org.junit.Test;

public class ReplaceFunctionTest {

	@Test
	public void testReplaceSubstring() {
		String string = "apenas xxx mais um teste xxx";
		String oldSubstring = "xxx";
		String newSubstring = "333";

		assertEquals("Fail on replace substring",
				"apenas 333 mais um teste 333",
				$replace(string, oldSubstring, newSubstring));
	}

	@Test
	public void testTranslateSubstring() {
		String string = "primeiro,.cliente,,..,";
		String oldSubstring = ",.";
		String newSubstring = "XY";

		assertEquals("Fail on translate substring", "primeiroXYclienteXXYYX",
				$translate(string, oldSubstring, newSubstring));
	}

	@Test
	public void testTranslateSubstringUsingNewSubstringDifferentLength() {
		String string = "primeiro,.cliente,,..,www.google.com ou   ddd";
		String oldSubstring = ",.w";
		String newSubstring = "XY";

		assertEquals("Fail on translate substring",
				"primeiroXYclienteXXYYXYgoogleYcom ou   ddd",
				$translate(string, oldSubstring, newSubstring));
	}

	@Test
	public void testTranslateNullSubstring() {
		String string = null;
		String oldSubstring = ",.w";
		String newSubstring = "XY";

		assertEquals("Fail on translate substring", "",
				$translate(string, oldSubstring, newSubstring));
	}

	@Test
	public void testTranslateSubstringEmptyOldSubstring() {
		String string = "nome do cliente";
		String oldSubstring = "";
		String newSubstring = "XY";

		assertEquals("Fail on translate substring", "nome do cliente",
				$translate(string, oldSubstring, newSubstring));
	}

	@Test
	public void testTranslateSubstringBlankOldSubstring() {
		String string = "nome do cliente";
		String oldSubstring = " ";
		String newSubstring = "XY";

		assertEquals("Fail on translate substring", "nomeXdoXcliente",
				$translate(string, oldSubstring, newSubstring));
	}

	@Test
	public void testCovertToUppercase() {
		assertEquals("Fail on converto to uppcase", "ASDF33",
				mFncUtil.zconvert("asdf33", "u"));
	}

	@Test
	public void testCovertToLowercase() {
		assertEquals("Fail on converto to lowercase", "asdf33",
				mFncUtil.zconvert("ASDF33", "L"));
	}

	@Test
	public void testCovertUsingModeNotDefined() {
		assertEquals("Fail on converto to lowercase", "asdf33",
				mFncUtil.zconvert("asdf33", "X"));
	}

}
