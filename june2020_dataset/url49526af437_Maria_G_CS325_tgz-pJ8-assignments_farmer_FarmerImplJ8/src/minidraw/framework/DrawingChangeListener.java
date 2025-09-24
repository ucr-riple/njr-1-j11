package minidraw.framework;

import java.util.EventListener;

/** DrawingChangeListener defines the observer role of an object
    listening to DrawingChangeEvents from a Drawing.  

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

public interface DrawingChangeListener extends EventListener {
  /** Called when a drawing has areas that needs to be redrawn.
   */
  public void drawingInvalidated(DrawingChangeEvent e);
  
  /** Callend when the drawing wants to be refreshed
   */
  public void drawingRequestUpdate(DrawingChangeEvent e);
}
