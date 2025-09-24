package minidraw.boardgame;

import minidraw.standard.*;

import java.awt.*;

/** A Figure specifically representing some object in a board game,
 * like a checker, a die, a card, or some other actionable object
 * like a button.
 * 
 * There are two types: moveable objects (like checkers that
 * can be moved by the mouse), and props (like a die that cannot be moved 
 * but only clicked to perform some action).
 * 
 * The BoardFigure role is intimately related to the BoardActionTool role
 * as they have a well defined protocol:
 * 
 * A BoardActionTool will ONLY click or move BoardFigure objects. Once
 * the tool's mouse up event is called, the method 'performAction' is
 * called on the BoardFigure.
 * 
 * A BoardFigure must be associated a command object, i.e. an instance
 * of a command pattern. It is this object's 'execute' method that
 * gets invoked on mouse up events from the BoardActionTool. 

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
public class BoardFigure extends ImageFigure {
  /** the associated command object to execute */
  private Command command;
  /** is this object moveable or a prop? */
  private boolean isMobile;
  
  public BoardFigure(String image, Point origin, 
      boolean isMobile, Command command) {
    super(image, origin);
    this.isMobile = isMobile;
    this.command = command;
  }

  public BoardFigure(String image, boolean isMobile, 
      Command command) {
    super(image, new Point(0,0));
    this.isMobile = isMobile;
    this.command = command;
  }
   
  public BoardFigure() {
    throw new UnsupportedOperationException("BoardFigures can only be created with an associated command object");
  }

  public BoardFigure(Image img, Point origin) {
    throw new UnsupportedOperationException("BoardFigures can only be created with an associated command object");
  }

  public BoardFigure(String name, Point origin) {
    throw new UnsupportedOperationException("BoardFigures can only be created with an associated command object");
  }


  /** Change the image to use. Will
   * invalidate and request redrawing
   * @param imageName name of image loaded by the
   * ImageManager
   */
  public void changeImage(String imageName) {
    ImageManager im = ImageManager.getSingleton();
    Image img = im.getImage(imageName);
    willChange();
    fImage = img;
    changed();
  }
  
  /** return true if this figure can be moved by the
   * BoardActionTool.
   * @return true if mobile
   */
  public boolean isMobile() {
    return isMobile;
  }
  
  /** do not invoke yourself, it is invoked by the BoardActionTool
   * once a BoardFigure has been moved or clicked. The
   * two last parameters are not used for immobile figures.
   * @param fromX x position the figure is moved from (or clicked)
   * @param fromY y position the figure is moved from (or clicked)
   * @param toX x position the figure is moved to
   * @param toY y position the figure is moved to
   * @return true if this manipulation made sense for the underlying
   * game domain (like it is valid to move a checker to this new
   * position.)
   */
  public boolean performAction(int fromX, int fromY, int toX, int toY){
    command.setFromCoordinates(fromX, fromY);
    command.setToCoordinates(toX, toY);
    return command.execute();
  }
}
