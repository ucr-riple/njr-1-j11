package Game_World;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import Graphics_Renderer.GraphicsRenderer;
import Graphics_Renderer.Tileset;
import Object_Interfaces.GameObject;

public class Wall implements GameObject {

	/**
	 * The Class represents an internal wall object that cannot be travessed through.
	 * Opening and closing depends on having a key.
	 * @author Danesh Abeyratne
	 */

	private Rectangle boundingBox;
	private String name;
	private Point offset;

	public Wall(Rectangle r) {
		boundingBox = r;
		name = null;
		offset = new Point(0, 0);
	}

	public Wall(Rectangle r, String s, Point p) {
		boundingBox = r;
		name = s;
		offset = p;
	}

	public Wall() {
	}; // default constructor for XML

	@Override
	public void draw(Graphics g) {
		if (name != null) {
			g.drawImage(GraphicsRenderer.getImage(name), boundingBox.x
					- offset.x, boundingBox.y - offset.y, null);
		}

		if (GraphicsRenderer.debug) {
			g.setColor(Color.GREEN);
			g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width,
					boundingBox.height);
		}
	}

	// private void initialiseImage() {
	// Tileset tileset = new Tileset(name, 40);
	// BufferedImage img = new BufferedImage(boundingBox.width
	// - boundingBox.width % 40, 120, BufferedImage.TYPE_4BYTE_ABGR);
	// Graphics canvas = img.getGraphics();
	// Random generator = new Random(boundingBox.width / 40);
	// for (int i = 0; i < boundingBox.width / 40; i++) {
	// canvas.drawImage(tileset.getTile(2 + generator.nextInt(2), 0),
	// i * 40, 0, null);
	// canvas.drawImage(tileset.getTile(2 + generator.nextInt(2), 1),
	// i * 40, 40, null);
	// canvas.drawImage(tileset.getTile(2 + generator.nextInt(2), 2),
	// i * 40, 80, null);
	// }
	// image = img;
	// }

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
		return false;
	}

	@Override
	public boolean isOverlapping(Rectangle rect) {
		return boundingBox.intersects(rect);
	}

	@Override
	public String getName() {
		if (name == null)
			return "Wall";
		else
			return name;
	}

	@Override
	public Rectangle getRect() {
		return boundingBox;
	}

	public String getTileImage() {
		return name;
	}

	public void setTileImage(String tileImage) {
		this.name = tileImage;
	}

	@Override
	public String toString() {
		return "Wall [boundingBox=" + boundingBox + ", name=" + name + "]";
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

	public Point getOffset() {
		return offset;
	}

	public void setOffset(Point offset) {
		this.offset = offset;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return getName();
	}

}
