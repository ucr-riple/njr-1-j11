/**
 *
 */
package Game_World;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Graphics_Renderer.GraphicsRenderer;
import Graphics_Renderer.ImageLayer;
import Object_Interfaces.GameObject;

/**
 * This Class represents a Location in the game.
 * Locations contain all their GameObject elements in their contents.
 * @author Danesh Abeyratne
 * @author benjamin
 *
 */
public class Location implements Serializable {
	// the contents of the room, including players Avatars.
	private List<GameObject> contents = new ArrayList<GameObject>();

	private String name;// The name of the room.

	public Location(String n) {
		name = n;
	}

	public Location() {}; // default constructor

	/**
	 * Returns all the objects that are in the location to be drawn
	 *
	 * @return
	 */
	public List<GameObject> getAllObjects() {
		return contents;
	}

	/********** Rendering methods and fields **********/

	/**
	 * Draws the current location/room
	 *
	 * @param g
	 */
	public void draw(Graphics g) {
		g.drawImage(GraphicsRenderer.getImage(name), 0, 0, null);
		try {
			Collections.sort(contents);
		} catch (IllegalArgumentException e) {
			System.out.println("An object had a bad comparable");
		}
		for (GameObject obj : contents) {
			obj.draw(g);

			if (GraphicsRenderer.debug) {
				Rectangle rect = obj.getRect();
				g.setColor(Color.GREEN);
				g.drawRect(rect.x, rect.y, rect.width, rect.height);
			}
		}
		// Order the list of all game objects in room from those in the
		// background to those in the foreground then iterate through drawing
		// them onto the canvas.
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GameObject> getContents() {
		return contents;
	}

	public void setContents(List<GameObject> contents) {
		this.contents = contents;
	}

}
