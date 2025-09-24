package minidraw.boardgame;

/** A Command pattern to encapsulate method calls on
 * a board game based upon clicking or moving graphical
 * figures on the graphical display. Typical examples:
 * A) Moving a graphical pawn image over a chess board
 * image should be tied to a command to move the pawn
 * in the underlying chess domain code; B) clicking a
 * card image in a memory game should 'flip' the card.
 * 
 * Collaborators: BoardActionTool and BoardFigure.
 * The standard sequence is to create a BoardFigure
 * with an instance of a command. Next the user
 * will move/click on a BoardFigure using the
 * BoardActionTool which knows the protocol:
 * The BoardActionTool will invoke method 'setFromCoordinates' 
 * on the mouse down click on a BoardFigure and invoke 
 * the 'setToCoordinates' and next 'execute' on mouse up. 
 * 
 * A final collaborator is of course the game domain object
 * itself. Typically this is assigned in the constructor
 * of objects implementing this interface.
 * 
 * Responsibilities:
 * A) translate mouse clicks/mouse drags on BoardFigure into
 * domain specific game state changes by implementing the
 * 'execute' method
 * B) the execute method is responsible for range checking
 * the mouse coordinates so no illegal parameters are
 * passed to the game instance.
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
public interface Command {
  
  /** set the coordinates of the mouse down event;
   * where the image is moved FROM.
   * @param fromX x coordinate of mouse 
   * @param fromY y coordinate of mouse
   */
  public void setFromCoordinates(int fromX, int fromY);

  /** set the coordinates of the mouse up event;
   * where the image is moved TO.
   * @param toX x coordinate of mouse 
   * @param toY y coordinate of mouse
   */
  public void setToCoordinates(int toX, int toY);
  
  /** execute encapsulates the method call on the board game. 
   * NOTE: it is this method's responsibility return false
   * in case the given from and to coordinates cannot
   * be translated into a meaningful board game invokation
   * and in this case return false. Example: moving a
   * piece outside the board may translate into calling
   * a game's move method with a null parameter which
   * will next generate a null pointer exception in the
   * game logic.
   * @return false if the action, typically a move, is illegal
   * in the board game. 
   * */
  public boolean execute();
}
