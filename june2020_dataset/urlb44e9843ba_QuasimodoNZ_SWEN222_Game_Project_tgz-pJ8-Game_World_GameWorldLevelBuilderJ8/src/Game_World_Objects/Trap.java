package Game_World_Objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import Graphics_Renderer.GraphicsRenderer;
import Object_Interfaces.GameObject;

public class Trap implements GameObject {

	/**
	 * This Class represents a Trap item, it is a gameObject.
	 * If an Avatar steps on a Trap they get damaged!
	 * @author Danesh Abeyratne (abeyratama, 300042001)
	 *
	 */

	private Rectangle boundingBox;
	private int damage;
	private boolean isTriggered = false;

	public Trap(Point p, int d) {
		boundingBox = new Rectangle(p.x, p.y, 53, 7);
		damage = d;
	}

	public Trap() {
	} // default constructor for XML

	@Override
	public void draw(Graphics g) {
		if (isTriggered)
			g.drawImage(GraphicsRenderer.getImage("Trap"), boundingBox.x,
					boundingBox.y - 10, null);
	}

	@Override
	public int compareTo(GameObject other) {
		return this.getRect().y - other.getRect().y;
	}

	@Override
	public Image toIcon() {
		return GraphicsRenderer.getImage("Trap").getScaledInstance(34, 34,
				Image.SCALE_DEFAULT);
	}

	@Override
	public boolean containsPoint(Point p) {
		return boundingBox.contains(p);
	}

	@Override
	public boolean isOverlapping(Rectangle p) {
		return boundingBox.intersects(p);
	}

	@Override
	public String getName() {
		return "Trap that deals: " + damage + " damage.";
	}

	@Override
	public Rectangle getRect() {
		return boundingBox;
	}

	@Override
	public boolean setRect(Rectangle r) {
		this.boundingBox = r;
		return true;
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * @return the damage
	 */
	public int getDamage() {
		isTriggered = true;
		return damage;
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
		Trap other = (Trap) obj;
		if (boundingBox == null) {
			if (other.boundingBox != null)
				return false;
		} else if (!boundingBox.equals(other.boundingBox))
			return false;
		if (damage != other.damage)
			return false;
		if (isTriggered != other.isTriggered)
			return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "Danger!";
	}

	public boolean isTriggered() {
		return isTriggered;
	}

	public void setTriggered(boolean isTriggered) {
		this.isTriggered = isTriggered;
	}



}
