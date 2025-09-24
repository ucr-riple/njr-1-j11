package coursescheduleapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import coursescheduleapp.model.DayPref;
import coursescheduleapp.model.TimePref;
import coursescheduleapp.view.WebControl;

public class WebTest {
	
	private WebControl web;

	@Before
	public void setUp() throws Exception {
		web = new WebControl();
	}
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
	@Test
	public void testGetSchedule()
	{
		JSONArray Data = new JSONArray();
		
		// Format:
	
	
		JSONArray courseData = new JSONArray();
		
		Data.put(courseData);
		System.out.println(Data.length() + "Data length");
		
		JSONArray equivCourses = new JSONArray();
		
		courseData.put(equivCourses);
		
		JSONObject courseChoice = new JSONObject();
		equivCourses.put(courseChoice);
		
		JSONArray courseObj = new JSONArray();
		
		JSONObject courseName = null;
		try {
			courseName = new JSONObject();
			courseName.put("CourseName", "Abnormal Psych");
			
			
			courseObj.put(courseName);
			
			courseChoice.append("choice1", courseObj);	// add the course
			
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		JSONArray equivCourses2 = new JSONArray();
		
		courseData.put(equivCourses2);
		
		JSONObject courseChoice2 = new JSONObject();
		equivCourses2.put(courseChoice2);
		
		JSONArray courseObj2 = new JSONArray();
		
		JSONObject courseName2 = null;
		try {
			courseName2 = new JSONObject();
			courseName2.put("CourseName", "Aerobic Dance");
			
			
			courseObj2.put(courseName2);
			JSONArray ins = new JSONArray();
			courseObj2.put(ins);
			
			ins.put("Kelly A. Block (P)");
			ins.put("Mary R. Lankford (P)");
			
			courseChoice2.append("choice1", courseObj2);	// add the course
			
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// the array that contains the course data
		JSONArray schedPrefs = new JSONArray();
		Data.put(schedPrefs); // the array that contains the Sched pref jsonobjects
		System.out.println(Data.length() + "Data length");
		
		JSONObject schedPrefOb = new JSONObject();
		try {
			schedPrefOb.append(TimePref.TimePref.toString(), TimePref.MORNING.toString());
			schedPrefOb.append(DayPref.DayPref.toString(), DayPref.MON.toString());
			schedPrefOb.append(DayPref.DayPref.toString(), DayPref.THUR.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		schedPrefs.put(schedPrefOb);
		
		try {
			
			JSONObject semO = new JSONObject();
			semO.append("Semester", "Summer 2010");
			
			Data.put(2,semO);	// The Semester
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // The 
		
		System.out.println(Data.length() + "Data length");
		web.getSchedule(Data);
		System.out.println("Format " + Data.toString());
		
	}
	
	@Test
	public void testGetInstructorNames() {
		
		JSONArray nameSem = new JSONArray();
		nameSem.put(new JSONArray());
		try {
			nameSem.getJSONArray(0).put("Abnormal Psych");
			nameSem.getJSONArray(0).put("Summer 2010");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		nameSem = web.getInstructorNames(nameSem);
		
		try {
			System.out.println(nameSem.getJSONArray(0).get(0).toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSemesterNames() {
		JSONArray ar = web.getSemesterNames();
		
		for (int i = 0; i < ar.length(); i++)
		{
			try {
				System.out.println(ar.getJSONArray(0).getString(i) + " " + ar.length());
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testGetCourseNames() {
		
		
		JSONObject ob = new JSONObject();
		try {
			ob.append("Semester", "Summer 2010");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		JSONArray ar = web.getCourseNames(ob);
		
		try {
			System.out.println(ar.get(0).toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			JSONArray s = ar.getJSONArray(0);
			System.out.println(s.get(0));
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
