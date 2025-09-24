package Game_World;

import java.awt.Point;
import java.beans.IntrospectionException;
import java.io.FileNotFoundException;

import Data_Storage.XMLWriter;
import Game_World_Objects.*;

/**
 * The Class represents the completed levels of the GameWorld, it uses the base templates from the BaseLocations
 * and the GameObjects in the GameWorld Objects package to create a whole level.
 * This class currently only creates level one so far.
 * @author JoshBrake
 * @author hoschurayn
 * @author Danesh Abeyratne
 *
 */
public class GameWorldLevelBuilder {

	private GameWorld level;
	private String levelName = null;

	public GameWorldLevelBuilder(String levelName) {
		this.levelName = levelName;
		createLevel();
	}

	private void createLevel() {
		if (levelName.equals("One")) {
			level = getLevelOne();
			writeXMLLevel();
		}
	}
	/**
	 * Returns level one of the Game
	 * @return GameWorld
	 *
	 */
	public static GameWorld getLevelOne() {
		GameWorld gw = new GameWorld();

		// The location for outside the home building
		Location outsideHome = BaseLocations.getOutsideHome();
		outsideHome.getAllObjects().add(new Relic(new Point(1300, 200)));
		outsideHome.getAllObjects().add(
				new ExternalDoor(new Point(725, 1080), 2, gw, "Inside Home",
						new Point(490, 1120)));
		outsideHome.getAllObjects().add(
				new ExternalDoor(new Point(901, 1084), 2, gw, "Inside Home",
						new Point(975, 1120)));
		outsideHome.getAllObjects().add(
				new ExternalDoor(new Point(737, 1493), 0, gw, "Road",
						new Point(413, 169)));
		outsideHome.getAllObjects().add(new Relic(new Point(106, 150)));

		outsideHome.getAllObjects().add(new Trap(new Point(672, 1296), 2));
		outsideHome.getAllObjects().add(new Trap(new Point(729, 1296), 2));
		outsideHome.getAllObjects().add(new Trap(new Point(786, 1296), 2));
		// Location for inside the home building
		Location insideHome = BaseLocations.getInsideHome();
		Game_World_Objects.Chest chest = new Game_World_Objects.Chest(
				new Point(547, 133));
		chest.getItems().add(new Food(new Point(10, 10), 15));
		chest.getItems().add(new Torch(new Point(10, 10)));
		chest.getItems().add(new Key(new Point(10, 10), 1));
		insideHome.getAllObjects().add(chest);
		insideHome.getAllObjects().add(new Key(new Point(722, 136), 1));
		insideHome.getAllObjects().add(new Key(new Point(722, 176), 1));
		insideHome.getAllObjects().add(new Food(new Point(162, 280), 0));
		insideHome.getAllObjects().add(new Torch(new Point(922, 776)));
		insideHome.getAllObjects().add(new Relic(new Point(883, 724)));
		insideHome.getAllObjects().add(
				new ExternalDoor(new Point(490, 1120), 0, gw, "Outside Home",
						new Point(730, 1084)));
		insideHome.getAllObjects().add(
				new ExternalDoor(new Point(975, 1120), 0, gw, "Outside Home",
						new Point(901, 1084)));

		// The location for the alter room
		Location alterRoom = BaseLocations.getAlterRoom();
		alterRoom.getAllObjects().add(
				new ExternalDoor(new Point(460, 923), 0, gw, "Road", new Point(
						2100, 50)));
		insideHome.getAllObjects().add(new Food(new Point(54, 930), 12));
		// cheat chest
		Game_World_Objects.Chest chest3 = new Game_World_Objects.Chest(
				new Point(331, 884));
		chest3.getItems().add(new Relic(new Point(10, 10)));
		chest3.getItems().add(new Relic(new Point(10, 10)));
		chest3.getItems().add(new Relic(new Point(10, 10)));
		chest3.getItems().add(new Relic(new Point(10, 10)));
		alterRoom.getAllObjects().add(chest3);

		// The location for the road
		Location road = BaseLocations.getRoad();

		road.getAllObjects().add(
				new ExternalDoor(new Point(413, 169), 2, gw, "Outside Home",
						new Point(737, 1493)));

		road.getAllObjects().add(
				new ExternalDoor(new Point(2100, 50), 2, gw,
						"Inside Alter Room", new Point(460, 923)));

		// road traps
		for (int i = 0; i < 30; i++) {
			road.getAllObjects().add(
					new Trap(new Point((int) ((Math.random() * 2360 + 40)),
							(int) ((Math.random() * 300 + 800))), (int) (Math
							.random() * 10)));
		}
		// forest traps
		for (int i = 0; i < 15; i++) {
			road.getAllObjects().add(
					new Trap(new Point((int) ((Math.random() * 2360 + 40)),
							(int) ((Math.random() * 300 + 500))), (int) ((Math
							.random() + 2) * 7)));
		}

		road.getAllObjects().add(new Relic(new Point(46, 761)));
		road.getAllObjects().add(new Relic(new Point(690, 1208)));
		road.getAllObjects().add(new Relic(new Point(2322, 49)));
		road.getAllObjects().add(new Relic(new Point(1646, 396)));
		road.getAllObjects().add(new Food(new Point(224, 384), 16));
		road.getAllObjects().add(new Food(new Point(1032, 618), 18));
		road.getAllObjects().add(new Food(new Point(1540, 1270), 17));
		road.getAllObjects().add(new Torch(new Point(237, 171)));
		road.getAllObjects().add(new Food(new Point(1909, 88), 8));
		road.getAllObjects().add(new Food(new Point(652, 592), 12));
		road.getAllObjects().add(new Food(new Point(1656, 619), 30));
		road.getAllObjects().add(new Relic(new Point(1363, 1211)));
		road.getAllObjects().add(new Relic(new Point(1786, 644)));
		Game_World_Objects.Chest chest2 = new Game_World_Objects.Chest(
				new Point(1270, 572));
		chest2.getItems().add(new Food(new Point(10, 10), 15));
		chest2.getItems().add(new Food(new Point(10, 10), 8));
		chest2.getItems().add(new Food(new Point(10, 10), 25));
		chest2.getItems().add(new Food(new Point(10, 10), 9));
		road.getAllObjects().add(chest2);

		// The final credits room
		Location creditRoom = BaseLocations.getCredits();
		for (int i = 0; i < 50; i++)
			creditRoom.getAllObjects().add(
					new Food(new Point((int) (Math.random() * (600-80-34+40) + 270+40-20),
							(int) (Math.random() * (200-80-34) + 600-20)), (int) (Math
							.random() * 28)));

		road.getAllObjects().add(new Trap(new Point(400, 400), 2));
		gw.getLocations().put(outsideHome.getName(), outsideHome);
		gw.getLocations().put(insideHome.getName(), insideHome);
		gw.getLocations().put(alterRoom.getName(), alterRoom);
		gw.getLocations().put(road.getName(), road);
		gw.getLocations().put(creditRoom.getName(), creditRoom);

		return gw;
	}

	private void writeXMLLevel() {
		XMLWriter xw = null;
		try {
			xw = new XMLWriter("level" + levelName);
		} catch (FileNotFoundException e) {
			System.out.println("XML write error" + e);
		} catch (IntrospectionException e) {
			System.out.println("XML write error" + e);
		}
		xw.write(level);
		xw.close();
		System.out.println("Level " + levelName + " has been written to XML.");
	}

	public static void main(String[] arguments) {
		GameWorldLevelBuilder builder = new GameWorldLevelBuilder("One");
	}
}
