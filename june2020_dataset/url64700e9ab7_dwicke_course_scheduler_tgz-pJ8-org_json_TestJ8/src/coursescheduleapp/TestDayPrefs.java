package coursescheduleapp;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import coursescheduleapp.model.Course;
import coursescheduleapp.model.CourseData;
import coursescheduleapp.model.Day;
import coursescheduleapp.model.DayPref;
import coursescheduleapp.model.DayPrefs;
import coursescheduleapp.model.Schedule;

public class TestDayPrefs {

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
	public void testDayPrefs() {

		
		dPrefs = new DayPrefs();
		assertTrue(0.0 == dPrefs.getFitness(sched));
	}

	@Test
	public void testDayPrefsString() {
		dPrefs = new DayPrefs(DayPref.THUR.toString());
		assertTrue(-.9 == dPrefs.getFitness(sched));
	}

	@Test
	public void testDayPrefsArrayListOfString() {
		dPrefs = new DayPrefs(daySt);

		assertTrue(2.7 == dPrefs.getFitness(sched));
	}

	@Test
	public void testSetDayPrefs() {
		dPrefs = new DayPrefs();
		assertTrue(0.0 == dPrefs.getFitness(sched));
		
		dPrefs.setDayPrefs(daySt);
		assertTrue(2.7 == dPrefs.getFitness(sched));
	}

	@Test
	public void testToString() {
		dPrefs = new DayPrefs();
		System.out.println(dPrefs.toString());
		assertTrue("".equals(dPrefs.toString()));
	}

	@Test
	public void testGetFitnessSchedule() {
		dPrefs = new DayPrefs(daySt);

		assertTrue(2.7 == dPrefs.getFitness(sched));

	}

	

	@Test
	public void testAddPref() {

		dPrefs = new DayPrefs(daySt);
		dPrefs.addPref(DayPref.FRI.toString());

		assertTrue(.9*4 == dPrefs.getFitness(sched));
	}

}
