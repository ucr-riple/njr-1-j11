/**
 *
 */
package Graphics_Renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import Game_World.Avatar;
import Game_World.GameWorld;
import Game_World.Location;
import Object_Interfaces.Container;

/**
 * Displays the state of the game using graphics
 * 
 * @author benjamin
 * 
 */
public class GraphicsRenderer {
	// Stores the graphics statically so that all everything can request their
	// images without sending more data over the network than necessary
	public static Map<String, Image> allGraphics = new HashMap<String, Image>();
	public static Map<String, Tileset> allTilesets = new HashMap<String, Tileset>();

	// Used for debugging the bounding boxes by drawing a green outline around
	// the box
	public static boolean debug = false;

	/**
	 * Draws the room that the given player is in onto the graphics given. It is
	 * offset by the position of the player so that it Avatar so that it appears
	 * in the center of the screen. It also draws the darkness if the player is
	 * outside.
	 * 
	 * @param g	The Graphics that is drawn on
	 * @param center	The point that is the center of the view of teh graphics
	 * @param gameWorld	The game world that is to be drawn
	 * @param avatarName	The name of the avatar
	 */
	public static void redraw(Graphics g, Point center, GameWorld gameWorld,
			String avatarName) {
		//Checks the input
		if (center == null)
			center = new Point(400, 400);
		if (g == null || gameWorld == null) {
			System.out.printf(
					"Graphics: %s\nPoint: %s\nGameWorld: %s\nName: %s\n", g,
					center, gameWorld, avatarName);
			return;
		}
		
		//Finds and checks the avatar
		Avatar avatar = null;
		for (Avatar a : gameWorld.getAvatars()) {
			if (a.getName().equals(avatarName)) {
				avatar = a;
			}
		}
		if (avatar == null) {
			System.out
					.printf("Avatar is null\nGraphics: %s\nPoint: %s\nGameWorld: %s\nName: %s\n",
							g, center, gameWorld, avatarName);
			return;
		}

		//Translates the graphics so that the avatar appears in the center of the screen
		g.translate(center.x - (int) avatar.getRect().getCenterX(), center.y
				- (int) avatar.getRect().getCenterY());
		
		// Finds and checks the room, if available it is then drawn
		Location room = gameWorld.getAvatarLocations().get(avatar);
		if (room != null)
			room.draw(g);
		else {
			System.out
					.println("Could not find the room that contains the avatar.");
			return;
		}
		
		//Draws the darkness if the avatar is outside
		if (room.getName().equals("Outside Home")
				|| room.getName().equals("Road"))
			drawDarkness(g, avatar.hasTorch(), avatar.getRect());
	}

	/**
	 * Draws a very dark shade onto the screen as if there is very little light.
	 * If the player has a torch then the area around him will be lit.
	 * 
	 * @param g
	 * @param hasTorch
	 */
	public static void drawDarkness(Graphics g, boolean hasTorch, Rectangle rect) {
		int x = (int) rect.getCenterX();
		int y = (int) rect.getCenterY();
		int shadeSize = 1500;
		g.setColor(new Color(0, 0, 0, 230));

		//Draws a large rectangle to cover the whole screen so that the player is in almost total darkness
		if (!hasTorch) {
			g.fillRect(x - shadeSize, y - shadeSize, shadeSize * 2,
					shadeSize * 2);
			return;
		}

		//Draws darkness around the player with a glow/transition
		Image glow = getImage("torch glow");
		g.drawImage(glow, x - glow.getWidth(null) / 2 - 1,
				y - glow.getHeight(null) / 2, null);
		g.fillRect(x + glow.getWidth(null) / 2 - 1, y - glow.getHeight(null)
				/ 2, shadeSize, shadeSize);
		g.fillRect(x - glow.getWidth(null) / 2,
				y - shadeSize - glow.getHeight(null) / 2, shadeSize, shadeSize);
		g.fillRect(x - shadeSize + glow.getWidth(null) / 2,
				y + glow.getHeight(null) / 2, shadeSize, shadeSize);
		g.fillRect(x - shadeSize - glow.getWidth(null) / 2, y - shadeSize
				+ glow.getHeight(null) / 2, shadeSize, shadeSize);
	}

