/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

/**
 *
 * @author Drew
 */
public class CourseCollectionFactory {

    /**
     *
     * @return
     * Returns the CourseCollectionDB object used for
     * querrying the database of courses.
     */

    public CourseCollectionDB createCourseCollection()
    {
        //return new CourseCollectionDBTXT();
    	
    	try {
			return new CourseDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

    }


}
