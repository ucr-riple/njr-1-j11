package minidraw.framework;

import java.awt.event.*;

/** Tool is the Controller role in the MVC pattern for MiniDraw. A
 * tool must process all user input events and translate them into
 * modifications of the Drawing (= model role).
 *
 * The interface follows the schema defined in JHotDraw.

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
public interface Tool {
  /** Handles mouse down events in the drawing view. */
  public void mouseDown(MouseEvent e, int x, int y);
  
  /** Handles mouse drag events in the drawing view (mouse button is
   * down). */
  public void mouseDrag(MouseEvent e, int x, int y);
  
  /** Handles mouse up in the drawing view. */
  public void mouseUp(MouseEvent e, int x, int y);
  
  /** Handles mouse moves (if the mouse button is up). */
  public void mouseMove(MouseEvent evt, int x, int y);
  
  /** Handles key down events in the drawing view. */
  public void keyDown(KeyEvent evt, int key);
}
