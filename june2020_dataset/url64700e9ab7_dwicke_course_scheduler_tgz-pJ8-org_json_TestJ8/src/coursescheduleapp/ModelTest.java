/**
 * 
 */
package coursescheduleapp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import coursescheduleapp.model.Model;

/**
 * @author Drew
 *
 */
public class ModelTest {

	private Model model;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		model = new Model(1,1);
	}

	/**
	 * Test method for {@link coursescheduleapp.model.Model#Model(int, int)}.
	 */
	@Test
	public void testModel() {
		//fail("Not yet implemented");
		assertEquals(1, model.getPrefSet().getCoursePref().getAllCourses().size());
		assertEquals(1, model.getPrefSet().getCoursePref().getAllCourses().get(0).getNumCourseSelection());
	}

	/**
	 * Test method for {@link coursescheduleapp.model.Model#createBestSchedule()}.
	 */
	@Test
	public void testCreateBestSchedule() {
		//fail("Not yet implemented");
		model.createBestSchedule();
		while(model.getSchedThread().isAlive())
		{
			
		}
		assertEquals(0, model.getBestSchedule().getNumCourses());
	}

	/**
	 * Test method for {@link coursescheduleapp.model.Model#getBestSchedule()}.
	 */
	@Test
	public void testGetBestSchedule() {
		//fail("Not yet implemented");
		assertEquals(null, model.getBestSchedule());
	}


	/**
	 * Test method for {@link coursescheduleapp.model.Model#getDB()}.
	 */
	@Test
	public void testGetDB() {
		if (model.getDB() == null)
		{
			fail("GetDB does not work");
		}
	}

	/**
	 * Test method for {@link coursescheduleapp.model.Model#getPrefSet()}.
	 */
	@Test
	public void testGetPrefSet() {
		
		assertEquals(model.getPrefSet().getCoursePref().getAllCourses().size(), 1);
		assertEquals(1, model.getPrefSet().getCoursePref().getAllCourses().get(0).getNumCourseSelection());
		assertEquals(0, model.getPrefSet().getSchedulePref().getAllPrefs().size());
	}

}
