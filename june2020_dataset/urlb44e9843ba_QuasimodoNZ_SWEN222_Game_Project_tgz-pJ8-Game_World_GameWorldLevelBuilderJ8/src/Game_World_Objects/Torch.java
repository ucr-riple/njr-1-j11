package Game_World_Objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import Graphics_Renderer.GraphicsRenderer;
import Object_Interfaces.Containable;
import Object_Interfaces.GameObject;

public class Torch implements GameObject, Containable {

	/**
	 * This Class represents a Torch item, it is a containable gameObject.
	 * A player needs a Torch in order to see in the dark of the outside world!
	 * @author Danesh Abeyratne (abeyratama, 300042001)
	 *
	 */

	private Rectangle boundingBox;

	public Torch(Point p) {
		boundingBox = new Rectangle(p.x, p.y, 34, 34);
	}

	public Torch() {} // default constructor for XML

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public void draw(Graphics g) {
		Image image = toIcon();
		g.drawImage(
				image,
				(int) boundingBox.getCenterX() - (int) image.getWidth(null) / 2,
				(int) boundingBox.getCenterY() - image.getHeight(null) / 2,
				null);
	}

	@Override
	public int compareTo(GameObject other) {
		return this.getRect().y - other.getRect().y;
	}

	@Override
	public Image toIcon() {
		return GraphicsRenderer.getTileset("420tiles").getTile(5, 16);

	}

	@Override
	public boolean containsPoint(Point p) {
		return boundingBox.contains(p);
	}

	@Override
	public boolean isOverlapping(Rectangle p) {
		return false;
	}

	@Override
	public String getName() {
		return "Torch";
	}

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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Torch to light up the dark";
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
		Torch other = (Torch) obj;
		if (boundingBox == null) {
			if (other.boundingBox != null)
				return false;
		} else if (!boundingBox.equals(other.boundingBox))
			return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "Burning Torch";
	}



}
