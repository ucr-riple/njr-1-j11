package clock;

import bbms.GlobalFuncs;
import gui.GUI_NB;

public class ClockControl {
	
	public static final int CLOCK_STEP = 100;			// Represents how large each clock step in game time is (ms) 
	private static int clockDelay = 100;		// Represents how long each step will take in real time (ms)
    static byte timescale = 4;					// Enumeration of rate of time
    public static boolean paused = true;		// Bool to show if the simulation paused or not
    
    public static int GetClockDelay() {
    	return clockDelay;
    }
    
    /** Sets paused status to a particular value */
    public static void SetPaused(boolean p) {
    	paused = p;
    	if (paused) GUI_NB.GCO("Time set to paused.");
        else GUI_NB.GCO("Time set to unpaused.");    	
    }
    
    /**
     * Toggles whether or not the clock is paused
     */
    public static void Pause() {
    	if (GlobalFuncs.runtoEq) GlobalFuncs.runtoEq = false;		// Changing the rate will stop autorun
        paused = !paused;
        if (paused) GUI_NB.GCO("Time is paused.");
        else GUI_NB.GCO("Time is unpaused.");
    }
    
    /**
     * Update clock delay stats for changes made by the user.
     */
    public static void AdjustDelayScale() {
    	clockDelay = (int)(100.0 / NumTimeScale());
    	GUI_NB.GCO("Timescale is now " + timescale + " with delay " + clockDelay);
    }
    
    /**
     * Increases the timescale of the clock by one setting.  Maxes out at x12
     */ 
    public static void AccelTime() {
    	if (GlobalFuncs.runtoEq) GlobalFuncs.runtoEq = false;		// Changing the rate will stop autorun
        if (timescale < 11) timescale++;
        AdjustDelayScale();        
    }
    
    /**
     * Decreases the timescale of the clock by one setting.  Minimum is x1/8
     */
    public static void DecelTime() {
    	if (GlobalFuncs.runtoEq) GlobalFuncs.runtoEq = false;		// Changing the rate will stop autorun
        if (timescale > 1) timescale--;
        AdjustDelayScale();                        
     }
    
    public static void SetTimeScale(byte i) {
    	timescale = i;
    	if (i < 1) timescale = 1;
    	if (i > 11) timescale = 11;
    	
    	AdjustDelayScale();
    }
    
    /**
     * Returns the string corresponding to the current time scale rate 
     * e.g. "x1/8" for 1/8th speed
     * Returns "ERROR" if invalid rate is given
     * @return
     */
    public static String PrintTimeScale() {
        if (paused) return "Paused";
        switch (timescale) {
        	case 1: return "x1/8";
        	case 2: return "x1/4";
        	case 3: return "x1/2";
        	case 4: return "x1";
        	case 5: return "x2";
        	case 6: return "x4";
        	case 7: return "x8";
        	case 8: return "x12";
        	case 9: return "x24";
        	case 10: return "x48";
        	case 11: return "x96";
        	default: return "ERROR";
        }
    }
    
    /**
     * Returns the double representing the current time rate scale
     * e.g. "0.125" for 1/8th speed
     * Returns -1 if invalid rate is given
     * @return
     */
    public static double NumTimeScale() {
        switch (timescale) {
        	case 1: return 0.125;
        	case 2: return 0.250;
        	case 3: return 0.500;
        	case 4: return 1.000;
        	case 5: return 2.000;
        	case 6: return 4.000;
        	case 7: return 8.000;
        	case 8: return 12.000;
        	case 9: return 24.000;
        	case 10: return 48.000;
        	case 11: return 96.000;
        	default: return -1.000;
        }
    }
}
