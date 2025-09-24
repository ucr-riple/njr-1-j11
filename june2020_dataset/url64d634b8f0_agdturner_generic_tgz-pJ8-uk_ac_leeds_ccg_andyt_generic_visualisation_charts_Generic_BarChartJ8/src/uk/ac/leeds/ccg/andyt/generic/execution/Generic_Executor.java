/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.execution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author geoagdt
 */
public class Generic_Executor {

    public Generic_ImageWriter i;
    
    public Generic_Executor() {
    }

    public Generic_Executor(Generic_ImageWriter i) {
    }

    public static void main(String[] args) {
        new Generic_Executor().run();
    }

    public void run() {
        int poolSize = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < poolSize * 2; i++) {
            final Generic_Runnable r = new Generic_Runnable();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    r.run();
                }
            });
        }
        executorService.shutdown();
        try {
            // wait for them to finish for up to one minute.
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException ex) {
            Logger.getLogger(Generic_Executor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
