package net.slreynolds.ds.util;

import static org.junit.Assert.*;

import net.slreynolds.ds.internal.util.ReflectionUtil;
import net.slreynolds.ds.model.ReflectionException;

import org.junit.Test;

public class ReflectionUtilTest {

	public static class DummyClass {
		@SuppressWarnings("unused")
		private int one;
		@SuppressWarnings("unused")
		private double two;
		@SuppressWarnings("unused")
		private char three;
		@SuppressWarnings("unused")
		private float four;
		@SuppressWarnings("unused")
		private byte five;
		@SuppressWarnings("unused")
		private boolean six;
		@SuppressWarnings("unused")
		private Object obj;
		public DummyClass(int one, double two, char three, float four,
				byte five, boolean six) {
			this.one = one;
			this.two = two;
			this.three = three;
			this.four = four;
			this.five = five;
			this.six = six;
		}
		public void setObj(Object o) { this.obj = o; }
	}
	
	@Test
	public void testReflectionOfDummy() throws ReflectionException {
		DummyClass dummy = new DummyClass(1,2.0,'c',4.0f,(byte)5,true);
		Double objForDummy = new Double(9.0);
		dummy.setObj(objForDummy);
		assertEquals("dummy.one",1,ReflectionUtil.getInt(dummy, "one"));
		assertEquals("dummy.obj",objForDummy,ReflectionUtil.getRef(Double.class, dummy, "obj"));
	}

}
