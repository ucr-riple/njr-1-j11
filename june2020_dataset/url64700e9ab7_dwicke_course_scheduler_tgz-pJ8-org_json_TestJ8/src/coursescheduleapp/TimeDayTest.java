package coursescheduleapp;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import coursescheduleapp.model.CourseTime;
import coursescheduleapp.model.Day;
import coursescheduleapp.model.TimeDayCollection;
import coursescheduleapp.model.TimePeriod;

public class TimeDayTest extends TestCase{


	private CourseTime eightAM;
	private CourseTime nineAM;
	private CourseTime twelvePM;
	private CourseTime onePM; 
	private CourseTime twoPM;
	private CourseTime fiveFourtyFivePM;
    private CourseTime sixThirtyPM;
   
    
    private TimePeriod morning, afternoon, evening, allDay;
    private ArrayList<Day> morningDays, afternoonDays, eveningDays, allDayDays;
    
    private TimeDayCollection tdColl;
    
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
		
		tdColl = new TimeDayCollection();
		
		morningDays = new ArrayList<Day>();
		morningDays.add(Day.M);
		this.morningDays.add(Day.T);
		afternoonDays = new ArrayList<Day>();
		afternoonDays.add(Day.M);
		afternoonDays.add(Day.T);
		eveningDays = new ArrayList<Day>();
		eveningDays.add(Day.M);
		eveningDays.add(Day.T);
		allDayDays = new ArrayList<Day>();
		allDayDays.add(Day.W);
		allDayDays.add(Day.F);
		
		tdColl.addTimeCollection(morning, morningDays);
		tdColl.addTimeCollection(afternoon, afternoonDays);
		tdColl.addTimeCollection(evening, eveningDays);
		
	}


	@Test
	public void testAddTimeCollection() {
		
		assertEquals(tdColl.getDaysInCollection().containsAll(afternoonDays), true);
		assertEquals(true, tdColl.addTimeCollection(allDay,allDayDays));
		ArrayList<Day> allDays = new ArrayList<Day>();
		allDays.add(Day.M);
		assertEquals(false, tdColl.addTimeCollection(allDay, allDays));
		
		assertEquals(tdColl.getCollection(Day.W).getNumInCollection(), 1);
		assertEquals(tdColl.getCollection(Day.F).getNumInCollection(), 1);
		
	}

	@Test
	public void testGetCollection() {
		assertEquals(3, tdColl.getCollection(Day.M).getNumInCollection());
	}

	@Test
	public void testHoursInDay() {
		assertEquals((int)tdColl.hoursInDay(Day.M), 10);
		assertEquals((int)tdColl.hoursInDay(Day.W), 0);
	}

	@Test
	public void testHoursInWeek() {
		assertEquals((int)tdColl.hoursInWeek(), 21);
	}

	@Test
	public void testNumDays() {
		assertEquals(tdColl.numDays(), 2);
	}

	@Test
	public void testGetDaysInCollection() {
		assertEquals(tdColl.getDaysInCollection().containsAll(eveningDays), true);
	}

}
