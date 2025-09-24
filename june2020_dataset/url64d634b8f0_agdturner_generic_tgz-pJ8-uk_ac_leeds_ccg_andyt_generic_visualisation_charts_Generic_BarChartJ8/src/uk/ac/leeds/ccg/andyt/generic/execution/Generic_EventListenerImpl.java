/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.execution;

/**
 *
 * @author geoagdt
 */
public class Generic_EventListenerImpl implements Generic_EventListener {

    boolean renderingComplete;
    
    @Override
    public void renderingComplete(Generic_RenderingCompleteEvent e) {
        renderingComplete = true;
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
