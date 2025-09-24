package test.java;

import main.java.Affirm;
import main.java.Test;

public class EqualsObjectTest extends Test {

	public EqualsObjectTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		equalsObjectTest();
	}

	private void equalsObjectTest() {
        assertEquals("equalsObjectTest", true, Affirm.checkEquals("Hello", "Hello"));
        assertEquals("equalsObjectTest", false, Affirm.checkEquals("Hello", "ByeBye"));
    }
}
