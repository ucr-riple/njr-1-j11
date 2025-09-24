/**
 *
 */
package Game_World;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import Graphics_Renderer.GraphicsRenderer;
import Object_Interfaces.GameObject;

/**
 * The Class represents Decorative objects which cannot be moved.
 * @author Benjamin
 *
 */
public class Decoration implements GameObject {
	String name;
	Point point;
	boolean onTop;
	Point offset;

	public Decoration(String n, Point p, boolean ot, Point os) {
		name = n;
		point = p;
		onTop = ot;
		offset = (os == null) ? new Point(0, 0) : os;
	}

	public Decoration() {
	} // default constructor for XML

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(GraphicsRenderer.getImage(name), point.x - offset.x,
				point.y - offset.y, null);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#compareTo(Object_Interfaces.GameObject)
	 */
	@Override
	public int compareTo(GameObject other) {
		if (onTop)
			return 1;
		return point.y - other.getRect().y;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#toIcon()
	 */
	@Override
	public Image toIcon() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#containsPoint(java.awt.Point)
	 */
	@Override
	public boolean containsPoint(Point p) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#isOverlapping(java.awt.Rectangle)
	 */
	@Override
	public boolean isOverlapping(Rectangle p) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#getRect()
	 */
	@Override
	public Rectangle getRect() {
		if (onTop)
			return new Rectangle(0, Integer.MIN_VALUE, 0, 0);
		return new Rectangle(point.x, point.y, 0, 0);
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public boolean isOnTop() {
		return onTop;
	}

	public void setOnTop(boolean onTop) {
		this.onTop = onTop;
	}

	public Point getOffset() {
		return offset;
	}

	public void setOffset(Point offset) {
		this.offset = offset;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean setRect(Rectangle r) {
		//this.boundingBox = r;
		return true;
	}

	@Override
	public String getDescription() {
		return "Decoration";
	}

}
