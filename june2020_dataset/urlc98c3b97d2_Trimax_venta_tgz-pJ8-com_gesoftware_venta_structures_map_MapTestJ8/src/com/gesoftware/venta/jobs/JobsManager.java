package com.gesoftware.venta.jobs;

import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.time.Timer;

import java.util.HashSet;
import java.util.Set;

/**
 * JobManager class definition
 **/
public final class JobsManager {
    /**
     * Job class definition
     **/
    private final class Job implements Runnable {
        /* Duration between task launches */
        private final int m_Duration;

        /* Task to launch */
        private final Runnable m_Task;

        /* Job timer */
        private final Timer m_Timer = new Timer();

        /* *
         * METHOD: Job class constructor
         *  PARAM: [IN] task - task to do
         * AUTHOR: Eliseev Dmitry
         * */
        public Job(final Runnable task) {
            this(task, 0);
        } /* End of 'Job::Job' method */

        /* *
         * METHOD: Job class constructor
         *  PARAM: [IN] task     - task to do
         *  PARAM: [IN] duration - duration between task executions
         * AUTHOR: Eliseev Dmitry
         * */
        public Job(final Runnable task, final int duration) {
            m_Duration = duration;
            m_Task     = task;
        } /* End of 'Job::Job' method */

        @Override
        public final void run() {
            m_Task.run();
            m_Timer.measure();
        } /* End of 'Job::run' method */

        /* *
         * METHOD: Determines if it is time to run task
         * RETURN: True if yes, False otherwise
         * AUTHOR: Eliseev Dmitry
         * */
        public final boolean isJobTime() {
            return m_Timer.getTimeSinceLastMeasure() >= m_Duration;
        } /* End of 'Job::isJobTime' method */
    } /* End of 'Job' class */

    /**
     * Worker class
     **/
    private final class Worker implements Runnable {
        /* Worker timer (for sleeps) */
        private final Timer m_Timer = new Timer();

        /* Is worker active */
        private boolean m_Active = true;

        /* Time between sleep execution (ms) */
        private final int m_TimeBetweenSleeps;

        /* Sleep duration (ms) */
        private final int m_SleepDuration;

        /* *
         * METHOD: Worker class constructor
         *  PARAM: [IN] timeBetweenSleeps - time (ms) between worker sleeps
         *  PARAM: [IN] sleepDuration     - sleep worker time (ms)
         * AUTHOR: Eliseev Dmitry
         * */
        public Worker(final int timeBetweenSleeps, final int sleepDuration) {
            m_TimeBetweenSleeps = timeBetweenSleeps;
            m_SleepDuration     = sleepDuration;
        } /* End of 'Worker::Worker' method */

        /* *
         * METHOD: Pauses worker thread
         * AUTHOR: Eliseev Dmitry
         * */
        private void sleep() {
            try {
                Thread.sleep(m_SleepDuration);
            } catch (final InterruptedException e) {
                LoggingUtility.error("Worker thread can't sleep: " + e.getMessage());
                m_Active = false;
            }
        } /* End of 'Worker::sleep' method */

        /* *
         * METHOD: Run all ready jobs
         * AUTHOR: Eliseev Dmitry
         * */
        private synchronized void doJob() {
            for (final Job job : m_Jobs)
                if (job.isJobTime())
                    job.run();
        } /* End of 'Worker::doJob' method */

        @Override
        public final void run() {
            m_Active = true;
            while (m_Active) {
                if (m_Timer.getTimeSinceLastMeasure() >= m_TimeBetweenSleeps) {
                    doJob();
                    sleep();
                    m_Timer.measure();
                }
            }
        } /* End of 'Worker::run' method */

        /* *
         * METHOD: Stops worker thread
         * AUTHOR: Eliseev Dmitry
         * */
        public final void stop() {
            m_Active = false;
        } /* End of 'Worker::stop' method */
    } /* End of 'Worker' class */

    /* Jobs collection */
    private final Set<Job> m_Jobs = new HashSet<Job>();

    /* Worker */
    private final Worker m_Worker;

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] sleepDuration - how long should thread sleeps
     * AUTHOR: Eliseev Dmitry
     * */
    public JobsManager(final int sleepDuration) {
        m_Worker = new Worker(0, sleepDuration);
        (new Thread(m_Worker)).start();
    } /* End of 'JobsManager::JobsManager' method */

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] timeBetweenSleeps - how much time should pass before sleeps
     *  PARAM: [IN] sleepDuration     - how long should thread sleeps
     * AUTHOR: Eliseev Dmitry
     * */
    public JobsManager(final int timeBetweenSleeps, final int sleepDuration) {
        m_Worker = new Worker(timeBetweenSleeps, sleepDuration);
        (new Thread(m_Worker)).start();
    } /* End of 'JobsManager::JobsManager' method */

    /* *
     * METHOD: Adds task to jobs manager
     *  PARAM: [IN] task     - task to run
     *  PARAM: [IN] duration - time (ms) between task launches
     * AUTHOR: Eliseev Dmitry
     * */
    public synchronized void addTask(final Runnable task, final int duration) {
        m_Jobs.add(new Job(task, duration));
    } /* End of 'JobsManager::addTask' method */

    /* *
     * METHOD: Adds task to jobs manager
     *  PARAM: [IN] task - task to run
     * AUTHOR: Eliseev Dmitry
     * */
    public synchronized void addTask(final Runnable task) {
        addTask(task, 0);
    } /* End of 'JobsManager::addTask' method */

    /* *
     * METHOD: Stops jobs manager
     * AUTHOR: Eliseev Dmitry
     * */
    public final void stop() {
        m_Worker.stop();
    } /* End of 'JobsManager::stop' method */
} /* End of 'JobsManager' class */
