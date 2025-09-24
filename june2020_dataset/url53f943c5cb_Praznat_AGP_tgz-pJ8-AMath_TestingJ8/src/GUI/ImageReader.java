package GUI;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.io.File;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.net.URL;
import AMath.Calc;

public class ImageReader {
	BufferedImage img;
    int[] xcolors;
    int cellW, cellH;
    static final int type = Transparency.BITMASK;

    public ImageReader(String iofile) {
    	//File source = new File(iofile);
    	URL url = getClass().getResource(iofile); 
    	Image image = Toolkit.getDefaultToolkit().getImage(url);
        img = toBufferedImage(image);
    	
        try {
        	//img = ImageIO.read(source);
        	//URL url2 = getClass().getResource(iofile); 
        	//img = (BufferedImage) Toolkit.getDefaultToolkit().getImage(url);
            //img = getCompatibleImage(img.getWidth(null),img.getHeight(null),type);
        }
        catch (Exception e) {
        	System.out.println("BIG FAIL " + iofile);
        }
        xcolors = new int[] {};
    }
    
    public void setWnH(int w, int h) {cellW = w; cellH = h;}
    public int getCellW() {return cellW;}
    public int getCellH() {return cellH;}
    public int[] getXColors() {return xcolors;}
    
    public void addXYColor(int x) {
    	int[] xout = new int[xcolors.length+1];
    	for (int i = 0; i < xcolors.length; i++) {xout[i] = xcolors[i];}  
    	xout[xout.length-1] = x;
    	int[] xcolors = new int[xout.length];
    	for (int i = 0; i < xcolors.length; i++) {xcolors[i] = xout[i];}
    	System.out.println("Added colors");
    	Calc.printArray(xcolors);
    }
    public void addXYColor(int[] x) {
    	Calc.printArray(xcolors);
    	xcolors = Calc.combineArrays(xcolors, x);
    }
    
    public BufferedImage Cell(int r, int c) {
    	return img.getSubimage(c*cellW+c+1, r*cellH+r+1, cellW, cellH);
    }
    
    
    private BufferedImage getCompatibleImage(int w, int h, int type) {
        return GraphicsEnvironment.getLocalGraphicsEnvironment()
                                  .getDefaultScreenDevice()
                                  .getDefaultConfiguration()
                                  .createCompatibleImage(w,h,type);
    }
    
    public BufferedImage convert(BufferedImage src, int[] cols) {
    	if (cols.length != xcolors.length) {System.out.println("# of colors doesn't match"); return src;}
        int w = src.getWidth();
        int h = src.getHeight();
        int type = Transparency.BITMASK;
        BufferedImage dst = getCompatibleImage(w,h,type);
        int buf[] = new int[w];
        for(int y = 0; y < h; y++) {
            src.getRGB(0, y, w, 1, buf, 0, w);
            for(int x = 0; x < w; x++) {
                for (int c = 0; c < xcolors.length; c++) {
                	if(buf[x] == xcolors[c]) {buf[x] = cols[c]; break;}
                }
            }
            dst.setRGB(0, y, w, 1, buf, 0, w);
        }
        return dst;
    }
    
    public static BufferedImage resizeImageWithHint(BufferedImage originalImage, int IMG_WIDTH, int IMG_HEIGHT) {
    	 
	    	BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
	    	Graphics2D g = resizedImage.createGraphics();
	    	g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
	    	g.dispose();	
	    	g.setComposite(AlphaComposite.Src);
	     
	    	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	    	RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    	g.setRenderingHint(RenderingHints.KEY_RENDERING,
	    	RenderingHints.VALUE_RENDER_QUALITY);
	    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	    	RenderingHints.VALUE_ANTIALIAS_ON);
	     
	    	return resizedImage;
        }
    
    
    public static BufferedImage blurImage(BufferedImage image, int x, int c) {
    	float ninth = 1.0f/9.0f;
    	float[] blurKernel = {
    	ninth, ninth, ninth,
    	ninth, ninth, ninth,
    	ninth, ninth, ninth
    	};

    	Map map = new HashMap();

    	map.put(RenderingHints.KEY_INTERPOLATION,
    	RenderingHints.VALUE_INTERPOLATION_BILINEAR);

    	map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

    	map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    	RenderingHints hints = new RenderingHints(map);
    	BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
    	return op.filter(image, null);
    } 
    
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                image.getWidth(null), image.getHeight(null), Transparency.OPAQUE);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }

    
}
