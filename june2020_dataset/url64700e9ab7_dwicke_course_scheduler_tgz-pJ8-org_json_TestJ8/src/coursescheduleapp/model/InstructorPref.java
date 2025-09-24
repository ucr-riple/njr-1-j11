package coursescheduleapp.model;

import java.util.Iterator;

public class InstructorPref implements Preference {

	@Override
	public void addPref(String pref) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getFitness(Schedule individual) {
		// TODO Auto-generated method stub
		double fitness = 0;
		Iterator<Course> i = individual.getCourses().iterator();
		// Then rate the fitness of each course
		while(i.hasNext())
		{
			fitness += getCourseFitness(i.next());
		}
		
		return fitness;
	}

	private double getCourseFitness(Course gene) {
		// TODO Auto-generated method stub
		double fitness = 0;

		// If the course has a preferred instructor add .8 points to the fitness.
		if (gene.isHasPrefInstructor() == true)
		{
			fitness += .8;
		}

		return fitness;
	}

}
