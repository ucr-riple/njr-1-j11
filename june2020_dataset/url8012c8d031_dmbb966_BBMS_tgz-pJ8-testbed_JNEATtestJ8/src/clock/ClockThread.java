package clock;

import bbms.GlobalFuncs;
import gui.GUI_NB;

// ClockThread version 3.0 - automatically updates the clock IAW
// the calculated ClockCalc and ClockDisplay intervals.
public class ClockThread implements Runnable {
  
    private int pausedSleepCheck = 100;
    
    long startCycle;
    long endCycle;
    long durationCycle;
	
    // Initialize clock date to the constructed value
    public ClockThread(){
    	
    }
        
    public void run() {       
    	GlobalFuncs.clockInitialized = true;
        GUI_NB.GCO("Running clock thread.");
        try {
        	do {
	            while (ClockControl.paused) Thread.sleep(pausedSleepCheck);
	            
	            startCycle = System.currentTimeMillis();
	            
	            // Do stuff here
	            Clock.ClockLoop(ClockControl.CLOCK_STEP);      
	            
	            durationCycle = System.currentTimeMillis() - startCycle;
	            if (durationCycle > ClockControl.GetClockDelay()) {
		            //System.out.println("WARNING: Cycle duration of " + durationCycle + " exceeds step delay.");	            	
	            }

	            Clock.IncrementMs((int)(ClockControl.CLOCK_STEP)); //  * ClockControl.NumTimeScale()));
	                                                       
	            // Waits at the end of a clock cycle
	            Thread.sleep(Math.max(ClockControl.GetClockDelay() - durationCycle, 0));    
	            
            } while (1 > 0); // Loops forever
        }         
        catch (Exception exc) {
            GUI_NB.GCO("Clock thread was interrupted.");            
            System.out.println (exc);
            exc.printStackTrace();
        }
    }    
}
