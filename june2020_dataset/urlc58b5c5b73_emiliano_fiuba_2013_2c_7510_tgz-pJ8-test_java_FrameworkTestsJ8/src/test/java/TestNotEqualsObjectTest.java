package test.java;

import main.java.Affirm;
import main.java.Test;

public class TestNotEqualsObjectTest extends Test {

	public TestNotEqualsObjectTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		testNotEqualsObjectTest();
	}

	private void testNotEqualsObjectTest() {
		assertNotEquals("testNotEqualsObjectTest", "Hello", "ByeBye");
    }
}
