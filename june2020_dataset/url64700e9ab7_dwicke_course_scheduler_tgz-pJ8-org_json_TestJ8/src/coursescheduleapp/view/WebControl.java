package coursescheduleapp.view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.json.*;

import coursescheduleapp.model.Course;
import coursescheduleapp.model.CourseCollectionDB;
import coursescheduleapp.model.CourseCollectionFactory;
import coursescheduleapp.model.CourseData;
import coursescheduleapp.model.CoursePref;
import coursescheduleapp.model.CourseSelection;
import coursescheduleapp.model.Day;
import coursescheduleapp.model.EquivCourseSelection;
import coursescheduleapp.model.PreferenceSet;
import coursescheduleapp.model.Schedule;
import coursescheduleapp.model.SchedulePref;
import coursescheduleapp.model.Scheduler;
import coursescheduleapp.model.SchedulerFactory;
import coursescheduleapp.model.Subscriber;

public class WebControl implements Subscriber {

	// I need to be able to return a list of courses in the schedule with their data
	//based on a JSON object containing a multidimentional array of course
	// names with corresponding preferred professors and a schedule preferences. 
	//
	// 
	
	Schedule retSched;
	Thread t;
	/*[ [course], [schedulePrefs], {semester}  ]
	 * [
	 * [
	 * [{"choice1":[[{"CourseName":"Abnormal Psych"}]]}],[{"choice1":[[{"CourseName":"Aerobic Dance"},["Kelly A. Block (P)","Mary R. Lankford (P)"]]]}]
	 * ],
	 * [{"TimePref":["Morning"],"Day Pref":["Monday","Thursday"]}],
	 * {"Semester":["Summer 2010"]}
	 * ]
	 * 
	 * 
	 * run the test and the Format ... will be shown in the output
	 */
	public JSONArray getSchedule(JSONArray data)
	{
		// First get which Semester the Course data is from
		// Semester is stored in the third array as an object
		CourseCollectionFactory fac = new CourseCollectionFactory();
		CourseCollectionDB db = fac.createCourseCollection();

		System.out.println(data.length());
		String semester = "";
		try {
			semester = data.getJSONObject(2).getJSONArray("Semester").getString(0);

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		db.setSemester(semester);


		// Now I can build the Course Preferences
		JSONArray schedule = new JSONArray();

		PreferenceSet prefs = new PreferenceSet(0,0);
		CoursePref coursePrefs = new CoursePref();
		prefs.setCoursePref(coursePrefs);
		
		
		

		// Course Data is stored as an array in the data at position 0
		JSONArray courseData = null;
		
		try {
			courseData = data.getJSONArray(0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Now loop through each course 
		for (int numCourses = 0; numCourses < courseData.length(); numCourses++)
		{
			EquivCourseSelection equiv = null;

			// The array that contains the course objects for each equiv course
			JSONArray equivCourses = null;

			try {
				equivCourses = courseData.getJSONArray(numCourses);
				equiv = new EquivCourseSelection(equivCourses.length());

				// Add the equivdata to pref
				coursePrefs.addEquivCourse(equiv);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Inside the equivCourses array contains objects for each equiv course
			for (int courseSelection = 0; courseSelection < equivCourses.length(); courseSelection++)
			{
				CourseSelection currSel = new CourseSelection();

				try {
					JSONObject courseChoice = equivCourses.getJSONObject(courseSelection);

					// The array that contains the course name and the list of preferred constructors
					JSONArray courseObj = courseChoice.getJSONArray("choice" + (courseSelection + 1));

					System.out.println(courseObj.getString(0));
					
					String courseName = courseObj.getJSONArray(0).getJSONObject(0).getString("CourseName");
					
					System.out.println(courseName);
					// Create courseselection object 
					try {
						currSel.setCourse(db.getEquivCourses(courseName));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}



					// add preferred instructors
					JSONArray preferredIns = courseObj.optJSONArray(0).optJSONArray(1);
					
					if (preferredIns != null)
					{
						
						for (int i = 0; i < preferredIns.length(); i++)
						{
							String prof = preferredIns.optString(i);
							currSel.addInstructorPref(prof);
						}
					}

					// finally add the courseSelection object to the equivCourse selection object
					equiv.setCourseSelection(courseSelection, currSel);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}


		}
		
		
		// Now set the Sched Prefs
		SchedulePref pr = new SchedulePref();
		pr.addAll();
		prefs.setSchedulePref(pr);
		
		
		try {
			JSONArray schedPrefs = data.getJSONArray(1);
			
			
			
			
				JSONObject pref = schedPrefs.getJSONObject(0);
				
				Iterator prefKeys = pref.keys();
				
			//	System.out.println(pref.toString());
				
				while (prefKeys.hasNext())
				{
					String key = prefKeys.next().toString();
					
					
					JSONArray dataPrefs = pref.getJSONArray(key);
					
					for (int k = 0; k < dataPrefs.length(); k++)
					{
						
						String prefString = dataPrefs.getString(k);
						System.out.println(prefString);
						pr.getPref(key).addPref(prefString);
					}
					
				}
				
				
				
				
				
				
				
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		// Finally make the schedule based on the prefs
		SchedulerFactory schedFac = new SchedulerFactory();
		Scheduler scheduler = schedFac.createSceduler();
		try {
			scheduler.addSubscriber(this);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		t = new Thread(scheduler);
		scheduler.setPref(prefs);
		t.start();
		
		while(t.isAlive())
		{
			
		}
		
		
		Iterator<Course> it = retSched.getCourses().iterator();
		
		//while (it.hasNext())
		//{
			JSONArray arData = new JSONArray();
			
			schedule.put(arData);
			
			arData.put(retSched.getCourses());
			
			
			
			try {
				System.out.println(arData.getJSONArray(0).toString());
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// add the day data
			Iterator<Course> it1 = retSched.getCourses().iterator();
			int count = 0;
			while(it1.hasNext())
			{
				Course c = it1.next();
				ArrayList<Day> days = (ArrayList<Day>) c.getValue(CourseData.DAY.toString());
				ArrayList<String> dayStrings = new ArrayList<String>();
				for (int i = 0; i < days.size(); i++)
				{
					dayStrings.add(days.get(i).toString());
				}
				
				try {
					
					schedule.getJSONArray(0).getJSONArray(0).getJSONObject(count).getJSONObject("courseInfo").remove("Day");//put(7, ls);
					schedule.getJSONArray(0).getJSONArray(0).getJSONObject(count).getJSONObject("courseInfo").accumulate("Day", dayStrings);//put(7, ls);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				count++;
			}
			
			try {
				System.out.println(arData.getJSONArray(0).toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

		return schedule;
	}







	// I need to be able to return a string containing a list of course names given a string containing the semester

	public JSONArray getCourseNames(JSONObject Jsemester)
	{
		JSONArray names = new JSONArray();
		//String semester = "";

		CourseCollectionFactory fac = new CourseCollectionFactory();
		CourseCollectionDB db = fac.createCourseCollection();

		String semester = "";
		try {
			semester = Jsemester.getJSONArray("Semester").getString(0);

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Collection c = null;
		try {
			db.setSemester(semester);
			c = db.getAllCourseNames();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(semester);

		try {
			names.put(0, c);
			System.out.println(names.getJSONArray(0));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}




		return names;
	}


	// I need to be able to return a string containing a list of semester names
	public JSONArray getSemesterNames()
	{
		JSONArray semesters = new JSONArray();

		CourseCollectionFactory fac = new CourseCollectionFactory();
		CourseCollectionDB db = fac.createCourseCollection();


		Collection c = null;
		try {

			c = db.getAllSemesters();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(c.toString());

		try {
			semesters.put(0, c);
			System.out.println(semesters.getJSONArray(0));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}






		return semesters;
	}


	// I need to be able to return the list of Instructors based on a semester and course name
	/**
	 * course name then semester
	 */
	public JSONArray getInstructorNames(JSONArray nameSem)
	{
		JSONArray instructors = new JSONArray();

		String courseName = "";
		String semester = "";
		try {
			courseName = nameSem.getJSONArray(0).getString(0);
			semester = nameSem.getJSONArray(0).getString(1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(semester + " " + courseName);

		CourseCollectionFactory fac = new CourseCollectionFactory();
		CourseCollectionDB db = fac.createCourseCollection();

		db.setSemester(semester);
		Collection c = null;
		try {
			c = db.getInstructors(courseName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			instructors.put(0, c);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return instructors;
	}







	@Override
	public void update(Object pub, Object code) throws RemoteException {
		// TODO Auto-generated method stub
		retSched = (Schedule) code;
		
	}



}
