package minidraw.standard;

import minidraw.framework.*;

import javax.swing.*;
import java.awt.*;

/** MiniDraw Application is a standard implementation of the
 * DrawingEditor role. It connects MiniDraw with Swing as it also
 * fulfill the JFrame role. You have to provide it with an abstract
 * factory for configuring the variability points of the application.

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

 * Implementation note: MiniDrawApplication ASSUMES that the
 * DrawingView returned from the factory is a subclass of JPanel. If
 * not you have to override the createContents method to handle this!
*/

public class MiniDrawApplication
  extends JFrame 
  implements DrawingEditor {

  /** the view that this application displays */
  protected DrawingView fView;

  /** the drawing that this application uses */
  protected Drawing fDrawing;

  /** the image manager of an application */
  protected ImageManager fImageManager;

  /** the tool being used by this editor */
  protected Tool fTool;

  /** abstract factory to make services */
  protected Factory factory;

  /** the status field */
  protected JTextField statusField;

  /** Construct a minidraw editor that also acts as the
  * JFrame application window.
  * @param title the title to put in the frame bar
  * @param f the factory to produce internal handlers
  */
  public MiniDrawApplication( String title, Factory f ) {
    super(title);
    try {
      // Set System L&F
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } 
    catch (Exception e) {
      // Ignore exceptions as they just mean
      // we run with the defaults 
    }

    fImageManager = new ImageManager(this);

    // create the (default) controller in MVC
    fTool = new NullTool();
  
    factory = f;
  
    setLocation( 100, 20 );
    setFrameCloseOperation();
  }

  /** define how the MiniDraw application responds to
   * the user clicking the window close button. Defaults
   * to exiting the application. Override if you need special
   * handling like disconnecting a socket.
   */
  protected void setFrameCloseOperation() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
  }
  
  public void open() {
    Container pane = getContentPane();

    // create the underlying model in the MVC triad 
    fDrawing = factory.createDrawing(this);

    // create a view for the MVC 
    fView = factory.createDrawingView(this);
    statusField = factory.createStatusField(this);

    JPanel panel = createContents(fView, statusField);

    pane.add(panel);
    
    pack();
    setVisible(true);
  }

  /** given a drawing view return the JPanel that encapsulate it.
   */ 
  protected JPanel createContents(DrawingView dv, JTextField tf) {
    if (tf == null) { return (JPanel) dv; }
    JPanel p = new JPanel();
    p.setLayout( new BorderLayout() );
    p.add( BorderLayout.CENTER, (JPanel) dv );
    p.add( BorderLayout.SOUTH, statusField );
    return p;
  }

 
  /** set a tool for this editor.
      @param t the tool to set. PRECondition: t must never be null; if
      no tool is wanted, then set it to the null tool
  */
  public void setTool(Tool t) {
    fTool = t;
  }

  public Tool tool() {
    return fTool;
  }

  public DrawingView view() {
    return fView;
  }
  
  public Drawing drawing() {
    return fDrawing;
  }
  
  public void showStatus(String message) {
    if ( statusField != null ) {
      statusField.setText(message);
    }
    // else status messages are simply ignored.
  }
}
