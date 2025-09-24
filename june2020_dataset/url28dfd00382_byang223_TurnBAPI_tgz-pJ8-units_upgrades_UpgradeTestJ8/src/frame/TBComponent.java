package frame;

import java.awt.Graphics2D;

/**
 * Interface for HUD structure
 * @author bryanyang
 *
 */
public interface TBComponent extends java.io.Serializable {

	public void setLocation(int x, int y);
	
	public int getHeight();
	
	public int getWidth();
	
	public int getXLocation();
	
	public int getYLocation();
	
	public void render(Graphics2D g);
	
	public void resize(int dx, int dy);
	
}
