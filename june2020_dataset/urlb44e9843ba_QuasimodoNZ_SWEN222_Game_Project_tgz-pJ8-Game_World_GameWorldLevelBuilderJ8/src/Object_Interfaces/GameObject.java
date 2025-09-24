package Object_Interfaces;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public interface GameObject extends java.io.Serializable,
		Comparable<GameObject> {
	/**
	 * This interface has all the methods for the GameObjects.
	 * @ author Danesh Abeyratne
	 */

	/**
	 * Draws itself onto the graphic. The object should know where it is on the
	 * map and therefore should work out where to draw itself.
	 *
	 * @param g
	 *            The canvas that it is to draw itself on.
	 */
	public void draw(Graphics g);

	/**
	 * Compared via what object is closer to the foreground
	 */
	@Override
	public int compareTo(GameObject other);

	/**
	 * Returns the icon representation of the object
	 *
	 * @return
	 */

	public Image toIcon();

	/**
	 * Returns whether the point is within the bound box of the object for mouse
	 * clicks
	 *
	 * @param Point
	 * @return
	 */
	public boolean containsPoint(Point p);

	/**
	 * Returns whether the objects bounding box intersects with the given
	 * rectangle
	 *
	 * @param p
	 * @return
	 */
	public boolean isOverlapping(Rectangle p);


	//public void initialiseImage();

	// Returns the name of the object
	public String getName();

	// Returns the description of the object
	public String getDescription();

	public Rectangle getRect();

	public boolean setRect(Rectangle r);
}
