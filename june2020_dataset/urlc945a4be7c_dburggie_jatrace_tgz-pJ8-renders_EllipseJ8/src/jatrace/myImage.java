package jatrace;

import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

/** Provides a simplified interface to the java.awt.image, java.io, and
 *  javax.imageio libraries. */
public class myImage
{
	protected final static int colorType = BufferedImage.TYPE_4BYTE_ABGR;
	
	protected BufferedImage image;
	protected WritableRaster raster;
	protected int width, height;
	
	/** Instantiates a new image with a buffer w pixels wide and h pixels tall. */
	public myImage(int w, int h)
	{
		
		image = new BufferedImage(w,h,colorType);
		width = w; height = h;
		raster = image.getRaster();
		
	}
	
	private myImage(BufferedImage bi)
	{
		this( bi.getWidth(), bi.getHeight() );
		Color color = new Color();
		
		//Copy buffered image into our writable raster
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				color.copyARGB(bi.getRGB(x,y));
				raster.setPixel(x,y,color.p());
			}
		}
		
		System.out.println("Load complete.");
	}
	
	/** Sets pixel (x,y) to specified color. These coordinates follow the
	 *  standard of (0,0) being the top-left of the image. */
	public myImage setPixel(int x, int y, Color c)
	{
		raster.setPixel(x,y,c.p());
		return this;
	}
	
	/** Gets a Color object at the specified coordinates */
	public Color getPixel(int x, int y)
	{
		return Color.fromARGB( image.getRGB(x,y) );
		//return new Color().copyARGB( image.getRGB(x,y) ) ;
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public static myImage read(String filename)
	{
		
		try {
			System.out.print("Loading resource '" + filename + "' ");
			File inputFile = new File(filename);
			myImage image = new myImage(ImageIO.read(inputFile));
			return image;
		} catch (IOException ioe) {
			String err = "Failed to read input file " + filename;
			System.out.println(err);
			System.exit(1);
		}
		System.out.println("something bad happened while reading an image");
		return null;
		
	}
	
	/** Writes image buffer to given filename. Note: calls System.exit(1) on
	 *  IOException, thus may exit program uncleanly. */
	public myImage write(String filename)
	{
		try {
			File of = new File(filename);
			ImageIO.write(image, "png", of);
		} catch (IOException e) {
			String err = "failed to open or write to file ";
			err += filename;
			System.out.println(err);
			System.exit(1);
		}
		return this;
	}
}
