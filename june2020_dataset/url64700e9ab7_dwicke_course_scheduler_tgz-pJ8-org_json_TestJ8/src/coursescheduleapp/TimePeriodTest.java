/**
 * 
 */
package coursescheduleapp;

import java.util.TreeSet;

import org.junit.Test;

import coursescheduleapp.model.CourseTime;
import coursescheduleapp.model.TimePeriod;
import junit.framework.TestCase;
/**
 * @author Drew
 *
 */
public class TimePeriodTest extends TestCase{

	private CourseTime eightAM;
	private CourseTime nineAM;
	private CourseTime twelvePM;
	private CourseTime onePM; 
	private CourseTime twoPM;
	private CourseTime fiveFourtyFivePM;
    private CourseTime sixThirtyPM;
   
    
    private TimePeriod morning, afternoon, evening, allDay;
    
	
	
	@Override
	protected void setUp(){
		
		
		
		eightAM= new CourseTime(480);
		nineAM= new CourseTime(540);
		
		morning = new TimePeriod(eightAM, nineAM);
		
		twelvePM = new CourseTime(720);
		
		onePM=new CourseTime(780);
		twoPM=new CourseTime(840);
		
		afternoon = new TimePeriod(onePM,twoPM);
		
		fiveFourtyFivePM=new CourseTime(1065);
		sixThirtyPM=new CourseTime(1110);
		
		evening = new TimePeriod(fiveFourtyFivePM, sixThirtyPM);
		
		allDay = new TimePeriod(eightAM, sixThirtyPM);
		
	}
	
	@Test
	public void testPayload()
	{
		TimePeriod per = new TimePeriod(onePM, twoPM, afternoon);
		assertEquals(afternoon, per.getPayload());
		per.setPayload(evening);
		assertEquals(evening, per.getPayload());
	}
	
	/**
	 * Test method for {@link coursescheduleapp.model.TimePeriod#timeInPeriod()}.
	 */
	@Test
	public void testTimeInPeriod() {
		assertEquals(morning.timeInPeriod(), 60);
		assertEquals(afternoon.timeInPeriod(), 60);
		assertEquals(evening.timeInPeriod(), 45);
	}

	/**
	 * Test method for {@link coursescheduleapp.model.TimePeriod#getStart()}.
	 */
	@Test
	public void testGetStart() {
		assertEquals(morning.getStart(), eightAM);
		assertEquals(afternoon.getStart(), onePM);
		assertEquals(evening.getStart(), fiveFourtyFivePM);
		assertEquals(allDay.getStart(), eightAM);
		
	}

	/**
	 * Test method for {@link coursescheduleapp.model.TimePeriod#getEnd()}.
	 */
	@Test
	public void testGetEnd() {
		assertEquals(morning.getEnd(), nineAM);
		assertEquals(afternoon.getEnd(), twoPM);
		assertEquals(evening.getEnd(), sixThirtyPM);
		assertEquals(allDay.getEnd(), sixThirtyPM);
	}

	/**
	 * Test method for {@link coursescheduleapp.model.TimePeriod#equals(coursescheduleapp.model.TimePeriod)}.
	 */
	@Test
	public void testEqualsTimePeriod() {
		assertEquals(morning.equals(allDay), true);
		assertEquals(afternoon.equals(allDay), true);
		assertEquals(evening.equals(allDay), true);
		assertEquals(morning.equals(afternoon), false);
		assertEquals(afternoon.equals(afternoon), true);
		assertEquals(afternoon.equals(evening), false);
	}

	/**
	 * Test method for {@link coursescheduleapp.model.TimePeriod#compareTo(coursescheduleapp.model.TimePeriod)}.
	 */
	@Test
	public void testCompareTo() {
		TreeSet<TimePeriod> set = new TreeSet<TimePeriod>();
		set.add(morning);
		set.add(evening);
		set.add(afternoon);
		set.add(allDay);
		assertEquals(set.size(), 3);
	}

	/**
	 * Test method for {@link coursescheduleapp.model.TimePeriod#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(morning.toString(), "Start: 8:00 am  End: 9:00 am");
		assertEquals(afternoon.toString(), "Start: 1:00 pm  End: 2:00 pm");
		assertEquals(evening.toString(), "Start: 5:45 pm  End: 6:30 pm");
		assertEquals(allDay.toString(), "Start: 8:00 am  End: 6:30 pm");

	}

}
