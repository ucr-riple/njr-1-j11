package com.disclinc.netmanager.function.test;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import mLibrary.mContext;
import mLibrary.mFnc;

public class DataFunctionTest {
	private mContext m$ = new mContext();

	@Before
	public void init() {
		m$.var("x").set(1);
		m$.var("x", "11").set(null);
		m$.var("x", "12").set(12);
		m$.var("x", "3").set(null);
		m$.var("x", "3", "33").set(33);
		m$.var("x", "4").set(4);
		m$.var("y").set(null);
	}

	@Test
	public void testCheckingUndefinedVariableAndWhichWithoutChildren() {
		Assert.assertEquals("This variable is undefined and has no one child",
				0, mFnc.$data(m$.var("y")));
		Assert.assertEquals("This variable is undefined and has no one child",
				0, mFnc.$data(m$.var("x", "11")));
	}

	@Test
	public void testCheckingVariableAndWithoutChildren() {
		Assert.assertEquals("This variable is undefined and has no one child",
				1, mFnc.$data(m$.var("x", "4")));
	}

	@Test
	public void testCheckingUndefinedVariableAndWithChildren() {
		Assert.assertEquals("This variable is undefined and has no one child",
				10, mFnc.$data(m$.var("x", "3")));
	}

	@Test
	public void testCheckingVariableAndWithChildren() {
		Assert.assertEquals("This variable is undefined and has no one child",
				11, mFnc.$data(m$.var("x")));
	}
}
