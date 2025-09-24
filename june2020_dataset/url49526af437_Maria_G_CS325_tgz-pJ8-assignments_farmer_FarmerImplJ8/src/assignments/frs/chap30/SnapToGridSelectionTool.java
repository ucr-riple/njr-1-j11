package assignments.frs.chap30;

import java.awt.event.MouseEvent;

import minidraw.framework.*;
import minidraw.standard.AbstractTool;
import minidraw.standard.NullTool;
import minidraw.standard.handlers.*;

public  class SnapToGridSelectionTool extends AbstractTool implements Tool {

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
  
  private int gridRows;
  
  private int gridCols;
  
  public SnapToGridSelectionTool(DrawingEditor editor, int gridRows, int gridCols) {
    this(editor, new StandardRubberBandSelectionStrategy() );
    this.gridRows = gridRows;
    this.gridCols = gridCols;
  }

  /** Define a selection tool where the SelectAreaTracker takes
      a special RubberBandSelection strategy.
  */
  public SnapToGridSelectionTool(DrawingEditor editor, 
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
    return new SnapToGridDragTracker(editor(), f, this.gridRows, this.gridCols);
  }

  /**
   * Factory method to create an Area Tracker. It is used to select an area.
   */
  protected Tool createAreaTracker() {
    return new SelectAreaTracker(editor(), selectionStrategy );
  }
 
}
