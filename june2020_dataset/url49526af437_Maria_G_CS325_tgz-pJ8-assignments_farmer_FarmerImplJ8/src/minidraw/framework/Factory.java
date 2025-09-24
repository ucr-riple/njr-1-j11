package minidraw.framework;

import javax.swing.JTextField;

/** Abstract factory for creating implementations of the central
    roles used in MiniDraw.

    Responsibilities:
    1) Create, upon request, instances of the roles that the 
       DrawingEditor needs.

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
public interface Factory {

  /** Create the drawing view (View role of the MVC pattern). 
   * @param editor the editor that the view will be associated with.
   * @return a new drawingview
  */
  public DrawingView createDrawingView( DrawingEditor editor );

  /** Create the drawing (Model role of the MVC pattern). 
   * @param editor the editor that the view will be associated with 
   * @return a new drawing
   */
  public Drawing createDrawing( DrawingEditor editor );

  /** Create the text field used for messages.
   * @param editor the editor that the view will be associated with 
   * @return a new status field or null in case no status field is needed.
   */
  public JTextField createStatusField( DrawingEditor editor );
}
