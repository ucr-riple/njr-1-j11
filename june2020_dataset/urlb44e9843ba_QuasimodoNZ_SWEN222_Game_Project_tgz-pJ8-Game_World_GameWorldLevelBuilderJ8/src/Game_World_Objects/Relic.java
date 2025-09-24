/**
 *
 */
package Game_World_Objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Graphics_Renderer.GraphicsRenderer;
import Object_Interfaces.Containable;
import Object_Interfaces.GameObject;

/**
 * @author riddelbenj
 *
 */
public class Relic implements GameObject, Containable {

	/**
	 * This Class represents a Relic item, it is a containable gameObject.
	 * A player must collect four Relics in order to win the game!
	 * @author Danesh Abeyratne (abeyratama, 300042001)
	 *
	 */

	private Rectangle boundingBox;

	public Relic(Point p) {
		boundingBox = new Rectangle(p.x, p.y, 36, 36);
	}

	public Relic() {} // default constructor

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		Image image = GraphicsRenderer.getImage("Piller");
		g.drawImage(image, (int) (boundingBox.x), (int) (boundingBox.y - 17),
				null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#compareTo(Object_Interfaces.GameObject)
	 */
	@Override
	public int compareTo(GameObject other) {
		return this.getRect().y - other.getRect().y;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#toIcon()
	 */
	@Override
	public Image toIcon() {
		return GraphicsRenderer.getImage("Piller").getScaledInstance(34, 34,
				Image.SCALE_DEFAULT);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#containsPoint(java.awt.Point)
	 */
	@Override
	public boolean containsPoint(Point p) {
		return boundingBox.contains(p);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#isOverlapping(java.awt.Rectangle)
	 */
	@Override
	public boolean isOverlapping(Rectangle rect) {
		return boundingBox.intersects(rect);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#getName()
	 */
	@Override
	public String getName() {
		return "Quest Relic";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#getRect()
	 */
	@Override
	public Rectangle getRect() {
		return boundingBox;
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	@Override
	public int getSize() {
		return 2;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "An old relic with a distinct bottom fitting, maybe it can be placed somewhere...";
	}

	@Override
	public boolean setRect(Rectangle r) {
		this.boundingBox = r;
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relic other = (Relic) obj;
		if (boundingBox == null) {
			if (other.boundingBox != null)
				return false;
		} else if (!boundingBox.equals(other.boundingBox))
			return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "Relic";
	}



}
