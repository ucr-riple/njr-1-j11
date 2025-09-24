/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Drew
 */
public class CourseCollectionDBTXT implements CourseCollectionDB{

    

	@Override
	public List<String> getAllSemesters() {
		// TODO Auto-generated method stub
		List<String> semesters = new ArrayList<String>();
		semesters.add("Spring 2010");
		semesters.add("Fall 2010");
		return semesters;
	}
	
	@Override
	public void setSemester(String semesterName) {
		// TODO Auto-generated method stub
		
	}

    
    public List<String> getInstructors(String courseName) {

    	List<String> ins = new ArrayList<String>();
    	
    	if (courseName.equals("AI"))
    	{
    		ins.add("Prof. Norvig");
    	}
    	else if (courseName.equals("Calc III"))
    	{
    		ins.add("Dr. Butler");
    	}
    	else if (courseName.equals("Machine Learning"))
    	{
    		ins.add("Drew");
    		ins.add("Andrew Ng");
    	}
    	else
    	{
    		ins.add("BAD SHOULD NOT HAPPEN");
    	}
    	return ins;
    }

	@Override
	public List<String> getAllCourseNames() {
		List<String> courses = new ArrayList<String>();
        courses.add("Choose Course");
        courses.add("AI");
        courses.add("Calc III");
        courses.add("Machine Learning");
        return courses;
	}

	

	@Override
	public List<Course> getEquivCourses(String courseName) {
		List<Course> cse = new ArrayList<Course>();
    	List<String> ins = new ArrayList<String>();
    	if (courseName == "AI")
    	{
    		Map<String, Object> inf = new TreeMap<String, Object>();
    		
    		inf.put(CourseData.NAME.toString(), "AI");
    		ins.add("Prof. Norvig");
    		inf.put(CourseData.INSTRUCTOR.toString(), ins);
    		inf.put(CourseData.TIME.toString(), new TimePeriod(new CourseTime(1200), new CourseTime(1260)));
    		ArrayList<Day> days = new ArrayList<Day>();
    		days.add(Day.M);
    		days.add(Day.W);
    		days.add(Day.F);
    		inf.put(CourseData.DAY.toString(), days);
    		
    		Course ai = new Course(inf);
    		cse.add(ai);
    		
    	}
    	else if (courseName.equals("Calc III"))
    	{
    		Map<String, Object> inf = new TreeMap<String, Object>();
    		inf.put(CourseData.NAME.toString(), "Calc III");
    		ins.add("Dr. Butler");
    		inf.put(CourseData.INSTRUCTOR.toString(), ins);
    		inf.put(CourseData.TIME.toString(), new TimePeriod(new CourseTime(800), new CourseTime(900)));
    		ArrayList<Day> days = new ArrayList<Day>();
    		days.add(Day.M);
    		days.add(Day.T);
    		days.add(Day.F);
    		inf.put(CourseData.DAY.toString(), days);
    		
    		cse.add(new Course(inf));
    	}
    	else if (courseName.equals("Machine Learning"))
    	{
    		Map<String, Object> inf = new TreeMap<String, Object>();
    		inf.put(CourseData.NAME.toString(), "Machine Learning ");
    		ins.add("Drew");
    		inf.put(CourseData.INSTRUCTOR.toString(), ins);
    		inf.put(CourseData.TIME.toString(), new TimePeriod(new CourseTime(500), new CourseTime(620)));
    		ArrayList<Day> days = new ArrayList<Day>();
    		days.add(Day.M);
    		inf.put(CourseData.DAY.toString(), days);
    		
    		cse.add(new Course(inf)); 
    		
    		Map<String, Object> inf2 = new TreeMap<String, Object>();
    		inf2.put(CourseData.NAME.toString(), "Machine Learning");
    		List<String> ins2 = new ArrayList<String>();
    		ins2.add("Andrew Ng");
    		ins2.add("Drew");
    		inf2.put(CourseData.INSTRUCTOR.toString(), ins2);
    		inf2.put(CourseData.TIME.toString(), new TimePeriod(new CourseTime(630), new CourseTime(720)));
    		ArrayList<Day> days2 = new ArrayList<Day>();
    		days2.add(Day.R);
    		inf2.put(CourseData.DAY.toString(), days);
    		cse.add(new Course(inf2));
    	}
    	else
    	{
    		Map<String, Object> inf2 = new TreeMap<String, Object>();
    		ins.add("NO INSTRUCTOR DATA");
    		inf2.put(CourseData.NAME.toString(), ins);
    		inf2.put(CourseData.INSTRUCTOR.toString(), "NO COURSE DATA " + courseName);
    		cse.add(new Course(inf2));
    	}
    	
    	return cse;
	}

	@Override
	public void addSubscriber(Subscriber s) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAllSubscribers() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSubscriber(Subscriber s) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	

}
