package Game_World_Objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import Object_Interfaces.Containable;
import Object_Interfaces.GameObject;

public class GoldBar implements GameObject, Containable {

	/**
	 * This Class represents a goldBar item, it is a containable gameObject.
	 * @author Danesh Abeyratne (abeyratama, 300042001)
	 *
	 */

	public GoldBar() {} // default constructor for XML

	private Rectangle boundingBox;
	private int value = 100;

	public GoldBar(int value, Rectangle r) {
		this.value = value;
		this.boundingBox = r;
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public void draw(Graphics g) {}

	@Override
	public int compareTo(GameObject other) {
		return this.getRect().y - other.getRect().y;
	}

	@Override
	public Image toIcon() {
		return null;
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
		return "GoldBar of value: " + value;
	}

	@Override
	public Rectangle getRect() {
		return boundingBox;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
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
		return "Valuable Gold Bar, tastes very nice";
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
		GoldBar other = (GoldBar) obj;
		if (boundingBox == null) {
			if (other.boundingBox != null)
				return false;
		} else if (!boundingBox.equals(other.boundingBox))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "Gold Bar";
	}



}
