package coursescheduleapp;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import coursescheduleapp.model.Course;
import coursescheduleapp.model.CourseData;
import coursescheduleapp.model.CourseTime;
import coursescheduleapp.model.Day;
import coursescheduleapp.model.InstructorPref;
import coursescheduleapp.model.Schedule;
import coursescheduleapp.model.TimePeriod;

public class TestInstructorPref {

	InstructorPref pref;
	Schedule sched;
	ArrayList<Course> list;
	ArrayList<String> profs;
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
		
		profs = new ArrayList<String>();
		profs.add("David Hovemeyer");
		profs.add("David Babcock");
		
		testOne.putValue(CourseData.DAY.toString(), days);
		testOne.putValue(CourseData.INSTRUCTOR.toString(), profs);
		list.add(testOne);
		
		sched.addCourse(testOne);
	}

	
	@Test
	public void testGetFitness() {
		pref = new InstructorPref();
		assertTrue(0.0 == pref.getFitness(sched));
		
		list.get(0).setHasPrefInstructor(true);
		assertTrue(.8 == pref.getFitness(sched));
		
	}

}
