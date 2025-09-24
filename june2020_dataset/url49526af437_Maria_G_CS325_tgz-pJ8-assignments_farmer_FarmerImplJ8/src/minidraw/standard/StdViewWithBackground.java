package minidraw.standard;

import minidraw.framework.*;

import java.awt.*;

/** A drawing view that paints the background with a fixed image */

public class StdViewWithBackground extends StandardDrawingView {

  Image background;

 /** Create a DrawingView that features a graphical image as
      background for figures.
      @param backgroundName name of an image previously loaded by the
      image manager.
  */
  public StdViewWithBackground(DrawingEditor editor, String backgroundName) {
    super(editor);
    ImageManager im = ImageManager.getSingleton();
    this.background = im.getImage(backgroundName);
  }

  public StdViewWithBackground(DrawingEditor editor, Image background) {
    super(editor);
    this.background = background;
  }

   public void drawBackground(Graphics g) {
    g.drawImage(background, 0, 0, null );
  }
  
  public Dimension getPreferredSize() {
    return new Dimension( background.getWidth(null),
                          background.getHeight(null) );
  }

  public Dimension getMinimumSize() {
    return getPreferredSize();
  }

}
