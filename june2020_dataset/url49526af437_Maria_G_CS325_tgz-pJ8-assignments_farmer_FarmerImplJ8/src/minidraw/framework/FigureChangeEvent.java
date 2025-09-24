package minidraw.framework;

import java.awt.Rectangle;
import java.util.EventObject;

/**
 * FigureChange event passed to FigureChangeListeners.
 *

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
public class FigureChangeEvent extends EventObject {
  private Rectangle rect;
  private static final Rectangle  
    emptyRect = new Rectangle(0, 0, 0, 0);
  
  /**
   * Constructs an event for the given source Figure. The rectangle is the
   * area to be invalvidated.
   */
  public FigureChangeEvent(Figure source, Rectangle r) {
    super(source);
    rect = r;
  }
  
  /**
   * Constructs an event for the given source Figure with an empty
   * rectangle.
   */
  public FigureChangeEvent(Figure source) {
    super(source);
    rect = emptyRect;
  }
  
  /**
   *  Gets the changed figure.
   */
  public Figure getFigure() {
    return (Figure)getSource();
  }
  
  /**
   *  Gets the changed rectangle.
   */
  public Rectangle getInvalidatedRectangle() {
    return rect;
  }
}
