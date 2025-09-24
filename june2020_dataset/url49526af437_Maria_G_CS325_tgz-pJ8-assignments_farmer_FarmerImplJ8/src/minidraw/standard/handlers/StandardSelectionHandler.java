package minidraw.standard.handlers;

import minidraw.framework.*;

import java.util.List;
import java.util.ArrayList;

/** The standard selection handler contains default implementation
    of manageing a drawing's multiple figure selection mechanism.

    Responsibility
    A) Act as a collection of figures presently selected in a drawing.

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

public class StandardSelectionHandler implements SelectionHandler {

  /** list of all figures currently selected */
  protected List<Figure> selectionList;
  
  public StandardSelectionHandler() {
    selectionList = new ArrayList<Figure>();
  }

  /**
   * Get an iterator over all selected figures
   */
  public List<Figure> selection() {
    return selectionList;
  }

  /**
   * Adds a figure to the current selection.
   */
  public void addToSelection(Figure figure) {
    if (! selectionList.contains(figure) ) {
      selectionList.add(figure);
      figure.changed();
    }
  }

  /**
   * Removes a figure from the selection.
   */
  public void removeFromSelection(Figure figure) {
    if ( selectionList.contains(figure) ) {
      selectionList.remove(figure);
      figure.changed();
    }
  }

  /**
   * If a figure isn't selected it is added to the selection.
   * Otherwise it is removed from the selection.
   */
  public void toggleSelection(Figure figure) {
    if (selectionList.contains(figure)) {
      removeFromSelection(figure);
    } else {
      addToSelection(figure);
      figure.changed();
    }
  }
  
  /**
   * Clears the current selection.
   */
  public void clearSelection() {
    for (Figure f: selectionList) {
      f.changed();
    }
    selectionList = new ArrayList<Figure>();
  }
}
