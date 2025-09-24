package minidraw.boardgame;

import java.awt.Point;
/** This role defines a strategy (strategy pattern) encapsulating the
 * algorithms to calculate the proper graphical position (x,y) for a
 * figure that is located on a given location on a board game.
 * 
 * Collaborators: BoardDrawing that uses this strategy during
 * initialization of the collection of figures (to position figures
 * correctly based upon the game's layout of pieces) and during
 * moves (to adjust a user's inaccurate dragging of a figure representing
 * a figure).
 * 
 * The typical example is a mouse drag of a chess piece to a square.
 * In most cases the piece will not be positioned by the user on
 * the exact center position of the square. The BoardDrawing will
 * request its PositioningStrategy object to calculate the correct
 * central position and then move the figure for perfect centering.
 *
 * Invariant: The positions returned by the calculate method must be
 * non-overlapping for disjoint locations. That is, if positions
 * returned by the positioning strategy for location X could be
 * mistaken as belonging to location Y, MiniDraw.BoardGame will become very
 * confused. The user will probably as well.
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
 */
public interface PositioningStrategy<LOCATION> {
  /** Calculate proper graphical (x,y) coordinates of a figure that
   * has been moved to a given location on the board.
   * @param location the location to which the figure has been moved
   * @param index the offset of this figure on the location. For games
   * that only allow one piece/figure per location, this index is
   * always 0. For games with multiple pieces on a location, it
   * indicates which of the pieces MiniDraw wants new coordinates
   * for. Eg. in backgammon if index = 1 then it is the second checker
   * on that location that is requested coordinates for.
   * @return the graphical (x,y) coordinates of the adjusted position of
   * the figure. 
   */
  public Point calculateFigureCoordinatesIndexedForLocation(LOCATION location, 
                                                            int index);
  /** Calculate proper coordinates for a prop (unmovable BoardFigure)
   * based upon its key (unique string identifier). Usually props
   * have fixed position so generally this calculation is just
   * a lookup.
   * @return the graphical (x,y) coordinates of the position of the prop.
   */
  public Point calculateFigureCoordinatesForProps(String keyOfProp);

}
