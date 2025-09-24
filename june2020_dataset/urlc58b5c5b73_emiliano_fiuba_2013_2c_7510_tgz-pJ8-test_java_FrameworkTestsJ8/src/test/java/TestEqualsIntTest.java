package test.java;

import main.java.Affirm;
import main.java.Test;

public class TestEqualsIntTest extends Test {

	public TestEqualsIntTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		testEqualsIntTest();
	}

	private void testEqualsIntTest() {
		assertEquals("testEqualsIntTest", 1, 1);
    }
}
