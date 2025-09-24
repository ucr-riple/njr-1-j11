/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

import java.rmi.RemoteException;
import java.util.Observable;

/**
 *
 * @author Drew
 */
public class Model extends Observable implements Subscriber{

	private Schedule bestSched;
	private CourseCollectionDB courseData;
	private Scheduler scheduler;
	private PreferenceSet prefSet;
	private Thread schedulerThread;
	private int numEquivCourses, numSelection;

	/**
	 *
	 * @param numEquivCourses
	 * @param numSelection
	 */
	public Model(int numEquivCourses, int numSelection)
	{
		this.numEquivCourses = numEquivCourses;
		this.numSelection = numSelection;
		prefSet = new PreferenceSet(numEquivCourses, numSelection);

		CourseCollectionFactory courseFac = new CourseCollectionFactory();
		courseData = courseFac.createCourseCollection();

		SchedulerFactory schedFac = new SchedulerFactory();
		scheduler = schedFac.createSceduler();
		try {
			scheduler.addSubscriber(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bestSched = null;
	}



	/**
	 *
	 * @return
	 */
	public void createBestSchedule()
	{
		scheduler.setPref(prefSet);
		schedulerThread = new Thread(scheduler);
		schedulerThread.start();

		//bestSched = scheduler.getSchedule();
		//bestSched = scheduler.createSchedule(prefSet);

	}
	
	public Thread getSchedThread()
	{
		return schedulerThread;
	}

	/**
	 *
	 * @return
	 */
	public Schedule getBestSchedule()
	{
		return bestSched;
	}


	/**
	 * 
	 * @return
	 */
	public CourseCollectionDB getDB()
	{
		return courseData;
	}

	/**
	 * 
	 * @return
	 */
	public PreferenceSet getPrefSet() {
		return prefSet;
	}



	@Override
	public void update(Object pub, Object code) throws RemoteException {
		// TODO Auto-generated method stub
		if (pub == scheduler)
		{
			bestSched = (Schedule) code;
			setChanged();
			this.notifyObservers();
		}
	}



}
