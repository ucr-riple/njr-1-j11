package test.java;

import main.java.Affirm;
import main.java.Test;

public class TestNotEqualsCharTest extends Test {

	public TestNotEqualsCharTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		testNotEqualsCharTest();
	}

	private void testNotEqualsCharTest() {
		assertNotEquals("testNotEqualsCharTest", 'a', 'b');
    }
}
