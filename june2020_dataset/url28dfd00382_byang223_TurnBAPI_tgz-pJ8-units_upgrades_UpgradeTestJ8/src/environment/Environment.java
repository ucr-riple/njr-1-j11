package environment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import map.Tile;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.util.ImageUtil;

/**
 * Abstract class of environmental effects placed on the map
 * @author lenajia
 * 
 */

public abstract class Environment extends Sprite implements java.io.Serializable {
	
	protected int myDimX;
	protected int myDimY;
	protected double myRatio;
	
	 /**
     * Constructor for environment
     * Sets the image-to-tile ratio.
     * @param ratio
     */
	public Environment(double ratio) {
		 BufferedImage img = null;
		 
	        try {
	           img = ImageIO.read(new File(getImageURL()));
	        } catch (IOException e) {
	        }
	    
		this.setImage(img);
		myRatio = ratio;
	}
	
	 /**
     * Default environment constructor. 
     * Sets environment image size to the full size of the tile
     */
	public Environment(){
		this (1);
	}
	
	public abstract String Name();
	
	public abstract String getImageURL();
	
	public abstract boolean isActive(int turn);
	
	public int getDimX() {
		return myDimX;
	}
	
	public int getDimY() {
		return myDimY;
	}
	
	/**Resizes environment image based on given tile x, y dimensions 
	 * and the ratio
	 * 
	 * @param dx
	 * @param dy
	 */
	public void resize(int tilex, int tiley){
	
		if(this.getImage()!=null){
			myDimX = (int) (myRatio * tilex);
			myDimY = (int) (myRatio * tiley);
			setImage(ImageUtil.resize(this.getImage(), myDimX, myDimY));
		}
	}	
}
