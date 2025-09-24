package minidraw.framework;

import java.awt.*;

/** This interface defines the role of a Figure in a MiniDraw
    drawing. Figure instances acts as 

    Responsibilities:
    A) Represent a Figure in the model.
    B) Draw itself in a Graphics context.
    C) Allow manipulations like moving. 
    D) Has the Subject role in the observer pattern as a Figure 
    broadcasts FigureChangeEvents whenever it changes.


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

public interface Figure {

  /**
   * Draws the figure.
   * @param g the Graphics to draw into
   */
  void draw(Graphics g);

  /**
   * Return the display box of this figure. The display box is the 
   * smallest rectangle that completely contains the figure.
   * @return the display box.
   */
  Rectangle displayBox();

  /**
   * Move the figure by a delta (dx, dy) offset from its present
   * position.
   * @param dx amount to move in x 
   * @param dy amount to move in y 
   */
  void moveBy(int dx, int dy);

  /**
   * Invalidates the figure. This method informs the listeners
   * that the figure's current display box is invalid and should be
   * redrawn.
   */
  void invalidate();

  /**
   * Informes that a figure has changed its display box.
   * This method also triggers an update call for its
   * registered observers.
   */
  void changed();

  /**
   * Adds a listener for this figure.
   */
  void addFigureChangeListener(FigureChangeListener l);
  
  /**
   * Removes a listener for this figure.
   */
  void removeFigureChangeListener(FigureChangeListener l);
}
