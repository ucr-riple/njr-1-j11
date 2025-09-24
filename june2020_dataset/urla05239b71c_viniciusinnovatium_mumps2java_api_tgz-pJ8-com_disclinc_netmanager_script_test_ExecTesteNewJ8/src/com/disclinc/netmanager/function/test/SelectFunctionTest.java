package com.disclinc.netmanager.function.test;

import static mLibrary.mFnc.*;
import static org.junit.Assert.*;
import mLibrary.BooleanObject;

import org.junit.Test;

public class SelectFunctionTest {

	@Test
	public void testSelectSomeValidConditional() {
		BooleanObject condition1 = BooleanObject.valueOf(false, "amarelo");
		BooleanObject condition2 = BooleanObject.valueOf(true, "vermelho");
		BooleanObject condition3 = BooleanObject.valueOf(false, "azul");

		assertEquals("Fail on select a valid condition", "vermelho",
				$select(condition1, condition2, condition3));
	}

	@Test
	public void testSelectNoValidConditional() {
		BooleanObject condition1 = BooleanObject.valueOf(false, "amarelo");
		BooleanObject condition2 = BooleanObject.valueOf(false, "vermelho");
		BooleanObject condition3 = BooleanObject.valueOf(false, "azul");

		assertEquals("Fail on select a valid condition", null,
				$select(condition1, condition2, condition3));
	}

	@Test
	public void testSelectNullConditionalSequence() {
		assertEquals("Fail on select a valid condition", null, $select(null));
	}

	@Test
	public void testSelectEmptyConditionalSequence() {
		assertEquals("Fail on select a valid condition", null,
				$select(new BooleanObject[] {}));
	}

}
