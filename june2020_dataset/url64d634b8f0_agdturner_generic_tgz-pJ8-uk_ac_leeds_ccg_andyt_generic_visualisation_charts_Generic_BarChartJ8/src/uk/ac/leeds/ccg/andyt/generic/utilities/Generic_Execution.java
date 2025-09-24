/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.utilities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Visualisation;

/**
 * Generic utility class for execution handling.
 */
public class Generic_Execution {

    /**
     * Uses default delays 
     * @param executorService
     * @param futures
     * @param obj 
     */
    public static void shutdownExecutorService(
            ExecutorService executorService,
            HashSet<Future> futures,
            Object obj) {
        long delay = 1000;
        long maxWait = 10;
        shutdownExecutorService(
                executorService,
                futures,
                obj,
                delay,
                maxWait);
    }

    /**
     * @param executorService
     * @param futures
     * @param obj
     * @param delay Number of milliseconds for loop to wait before testing if a
     * Future is returned.
     * @param maxWait Number of minutes to block shutdown after all futures 
     * returned 
     */
    public static void shutdownExecutorService(
            ExecutorService executorService,
            HashSet<Future> futures,
            Object obj,
            long delay,
            long maxWait) {
        //What is still left to do from futures?
        Iterator<Future> ite = futures.iterator();
        System.out.println("There are " + futures.size() + " jobs to check.");
        int doneJobsCounter = 0;
        int notDoneJobsCounter = 0;
        while (ite.hasNext()) {
            Future f = ite.next();
            if (f.isDone()) {
                doneJobsCounter++;
            } else {
                notDoneJobsCounter++;
            }
        }
        System.out.println("There are " + doneJobsCounter + " jobs done.");
        System.out.println("There are " + notDoneJobsCounter + " jobs not done.");
        ite = futures.iterator();
        while (ite.hasNext()) {
            Future future = ite.next();
            long counter = 0;
            while (!future.isDone()) {
                System.out.println("Job not done waiting " + delay + " milliseconds having already waited " + counter + " milliseconds");
                counter += delay;
                //try {
                Generic_Execution.waitSychronized(obj, delay);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
            }
        }
        System.out.println("Jobs done.");
        executorService.shutdown();

        try {
            boolean allterminated = executorService.awaitTermination(maxWait, TimeUnit.MINUTES);
            System.out.println("All output terminated " + allterminated);
        } catch (InterruptedException ex) {
            Logger.getLogger(Generic_Execution.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Runnable> unfinishedJobs = executorService.shutdownNow();
        if (unfinishedJobs.size() > 0) {
            System.out.println("There are " + unfinishedJobs.size() + " unfinshed jobs");
        } else {
            System.out.println("All jobs finished");
        }
    }

    /**
     * 
     * @param executorService
     * @param future
     * @param obj 
     */
    public static void shutdownExecutorService(
            ExecutorService executorService,
            Future future,
            Object obj) {
        long delay = 1000;
        long maxWait = 10;
        shutdownExecutorService(
                executorService,
                future,
                obj,
                delay,
                maxWait);
    }

    public static void shutdownExecutorService(
            ExecutorService executorService,
            Future future,
            Object obj,
            long delay,
            long maxWait) {
        long counter = 0;
        while (!future.isDone()) {
            System.out.println("Job not done waiting " + delay + " milliseconds having already waited " + counter + " milliseconds");
            counter += delay;
            //try {
            Generic_Execution.waitSychronized(obj, delay);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
        System.out.println("Job done.");
        executorService.shutdown();
        try {
            boolean allterminated = executorService.awaitTermination(maxWait, TimeUnit.MINUTES);
            System.out.println("All output terminated " + allterminated);
        } catch (InterruptedException ex) {
            Logger.getLogger(Generic_Execution.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Runnable> unfinishedJobs = executorService.shutdownNow();
        if (unfinishedJobs.size() > 0) {
            System.err.println("There were " + unfinishedJobs.size() + " unfinshed jobs");
        } else {
            System.out.println("All jobs finished");
        }
    }

    public static void waitSychronized(Object obj, long timeInMilliseconds) {
        try {
            synchronized (obj) {
                obj.wait(timeInMilliseconds);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Generic_Visualisation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
