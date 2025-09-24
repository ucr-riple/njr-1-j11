package minidraw.standard.handlers;

import minidraw.framework.*;

import java.awt.*;
import java.util.Iterator;

/** The standard RubberBandSelection strategy that simply selects
    all figures within the rubber band rectangle.

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

public class StandardRubberBandSelectionStrategy
  implements RubberBandSelectionStrategy {

  public void selectGroup(Drawing model, Rectangle rubberBandRectangle,
                          boolean toggle) {
    Iterator<Figure> i = model.iterator();
    while ( i.hasNext() ) {
      Figure figure = i.next();
      Rectangle r2 = figure.displayBox();
      if (rubberBandRectangle.contains(r2.x, r2.y) && 
          rubberBandRectangle.contains(r2.x+r2.width, r2.y+r2.height)) {
        if (toggle)
          model.toggleSelection(figure);
        else
          model.addToSelection(figure);
       }
    }
  }
}
