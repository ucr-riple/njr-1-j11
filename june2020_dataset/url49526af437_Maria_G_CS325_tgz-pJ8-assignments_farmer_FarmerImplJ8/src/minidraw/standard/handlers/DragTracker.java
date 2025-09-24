package minidraw.standard.handlers;

import minidraw.framework.*;
import minidraw.standard.*;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

/** A DragTracker tool moves the set of figures defined by the drawing's
 * selection container (= the figures presently selected).

 * This code is partially copied from JHotDraw 5.1

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

public class DragTracker extends AbstractTool implements Tool {

  private Figure  figure;
  private int     fLastX, fLastY;      // previous mouse position

  public DragTracker(DrawingEditor editor, Figure figure) {
    super(editor);
    this.figure = figure;
    
  }

  public void mouseDown(MouseEvent e, int x, int y) {
    super.mouseDown(e, x, y);
    fLastX = x; fLastY = y;
    
    Drawing model = editor().drawing();
    
    if ( e.isShiftDown() ) {
      model.toggleSelection(figure);
    } else if ( ! model.selection().contains(figure) ) {
      model.clearSelection();
      model.addToSelection(figure);
    }
  }

  public void mouseDrag(MouseEvent e, int x, int y) {
    for ( Figure f : editor().drawing().selection() ) {
      f.moveBy( x - fLastX, y - fLastY );
    }
    fLastX = x;
    fLastY = y; 
  }

  public void keyDown(KeyEvent evt, int key) {
  }
}
