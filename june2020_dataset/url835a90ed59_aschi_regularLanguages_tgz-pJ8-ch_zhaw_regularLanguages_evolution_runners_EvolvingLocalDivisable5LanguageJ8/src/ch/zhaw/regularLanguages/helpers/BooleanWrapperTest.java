package ch.zhaw.regularLanguages.helpers;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.zhaw.regularLanguages.language.BooleanWrapper;

public class BooleanWrapperTest {

	@Test
	public void testEqualsEqTT() {
		BooleanWrapper b1 = new BooleanWrapper(true);
		BooleanWrapper b2 = new BooleanWrapper(true);
		
		assertTrue(b1.equals(b2));
	}

	@Test
	public void testEqualsEqFF() {
		BooleanWrapper b1 = new BooleanWrapper(false);
		BooleanWrapper b2 = new BooleanWrapper(false);
		
		assertTrue(b1.equals(b2));
	}
	
	@Test
	public void testEqualsNEQ() {
		BooleanWrapper b1 = new BooleanWrapper(true);
		BooleanWrapper b2 = new BooleanWrapper(false);
		
		assertTrue(!b1.equals(b2));
	}
}
