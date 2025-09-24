package test.java;

import main.java.Affirm;
import main.java.Test;

public class EqualsCharTest extends Test {

	public EqualsCharTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		equalsCharTest();
	}
    
	private void equalsCharTest() {
        assertEquals("equalsCharTest", true, Affirm.checkEquals('a', 'a'));
        assertEquals("equalsCharTest", false, Affirm.checkEquals('a', 'b'));
    }
}
