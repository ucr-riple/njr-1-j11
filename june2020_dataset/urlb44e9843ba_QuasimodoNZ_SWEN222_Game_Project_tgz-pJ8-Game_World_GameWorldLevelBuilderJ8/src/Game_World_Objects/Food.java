package Game_World_Objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import Graphics_Renderer.GraphicsRenderer;
import Object_Interfaces.Containable;
import Object_Interfaces.GameObject;

public class Food implements GameObject, Containable {

	/**
	 * This Class represents a food item, it is a containable gameObject
	 * it can be eaten by an avatar, and will replenish an avatar's health.
	 * @author Danesh Abeyratne (abeyratama, 300042001)
	 *
	 */

	private Rectangle boundingBox;
	private int value;

	public Food() {}; // default constructor

	public Food(Point p, int v) {
		boundingBox = new Rectangle(p.x,p.y,30,30);
		value = v;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(toIcon(), (int)boundingBox.getCenterX() -17, (int)boundingBox.getCenterY() -17, null);
	}

	@Override
	public int compareTo(GameObject other) {
		return this.getRect().y - other.getRect().y;
	}

	@Override
	public Image toIcon() {
		return GraphicsRenderer.getTileset("420tiles").getTile(value);
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
		return "Food";
	}

	@Override
	public Rectangle getRect() {
		return boundingBox;
	}

	@Override
	public int getSize() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Food that heals "+value+"% of total health";
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	@Override
	public boolean setRect(Rectangle r) {
		this.boundingBox = r;
		return true;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	/* (non-Javadoc)
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
		Food other = (Food) obj;
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
		switch (value) {
			case 0: return "Black Grapes";
			case 1: return "Tomato";
			case 2: return "Green Apple";
			case 3: return "Red Apple";
			case 4: return "Orange";
			case 5: return "Green Grapes";
			case 6: return "Purple Grapes";
			case 7: return "Watermelon";
			case 8: return "Strawberry";
			case 9: return "Green Pear";
			case 10: return "Lemon";
			case 11: return "Pineapple";
			case 12: return "Banana";
			case 13: return "Acorn";
			case 14: return "Turnip";
			case 15: return "Carrot";
			case 16: return "Green Capsicum";
			case 17: return "Orange Capsicum";
			case 18: return "Red Capsicum";
			case 19: return "Mushroom";
			case 20: return "Egg";
			case 21: return "Bread";
			case 22: return "Chocolate Cake";
			case 23: return "Cheese";
			case 24: return "Raw Fish";
			case 25: return "Raw Steak";
			case 26: return "Cooked Fish";
			case 27: return "Cooked Steak";
			case 28: return "Red Potion";
			case 29: return "Orange Potion";
			case 30: return "Yellow potion";
			default: return "Consumable";
		}
	}



}
