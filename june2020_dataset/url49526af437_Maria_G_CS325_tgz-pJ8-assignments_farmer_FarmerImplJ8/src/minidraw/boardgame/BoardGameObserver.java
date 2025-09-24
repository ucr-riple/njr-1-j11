package minidraw.boardgame;

/** This defines the Observer pattern's observer role that allows the
 * BoardDrawing to observe a board game's game instance and react by
 * redrawing figures. A distinction is made between 'piece' which are
 * the moveable objects in a board game (like checkers), and 'props'
 * which are the objects with fixed position but changeable
 * appearance (like cards or dice).

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
public interface BoardGameObserver<LOCATION> {
  /** the update method for pieces moved from location 'from' to
   * location 'to'. 
   * @param from the LOCATION that a piece has moved from
   * @param to the LOCATION that a piece has moved to
   */
  public void pieceMovedEvent(LOCATION from, LOCATION to);

  /** the update method for props changed. 
   * @param keyOfChangedProp a string key identifying the
   * game domain object that has changed. This key must
   * be identical to that assigned in the FigureFactory's
   * 
   */
  public void propChangeEvent(String keyOfChangedProp);
}
