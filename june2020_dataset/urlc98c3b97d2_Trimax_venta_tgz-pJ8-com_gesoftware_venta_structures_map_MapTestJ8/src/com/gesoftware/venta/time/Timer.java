package com.gesoftware.venta.time;

/**
 * Timer class definition
 **/
public final class Timer {
    private long m_LastTimeStamp;

    /* *
     * METHOD: Class constructor
     * AUTHOR: Eliseev Dmitry
     * */
    public Timer() {
        measure();
    } /* End of 'Timer::Timer' method */

    /* *
     * METHOD: Determines the number of milliseconds since last measurement
     * RETURN: The number of milliseconds since last measurement
     * AUTHOR: Eliseev Dmitry
     * */
    public final long getTimeSinceLastMeasure() {
        return System.currentTimeMillis() - m_LastTimeStamp;
    } /* End of 'Timer::getTimeSinceLastMeasure' method */

    /* *
     * METHOD: Measures time right now
     * AUTHOR: Eliseev Dmitry
     * */
    public final void measure() {
        m_LastTimeStamp = System.currentTimeMillis();
    } /* End of 'Timer::measure' method */
} /* End of 'Timer' class */
