package test.java;

import main.java.Affirm;
import main.java.Test;

public class NotEqualsBooleanTest extends Test {

	public NotEqualsBooleanTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		notEqualsBooleanTest();
	}
	
    private void notEqualsBooleanTest() {
    	assertEquals("notEqualsBooleanTest", true, Affirm.checkNotEquals(true, false));
        assertEquals("notEqualsBooleanTest", false, Affirm.checkNotEquals(true, true));
    }

}
