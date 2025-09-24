package coursescheduleapp.view;

import java.util.List;

import javax.swing.text.StyledDocument;

import coursescheduleapp.model.Course;
import coursescheduleapp.model.Schedule;

public interface SchedViewI {

	public void getResults(Course curr, StyledDocument doc);
	public List<Course> orderSched(Schedule sched);
	
}

