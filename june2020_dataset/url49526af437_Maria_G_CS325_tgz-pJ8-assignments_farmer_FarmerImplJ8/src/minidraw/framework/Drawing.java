package minidraw.framework;

import java.util.*;

/** Drawing is the model role of the MVC pattern, a container of
 * Figure instances in MiniDraw.
 
 * Responsibilities:
 *  A) Maintain the set of figures (add,remove).
 *  B) Maintain a selection of a subset of these
 *  C) Serve as Subject in observer pattern and notify DrawingListeners
 *  when changes to any figure happens.
 *  D) Serve as Observer in the observer pattern as it observes all
 *  changes in its associated Figures.

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


 * Implementation notes: Much of the interface specification is copied
 * literally from JHotDraw 5.1 however an effort has been made to
 * repartition the interface into smaller interfaces:
 * SelectionHandler, DrawingChangeListenerHandler.
 */
public interface Drawing 
  extends SelectionHandler,
          FigureChangeListener,
          DrawingChangeListenerHandler {

  /** Adds a figure and sets its container to refer to this
   * drawing. If you have several threads that may call add, scope it
   * by the lock/unlock methods.
   * @return the figure that was inserted.
   */
  public Figure add(Figure figure);

  /** Removes a figure. If you have several threads that may call add,
   * scope it by the lock/unlock methods.
   * @return the figure to remove
   */
  public Figure remove(Figure figure);

  /** Return an iterator over drawing's contents.
   @return an iterator over all figures in this drawing.
  */
  public Iterator<Figure> iterator();

  /** Find and return the figure covering position (x,y).
   @param x X coordinate
   @param y Y coordinate
   @return the figure at position (x,y) or null if there is not any there.
  */
  public Figure findFigure(int x, int y);

  /** Request update: force a "repaint" event to all associated
      listeners on this drawing */
  public void requestUpdate();

  /** Acquires a lock on the list of figures in this drawing. If you
   * add or remove figures to a drawing when other threads are
   * potentially iterating over the very same list (as AWT/Swing does
   * when redrawing!) you have to scope the add/remove by a
   * lock/unlock pair. For instance like this:
   *
   * drawing.lock();
   * drawing.add( aFigure );
   * drawing.unlock();
   *
   * Otherwise you may get a ConcurrentModificationException.
   */
  public void lock();
  
  /** Releases the drawing lock. See the discussion for the lock()
   * method.
   */
  public void unlock();
}



