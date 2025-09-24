package Game_World;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import Graphics_Renderer.GraphicsRenderer;
import Object_Interfaces.GameObject;

public class InternalDoor implements GameObject {

	/**
	 * The Class represents an internal doorway that can be open or closed.
	 * Opening and closing depends on having a key.
	 * @author Danesh Abeyratne
	 */

	private Rectangle boundingBox;
	private int key;
	private boolean locked = true;

	public InternalDoor() {}

	public InternalDoor(Point p) {
		boundingBox = new Rectangle(p.x,p.y,166,40);
	}

	// makes sure that the avatar is 'close' to the door
	private boolean avatarIsClose(Avatar a) {
		if (Math.abs((a.getBoundingBox().getCenterX() - boundingBox.getCenterX())) < 20
				&& Math.abs((a.getBoundingBox().getCenterY() - boundingBox.getCenterY())) < 20) {
			return true;
		}
		return false;
	}


	/**
	 * Opens and unlocks this door
	 * @param Avatar a
	 * @param int keyType
	 */
	public void unlockDoor(Avatar a, int k) {
		if (k == key && avatarIsClose(a)) {
			locked = false;
		}
	}

	@Override
	public void draw(Graphics g) {
		Image image;
		if (locked) {
			image = GraphicsRenderer.getImage("Door-closed");
		} else
			image = GraphicsRenderer.getImage("Door-open");
		g.drawImage(image, boundingBox.x, boundingBox.y-75, null);
	}

	@Override
	public int compareTo(GameObject other) {
		return boundingBox.y - other.getRect().y;
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
		if (locked && boundingBox.intersects(p)) {
			return true;// can't move through
		}
		return false;
	}

	@Override
	public String getName() {
		return "Internal Door" + boundingBox.getLocation();
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

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public boolean setRect(Rectangle r) {
		this.boundingBox = r;
		return true;
	}

	@Override
	public String getDescription() {
		return "Internal Door";
	}

}
