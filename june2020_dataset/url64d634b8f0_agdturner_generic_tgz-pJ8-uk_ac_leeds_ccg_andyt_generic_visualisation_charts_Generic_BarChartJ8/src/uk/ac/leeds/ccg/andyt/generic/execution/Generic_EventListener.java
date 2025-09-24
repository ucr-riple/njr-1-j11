/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.execution;

import java.util.EventListener;

/**
 *
 * @author geoagdt
 */
public interface Generic_EventListener extends EventListener {
    
    public void renderingComplete(Generic_RenderingCompleteEvent e);
    
}
