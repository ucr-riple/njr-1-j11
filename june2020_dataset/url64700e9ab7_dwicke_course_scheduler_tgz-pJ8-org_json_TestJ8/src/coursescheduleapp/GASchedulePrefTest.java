/**
 * 
 */
package coursescheduleapp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import coursescheduleapp.model.*;

/**
 * @author dwicke
 *
 */
public class GASchedulePrefTest {

	GASchedulePref gaPref;
	SchedulePref schedulePref;
	DayPrefs dPrefs;
	Schedule sched;
	ArrayList<Course> list;
	
	ArrayList<Day> days;
	ArrayList<String> daySt;
	
	
	@Before
	public void setUp() throws Exception {
		
		sched = new Schedule();
		
		Course testOne = new Course();
		
		days = new ArrayList<Day>();
		days.add(Day.M);
		days.add(Day.W);
		days.add(Day.F);
		
		daySt = new ArrayList<String>();
		daySt.add(DayPref.MON.toString());
		daySt.add(DayPref.WED.toString());
		
		testOne.putValue(CourseData.DAY.toString(), days);
		
		sched.addCourse(testOne);
	}
	
	@Test
	public void testGetFitness()
	{
		dPrefs = new DayPrefs(daySt);
		schedulePref = new SchedulePref();
		schedulePref.setPref(DayPref.DayPref.toString(), dPrefs);
		
		gaPref = new GASchedulePref(schedulePref);
		assertTrue(2.7 == gaPref.getFitness(sched));
	}

}
