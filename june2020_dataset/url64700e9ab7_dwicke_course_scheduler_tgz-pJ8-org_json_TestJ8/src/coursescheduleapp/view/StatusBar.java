package coursescheduleapp.view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;

import coursescheduleapp.model.Course;
import coursescheduleapp.model.CourseData;
import coursescheduleapp.model.Day;
import coursescheduleapp.model.Model;
import coursescheduleapp.model.TimeDayCollection;
import coursescheduleapp.model.TimePeriod;

public class StatusBar extends JLabel implements Runnable{
    
	private Model model;
	
    /** Creates a new instance of StatusBar */
    public StatusBar() {
        super();
        
        super.setPreferredSize(new Dimension(100, 16));
        setMessage("");
    }
    public void setModel(Model model)
    {
    	this.model = model;
    }
    public void setMessage(String message) {
        setText(" "+message);        
    }

    boolean isNew;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		isNew = false;
		while(true)
		{
			if (model.getSchedThread() != null && model.getSchedThread().isAlive())
			{
				setMessage("Please Wait...");
				isNew = true;
			}
			else
			{
				if (model.getBestSchedule() == null || model.getBestSchedule().getNumCourses()  == 0)
				{
					setMessage("");
					
				}
				else if (isNew == true)
				{
					TimeDayCollection schedColl = new TimeDayCollection();
					Iterator<Course> it = model.getBestSchedule().getCourses().iterator();

					// Iterate over each course to create a time table for the days and times
					// of the courses.
					while(it.hasNext()) 
					{
						Course curr = it.next();
						TimePeriod p = (TimePeriod)curr.getValue(CourseData.TIME.toString());
						p.setPayload(curr);
						schedColl.addTimeCollection(p,(ArrayList<Day>)curr.getValue(CourseData.DAY.toString()));
					}
					double hours = schedColl.hoursInWeek();
					
					setMessage("Time at school a week: " + (int)hours + " hours and " + (int)((hours - (int)hours) * 60) + " min");
					
				}
				isNew = false;
			}
		}
	}        
}
