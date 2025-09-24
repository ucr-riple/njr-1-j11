package minidraw.standard;

import minidraw.framework.*;

import java.util.*;
import java.awt.Rectangle;

/**
 * A Group figure is a fully operational figure containing other figures.

 * Implementation based on JHotDraw 5.1 
 */


public class GroupFigure extends CompositeFigure {

  public Rectangle displayBox() {
    if ( fFigures.size() > 0 ) {
      Iterator<Figure> i = fFigures.iterator();
      Rectangle r = (Rectangle) i.next().displayBox().clone();
      while ( i.hasNext() ) {
        r.add( i.next().displayBox() );
      }
      return r;
    } else {
      return new Rectangle();
    }
  }
}


