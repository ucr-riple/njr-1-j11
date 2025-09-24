package test.java;

import main.java.Affirm;
import main.java.Test;

public class TestEqualsObjectTest extends Test {

	public TestEqualsObjectTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		testEqualsObjectTest();
	}

	private void testEqualsObjectTest() {
		assertEquals("testEqualsObjectTest", "Hello", "Hello");
    }
}
