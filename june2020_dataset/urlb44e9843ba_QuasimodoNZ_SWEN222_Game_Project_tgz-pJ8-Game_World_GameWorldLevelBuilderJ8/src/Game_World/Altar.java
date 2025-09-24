/**
 *
 */
package Game_World;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.RectangularShape;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Game_World_Objects.Relic;
import Graphics_Renderer.GraphicsRenderer;
import Object_Interfaces.GameObject;

/**
 * Represents the altar of where Relics are placed. Once a player places
 * four relics, that player wins the game.
 *
 * @author riddelbenj and Danesh Abeyratne (abeyratama, 300042001)
 *
 */

public class Altar implements GameObject {
	private static final Rectangle boundingBox = new Rectangle(240, 320, 440,
			440);
	static Point[][] pillerPlaces = {
		// Player 1's piller slots
		{ new Point(boundingBox.x + 40, boundingBox.y + 120),
			new Point(boundingBox.x + 80, boundingBox.y + 80),
			new Point(boundingBox.x + 120, boundingBox.y + 40),
			new Point(boundingBox.x + 160, boundingBox.y + 40) },
			// Player 2's piller slots
			{ new Point(boundingBox.x + 160, boundingBox.y + 360),
				new Point(boundingBox.x + 120, boundingBox.y + 360),
				new Point(boundingBox.x + 80, boundingBox.y + 320),
				new Point(boundingBox.x + 40, boundingBox.y + 280) },
				// Player 3's piller slots
				{ new Point(boundingBox.x + 240, boundingBox.y + 40),
					new Point(boundingBox.x + 280, boundingBox.y + 40),
					new Point(boundingBox.x + 320, boundingBox.y + 80),
					new Point(boundingBox.x + 360, boundingBox.y + 120) },
					// Player 4's piller slots
					{ new Point(boundingBox.x + 360, boundingBox.y + 280),
						new Point(boundingBox.x + 320, boundingBox.y + 320),
						new Point(boundingBox.x + 280, boundingBox.y + 360),
						new Point(boundingBox.x + 240, boundingBox.y + 360) } };

	List<List<Relic>> pillers = new ArrayList<List<Relic>>();


	// constructor:
	public Altar() {
		for (int count = 0; count < 4; count++){
			pillers.add(new ArrayList<Relic>());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(GraphicsRenderer.getImage("Start Alter"), boundingBox.x, boundingBox.y, null);
		for (List<Relic> playerPillers : pillers)
			for (Relic piller : playerPillers)
				piller.draw(g);
	}

	/**
	 * Add a Relic to the Altar. If you have four Relics in your Altar you win the game!
	 * @param int playerNumber
	 * @param Relic piller
	 *
	 */

	public int addPiller(int playerNumber, Relic piller) {
		if(pillers.get(playerNumber).size() < 4){
		pillers.get(playerNumber).add(piller);
		piller.setRect(new Rectangle(pillerPlaces[playerNumber][pillers.get(
				playerNumber).size() - 1]));
		if (pillers.get(playerNumber).size() > 3){
			return playerNumber;// This is how we win the game
		}
		}
		return -1;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#compareTo(Object_Interfaces.GameObject)
	 */
	@Override
	public int compareTo(GameObject other) {
		return this.getRect().y - other.getRect().y;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#toIcon()
	 */
	@Override
	public Image toIcon() {
		return (new Relic(null)).toIcon();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#containsPoint(java.awt.Point)
	 */
	@Override
	public boolean containsPoint(Point p) {
		return boundingBox.contains(p);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#isOverlapping(java.awt.Rectangle)
	 */
	@Override
	public boolean isOverlapping(Rectangle rect) {
		return boundingBox.intersects(rect);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#getName()
	 */
	@Override
	public String getName() {
		return "Quest Altar";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Object_Interfaces.GameObject#getRect()
	 */
	@Override
	public Rectangle getRect() {
		return boundingBox;
	}

	@Override
	public boolean setRect(Rectangle r) {
		//this.boundingBox = r;
		return true;
	}

	@Override
	public String getDescription() {
		return "Altar";
	}
}
