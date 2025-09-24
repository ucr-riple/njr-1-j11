package com.disclinc.netmanager.function.test;

import static junit.framework.Assert.*;
import mLibrary.mOp;

import org.junit.Test;

public class BooleanOperatorTest {
	@Test
	public void testAndOperatorOverIntegers() {
		assertFalse(mOp.And(0, 0));
		assertFalse(mOp.And(1, 0));
		assertFalse(mOp.And(0, 1));
		assertTrue(mOp.And(1, 1));
	}
	
	@Test
	public void testAndOperatorOverStrings() {
		assertFalse(mOp.And(0, "0"));
		assertFalse(mOp.And("0", "0"));
		assertFalse(mOp.And(1, "teste"));
		assertTrue(mOp.And(1, "1teste"));
		assertFalse(mOp.And(0, "teste"));
		assertTrue(mOp.And("+1", "1"));
		assertTrue(mOp.And("+1", "---+++1"));		
	}

	@Test
	public void testOrOperatorOverIntegers() {
		assertFalse(mOp.Or(0, 0));
		assertTrue(mOp.Or(1, 0));
		assertTrue(mOp.Or(0, 1));
		assertTrue(mOp.Or(1, 1));
	}

	@Test
	public void testOrOperatorOverStrings() {
		assertFalse(mOp.Or(0, "0"));
		assertFalse(mOp.Or("0", "0"));
		assertTrue(mOp.Or(1, "teste"));
		assertTrue(mOp.Or(1, "1teste"));
		assertFalse(mOp.Or(0, "teste"));
	}
}
