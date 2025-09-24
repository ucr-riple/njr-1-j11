package test.java;

import main.java.Affirm;
import main.java.Test;

public class NotEqualsObjectTest extends Test {

	public NotEqualsObjectTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		notEqualsObjectTest();
	}

    private void notEqualsObjectTest() {
        assertEquals("notEqualsObjectTest", true, Affirm.checkNotEquals("Hello", "ByeBye"));
        assertEquals("notEqualsObjectTest", false, Affirm.checkNotEquals("Hello", "Hello"));
    }
}
