package minidraw.standard;

import minidraw.framework.*;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.*;
import javax.swing.*;

import java.util.*;

/** Standard implemementation of the DrawingView role in MiniDraw.
 * Most code is copied from JHotDraw 5.1
*/

public class StandardDrawingView 
  extends JPanel
  implements DrawingView, 
             MouseListener,
             MouseMotionListener,
             KeyListener {

  /**
   * 
   */
  private static final long serialVersionUID = 3722170409196832977L;

  /** the object server providing yellow page access
      to all relevant parts of the editor */
  private DrawingEditor editor;
  
  /** the dirty rectangle of this view. it basically
      accumulates all rectangles that have seen some change
      since the last repaint */
  private Rectangle dirtyRectangle;

  /** Create a drawing view associated with the given editor */
  public StandardDrawingView(DrawingEditor editor) {
    this(editor, new Dimension(600,400) );
  }
  
  public StandardDrawingView(DrawingEditor editor, Dimension size) {
    this.editor = editor;
    addMouseListener(this);
    addMouseMotionListener(this);
    addKeyListener(this);

    dirtyRectangle = null;

    // register this view as listener on change events from the
    // model
    if ( editor.drawing() == null ) {
      throw new RuntimeException( "StandardDrawingView: Drawing undefined!" );
    }
    editor.drawing().addDrawingChangeListener( this );

    setSize(size);
  }

  public DrawingEditor editor() { return editor; }

  public synchronized void checkDamage() { 
    editor().drawing().requestUpdate();
  }

  public synchronized void repairDamage() {
    if ( dirtyRectangle != null ) {
      repaint( dirtyRectangle.x, dirtyRectangle.y,
               dirtyRectangle.width, dirtyRectangle.height );
      dirtyRectangle = null;
    }

    // the hard coded bit is just to invoke repaint()
    // repaint();
  }

  /** Paints the drawing view */
  public void paint(Graphics g) {
    
    drawAll(g);
  }

  /**
   * Draws the contents of the drawing view.
   * The view has three layers: background, drawing, highlights, overlay.
   * The layers are drawn in back to front order.
   */
  public void drawAll(Graphics g) {
    drawBackground(g);
    drawDrawing(g);
    drawSelectionHighlight(g);
    drawOverlay(g);
  }
  
  /**
   * Draws the drawing.
   */
  public void drawDrawing(Graphics g) {
    Drawing d = editor.drawing();
    d.lock();
    Iterator<Figure> i = d.iterator();
    while ( i.hasNext() ) {
      Figure f = i.next();
      f.draw(g);
      /* Uncomment for slowing down drawing to test for
         concurrent modification failures 
      try {
        Thread.sleep(300);
      } catch ( InterruptedException e ) {}
      */
    }
    d.unlock();
  }

  /**
   * Draws the background. If a background pattern is set it
   * is used to fill the background. Otherwise the background
   * is filled in the background color.
   */
  public void drawBackground(Graphics g) {
    g.setColor( Color.LIGHT_GRAY );
    g.fillRect(0, 0, getBounds().width, getBounds().height);
  }

  /**
   * Draws the selection. If figures are selected then they
   * are shown using the associated SelectionHighlightStrategy.
   */
  public void drawSelectionHighlight(Graphics g) {
    List<Figure> l = editor().drawing().selection();
    // TODO: fix this fast hack
    if ( l.size() > 1 ) {
      g.setColor(Color.red);
      Rectangle r;
      for (Figure f: l) {
        r = f.displayBox();
        g.drawRect(r.x, r.y, r.width-1, r.height-1);
      }
    }
  }

  /** Standard is no overlay */
  public void drawOverlay(Graphics g) { }

  public Dimension getPreferredSize() {
    return getSize();
  }

  public Dimension getMinimumSize() {
    return getPreferredSize();
  }

  /**
   * Constrains a point to the current grid.
   */
  protected Point constrainPoint(Point p) {
    // constrin to view size
    Dimension size = getSize();
    //p.x = Math.min(size.width, Math.max(1, p.x));
    //p.y = Math.min(size.height, Math.max(1, p.y));
    p.x = range(1, size.width, p.x);
    p.y = range(1, size.height, p.y);
    
    return p;
  }
  
  /**
   * Constains a value to the given range.
   * @return the constrained value
   */
  private static int range(int min, int max, int value) {
    if (value < min)
      value = min;
    if (value > max)
      value = max;
    return value;
  }
  
  // === Mouse event handling
  protected Point fLastClick;

  /**
   * Handles mouse down events. The event is delegated to the
   * currently active tool.
   */
  public void mousePressed(MouseEvent e) {
    requestFocus();
    Point p = constrainPoint(new Point(e.getX(), e.getY()));
    fLastClick = new Point(e.getX(), e.getY());
    editor.tool().mouseDown(e, p.x, p.y);
  }

  /**
   * Handles mouse drag events. The event is delegated to the
   * currently active tool.
   */
  public void mouseDragged(MouseEvent e) {
    Point p = constrainPoint(new Point(e.getX(), e.getY()));
    editor.tool().mouseDrag(e, p.x, p.y);
  }

  /**
   * Handles mouse move events. The event is delegated to the
   * currently active tool.
   */
  public void mouseMoved(MouseEvent e) {
    editor.tool().mouseMove(e, e.getX(), e.getY());
  }
  
  /**
   * Handles mouse up events. The event is delegated to the
   * currently active tool.
   */
  public void mouseReleased(MouseEvent e) {
    Point p = constrainPoint(new Point(e.getX(), e.getY()));
    editor.tool().mouseUp(e, p.x, p.y);
  }

  // These listener methods are not interesting.
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}
  public void keyTyped(KeyEvent e) { }
  public void keyReleased(KeyEvent e) { }
  public void keyPressed(KeyEvent e) {
    editor.tool().keyDown(e, e.getKeyCode() );
  }

  
  public void drawingInvalidated(DrawingChangeEvent e) {
    Rectangle r = e.getInvalidatedRectangle();
    if ( dirtyRectangle == null ) {
      dirtyRectangle = r;
    } else {
      dirtyRectangle.add(r);
    }
  }
  public void drawingRequestUpdate(DrawingChangeEvent e) {
    repairDamage();
  }
}

