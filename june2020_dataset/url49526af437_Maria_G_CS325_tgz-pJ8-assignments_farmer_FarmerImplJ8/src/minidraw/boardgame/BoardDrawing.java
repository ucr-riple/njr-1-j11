package minidraw.boardgame;

import java.awt.Rectangle;
import java.awt.Point;
import java.util.*;
import java.util.Map.Entry;

import minidraw.framework.*;
import minidraw.standard.StandardDrawing;

/** An implementation of Drawing that is custom made to support storing
 * figures that represent objects in board games and collaborate with
 * factories and strategies to make board game graphics building and
 * manipulation easy and make the graphics look appealing.
 * 
 * This is a Frozen Spot in the BoardGame framework.
 *
 * It acts as observer of a board game via the BoardGameObserver
 * interface.
 * 
 * Responsibility:
 * A) To facilitate storing BoardFigures (pieces and props)
 *
 * B) To populate the collection of figures based upon a supplied
 * factory.
 *
 * C) To react to game state changes of piece movement and prop changes
 * via the observer, and ensure the graphical output is esthetical
 * pleasing.
 * 
 * Collaborators: 
 * A) FigureFactory, an abstract factory to deliver the BoardFigure
 * instances representing pieces and the immovable objects (props).
 * B) PositioningStrategy, a strategy to adjust a figure's positioning
 * so it appears pleasing on the background (e.g. center a piece on a
 * chess board square)
 * C) BoardFigure, the factory is required to deliver board figures
 * as the positioning strategy will only adjust positions of these
 * D) BoardActionTool, is the obvious choice of a tool to manipulate
 * the figures representing pieces and props as it will invoke their
 * associated commands
 * E) the underlying game domain code, via the BoardGameObserver
 * protocol - the BoardDrawing must be notified of pieces moved 
 * in the game so it can update the graphics appropriately.
 * F) PropAppearanceStrategy, the strategy for calculating a
 * new appearance of a prop when the board drawing receives a
 * change event.
 * 
 * This implementation can handle having multiple pieces on a single
 * location, as known in the Backgammon type of games.
 *
 * It cannot handle games where pieces are generated or destroyed
 * during a game. For chess and backgammon, provide an "out of game"
 * position to move captured or born off pieces.
 *
 * It cannot handle games where the pieces partly overlap because
 * no real Z-ordering of graphics is implemented.

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
public class BoardDrawing<LOCATION> extends StandardDrawing 
  implements BoardGameObserver<LOCATION> {

  /** "Map of list" collection, mapping each location to the set of
   * images positioned on it.
   */
  protected Map<LOCATION, List<BoardFigure>> figureMap = null;

  /** Map collection, mapping graphical (x,y) positions to
   * the props of the game.
   */
  protected Map<String, BoardFigure> propMap = null;
  
  protected FigureFactory<LOCATION> factory;
  protected PositioningStrategy<LOCATION> adjuster;
  protected PropAppearanceStrategy propChanger;

  /** Construct a Drawing specifically for handling figures
   * associated with Board games.
   * @param factory abstract factory to return the initial set of 
   * piece images and props
   * @param adjuster the strategy for calculating proper positions
   * of piece images on locations. Must be non-null.
   * @param propChanger the strategy for changing the appearance
   * of props. This reference may be null if no props are used and
   * the domain code ensures that no propChangeEvents are ever
   * sent.
   */
  public BoardDrawing(FigureFactory<LOCATION> factory, 
                      PositioningStrategy<LOCATION> adjuster,
                      PropAppearanceStrategy propChanger ) {
    super();
    this.factory = factory;
    this.adjuster = adjuster;
    this.propChanger = propChanger;
    buildPieceMap();
    buildPropMap();
  }
  
  protected void adjustFigurePosition(BoardFigure figure, 
                                      LOCATION location,
                                      int index) {
    int cx, cy; // current (x,y)
    Rectangle rect = figure.displayBox();
    cx = rect.x; cy = rect.y;
    Point properPosition;

    properPosition = 
      adjuster.calculateFigureCoordinatesIndexedForLocation(location, index);

    int rx, ry;
    rx = properPosition.x; ry = properPosition.y;
    figure.moveBy( rx-cx, ry-cy);
  }

  protected void buildPieceMap() {
    figureMap = factory.generatePieceMultiMap();
    if ( figureMap == null ) {
      throw new RuntimeException("BoardGame contract violation: buildPieceMap assumes"+
          " a non-null map is return from the FigureFactory." );
    }

    for ( Entry<LOCATION,List<BoardFigure>> e : figureMap.entrySet() ) {
      int index = 0;
      for ( BoardFigure f : e.getValue()) {
        adjustFigurePosition(f, e.getKey(), index++);
        super.add(f);
      }
    }
  }
  protected void buildPropMap() {
    propMap = factory.generatePropMap();
    if ( propMap == null ) {
      // create a null object to avoid if's all over the place.
      propMap = new HashMap<String,BoardFigure>();
    }
    for ( String key : propMap.keySet()) {
      Figure f = propMap.get(key);
      Point p = adjuster.calculateFigureCoordinatesForProps(key);
      if ( p == null ) {
        throw new RuntimeException("BoardDrawing: PositionStrategy"+
                                   " returns null for Prop with key "+key);
      }
      f.moveBy(p.x, p.y);
      super.add(f);
    }
  }
  
  public void pieceMovedEvent(LOCATION from, LOCATION to) {
    // Handle 'from' position
    List<BoardFigure> list = figureMap.get(from);
    // Defensive programming - check that the framework user
    // has kept the invariant that there must be list
    // of all possible from fields.
    if (list == null) {
      throw new RuntimeException("BoardDrawing invariant not establised:"+
                                 " The FigureFactory has not defined a list"+
                                 " of BoardFigures for location "+from);
    }
    // we always moves the 'last' figure in the list of
    // figures on this location, as the following
    // repositioning will ensure all figures look
    // nice on the board.
    int index = list.size()-1;
    // get last figure
    BoardFigure f = list.get(index);
    // ... and remove it.
    list.remove(f);
    
    // next adjust the position of ALL pieces on
    // this location. handles that the user has
    // taken a piece 'in the middle' of e.g. backgammon
    // stack of pieces
    int posIndex = 0; 
    for ( BoardFigure ff : list ) {
      adjustFigurePosition(ff, from, posIndex++);
    }

    // next do the exact same with the to position
    List<BoardFigure> listTo = figureMap.get(to);

    // may be null if the appl developer has not generated
    // lists for empty locations.
    if ( listTo == null ) {
      listTo = new ArrayList<BoardFigure>();
      figureMap.put(to, listTo);
    }
    // and add the moved figure to it.
    listTo.add(f);
    
    // and adjust all pieces...
    posIndex = 0; 
    for ( BoardFigure ft : listTo ) {
      adjustFigurePosition(ft, to, posIndex++);
    }
  }
  
  public void propChangeEvent(String key) {
    Figure prop = propMap.get(key);
    if ( prop == null ) {
      throw new RuntimeException(
"BoardGame contract violation: Received propChangeEvent with key "+key+
" but no such entry in the prop map returned by your FigureFactory." );
    }
    if ( propChanger == null ) {
      throw new RuntimeException(
"BoardGame contract violation: The PropAppearanceStrategy must be "+
"defined if propChangeEvents are posted." );
    }

    // use the propChanger strategy to calculate the new
    // image to use for the prop
    String nameOfImageToShow = 
      propChanger.calculateImageNameForPropWithKey(key);
    // get the prop
    BoardFigure f = propMap.get(key);
    // Point p = adjuster.calculateFigureCoordinatesForProps(key);
    // and change the image
    f.changeImage(nameOfImageToShow);
    
    //BROKEN TEST

      /** problem: how do board drawing know which new image to
          put onto the prop figure? */

      
  }

}
