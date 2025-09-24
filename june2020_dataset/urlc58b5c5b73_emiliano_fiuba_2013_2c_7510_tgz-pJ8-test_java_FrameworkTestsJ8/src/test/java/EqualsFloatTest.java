package test.java;

import main.java.Affirm;
import main.java.Test;

public class EqualsFloatTest extends Test {

	public EqualsFloatTest(String newName) {
		super(newName);
	}

	@Override
	public void runTest() {
		equalsFloatTest();
	}
    
	private void equalsFloatTest() {
        assertEquals("equalsFloatTest", true, Affirm.checkEquals(1.1, 1.1));
        assertEquals("equalsFloatTest" ,false, Affirm.checkEquals(1.1, 1.2));
    }
}
