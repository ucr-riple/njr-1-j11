package test.java;

import main.java.Affirm;
import main.java.Test;

public class TestNotEqualsFloatTest extends Test {

	public TestNotEqualsFloatTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		testNotEqualsFloatTest();
	}

	private void testNotEqualsFloatTest() {
		assertNotEquals("testNotEqualsFloatTest", 1.1, 1.2);
    }
}
