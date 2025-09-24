package coursescheduleapp;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import coursescheduleapp.model.Course;
import coursescheduleapp.model.CourseData;
import coursescheduleapp.model.CourseSpacing;
import coursescheduleapp.model.CourseTime;
import coursescheduleapp.model.Day;
import coursescheduleapp.model.Schedule;
import coursescheduleapp.model.TimePeriod;
import coursescheduleapp.model.TimeSpacingPrefs;

public class TestTimeSpacingPrefs {

	private TimeSpacingPrefs pref;
	private Schedule sched;
	private ArrayList<Course> list;
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
		
		
		
		Course testTwo = new Course();
		per = new TimePeriod(new CourseTime(10,9), new CourseTime(11,0));
		
		testTwo.putValue(CourseData.TIME.toString(), per);
		
		days = new ArrayList<Day>();
		days.add(Day.M);
		days.add(Day.W);
		days.add(Day.F);
		
		testTwo.putValue(CourseData.DAY.toString(), days);
		
		
		
		list = new ArrayList<Course>();
		list.add(testOne);
		list.add(testTwo);
		
		
		sched.addCourse(testOne);
		sched.addCourse(testTwo);
		
		
	}

	@Test
	public void testTimeSpacingPrefs() {
		pref = new TimeSpacingPrefs();
		assertTrue(0.0 == pref.getFitness(sched));
		
	}

	@Test
	public void testTimeSpacingPrefsString() {
		pref = new TimeSpacingPrefs(CourseSpacing.TENMIN.toString());
		assertTrue(.1 * days.size() == pref.getFitness(sched));
	}

	@Test
	public void testSetSpacing() {

		pref = new TimeSpacingPrefs();
		pref.setSpacing(CourseSpacing.HOUR.toString());
		assertTrue(.1 * days.size() == pref.getFitness(sched));
	}

	@Test
	public void testGetFitnessSchedule() {
		pref = new TimeSpacingPrefs(CourseSpacing.TENMIN.toString());
		assertTrue(.1 * days.size() == pref.getFitness(sched));
	}

	

	@Test
	public void testToString() {
		pref = new TimeSpacingPrefs(CourseSpacing.TENMIN.toString());
		assertTrue(CourseSpacing.TENMIN.toString().equals(pref.toString()));
	}

	@Test
	public void testAddPref() {

		pref = new TimeSpacingPrefs();
		pref.addPref(CourseSpacing.HOUR.toString());
		assertTrue(.1 * days.size() == pref.getFitness(sched));
	}

}
