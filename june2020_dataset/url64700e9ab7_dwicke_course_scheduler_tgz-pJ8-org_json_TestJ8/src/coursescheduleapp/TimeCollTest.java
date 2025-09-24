package coursescheduleapp;

import java.util.TreeSet;

import junit.framework.TestCase;

import org.junit.Test;

import coursescheduleapp.model.CourseTime;
import coursescheduleapp.model.TimeCollection;
import coursescheduleapp.model.TimePeriod;

public class TimeCollTest extends TestCase{


	private CourseTime eightAM;
	private CourseTime nineAM;
	private CourseTime twelvePM;
	private CourseTime onePM; 
	private CourseTime twoPM;
	private CourseTime fiveFourtyFivePM;
    private CourseTime sixThirtyPM;
   
    
    private TimePeriod morning, afternoon, evening, allDay;
    
	private TimeCollection blank, coll;
	
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
		
		blank = new TimeCollection();
		
	}

	@Test
	public void testTimeCollection()
	{
		assertEquals(blank.getNumInCollection(), 0);
	}
	@Test
	public void testTimeCollectionTreeSetOfTimePeriod() {
		TreeSet<TimePeriod> set = new TreeSet<TimePeriod>();
		set.add(morning);
		set.add(evening);
		set.add(afternoon);
		set.add(allDay);
		coll = new TimeCollection(set);
		assertEquals(coll.getNumInCollection(), 3);
	}

	@Test
	public void testGetNumInCollection() {
		assertEquals(blank.getNumInCollection(), 0);
	}

	@Test
	public void testGetCollection() {
		TreeSet<TimePeriod> set = new TreeSet<TimePeriod>();
		set.add(morning);
		set.add(evening);
		set.add(afternoon);
		set.add(allDay);
		coll = new TimeCollection(set);
		assertEquals(coll.getCollection(), set);
	}

	@Test
	public void testSetCollection() {
		TreeSet<TimePeriod> set = new TreeSet<TimePeriod>();
		set.add(morning);
		set.add(evening);
		set.add(afternoon);
		set.add(allDay);
		blank.setCollection(set);
		assertEquals(blank.getCollection(), set);
	}

	@Test
	public void testAddPeriod() {
		TimePeriod per = new TimePeriod(nineAM, twelvePM);
		blank.addPeriod(per);
		assertEquals(blank.getNumInCollection(), 1);
	}

}
