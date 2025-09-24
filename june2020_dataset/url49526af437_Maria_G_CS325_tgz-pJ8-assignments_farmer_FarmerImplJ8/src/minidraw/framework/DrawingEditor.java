package minidraw.framework;

/** The DrawingEditor is the interface of the "main" class of
    any minidraw application. It must instantiate all relevant
    parts of the framework as well as open the relevant graphical
    user interface windows.
   
    It is also the Mediator of the Mediator pattern that allows the
    different implementation of MiniDraw roles to access each
    other. It is, however, the object server variant as it does not
    centralize control.
    
    The default implementation, MiniDrawApplication, should suffice for
    almost any need as it is highly configurable via the Factory interface.
    
    Responsibilities:
    A) Main class of a minidraw application, that is the editor must
       instantiate all parts of the application.
    B) Open a window to become visible.
    B) Acts as Mediator for the various parts of MiniDraw.
    C) Allow changing the active tool.
    D) Allow displaying a message in the status bar.

    Release history:

    Pre 1.0: Many many releases for various course instances.

    1.0: Version of MiniDraw at the time of book submission to
    CRC Press.

    1.1: Defect in boardgame hotspots fixed. Move from a location to the
    same location would not readjust the figure on the graphical location.

    1.2: Reduced the number of hotspots associated with the
    positioning strategy in the boardgame extensions.

    1.3: Changes introduced to handle 'props' in the
    BoardGame extension of MiniDraw. 

    1.4: Some defensive programming techniques introduced
    in BoardGame extension.

    1.5: Fixed bug concerning movement of props in BoardGame.
    Minor documentation changes.
    
    1.6: Fixed bug in BoardGame's BoardActionTool concerning
    actions performed on props that return false.
    
    1.7: Several changes made in the notification and repainting
    sequence as some defects were discovered (which actually
    are embedded in the original JHotDraw code as well.)
    See comment in the source code below.
    
    1.8: Added hook method in MiniDrawApplication to override
    windows close operation.

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

/* Version 1.7 notes.
 *     
    The update sequence when moving a figure is this:
      a) moveBy invokes 'invalidate()' twice and 'changed()' once.
      b) invalidate() notifies FigureListeners of 'figureInvalidated() while
      changed() notifies of 'figureChanged()'
      c) StandardDrawing implements figureInvalidated() but not figureChanged();
      the latter was instead handled by the inherited method from CompositeFigure.
      d) StandardDrawing's figureInvalidated() notifies all DrawingListeners of
      DrawingInvalidated but CompositeFigure's implementation of figureChanged() is empty!
      e) Thus DrawingView is only notified of invalidation (adds dirty rectangles) but
      never changes (i.e. force the repaint).
      
    How come anything was then redrawn?
    
    Because StandardDrawingView invokes 'checkDamage()' after every mouse event which
    calls the requestUpdate() method in StandardDrawing which force drawingRequestUpdate
    calls to all views - which then again invokes repairDamage() that repaints.
    
    The change that has been made:
    a) all checkDamage() removed from StandardDrawingView's mouse event methods
    b) StandardDrawingView's figureChanged() method now fire DrawingRequestUpdate notifications
    
    Upon testing defects were identified in StandardSelectionHandler: calls to figure.invalidate()
    changed to figure.changed() to force the proper repaint.

*/

public interface DrawingEditor {
  
  /** Version of Minidraw. */
  public static final String VERSION = "1.8";
  
  /** get the drawing this editor is associated with */
  public Drawing drawing();

  /** get the view associated with this editor */
  public DrawingView view();

  /** set a new tool to use in this editor.
   @param t the tool to use. t is not allowed to be null. Use
   a null tool instead.
  */
  public void setTool(Tool t);

  /** return the tool presently set */
  public Tool tool();

  /** open the editor. This makes the editor visible */
  public void open();

  /** show a status message to the user */
  public void showStatus(String message);
}
