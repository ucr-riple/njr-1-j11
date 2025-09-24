package coursescheduleapp;


import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import coursescheduleapp.model.Course;
import coursescheduleapp.model.CourseData;
import coursescheduleapp.model.CourseTime;
import coursescheduleapp.model.Day;
import coursescheduleapp.model.LeastDayPref;
import coursescheduleapp.model.Schedule;
import coursescheduleapp.model.TimePeriod;



public class LeastDayPrefTest extends TestCase {

	LeastDayPref pref;
	Schedule sched;
	ArrayList<Course> list;
	TimePeriod per;
	private ArrayList<Day> days;
	
	@Before
	public void setUp() throws Exception {
		sched = new Schedule();
		list = new ArrayList<Course>();
		
		Course testOne = new Course();
		per = new TimePeriod(new CourseTime(9,0), new CourseTime(10,0));
		
		testOne.putValue(CourseData.TIME.toString(), per);
		
		days = new ArrayList<Day>();
		days.add(Day.M);
		days.add(Day.W);
		days.add(Day.F);
		
		testOne.putValue(CourseData.DAY.toString(), days);
		
		sched.addCourse(testOne);
	}

	@Test
	public void testGetFitness()
	{
		pref = new LeastDayPref();
		
		assertTrue((double) (1/3) == pref.getFitness(sched));
	}
	
	
}
