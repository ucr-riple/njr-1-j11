package minidraw.standard;

import minidraw.framework.*;

import java.util.*;
import java.awt.Graphics;

/**
 * A Figure that is composed of several figures. 

 * Implementation based on JHotDraw 5.1 
 */


public abstract class CompositeFigure
  extends AbstractFigure
  implements FigureChangeListener {
  
  protected List<Figure> fFigures; 
  
  public CompositeFigure() {
    fFigures= new ArrayList<Figure>();
  }

  public synchronized Figure add(Figure figure) {
    if ( ! fFigures.contains(figure) ) {
      fFigures.add(figure);
      figure.addFigureChangeListener(this);
      figure.changed();
    }
    return figure;
  }

  public synchronized Figure remove(Figure figure) {
    if ( fFigures.contains(figure) ) {
      fFigures.remove(figure);
      figure.changed();
      figure.removeFigureChangeListener(this);
    }
    return figure;
  }
  
  /** NOTE: Iteration is not thread safe. If you
      modify in one thread and iterate in another
      you must 'lock' the drawing while iteration occurs.
   */
  public Iterator<Figure> iterator() {
    return fFigures.iterator();
  }
  
  public Figure findFigure(int x, int y) {
    // Figures are drawn in order 0..size of fFigures,
    // which means that last added figure is on top.
    // Thus to find the "right" figure when they overlap
    // we must iterate backwards (otherwise the user
    // clicks a figure but gets any figure behind it).
    Figure found = null;
    // doing it the old raw way
    int i = fFigures.size()-1;
    while( i >= 0 ) {
      Figure f = fFigures.get(i);
      if ( f.displayBox().contains(x,y) ) {
        found = f; 
        break;
      }
      i--;
    }
    return found;
  }

  public void draw(Graphics g) {
    for ( Figure f : fFigures ) {
       f.draw(g);
     }
  }

  protected void basicMoveBy(int dx, int dy) {
    for ( Figure f : fFigures ) {
       f.moveBy(dx, dy);
     }
  }

  public void figureInvalidated(FigureChangeEvent e) {
  }

  public void figureChanged(FigureChangeEvent e){
  }

  public void figureRemoved(FigureChangeEvent e){}

  public void figureRequestRemove(FigureChangeEvent e){}

  public void figureRequestUpdate(FigureChangeEvent e){}

}

