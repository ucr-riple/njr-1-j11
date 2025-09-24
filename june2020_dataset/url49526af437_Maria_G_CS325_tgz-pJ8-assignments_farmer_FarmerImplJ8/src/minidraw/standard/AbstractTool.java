package minidraw.standard;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import minidraw.framework.*;

/**
 * AbstractTool. Provides some rudimentary behaviour for
 * other tools to override.
 *
 * Modeled over the schema from JHotDraw.
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

public abstract class AbstractTool implements Tool {

  protected DrawingEditor editor;
  protected int fAnchorX, fAnchorY;

  /** Abstract base class for all tools.
      @param editor the editor (object server) that this tool is
      associated with.
  */
  public AbstractTool(DrawingEditor editor) {
    this.editor = editor;
  }

  public void mouseDown(MouseEvent e, int x, int y) {
    fAnchorX = x;
    fAnchorY = y;
  }
  
  public void mouseDrag(MouseEvent e, int x, int y) { }
  
  public void mouseUp(MouseEvent e, int x, int y) { }

  public void mouseMove(MouseEvent evt, int x, int y) { }
  
  public void keyDown(KeyEvent evt, int key) { }

  public DrawingEditor editor() {
    return editor;
  }
  
}
