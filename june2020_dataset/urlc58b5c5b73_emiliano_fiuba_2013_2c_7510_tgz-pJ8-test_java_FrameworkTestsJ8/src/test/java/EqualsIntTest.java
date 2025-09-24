package test.java;

import main.java.Affirm;
import main.java.Test;

public class EqualsIntTest extends Test {

	public EqualsIntTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		equalsIntTest();
	}
    
	private void equalsIntTest() {
        assertEquals("equalsIntTest", true, Affirm.checkEquals(1, 1));
        assertEquals("equalsIntTest" ,false, Affirm.checkEquals(1, 2));
    }
}
