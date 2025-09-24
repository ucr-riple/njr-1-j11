package minidraw.framework;

/** The DrawingChangeListenerHandler defines a role for an object that
    maintains the set of DrawingChangeListener's used by a Drawing.

    Responsibility
    A) Maintain the list of DrawingListeners


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
public interface DrawingChangeListenerHandler {
  /**
   * Adds a listener for this drawing.
   */
  public void addDrawingChangeListener(DrawingChangeListener listener);
  
  /**
   * Removes a listener from this drawing.
   */
  public void removeDrawingChangeListener(DrawingChangeListener listener);
}
