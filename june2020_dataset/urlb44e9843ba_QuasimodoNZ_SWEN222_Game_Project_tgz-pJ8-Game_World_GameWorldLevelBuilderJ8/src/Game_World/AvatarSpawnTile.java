package Game_World;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import Object_Interfaces.GameObject;

public class AvatarSpawnTile implements GameObject {

	/**
	 * Represents a spawn location for an Avatar.
	 * @author riddelbenj and Danesh Abeyratne (abeyratama, 300042001)
	 *
	 */

	private Rectangle boundingBox;

	public AvatarSpawnTile(Rectangle r) {
		boundingBox = r;
	}

	public AvatarSpawnTile(){}; // default constructor for XML

	@Override
	public void draw(Graphics g) {
	}

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
		return "SpawnTile"+boundingBox.getLocation();
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

	@Override
	public boolean setRect(Rectangle r) {
		this.boundingBox = r;
		return true;
	}

	@Override
	public String getDescription() {
		return "Spawn Tile";
	}


}
