package test.java;

import main.java.Affirm;
import main.java.Test;

public class TestNotEqualsIntTest extends Test {

	public TestNotEqualsIntTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		testNotEqualsIntTest();
	}

	private void testNotEqualsIntTest() {
		assertNotEquals("testNotEqualsIntTest", 1, 2);
    }
}
