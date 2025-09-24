package minidraw.standard;

import minidraw.framework.*;
import minidraw.standard.handlers.*;

import java.awt.*;
//import javax.swing.*;

import java.util.List;

/** Standard implemementation of drawing.  */

public class StandardDrawing 
  extends CompositeFigure 
  implements Drawing {

  /** list of all figures currently selected */
  protected SelectionHandler selectionHandler;
  
  /** use a StandardDrawingChangeListenerHandler to handle all
      observer pattern subject role behaviour */
  protected StandardDrawingChangeListenerHandler listenerHandler;

  public StandardDrawing() {
    selectionHandler = new StandardSelectionHandler();
    listenerHandler = new StandardDrawingChangeListenerHandler();
  }

  // === Delegation methods for the DrawingChangeListeners

  /**
   * Adds a listener for this drawing.
   */
  public void addDrawingChangeListener(DrawingChangeListener listener) {
    listenerHandler.addDrawingChangeListener(listener);
  }
  
  /**
   * Removes a listener from this drawing.
   */
  public void removeDrawingChangeListener(DrawingChangeListener listener) {
    listenerHandler.removeDrawingChangeListener(listener);
  }

  /**
   * Invalidates a rectangle and merges it with the
   * existing damaged area.
   * @see FigureChangeListener
   */
  public void figureInvalidated(FigureChangeEvent e) {
    listenerHandler.
      fireDrawingInvalidated( this, e.getInvalidatedRectangle() );
  }
 
  public void figureChanged(FigureChangeEvent e){
    listenerHandler.fireDrawingRequestUpdate( this );
  }

  public void requestUpdate() {
    listenerHandler.fireDrawingRequestUpdate( this );
  }

  // === Delegation methods for the Selection handling

  /**
   * Get a list of all selected figures
   */
  public List<Figure> selection() {
    return selectionHandler.selection();
  }

  /**
   * Adds a figure to the current selection.
   */
  public void addToSelection(Figure figure) {
    selectionHandler.addToSelection(figure);
  }

  /**
   * Removes a figure from the selection.
   */
  public void removeFromSelection(Figure figure) {
    selectionHandler.removeFromSelection(figure);
  }

  /**
   * If a figure isn't selected it is added to the selection.
   * Otherwise it is removed from the selection.
   */
  public void toggleSelection(Figure figure) {
    selectionHandler.toggleSelection(figure);
  }
  
  /**
   * Clears the current selection.
   */
  public void clearSelection() {
    selectionHandler.clearSelection();
  }

  // === We do some opportunistic reuse of the CompositeFigure but
  // unfortunately it defines methods that are not usable for a
  // Drawing.

  /** should not be used by a drawing - the reason this method is
      defined is because of the opportunistic reuse opportunities of
      the CompositeFigure */
  public void basicDisplayBox( int x, int y ) {
    throw new 
      RuntimeException( "StandardDrawing: basicDisplayBox should not be invoked");
  }
  
  public Rectangle displayBox() {
      throw new 
        RuntimeException( "StandardDrawing: displayBox should not be invoked");
  }


  /**
   * boolean that serves as a condition variable
   * to lock the access to the drawing.
   * The lock is recursive and we keep track of the current
   * lock holder.
   */
  private transient Thread    fDrawingLockHolder = null;
  
  public synchronized void lock() {
    // recursive lock
    Thread current = Thread.currentThread();
    if (fDrawingLockHolder == current) {
      return;
    }
    while (fDrawingLockHolder != null) {
      try { wait(); } catch (InterruptedException ex) { }
    }
    fDrawingLockHolder = current;
  }
  
  public synchronized void unlock() {
    if (fDrawingLockHolder != null) {
      fDrawingLockHolder = null;
      notifyAll();
    }
  }
}

