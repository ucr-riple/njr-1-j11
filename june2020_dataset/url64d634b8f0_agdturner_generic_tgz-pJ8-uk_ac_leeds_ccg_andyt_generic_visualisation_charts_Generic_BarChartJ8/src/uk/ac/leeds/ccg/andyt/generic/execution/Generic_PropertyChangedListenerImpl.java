/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.execution;

import java.beans.PropertyChangeEvent;

/**
 *
 * @author geoagdt
 */
public class Generic_PropertyChangedListenerImpl implements Generic_PropertyChangedListener {
    
    boolean renderingComplete;
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void renderingComplete(Generic_PropertyChangedListener e) {
        renderingComplete = true;
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
