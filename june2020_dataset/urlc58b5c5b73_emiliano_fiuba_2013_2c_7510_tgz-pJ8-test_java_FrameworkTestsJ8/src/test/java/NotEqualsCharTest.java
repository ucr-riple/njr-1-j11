package test.java;

import main.java.Affirm;
import main.java.Test;

public class NotEqualsCharTest extends Test {

	public NotEqualsCharTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		notEqualsCharTest();
	}
	
    private void notEqualsCharTest() {
        assertEquals("notEqualsCharTest", true, Affirm.checkNotEquals('a', 'b'));
        assertEquals("notEqualsCharTest", false, Affirm.checkNotEquals('a', 'a'));
    }

}
