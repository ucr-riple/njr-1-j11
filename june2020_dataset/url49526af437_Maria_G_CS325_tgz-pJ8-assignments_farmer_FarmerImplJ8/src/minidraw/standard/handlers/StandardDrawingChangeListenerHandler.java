package minidraw.standard.handlers;

import minidraw.framework.*;

import java.awt.*;
//import javax.swing.*;

import java.util.List;
import java.util.ArrayList;

/** The Subject role of the observer pattern for DrawingChangeListeners.

    You can use this default implementation in all objects that must
    handle DrawingChangeListeners.

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Computer Science Department
     Aarhus University
   
   This source code is provided WITHOUT ANY WARRANTY either 
   expressed or implied. You may study, use, modify, and 
   distribute it for non-commercial purposes. For any 
   commercial use, see http://www.baerbak.com/
*/

public class StandardDrawingChangeListenerHandler
  implements DrawingChangeListenerHandler {

  /** list over all associated listeners */
  protected List<DrawingChangeListener> fListeners;

  public StandardDrawingChangeListenerHandler() {
    fListeners = new ArrayList<DrawingChangeListener>();
  }

  /**
   * Adds a listener for this drawing.
   */
  public void addDrawingChangeListener(DrawingChangeListener listener) {
    fListeners.add(listener);
  }
  
  /**
   * Removes a listener from this drawing.
   */
  public void removeDrawingChangeListener(DrawingChangeListener listener) {
    fListeners.remove(listener);
  }

  /**
   * Fire a 'drawingInvalidated' event
   */
  public void fireDrawingInvalidated(Drawing source, Rectangle r) {
    for (DrawingChangeListener l : fListeners ) {
      l.drawingInvalidated(new DrawingChangeEvent(source, r));
    }
  }

  /**
   * Fire a 'drawingUpdate' event
   */
  public void fireDrawingRequestUpdate(Drawing source) {
    for (DrawingChangeListener l : fListeners ) {
      l.drawingRequestUpdate(new DrawingChangeEvent(source, null));
    }
  }
}
