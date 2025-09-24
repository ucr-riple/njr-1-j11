/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.execution;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author geoagdt
 */
public class Generic_ImageWriter extends Generic_Executor {

    private Set<Generic_EventListener> listeners;

    public Generic_ImageWriter() {
        listeners = new HashSet<Generic_EventListener>();
    }
    
    public static void main(String[] args) {
//                Generic_ImageWriter i = new Generic_ImageWriter();
//                Generic_EventListenerImpl listener = new Generic_EventListenerImpl();
//                i.addGeneric_EventListener(listener);
//                i.start();
        Generic_ImageWriter i = new Generic_ImageWriter();
        Generic_EventListenerImpl listener = new Generic_EventListenerImpl();
        i.addGeneric_EventListener(listener);
        i.start();
        }

    public void addGeneric_EventListener(Generic_EventListener listener) {
        this.listeners.add(listener);
    }

    public void removeGeneric_EventListener(Generic_EventListener listener) {
        this.listeners.remove(listener);
    }

    public void start() {
        run();
        notifyListenersOfRenderingComplete();
        run();
        notifyListenersOfRenderingComplete();
    }

    private void notifyListenersOfRenderingComplete() {
        for (Generic_EventListener e : listeners) {
            e.renderingComplete(new Generic_RenderingCompleteEvent(this));
        }
    }
}
