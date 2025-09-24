package minidraw.standard;

import java.awt.*;

/** ImageFigure

    Responsibility: A Figure showing an Image.

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

public class ImageFigure extends AbstractFigure {

  protected Image fImage;
  protected Rectangle fDisplayBox;

  /** Create a null image figure where the image and position is given
      later by the set method */
  public ImageFigure() {
    fImage = null;
    fDisplayBox = new Rectangle(0,0,0,0);
  }

  /** Change the image and position of this image figure */
  public void set(Image img, Point p) {
    fImage = img;
    setDisplayBox(p);
  }

  /** Change the image (based on the string used in
   * the image manager) and position of this image figure */
  public void set(String imagename, Point p) {
    ImageManager im = ImageManager.getSingleton();
    fImage = im.getImage(imagename);
    setDisplayBox(p);
  }
  
  /** Create an image figure from a given image */
  public ImageFigure(Image img, Point origin) {
    fImage = img;
    setDisplayBox(origin);
  }

  /* Create an image figure from a previously loaded image
     by the image manager. The image to use is identified by
     the name in the preload.lst file */
  public ImageFigure(String name, Point origin) {
    ImageManager im = ImageManager.getSingleton();
    fImage = im.getImage(name);
    setDisplayBox(origin);
  }

  private void setDisplayBox(Point origin) {
    willChange();
    fDisplayBox = new Rectangle(origin.x, origin.y, 0, 0);
    fDisplayBox.width = fImage.getWidth(null);
    fDisplayBox.height = fImage.getHeight(null);
    changed();
  }


  public void draw(Graphics g) {
    if (fImage != null) {
      g.drawImage(fImage, fDisplayBox.x, fDisplayBox.y, 
                  fDisplayBox.width, fDisplayBox.height, null);
    }
  }

  public Rectangle displayBox() { 
   return fDisplayBox;
  }

  protected void basicMoveBy(int x, int y) {
    fDisplayBox.translate(x,y);
  }
}
