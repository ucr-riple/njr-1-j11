package Game_World;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Graphics_Renderer.GraphicsRenderer;
import Object_Interfaces.GameObject;

public class ExternalDoor implements GameObject {

	/**
	 * The Class represents a unidirectional doorway that takes an avatar from their
	 * current location to another location.
	 * @author Danesh Abeyratne
	 */

	private static final long serialVersionUID = 1L;
	private Rectangle boundingBox;// this is where the door appears
	private String endLocationName;
	private Point endLocationPoint;// point in x-y where the avatar will materialise
	private int direction;
	private boolean locked = false;
	private int key;
	transient Image image = null;


	public ExternalDoor() {	}

	public ExternalDoor(Point currentPoint, int dir, GameWorld world,
			String endLocationName, Point endLocationPoint) {
		this.endLocationName = endLocationName;
		this.boundingBox = new Rectangle(currentPoint.x, currentPoint.y, 60, 60);
		this.endLocationPoint = endLocationPoint;
		this.direction = dir;
	}

	// makes sure that the avatar is 'close' to the door (default is 100 pixels
	// atm)
	public boolean avatarIsClose(Avatar a) {
		if (Math.abs((a.getBoundingBox().getCenterX() - boundingBox
				.getCenterX())) < 100
				&& Math.abs((a.getBoundingBox().getCenterY() - boundingBox
						.getCenterY())) < 100) {
			return true;
		}
		return false;
	}


	/**
	 * Unlocks this door
	 * @param Avatar a
	 * @param int keyType
	 */
	// avatar should use this method to unlock this door
	public void unlockDoor(Avatar a, int k) {
		if (k == key && avatarIsClose(a)) {
			locked = false;
		}
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	public String getEndLocation() {
		return endLocationName;
	}

	public void setEndLocation(String endLocation) {
		this.endLocationName = endLocation;
	}

	public Point getPlaceInEndLocation() {
		return endLocationPoint;
	}

	public void setPlaceInEndLocation(Point placeInEndLocation) {
		this.endLocationPoint = placeInEndLocation;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public void draw(Graphics g) {
		String image;
		if (direction % 4 == 0)
			image = "Door-south";
		else if (direction % 4 == 1)
			image = "Door-west";
		else if (direction % 4 == 2)
			image = "Door-north";
		else
			image = "Door-east";

		g.drawImage(GraphicsRenderer.getImage(image), boundingBox.x,
				boundingBox.y, null);

	}

	@Override
	public int compareTo(GameObject other) {
		return boundingBox.y-other.getRect().y;
	}

	@Override
	public Image toIcon() {
		try {
			return ImageIO.read(
					new File("Images/Objects/door-" + direction + ".png"))
					.getScaledInstance(34, 34, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean containsPoint(Point p) {
		return boundingBox.contains(p);
	}

	@Override
	public boolean isOverlapping(Rectangle rect) {
		return false;
	}

	@Override
	public String getName() {
		return "Door to " + endLocationName;
	}

	@Override
	public Rectangle getRect() {
		return boundingBox;
	}

	public String getLocationOne() {
		return endLocationName;
	}

	public void setLocationOne(String locationFrom) {
		this.endLocationName = locationFrom;
	}

	public Point getPlaceWhereAvatarExits() {
		return endLocationPoint;
	}

	public void setPlaceWhereAvatarExits(Point placeWhereAvatarExits) {
		this.endLocationPoint = placeWhereAvatarExits;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getEndLocationName() {
		return endLocationName;
	}

	public void setEndLocationName(String endLocationName) {
		this.endLocationName = endLocationName;
	}

	public Point getEndLocationPoint() {
		return endLocationPoint;
	}

	@Override
	public boolean setRect(Rectangle r) {
		this.boundingBox = r;
		return true;
	}

	@Override
	public String getDescription() {
		return getName();
	}

}
