package com.disclinc.netmanager.variable.test;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;

public class DataStructureUtilTest {
	private Object[] start = new Object[] { 1, 2, 3, 4 };
	private Object[] end = new Object[] { 5, 6, 7, 8, 9, 0 };
	private Object[] query = new Object[] { "^www001", "x", "1", "2", "3" };

	@Test
	public void testConcat() {
		Object[] concat = DataStructureUtil.concat(start, end);
		Object[] result = new Object[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
		Assert.assertTrue("All elements must have the same value",
				Arrays.equals(result, concat));

	}

	@Test
	public void testConcatLastElement() {
		Object[] concat = DataStructureUtil.concat(start, 88);
		Object[] result = new Object[] { 1, 2, 3, 4, 88 };
		Assert.assertTrue("All elements must have the same value",
				Arrays.equals(result, concat));
	}

	@Test
	public void testConcatSinceLastElement() {
		Object[] concat = DataStructureUtil.concatSinceLast(start, end);
		Object[] result = new Object[] { 1, 2, 3, 5, 6, 7, 8, 9, 0 };
		Assert.assertTrue("All elements must have the same value",
				Arrays.equals(result, concat));
	}

	@Test
	public void testGenerateKeyOfTheSubscripts() {
		Assert.assertEquals("^www001~x~1~2~3",
				DataStructureUtil.generateKey(query));
	}

	@Test
	public void testGenerateKeyOfParentSubscripts() {
		Assert.assertEquals("^www001~x~1~2",
				DataStructureUtil.generateKeyOfParent(query));
	}

	@Test
	public void testGenerateKeyToLikeQuery() {
		Assert.assertEquals("x~1~2~",
				DataStructureUtil.generateKeyToLikeQuery(query));
	}

}
