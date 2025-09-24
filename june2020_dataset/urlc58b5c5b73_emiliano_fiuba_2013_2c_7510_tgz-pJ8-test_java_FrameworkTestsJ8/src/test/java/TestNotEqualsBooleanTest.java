package test.java;

import main.java.Affirm;
import main.java.Test;

public class TestNotEqualsBooleanTest extends Test {

	public TestNotEqualsBooleanTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		testNotEqualsBooleanTest();
	}

	private void testNotEqualsBooleanTest() {
		assertNotEquals("testNotEqualsBooleanTest", true, false);
    }
}
