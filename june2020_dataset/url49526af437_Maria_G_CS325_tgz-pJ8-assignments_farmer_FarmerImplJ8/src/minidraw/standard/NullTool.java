package minidraw.standard;

import minidraw.framework.*;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

/**
 
 Null tool. Null object for tools.
 
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

public class NullTool implements Tool {
  public void mouseDown(MouseEvent e, int x, int y) {}
      
  public void mouseDrag(MouseEvent e, int x, int y) { }

  public void mouseMove(MouseEvent e, int x, int y) { }

  public void mouseUp(MouseEvent e, int x, int y) { }

  public void activate() { }

  public void deactivate() { }

  public void keyDown(KeyEvent evt, int key) { }
}
