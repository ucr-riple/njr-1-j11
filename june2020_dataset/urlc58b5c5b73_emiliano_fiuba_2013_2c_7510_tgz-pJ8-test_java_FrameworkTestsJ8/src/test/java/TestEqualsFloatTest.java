package test.java;

import main.java.Affirm;
import main.java.Test;

public class TestEqualsFloatTest extends Test {

	public TestEqualsFloatTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		testEqualsFloatTest();
	}

	private void testEqualsFloatTest() {
		assertEquals("testEqualsFloatTest", 1.1, 1.1);
    }
}
