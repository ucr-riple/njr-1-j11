package assignments.frs.chap30;

import minidraw.framework.*;
import minidraw.standard.*;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

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

public class SnapToGridDragTracker extends AbstractTool implements Tool {

  private Figure  figure;
  private int     fLastX, fLastY;      // previous mouse position
  private int 	  gridRows, gridCols;

  public SnapToGridDragTracker(DrawingEditor editor, Figure figure, int gridRows, int gridCols) {
    super(editor);
    this.figure = figure;
    this.gridRows = gridRows;
    this.gridCols = gridCols;
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

  public void mouseUp(MouseEvent e, int x, int y) {
	  
	  StdViewWithBackground edView = (StdViewWithBackground) editor.view();
	  
	  Rectangle sz = edView.getBounds();
	  
	  Rectangle box;
		for ( Figure f : editor().drawing().selection() ) {
			box=f.displayBox();	
			  
			int xVal = findGridPointValue(box.x, this.gridCols, sz.width);
			int yVal = findGridPointValue(box.y, this.gridRows, sz.height);
			f.moveBy( xVal - box.x, yVal - box.y );
		}
	  
  }
  
  private int findGridPointValue(int ptVal, int numGridBoxes, int maxSz){
	  int gridBoxSz = maxSz / numGridBoxes; 

	  //Initialize ArrayLists
	  ArrayList<Integer> gridBoxPts = new ArrayList<Integer>(numGridBoxes);	 
	  ArrayList<Integer> gridBoxPtsDiffs = new ArrayList<Integer>(numGridBoxes);
	  
	  //Create ArrayLists
	  for(int i = 0; i < numGridBoxes; i++){
		  gridBoxPts.add(i, i * gridBoxSz);
	  }	  
	  for(int i = 0; i < numGridBoxes; i++){
		  gridBoxPtsDiffs.add(i, Math.abs(ptVal - gridBoxPts.get(i)));
	  }
	  	  
	  int idxVal = gridBoxPtsDiffs.indexOf(Collections.min(gridBoxPtsDiffs));
	  
	  return gridBoxPts.get(idxVal);	  
  }
  
  public void keyDown(KeyEvent evt, int key) {
  }
}
