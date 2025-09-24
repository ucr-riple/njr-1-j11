package minidraw.framework;

import java.util.List;

/** The selection handler role defines the interface for the
    responsibility of managing a drawing's multiple figure selection mechanism.

    Responsibility
    A) Act as a collection of figures presently selected in a drawing.
    B) Support adding, removing, and state toggling figures
    C) Any adding or removing of a figure MUST be followed
    by a call to the figure's 'changed' method in order for the
    views to repaint the figure correctly! See StandardSelectionHandler.

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
public interface SelectionHandler {

  /**
   * Get an iterator over all selected figures
   */
  public List<Figure> selection();
  
  /**
   * Adds a figure to the current selection.
   * @param figure the figure to add to the selection
   */
  public void addToSelection(Figure figure);
  
  /**
   * Removes a figure from the selection.
   * @param figure the figure to remove
   */
  public void removeFromSelection(Figure figure);
  
  /**
   * If a figure isn't selected it is added to the selection.
   * Otherwise it is removed from the selection.
   * @param figure the figure to toggle
   */
  public void toggleSelection(Figure figure);
  
  /**
   * Clears the current selection.
   */
  public void clearSelection();
}
