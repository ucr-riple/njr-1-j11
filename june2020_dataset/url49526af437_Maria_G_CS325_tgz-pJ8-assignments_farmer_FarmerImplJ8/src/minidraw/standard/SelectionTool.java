package minidraw.standard;

import java.awt.event.MouseEvent;

import minidraw.framework.*;
import minidraw.standard.handlers.*;

/**
  Selection tool: Uses a internal state pattern to define what
 type of tool to use in the current situation.

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

public  class SelectionTool extends AbstractTool implements Tool {

  /** Subtool to delegate to. The selection tool is in itself
     a state tool that may be in one of several states given
     by the sub tool.
     Class Invariant: fChild tool is never null */
  protected Tool fChild;
  /** helper null tool to avoid creating and destroying objects
     all the time */
  protected Tool cachedNullTool;
  
  /** the figure that is being dragged. If null then its operation
      is not that of dragging a figure (or a set of figures) */
  protected Figure draggedFigure;

  /** the rubber band selection strategy to use. */
  RubberBandSelectionStrategy selectionStrategy;
  
  public SelectionTool(DrawingEditor editor) {
    this(editor, new StandardRubberBandSelectionStrategy() );
  }

  /** Define a selection tool where the SelectAreaTracker takes
      a special RubberBandSelection strategy.
  */
  public SelectionTool(DrawingEditor editor, 
                       RubberBandSelectionStrategy selectionStrategy ) {
    super(editor);
    fChild = cachedNullTool = new NullTool();
    draggedFigure = null;
    this.selectionStrategy = selectionStrategy;
  }

  /**
   * Handles mouse down events and starts the corresponding tracker.
   */
  public void mouseDown(MouseEvent e, int x, int y)
  {
    Drawing model = editor().drawing();
    
    model.lock();

    draggedFigure = model.findFigure(e.getX(), e.getY());

    if ( draggedFigure != null ) {
      fChild = createDragTracker( draggedFigure );
    } else {
      if ( ! e.isShiftDown() ) {
        model.clearSelection();
      }
      fChild = createAreaTracker();
    }
    fChild.mouseDown(e, x, y);
  }
      
  public void mouseDrag(MouseEvent e, int x, int y) {
    fChild.mouseDrag(e, x, y);
  }

  public void mouseMove(MouseEvent e, int x, int y) {
    fChild.mouseMove(e, x, y);
  }

  public void mouseUp(MouseEvent e, int x, int y) {
    editor().drawing().unlock();

    fChild.mouseUp(e, x, y);
    fChild = cachedNullTool;
    draggedFigure = null;
  }
   

  /**
   * Factory method to create a Drag tracker. It is used to drag a figure.
   */
  protected Tool createDragTracker(Figure f) {
    return new DragTracker(editor(), f);
  }

  /**
   * Factory method to create an Area Tracker. It is used to select an area.
   */
  protected Tool createAreaTracker() {
    return new SelectAreaTracker(editor(), selectionStrategy );
  }
 
}
