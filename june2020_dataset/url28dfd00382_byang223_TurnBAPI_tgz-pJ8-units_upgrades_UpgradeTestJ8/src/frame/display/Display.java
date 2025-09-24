package frame.display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.GameFontManager;

import frame.TBComponent;
import frame.background.TBBackground;

/**
 * Building the menu objects through GTGE framework
 * 
 * @author bryanyang
 * 
 */
public abstract class Display implements TBComponent {

	public TBBackground myBackground;
	private int xLoc, yLoc, xDim, yDim;
	private boolean myVisibility;
	private GameFont font;
	private GameFontManager fontManager;

	/**
	 * Default initialization called for every kind of displays. makes basic set
	 * up
	 */
	public Display() {
		loadFont();
		myVisibility = true;
	}

	/**
	 * Creates displays with specific background
	 * 
	 * @param b
	 */
	public Display(TBBackground b) {
		loadFont();
		myBackground = b;
		myVisibility = true;
	}

	/**
	 * Creates display with specific buffered image background
	 * 
	 * @param i
	 */
	public Display(BufferedImage i) {
		loadFont();
		myBackground = new TBBackground(i);
		myVisibility = true;
	}

	/**
	 * Creates default displays as specific loactions
	 * 
	 * @param x
	 * @param y
	 */
	public Display(int x, int y) {
		this();
		xLoc = x;
		yLoc = y;
		myVisibility = true;
	}

	/**
	 * Render will be called from game to create menus Will also deal with
	 * updating information
	 * 
	 * @param g
	 */
	public abstract void render(Graphics2D g);

	/**
	 * Allows user to directly control the location of the menus
	 * 
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y) {
		xLoc = x;
		yLoc = y;
		myBackground.setLocation(x, y);
	}

	/**
	 * Gets value for xCoordinate
	 * 
	 * @return
	 */
	public int getXLocation() {
		return xLoc;
	}

	/**
	 * Gets value for yCoordinate
	 * 
	 * @return
	 */
	public int getYLocation() {
		return yLoc;
	}

	/**
	 * Loads font and fontManager so that they could be used.
	 */
	public void loadFont() {
		try {
			fontManager = new GameFontManager();
			font = fontManager.getFont(ImageIO.read(new File(
			        "resources/BitmapFont.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets rendering ability
	 * 
	 * @return
	 */
	public boolean isVisible() {
		return myVisibility;
	}

	/**
	 * Sets rendering ability
	 * 
	 * @param b
	 */
	public void setVisible(boolean b) {
		myVisibility = b;
	}

	public int getWidth() {
		return xDim;
	}

	public int getHeight() {
		return yDim;
	}

	public GameFont getFont(){
		return font;
	}
	
	public void setSize(int dx, int dy){
		xDim = dx;
		yDim = dy;
	}
	
	/**
	 * Resizes display to specified x and y points
	 */
	public void resize(int x, int y) {
		xDim = x;
		yDim = y;

		myBackground.resize(x, y);

	}

}
