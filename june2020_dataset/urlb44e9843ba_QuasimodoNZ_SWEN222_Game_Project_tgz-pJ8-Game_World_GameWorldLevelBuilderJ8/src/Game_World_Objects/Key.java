package Game_World_Objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import Graphics_Renderer.GraphicsRenderer;
import Object_Interfaces.Containable;
import Object_Interfaces.GameObject;

public class Key implements GameObject, Containable {

	/**
	 * This Class represents a Key item, it is a containable gameObject.
	 * Keys enable the opening and closing of doors.
	 * @author Danesh Abeyratne (abeyratama, 300042001)
	 *
	 */

	private Rectangle boundingBox;
	private int keyType;

	public Key() {
	}

	public Key(Point p, int k) {
		boundingBox = new Rectangle(p.x, p.y, 34, 34);
		keyType = k;
	}

	@Override
	public void draw(Graphics g) {

		g.drawImage(toIcon(), (int) boundingBox.getCenterX() - 17,
				(int) boundingBox.getCenterY() - 17, null);
	}

	@Override
	public int compareTo(GameObject other) {
		return this.getRect().y - other.getRect().y;
	}

	@Override
	public Image toIcon() {
		return GraphicsRenderer.getTileset("420tiles").getTile(7 + keyType, 16);
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
		return "A Silver Key";
	}

	@Override
	public Rectangle getRect() {
		return boundingBox;
	}

	@Override
	public int getSize() {
		return 1;
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	public int getKeyType() {
		return keyType;
	}

	public void setKeyType(int keyType) {
		this.keyType = keyType;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Key to unlock the corresponding door";
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
		Key other = (Key) obj;
		if (boundingBox == null) {
			if (other.boundingBox != null)
				return false;
		} else if (!boundingBox.equals(other.boundingBox))
			return false;
		if (keyType != other.keyType)
			return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "Silver Key";
	}



}
