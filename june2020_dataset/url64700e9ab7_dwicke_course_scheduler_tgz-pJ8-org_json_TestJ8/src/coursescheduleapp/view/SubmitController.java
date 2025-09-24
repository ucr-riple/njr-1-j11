package coursescheduleapp.view;

import java.util.Iterator;

import coursescheduleapp.model.CourseSelection;
import coursescheduleapp.model.EquivCourseSelection;
import coursescheduleapp.model.Model;

public class SubmitController {

	public void createSchedule(Model model)
	{
		//Check to make sure the user has chosen courses
		int numCourses = 0;
		Iterator<EquivCourseSelection> equivI = model.getPrefSet().getCoursePref().getAllCourses().iterator();
		while(equivI.hasNext())
		{
			Iterator<CourseSelection> courI = equivI.next().getCourseSelectionList().iterator();
			while(courI.hasNext())
			{
				numCourses += courI.next().getNumCourses(); 
			}
		}
		
		if (numCourses > 0)
		{
			model.createBestSchedule();
			
		}
	}
}
