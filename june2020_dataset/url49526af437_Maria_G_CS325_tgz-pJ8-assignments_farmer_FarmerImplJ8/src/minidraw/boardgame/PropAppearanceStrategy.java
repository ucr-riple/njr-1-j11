package minidraw.boardgame;

/** This role defines a strategy (strategy pattern) encapsulating the
 * algorithms to determine the appearance of a prop used in
 * a board game.
 *
 * Create an instance of this strategy and supply it to the BoardDrawing
 * so it can find the proper image to set on a prop (BoardFigure) once
 * the underlying game emits a propChangeEvent.
 *
 * Example: In backgammon, when a die is rolled, the game instance
 * should emit a propChangeEvent for that particular die. The
 * BoardDrawing will listen/observe the propChangeEvent and
 * for the given die, request an instance of this interface to
 * generate the die image to display on the GUI. 

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
public interface PropAppearanceStrategy {
  /* return the name of an image to be used for a prop.
   * @param keyOfProp the key of the particular prop in the (key,
   * board figure) map that the FigureFactory has generated.
   * @return the string identifying the image stored in the MiniDraw
   * ImageManager that should be used for the given prop.
*/
  public String calculateImageNameForPropWithKey(String keyOfProp);
}
