package Game_World_Objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Graphics_Renderer.GraphicsRenderer;
import Object_Interfaces.Containable;
import Object_Interfaces.Container;

import Object_Interfaces.GameObject;

public class Chest implements GameObject, Container {

	/**
	 * This Class represents a chest, it can contain up to 16 GameObjects that are Containable.
	 * This collection of objects can be moved into the avatar's posessions.
	 *
	 * @author Danesh Abeyratne (abeyratama, 300042001)
	 *
	 */

	private Rectangle boundingBox;
	private List<Containable> contents = new ArrayList<Containable>();

	public Chest(Point p) {
		boundingBox = new Rectangle(p.x, p.y, 37, 21);
	}

	public Chest() {} // default constructor

	@Override
	public void draw(Graphics g) {

		g.drawImage(GraphicsRenderer.getImage("Chest"), (boundingBox.x),
				boundingBox.y - 13, null);
	}

	@Override
	public int compareTo(GameObject other) {
		return boundingBox.y - other.getRect().y;
	}

	@Override
	public Image toIcon() {
		return GraphicsRenderer.getTileset("420tiles").getTile(413);
	}

	@Override
	public boolean isOverlapping(Rectangle rect) {
		return boundingBox.intersects(rect);
	}

	@Override
	public boolean containsPoint(Point p) {
		return boundingBox.contains(p);
	}

	@Override
	public String getName() {
		return "Chest - Number of Items: " + contents.size();
	}

	@Override
	public boolean canContain(Containable item) {
		return contents.size() < 16;
	}

	@Override
	public List<Containable> getItems() {
		return contents;
	}

	@Override
	public int getInventorySize() {
		return 16;
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

	public List<Containable> getContents() {
		return contents;
	}

	public void setContents(List<Containable> contents) {
		this.contents = contents;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Chest with %d items inside and space for 16", contents.size());
	}

	@Override
	public boolean setRect(Rectangle r) {
		this.boundingBox = r;
		return true;
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
		Chest other = (Chest) obj;
		if (boundingBox == null) {
			if (other.boundingBox != null)
				return false;
		} else if (!boundingBox.equals(other.boundingBox))
			return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "Chest";
	}


}