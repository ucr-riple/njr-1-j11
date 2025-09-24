package minidraw.standard;

import minidraw.framework.*;

import java.awt.Rectangle;
import java.util.*;

/** Abstract Figure: Base implementation of some Figure behaviour.
 * Responsibilities: A) All observer role "Subject" base functionality 
 * is provided.
 *
 * Code partly copied from JHotDraw 5.1.
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
 *
*/

public abstract class AbstractFigure implements Figure {

  /** the listeners of this figure */
  List<FigureChangeListener> listenerList;

  /** Base construction of a figure */
  public AbstractFigure() {
    listenerList = new ArrayList<FigureChangeListener>();
  }

  public void moveBy(int dx, int dy) {
    willChange();
    basicMoveBy(dx, dy);
    changed();
  }
  
  /**
   * Informes that a figure is about to change something that
   * affects the contents of its display box.
   */
  protected void willChange() {
    invalidate();
  }

  public void invalidate() {
    for (FigureChangeListener l : listenerList ) {
      Rectangle r = (Rectangle) displayBox().clone();
      FigureChangeEvent e = new FigureChangeEvent(this, r);
      l.figureInvalidated( e );
    }
  }
  
  /**
   * This is the hook method to be overridden when a figure
   * moves. Do not call directly, instead call 'moveBy'.
   */
  protected abstract void basicMoveBy(int dx, int dy);

  
  public void changed() {
    invalidate();
    for (FigureChangeListener l : listenerList ) {
      FigureChangeEvent e = new FigureChangeEvent(this);
      l.figureChanged( e );
    }
  }

  public void addFigureChangeListener(FigureChangeListener l) {
    listenerList.add(l);
  }
  
  public void removeFigureChangeListener(FigureChangeListener l) {
    listenerList.remove(l);
  }
  
}
