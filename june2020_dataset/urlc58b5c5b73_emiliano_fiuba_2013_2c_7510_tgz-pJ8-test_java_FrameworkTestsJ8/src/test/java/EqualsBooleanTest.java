package test.java;

import main.java.Affirm;
import main.java.Test;

public class EqualsBooleanTest extends Test {

	public EqualsBooleanTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		equalsBooleanTest();
	}

	private void equalsBooleanTest() {
		assertEquals("equalsBooleanTest", true, Affirm.checkEquals(true, true));
        assertEquals("equalsBooleanTest", false, Affirm.checkEquals(true, false));
    }
}
