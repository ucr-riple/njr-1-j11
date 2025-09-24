
package coursescheduleapp.model;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cboyle1
 */
public class CourseDatabase implements CourseCollectionDB
{
    private Connection conn;
    /*private*/public Statement stat;
    private String semester;
    private BasicPublisher pub;
    public CourseDatabase() throws Exception
    {
    	pub = new BasicPublisher();
        //I have no idea what this does, but its important somehow..
        Class.forName("org.sqlite.JDBC");

        //Create connection
        //conn = DriverManager.getConnection("jdbc:sqlite:courses.sqlite");
        conn = DriverManager.getConnection("jdbc:sqlite:classes.sqlite");
        stat = conn.createStatement();
        
        semester = null;
    }
    
    /*
     * --Table: classes (FIXME: TYPES??)--
     * crn INTEGER PRIMARY KEY
     * subject TEXT
     * course INTEGER
     * sec INTEGER
     * cred INTEGER (credits * 1000)
     * title TEXT
     * days TEXT
     * timestart INTEGER (in minutes past 12:00 am)
     * timeend INTEGER (in minutes past 12:00 am)
     * cap INTEGER (max seats)
     * rem INTEGER (seats remaining)
     * instructor TEXT
     * datestart INTEGER (MM/DD)
     * dateend INTEGER (MM/DD)
     * location TEXT
     * attribute TEXT (FIXME: Whats this mean?)
     */
	
	@Override
	public List<String> getAllSemesters() throws SQLException {
		ArrayList<String> results=new ArrayList<String>();
		ResultSet rs = stat.executeQuery("SELECT DISTINCT(`semester`) FROM `classes`;");
        while (rs.next()) {
        	results.add(rs.getString("semester"));
        }
        rs.close();
		return results;
	}
	
	@Override
	public List<String> getAllCourseNames() throws Exception {
		if(semester==null)throw new Exception("Attempt to call getAllCourseNames() without setting semester!");
		ArrayList<String> results=new ArrayList<String>();

		PreparedStatement ps=conn.prepareStatement("SELECT DISTINCT(`title`) FROM `classes` WHERE `semester` = ?;");
		ps.setString(1,semester);
		ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	results.add(rs.getString("title"));
        }
        rs.close();
        ps.close();
		return results;

	}
	
	@Override
	public List<Course> getEquivCourses(String courseName) throws Exception {
		if(semester==null)throw new Exception("Attempt to call getEquivCourses() without setting semester!");
		List<Course> courseLs = new ArrayList<Course>();
		
		PreparedStatement ps=conn.prepareStatement("SELECT * FROM `classes` WHERE `title`=? AND `semester` = ?;");
		
		ps.setString(1,courseName);
		ps.setString(2,semester);
		
		
		ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	String instructor=rs.getString("instructor");
        	ArrayList<String> instructors=new ArrayList<String>();
        	for(String i : instructor.split(",\\s*"))
        	{
        		instructors.add(i);
        	}
        	
        	
        	
        	TimePeriod time=new TimePeriod(
        		new CourseTime(Integer.parseInt(rs.getString("timestart"))),
        		new CourseTime(Integer.parseInt(rs.getString("timeend"))));
        	
        	rs.getString("days");
        	
        	ArrayList<Day> days=new ArrayList<Day>();
        	for(Character c : rs.getString("days").toCharArray())
        	{
        		
        		Day day;
        		switch(c)
        		{
        		case 'M':day=Day.M;break;
        		case 'T':day=Day.T;break;
        		case 'W':day=Day.W;break;
        		case 'R':day=Day.R;break;
        		case 'F':day=Day.F;break;
        		case 'S':day=Day.S;break;
        		default:throw new Exception("Illegal char in database DAYS column! " + c);
        		}
        		days.add(day);
        	}
        	
        	
        	Course course=new Course();
        	
        	
           	
        	
        	course.putValue(CourseData.TIME.toString(),time);
        	course.putValue(CourseData.DAY.toString(), days);
        	
        	course.putValue(CourseData.INSTRUCTOR.toString(), instructors);
        	
        	course.putValue(CourseData.BLDG.toString(),rs.getString("location"));
        	course.putValue(CourseData.CRN.toString(),rs.getString("crn"));
        	//days
        	course.putValue(CourseData.DEPT.toString(),rs.getString("subject"));
        	//instructor
        	course.putValue(CourseData.NAME.toString(),rs.getString("title"));
        	//time
     
        	course.putValue(CourseData.CRDT.toString(), String.valueOf(rs.getInt("cred")/ 1000));
        	courseLs.add(course);
        	
        	
        }
        rs.close();
        ps.close();
		
		// create a course object with all of the data about the course
		// for each of the unique courses with the provided courseName.
		
		
		return courseLs;
	}
	
	@Override
	public List<String> getInstructors(String courseName) throws Exception {
		if(semester==null)throw new Exception("Attempt to call getInstructors() without setting semester!");
		ArrayList<String> results=new ArrayList<String>();
		
		PreparedStatement ps=conn.prepareStatement("SELECT DISTINCT(`instructor`) FROM `classes` WHERE `title`=? AND `semester` = ?;");
		ps.setString(1,courseName);
		ps.setString(2,semester);
		ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	results.add(rs.getString("instructor"));
        }
        rs.close();
        ps.close();
		return results;
	}
	
	@Override
	public void setSemester(String semesterName) {
		semester=semesterName;
		//System.out.println(semesterName);
		//notify the subscribers that the semester has changed.
		pub.notifySubscribers(this, semesterName);
	}

	
	
	/*
	 * Publisher methods
	 * 
	 */
	
	@Override
	public void addSubscriber(Subscriber s) throws RemoteException {
		// TODO Auto-generated method stub
		pub.addSubscriber(s);
		
	}

	@Override
	public void removeAllSubscribers() throws RemoteException {
		// TODO Auto-generated method stub
		pub.removeAllSubscribers();
	}

	@Override
	public void removeSubscriber(Subscriber s) throws RemoteException {
		// TODO Auto-generated method stub
		pub.removeSubscriber(s);
	}
}
