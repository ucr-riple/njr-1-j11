package minidraw.framework;

import java.awt.*;
import java.util.EventObject;

/** The change event originating from a drawing.
 
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
public class DrawingChangeEvent extends EventObject {

  private Rectangle fRectangle;

  /** Constructs a drawing change event. 
   @param source the Drawing instance this change event stems from
   @param r the rectangle that needs to be redrawn
  */
  public DrawingChangeEvent(Drawing source, Rectangle r) {
    super(source);
    fRectangle = r;
  }
  
  /** Return the drawing that was changed
      @return a reference to the changed drawing
  */
  public Drawing getDrawing() {
    return (Drawing)getSource();
  }
  
  /** Gets the changed rectangle
      @return the rectangle that needs to be redrawn
  */
  public Rectangle getInvalidatedRectangle() {
    return fRectangle;
  }
}
