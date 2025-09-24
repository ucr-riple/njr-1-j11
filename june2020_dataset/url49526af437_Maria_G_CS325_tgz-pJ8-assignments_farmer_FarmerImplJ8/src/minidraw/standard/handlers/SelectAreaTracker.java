package minidraw.standard.handlers;

import minidraw.framework.*;
import minidraw.standard.*;

import java.awt.*;
import java.awt.event.MouseEvent;

/** SelectAreaTracker is a tool to select a set of figures using a
  rubberband.  

  This tool is more or less a direct copy from the JHotDraw 5.1 source,
  except it is augmented with a selection strategy.
 
 */

public class SelectAreaTracker extends AbstractTool {

  private Rectangle selectionRectangle;
  
  protected RubberBandSelectionStrategy rubberBandSelectionStrategy;
  
  public SelectAreaTracker(DrawingEditor editor) {
    this( editor, new StandardRubberBandSelectionStrategy() );
  }
  
  /** define the selection area tracker.
      @param rbss the selection strategy to use */
  public SelectAreaTracker(DrawingEditor editor, 
                           RubberBandSelectionStrategy rbss ) {
    super(editor);
    rubberBandSelectionStrategy = rbss;
  }
  
  public void mouseDown(MouseEvent e, int x, int y) {
    // use event coordinates to suppress any kind of
    // transformations like constraining points to a grid
    super.mouseDown(e, e.getX(), e.getY());
    rubberBand(fAnchorX, fAnchorY, fAnchorX, fAnchorY);
  }
  
  public void mouseDrag(MouseEvent e, int x, int y) {
    super.mouseDrag(e, x, y);
    eraseRubberBand();
    rubberBand(fAnchorX, fAnchorY, x, y);
  }

  public void mouseUp(MouseEvent e, int x, int y) {
    super.mouseUp(e, x, y);
    eraseRubberBand();

    // use the strategy to make the proper selection.
    rubberBandSelectionStrategy.selectGroup( editor().drawing(),
                                             selectionRectangle,
                                             e.isShiftDown() );
  }

  private void rubberBand(int x1, int y1, int x2, int y2)
  {
    selectionRectangle = new Rectangle(new Point(x1, y1));
    selectionRectangle.add(new Point(x2, y2));
    drawXORRect(selectionRectangle);
  }

  private void eraseRubberBand()
  {
    drawXORRect(selectionRectangle);
  }
  
  private void drawXORRect(Rectangle r)
  {
    DrawingView view = editor().view();

    Graphics g = view.getGraphics();
    g.setXORMode(Color.white);
    g.setColor(Color.black);
    g.drawRect(r.x, r.y, r.width, r.height);
  }
}