	/**
	 * Given a 2d array of integers that correspond to the tile ids in the given
	 * Tileset, it will make an image and draw each tile on and return the final
	 * product
	 * 
	 * @param tileIDs
	 * @param tileset
	 * @return
	 */
	public static Image combineTiles(int[][] tileIDs, Tileset tileset) {
		//Sets up the image that will be returned
		BufferedImage image = new BufferedImage(tileIDs[0].length
				* tileset.getTileWidth(), tileIDs.length
				* tileset.getTileHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		//Seperates out a graphics from the image so that the image can be manipulated
		Graphics canvas = image.getGraphics();
		
		//Draws each tile at its corresponding x and y coordinates based on the size of each tile
		for (int x = 0; x < tileIDs[0].length; x++)
			for (int y = 0; y < tileIDs.length; y++)
				if (tileIDs[y][x] != -1)
					canvas.drawImage(tileset.getTile(tileIDs[y][x]), x
							* tileset.getTileWidth(),
							y * tileset.getTileHeight(), null);

		return image;
	}

	/**
	 * Given a Graphics and Container, will draw a grid for the Container and
	 * draw all the icon versions of the objects onto the grid
	 * 
	 * @param g
	 * @param c
	 */
	public static void drawInventory(Graphics g, Container c) {
		
		//Draws tiles for each available slot in the container
		int size = c.getInventorySize();
		if (size != 4 && size != 9 && size != 16)
			throw new IllegalArgumentException(
					"The container c must have a size of 4, 9 or 16. Its size is "
							+ size);
		for (int x = 0; x < Math.sqrt(size); x++)
			for (int y = 0; y < Math.sqrt(size); y++)
				try {
					g.drawImage(ImageIO.read(new File(
							"Images/Inventory Square.png")), x * 38,
							y * 38, null);
				} catch (IOException e) {
					e.printStackTrace();
				}

		//Draws each item in the container in its allocated tile slot
		for (int i = 0; i < c.getItems().size(); i++) {
			int x = (int) (i % Math.sqrt(size)) * 38 + 2;
			int y = (int) (i / Math.sqrt(size)) * 38 + 2;
			g.drawImage(c.getItems().get(i).toIcon(), x, y, null);
		}
	}

	/**
	 * If the static map allGraphics contains the key string, it returns the
	 * image otherwise it initialises the image and returns it
	 * 
	 * @param key
	 * @return
	 */
	public static Image getImage(String key) {
		if (allGraphics.containsKey(key))
			return allGraphics.get(key);
		else
			return initialiseImage(key);
	}

	/**
	 * If the static map allTilesets contains the key string, it returns the
	 * tileset otherwise it initialises the tileset and returns it
	 * 
	 * @param key
	 * @return
	 */
	public static Tileset getTileset(String key) {
		if (allTilesets.containsKey(key))
			return allTilesets.get(key);
		else
			return initialiseTileset(key);
	}

	/**
	 * It creates an Image based on the string key, it then adds it to the
	 * static map allGraphics and then returns the image
	 * 
	 * @param key
	 * @return
	 */
	private static Image initialiseImage(String key) {
		Image image = null;
		if (key.equals("Inside Alter Room"))
			image = drawLayers(LocationImageLayers.getAlterRoom());
		if (key.equals("Inside Home"))
			image = drawLayers(LocationImageLayers.getInsideHome());
		if (key.equals("Outside Home"))
			image = drawLayers(LocationImageLayers.getOutsideHome());
		if (key.equals("Road"))
			image = drawLayers(LocationImageLayers.getRoad());
		if (key.equals("Home Building"))
			image = drawLayers(LocationImageLayers.getHomeBuilding());
		if (key.equals("Home Gate"))
			image = drawLayers(LocationImageLayers.getHomeGate());
		if (key.equals("Home middle wall"))
			image = drawLayers(LocationImageLayers.getHomeMiddleWall());
		if (key.equals("Credit Room"))
			image = drawLayers(LocationImageLayers.getCreditRoom());
		if (key.equals("Steak Table"))
			image = drawLayers(LocationImageLayers.getSteakTable());

		if (image == null)
			try {
				image = ImageIO.read(new File("Images/Objects/" + key
						+ ".png"));
			} catch (IOException e) {
				throw new IllegalArgumentException("The graphics name \"" + key
						+ "\" is unknown");
			}
		allGraphics.put(key, image);
		return image;
	}

	/**
	 * It creates a Tileset based on the string key, it then adds it to the
	 * static map allGraphics and then returns the tileset
	 * 
	 * @param key
	 * @return
	 */
	private static Tileset initialiseTileset(String key) {
		Tileset tileset = null;

		if (key.equals("420tiles"))
			tileset = new Tileset("Images/tilesets/420tiles.png", 34);

		if (key.startsWith("player"))
			tileset = new Tileset("Images/tilesets/" + key + ".png", 48);
		allTilesets.put(key, tileset);
		return tileset;
	}

	/**
	 * Given a list of ImageLayers it will create an image pertaining to the
	 * largest ImageLayer and draw each layer in their corresponding order and
	 * return it
	 * 
	 * @param layers
	 * @return
	 */
	public static Image drawLayers(List<ImageLayer> layers) {

		// Sets up an image to be drawn onto via a Graphics instance, finds the
		// maximum number of rows and columns expected
		int columns = 0;
		int rows = 0;

		for (ImageLayer layer : layers) {
			columns = Math.max(columns, layer.getWidth());
			rows = Math.max(rows, layer.getHeight());
		}
		BufferedImage image = new BufferedImage(columns * 40, rows * 40,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics canvas = image.getGraphics();

		// Draws each layer on the image
		for (ImageLayer layer : layers) {
			canvas.drawImage(layer.toImage(), 0, 0, null);
		}

		return image;
	}
	/*
	 * //Unused method public static void drawMiniMap(Graphics g, String player,
	 * GameWorld gameWorld) { g.setColor(new Color(255, 255, 0, 100)); String
	 * room = null; for (Map.Entry<Avatar, Location> entry :
	 * gameWorld.getAvatarLocations() .entrySet()) if
	 * (entry.getKey().equals(player)) room = entry.getValue().getName();
	 * 
	 * if (room.contains("Home")) g.fillRect(2, 74, 35, 29); if
	 * (room.equals("Road")) g.fillRect(0, 104, 152, 40); }
	 */
}
