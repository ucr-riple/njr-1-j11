package coursescheduleapp;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import coursescheduleapp.model.Course;
import coursescheduleapp.model.CourseData;
import coursescheduleapp.model.CourseTime;
import coursescheduleapp.model.Day;
import coursescheduleapp.model.Schedule;
import coursescheduleapp.model.TimePeriod;
import coursescheduleapp.model.TimePref;
import coursescheduleapp.model.TimePrefs;

public class TestTimePrefs {
	
	TimePrefs pref;
	Schedule sched;
	ArrayList<Course> list;
	private TimePeriod per;
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
	public void testTimePrefs() {
		pref = new TimePrefs();
		assertTrue(0.0 == pref.getFitness(sched));
	}

	@Test
	public void testTimePrefsString() {
		pref = new TimePrefs(TimePref.MORNING.toString());

		assertTrue((double).25 * days.size() == pref.getFitness(sched));
	}

	@Test
	public void testSetTimePref() {

		pref = new TimePrefs();
		pref.setTimePref(TimePref.EVENING.toString());
		
		assertTrue(0.0 == pref.getFitness(sched));
	}

	@Test
	public void testGetFitnessSchedule() {
		pref = new TimePrefs(TimePref.MORNING.toString());

		assertTrue((double).25 * days.size() == pref.getFitness(sched));
		
		pref = new TimePrefs(TimePref.EVENING.toString());
		assertTrue(0.0 == pref.getFitness(sched));
	}

	@Test
	public void testAddPref() {
		pref = new TimePrefs();
		pref.addPref(TimePref.EVENING.toString());
		
		assertTrue(0.0 == pref.getFitness(sched));
	}

}
