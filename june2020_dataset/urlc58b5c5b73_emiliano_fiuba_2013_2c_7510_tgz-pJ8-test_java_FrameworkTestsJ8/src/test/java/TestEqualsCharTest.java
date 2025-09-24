package test.java;

import main.java.Affirm;
import main.java.Test;

public class TestEqualsCharTest extends Test {

	public TestEqualsCharTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		testEqualsCharTest();
	}

	private void testEqualsCharTest() {
		assertEquals("testEqualsCharTest", 'a', 'a');
    }
}
