package test.java;

import main.java.Affirm;
import main.java.Test;

public class NotEqualsIntTest extends Test {

	public NotEqualsIntTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		notEqualsIntTest();
	}
	
    private void notEqualsIntTest() {
        assertEquals("notEqualsIntTest", true, Affirm.checkNotEquals(1, 2));
        assertEquals("notEqualsIntTest", false, Affirm.checkNotEquals(1, 1));
    }

}
