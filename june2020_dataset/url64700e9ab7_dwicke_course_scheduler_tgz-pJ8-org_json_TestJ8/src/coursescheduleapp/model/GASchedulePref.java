/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

import java.util.Iterator;

/**
 * Fitness function.
 * @author Drew
 */
public class GASchedulePref implements GAFitness<Schedule>{

	private SchedulePref schedPref;

	public GASchedulePref(SchedulePref schedulePref) {
		this.schedPref = schedulePref;
	}

	@SuppressWarnings("unchecked")
	public double getFitness(Schedule individual) {

		// First rate the fitness of the schedule as a whole

		double fitness = 0;
		
		
		Iterator<Preference> vals = schedPref.getAllPrefs().values().iterator();
		
		while(vals.hasNext())
		{
			fitness += vals.next().getFitness(individual);
		}
		
		InstructorPref coursePref = new InstructorPref();
		
		fitness += coursePref.getFitness(individual);
		
		return fitness;
	}












}
