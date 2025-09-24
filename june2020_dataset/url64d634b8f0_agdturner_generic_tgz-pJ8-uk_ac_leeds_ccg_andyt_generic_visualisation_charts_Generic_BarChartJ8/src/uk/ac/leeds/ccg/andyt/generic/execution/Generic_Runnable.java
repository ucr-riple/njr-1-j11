/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.execution;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author geoagdt
 */
public class Generic_Runnable extends Thread implements Runnable  {

    Generic_EventListenerImpl listener;
    
    public Generic_Runnable(){}
    
    public static void main(String[] args){
        new Generic_Runnable().run();
    }
    
    @Override
    public void run() {
        try {
            long timeSleepInMillis = 10000;
            System.out.println("Sleeping for " + timeSleepInMillis + " milliseconds...");
            Thread.sleep(timeSleepInMillis);
            System.out.println("...done sleeping");
            listener.renderingComplete(new Generic_RenderingCompleteEvent(this));
            Thread.sleep(timeSleepInMillis*timeSleepInMillis);
        } catch (InterruptedException ex) {
            Logger.getLogger(Generic_Runnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
