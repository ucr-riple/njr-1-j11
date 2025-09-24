package coursescheduleapp.model;

import java.rmi.RemoteException;

public class TestScheduler implements Scheduler {

	
	public Schedule createSchedule(PreferenceSet prefSet) {
		// TODO Auto-generated method stub
		Schedule best = new Schedule();
		if(prefSet.getCoursePref().getCourseSelection(0, 0).getNumCourses() == 0)
		{
			best.addCourse(prefSet.getCoursePref().getCourseSelection(0, 1).getCourse(0));
		}
		else
		{
		best.addCourse(prefSet.getCoursePref().getCourseSelection(0, 0).getCourse(0));
		}
		best.addCourse(prefSet.getCoursePref().getCourseSelection(1, 0).getCourse(0));
		return best;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
	public Schedule getSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPref(PreferenceSet prefSet) {
		// TODO Auto-generated method stub
		
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
