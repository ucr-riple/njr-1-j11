package junit;

import java.util.Calendar;

import junit.framework.TestCase;
import ninja.Time;

public class TimeTest extends TestCase {

	public void testTimeNinjaLong() {
		Time ninja = new Time(1234567890);
		assertTrue(ninja.getTimeScale() == 1);
	}

	public void testTimeNinja() {
		new Time();
	}

	public void testSetTimeScale() {
		Time ninja = new Time();
		ninja.setTimeScale(1);
		assertTrue(ninja.getTimeScale() == 1);
	}

	public void testGetTime() {
		Time ninja = new Time();
		assertTrue(ninja.getTime() == Calendar.getInstance().getTimeInMillis() / 1000);
	}

	public void testFormatTime() {
		fail("Not yet implemented");
	}

	public void testConvertToNinjaMilliseconds() {
		fail("Not yet implemented");
	}

	public void testConvertToRealMinutes() {
		fail("Not yet implemented");
	}

}
