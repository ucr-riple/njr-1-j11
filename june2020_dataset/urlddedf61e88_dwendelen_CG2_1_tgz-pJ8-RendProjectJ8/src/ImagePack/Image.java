package ImagePack;


import java.io.*;
import Color.*;
import ScenePack.*;


public class Image extends Object {

  RGBColor[][]  pixel;
  double [][] depthBuffer;
  int height;
  int width;

  /** Creates new Image */
  public Image(int width, int height) {
    int i,j;

    this.pixel = new RGBColor[width][height];
    this.depthBuffer = new double[width][height];
    this.height = height;
    this.width = width;

    for (i=0;i<width;i++) {
      for (j=0;j<height;j++) {
        setColorOfPixel(i,j, Settings.BACKGROUND_COLOR);
        depthBuffer[i][j] = 0.0;
      }
    }
  }

  public void setColorOfPixel(int x, int y, RGBColor color) {
    this.pixel[x][y] = color.makeCopy();
  }

  public void setColorAndDepthOfPixel(int x, int y, double depth, RGBColor color) {
    if (depth > depthBuffer[x][y]) {
      this.pixel[x][y] = color.makeCopy();
      depthBuffer[x][y] = depth;
    }
  }

  public RGBColor getColorOfPixel(int x, int y) {
    RGBColor tmp;
    tmp = this.pixel[x][y].makeCopy();
    return(tmp);
  }


  public void writeImageAsRGBE(String filename) {
    try {
      RGBEFormat.write(this,null,null,filename);
    }
    catch (IOException e) {
      System.out.println("I/O Exception: " + e);
    }
  }

  public int getWidth() {
    return(this.width);
  }

  public int getHeight() {
    return(this.height);
  }

  public RGBColor accumulateColorOfAllPixels() {
    int i,j;
    RGBColor totalColor;

    totalColor = new RGBColor(0.0,0.0,0.0);
    for (i=0;i<width;i++) {
      for (j=0;j<height;j++) {
        totalColor = RGBColor.add(totalColor, this.pixel[i][j]);
      }
    }
    return(totalColor);
  }

  public static Image subtract(Image image1, Image image2) {
    Image image;
    double diff;
    RGBColor c;
    int i,j;
    
    if ((image1.getWidth() != image2.getWidth())||(image1.getHeight() != image2.getHeight())) {
      return(null);
    }
    image = new Image(image1.getWidth(),image1.getHeight());
    for (i=0;i<image1.getWidth();i++) {
      for (j=0;j<image1.getHeight();j++) {
        diff = (RGBColor.subtract(image1.pixel[i][j] , image2.pixel[i][j])).sumOfComponents();
        if (diff >= 0.0) {
          image.pixel[i][j] = new RGBColor(diff,0.0,0.0);
        }
        else {
          image.pixel[i][j] = new RGBColor(0.0,-diff,0.0);;
        }
        image.depthBuffer[i][j] = (image1.depthBuffer[i][j] + image2.depthBuffer[i][j])/2.0;
      }
    }
    return(image);

  }

  public static void main (String[] args) {
    int i,j;
    int height = 200;
    int width = 400;
    Image image = new Image(width,height);

    for (i=0;i<height;i++) {
      for (j=0;j<width;j++) {
        image.setColorOfPixel(j,i,new RGBColor((float)j,(float)i,0.0));
      }
    }
    image.writeImageAsRGBE("dutre.rgbe");
  }

}