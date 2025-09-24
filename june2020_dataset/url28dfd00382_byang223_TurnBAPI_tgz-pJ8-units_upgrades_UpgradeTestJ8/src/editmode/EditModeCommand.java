package editmode;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import map.LevelMap;
import modes.EditMode;
import modes.EditMode.GameState;
import modes.GameMode;

import com.golden.gamedev.object.Sprite;

/**
 * Abstract class of with its classes functions to instantiate
 * @author tianyu shi
 * 
 * I did not write all of this class including the function wasclicked
 */

public abstract class EditModeCommand extends Sprite {

	private String imageFilepath;
	private String clickedImageFilepath;
	private GameMode State;
	private EditMode Mode;

	protected static LevelMap map;

	public EditModeCommand(double x, double y) {
		super(x, y);
	}

	public EditModeCommand() {
		super();
	}

	public abstract String CommandName();

	/**
	 * Passes in GameMode and EditMode itself so that the subclasses function according to
	 * whose turn it is and how certain methods like CommandAdd should function
	 */

	public abstract void performCommand(GameState currState, EditMode Mode);

	public void setImage() {
		super.setImage(getBufferedImage(imageFilepath));
	}

	public BufferedImage getBufferedImage(String image) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(image));
		} catch (IOException e) {
		}
		return img;
	}

	public boolean wasClicked(int x, int y) {
		double xLoc = this.getX();
		double yLoc = this.getY();
		double w = (double) this.getWidth();
		double h = (double) this.getHeight();
		if (x > xLoc && x < (xLoc + w)) {
			if (y > yLoc && y < (yLoc + h)) {
				return true;
			}
		}       
		return false;
	}

	protected void setImageFilepath(String s) {
		this.imageFilepath = s;
	}

	protected void setClickedImageFilepath(String s) {
		this.clickedImageFilepath = s;
	}

	public void setClickedButtonImage() {
		super.setImage(getBufferedImage(clickedImageFilepath));
	}

	public void setUnclickedButtonImage() {
		super.setImage(getBufferedImage(imageFilepath));
	}

	public void setImage(BufferedImage image) {
		super.setImage(image);
	}
}
