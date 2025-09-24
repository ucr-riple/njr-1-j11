package environment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import map.LevelMap;


/**
 * Concrete example of a transient weather environment
 * @author lenajia
 *
 */
public class Snow extends Transient{

	/**
	 * Sets the image-to-tile ratio.
     * @param ratio
     */
	public Snow(double ratio, int start, int end){
		super(ratio, start, end);
	}
	
	@Override
    public String Name() {
	    return "Snow";
    }


	/**Sets active if the current turn is within the active turn bounds
	 * 
	 * @param turn
	 * @return boolean
	 */
	public boolean isActive(int turn){
		if (turn >= myStartTurn && turn <= myEndTurn){
			this.setActive(true);
			return true;
		}
		this.setActive(false);
		return false;
	}
	
	@Override
    public String getImageURL() {
	    return "resources/environment/snowflake.jpg";
	}

}
