package test.java;

import main.java.Affirm;
import main.java.Test;

public class TestEqualsBooleanTest extends Test {

	public TestEqualsBooleanTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		testEqualsBooleanTest();
	}

	private void testEqualsBooleanTest() {
		assertEquals("testEqualsBooleanTest", "Hello", "Hello");
    }
}
