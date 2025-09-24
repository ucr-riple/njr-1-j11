/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

/**
 *
 * @author Drew
 */
public class SchedulerFactory {

    public Scheduler createSceduler()
    {
    	
    	
    	 Runtime runtime = Runtime.getRuntime();
         
         int nrOfProcessors = runtime.availableProcessors();
         
         System.out.println("Number of processors available to the Java Virtual Machine: " + nrOfProcessors);
        
         
        return new GeneticAlgorithm(nrOfProcessors);
    	//return new TestScheduler();
    }

}
