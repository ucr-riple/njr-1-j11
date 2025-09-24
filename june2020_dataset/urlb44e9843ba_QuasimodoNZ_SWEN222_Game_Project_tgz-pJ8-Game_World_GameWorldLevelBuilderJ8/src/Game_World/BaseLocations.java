/**
 *
 */
package Game_World;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import Game_World_Objects.Food;
import Game_World_Objects.Trap;
import Graphics_Renderer.ImageLayer;

/**
 * The Class represents a set of pre-generated template or base locations, which will be individualised \
 * and populated with gameobjects to generate the various levels.
 * @author Benjamin
 *
 */
public class BaseLocations {
	static Point fatTreeOffset = new Point(40, 60);
	static Point tallTreeOffset = new Point(20, 80);
	static Point fatRockOffset = new Point(0, 10);
	static Point tallRockOffset = new Point(0, 30);
	static Point tableOffset = new Point(0, 25);
	static Point chairOffset = new Point(0, 33);
	static Point armourOffset = new Point(0, 55);
	static Point benchOffset = new Point(0, 20);

	static Point fireplaceOffset = new Point(0, 40);

	private static Rectangle fatTreeRect(int x, int y) {
		return new Rectangle(x, y, 40, 40);
	}

	private static Rectangle tallTreeRect(int x, int y) {
		return new Rectangle(x, y, 30, 30);
	}

	private static Rectangle fatRockRect(int x, int y) {
		return new Rectangle(x, y, 40, 20);
	}

	private static Rectangle tallRockRect(int x, int y) {
		return new Rectangle(x, y, 30, 20);
	}

	private static Rectangle tableRect(int x, int y) {
		return new Rectangle(x, y, 115, 36);
	}

	private static Rectangle chairRect(int x, int y) {
		return new Rectangle(x, y, 36, 25);
	}

	private static Rectangle armourRect(int x, int y) {
		return new Rectangle(x, y, 30, 15);
	}

	private static Rectangle benchRect(int x, int y) {
		return new Rectangle(x, y, 75, 22);
	}

	private static Rectangle fireplaceRect(int x, int y) {
		return new Rectangle(x, y, 110, 30);
	}

	public static Location getAlterRoom() {

		Location location = new Location("Inside Alter Room");

		// Adds the objects to the room
		location.getAllObjects().add(new Altar());
		location.getAllObjects().add(new Wall(new Rectangle(0, 80, 920, 40)));
		location.getAllObjects().add(new Wall(new Rectangle(0, 120, 40, 840)));
		location.getAllObjects()
				.add(new Wall(new Rectangle(880, 120, 40, 840)));
		location.getAllObjects().add(new Wall(new Rectangle(0, 960, 920, 40)));

		location.getAllObjects().add(
				new Decoration("Door-border", new Point(440, 960), true, null));

		return location;
	}

	public static Location getOutsideHome() {
		Location location = new Location("Outside Home");

		location.getAllObjects().add(
				new Wall(new Rectangle(440, 240, 560, 840)));
		location.getAllObjects().add(
				new Wall(new Rectangle(1000, 720, 160, 360)));
		location.getAllObjects().add(new Wall(new Rectangle(40, 80, 1440, 40)));
		location.getAllObjects()
				.add(new Wall(new Rectangle(40, 120, 40, 1400)));
		location.getAllObjects().add(
				new Wall(new Rectangle(40, 1520, 1440, 40)));
		location.getAllObjects().add(
				new Wall(new Rectangle(1440, 120, 40, 1400)));
		location.getAllObjects().add(
				new Decoration("Home Building", new Point(0, 0), true, null));
		location.getAllObjects().add(
				new Decoration("Home Gate", new Point(0, 1440), true, null));

		location.getAllObjects().add(
				new Wall(tallTreeRect(840, 1200), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(640, 1200), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(840, 1300), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(640, 1300), "tree3", tallTreeOffset));

		location.getAllObjects().add(
				new Wall(tallTreeRect(840, 1400), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(640, 1400), "tree3", tallTreeOffset));

		return location;
	}

	public static Location getInsideHome() {
		Location location = new Location("Inside Home");
		location.getAllObjects().add(new Wall(new Rectangle(0, 80, 800, 40)));
		location.getAllObjects().add(new Wall(new Rectangle(0, 120, 40, 1040)));
		location.getAllObjects().add(
				new Wall(new Rectangle(760, 120, 80, 1040)));
		location.getAllObjects()
				.add(new Wall(new Rectangle(0, 1160, 1200, 40)));
		location.getAllObjects()
				.add(new Wall(new Rectangle(840, 640, 360, 40)));
		location.getAllObjects().add(
				new Wall(new Rectangle(1160, 680, 40, 480)));

		location.getAllObjects()
				.add(new Decoration("Door-border", new Point(480, 1160), true,
						null));
		location.getAllObjects()
				.add(new Decoration("Door-border", new Point(960, 1160), true,
						null));

		// Top door and wall
		location.getAllObjects().add(
				new Wall(new Rectangle(40, 440, 525, 40), "Home middle wall",
						new Point(0, 80)));
		location.getAllObjects().add(new InternalDoor(new Point(548, 440)));
		location.getAllObjects()
				.add(new Wall(new Rectangle(634, 440, 120, 40)));

		// Bottom door and wall
		location.getAllObjects().add(
				new Wall(new Rectangle(40, 800, 525, 40), "Home middle wall",
						new Point(0, 80)));
		location.getAllObjects().add(new InternalDoor(new Point(548, 800)));
		location.getAllObjects()
				.add(new Wall(new Rectangle(634, 800, 120, 40)));

		location.getAllObjects().add(
				new Wall(tableRect(300, 600), "Table", tableOffset));
		location.getAllObjects().add(
				new Wall(chairRect(420, 605), "Chair1", chairOffset));
		location.getAllObjects().add(
				new Wall(chairRect(1110, 800), "Chair2", chairOffset));
		location.getAllObjects().add(
				new Wall(armourRect(530, 120), "Suit of armour", armourOffset));
		location.getAllObjects().add(
				new Wall(armourRect(350, 120), "Suit of armour", armourOffset));
		location.getAllObjects().add(
				new Wall(benchRect(80, 200), "Bench", benchOffset));
		location.getAllObjects()
				.add(new Wall(fireplaceRect(400, 100), "Fireplace",
						fireplaceOffset));
		location.getAllObjects()
				.add(new Wall(fireplaceRect(250, 820), "Fireplace",
						fireplaceOffset));
		location.getAllObjects().add(
				new Decoration("Bowl", new Point(385, 585), true, null));
		location.getAllObjects().add(
				new Decoration("Candle", new Point(350, 575), true, null));
		return location;
	}

	public static Location getRoad() {
		Location location = new Location("Road");

		// Top edge
		location.getAllObjects().add(new Wall(new Rectangle(0, 120, 800, 40)));
		location.getAllObjects()
				.add(new Wall(new Rectangle(760, 160, 40, 400)));
		location.getAllObjects()
				.add(new Wall(new Rectangle(800, 520, 840, 40)));
		location.getAllObjects().add(new Wall(new Rectangle(1600, 0, 40, 520)));
		location.getAllObjects().add(new Wall(new Rectangle(1640, 0, 760, 40)));
		// Left edge
		location.getAllObjects().add(new Wall(new Rectangle(0, 160, 40, 1080)));
		// Right edge
		location.getAllObjects().add(
				new Wall(new Rectangle(2360, 40, 40, 1280)));
		// Bottom edge
		location.getAllObjects()
				.add(new Wall(new Rectangle(40, 1200, 480, 40)));
		location.getAllObjects().add(
				new Wall(new Rectangle(480, 1240, 320, 40)));
		location.getAllObjects().add(
				new Wall(new Rectangle(760, 1200, 400, 40)));
		location.getAllObjects().add(
				new Wall(new Rectangle(1160, 1240, 40, 140)));
		location.getAllObjects().add(
				new Wall(new Rectangle(1200, 1340, 280, 40)));
		location.getAllObjects().add(
				new Wall(new Rectangle(1480, 1300, 240, 40)));
		location.getAllObjects().add(
				new Wall(new Rectangle(1720, 1260, 80, 40)));
		location.getAllObjects().add(
				new Wall(new Rectangle(1800, 1200, 400, 100)));
		location.getAllObjects().add(
				new Wall(new Rectangle(2200, 1300, 160, 40)));

		// Adds the trees and rocks
		location.getAllObjects().add(
				new Wall(fatTreeRect(150, 400), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(40, 320), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(260, 270), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(40, 720), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(100, 613), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(300, 512), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(600, 290), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(240, 712), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(621, 689), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(557, 615), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(631, 526), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(840, 712), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(743, 625), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(689, 1172), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(923, 593), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(979, 590), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(956, 646), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1179, 720), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1126, 626), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1298, 709), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1356, 586), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1500, 605), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1590, 713), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1298, 1250), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1356, 1160), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1500, 1250), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1590, 1200), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2000, 40), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1950, 90), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(tallRockRect(1900, 130), "rock4", tallRockOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(1925, 160), "rock1", fatRockOffset));
		location.getAllObjects().add(
				new Wall(tallRockRect(1895, 190), "rock3", tallRockOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(1850, 216), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1813, 260), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1803, 302), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1786, 360), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1812, 407), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(tallRockRect(1802, 459), "rock4", tallRockOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(1830, 503), "rock1", fatRockOffset));
		location.getAllObjects().add(
				new Wall(tallRockRect(1870, 567), "rock3", tallRockOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(1845, 531), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1930, 545), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1965, 600), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(2013, 603), "rock1", fatRockOffset));
		location.getAllObjects().add(
				new Wall(tallRockRect(2073, 653), "rock3", tallRockOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(2116, 712), "tree4", tallTreeOffset));

		location.getAllObjects().add(
				new Wall(fatRockRect(1730, 531), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1689, 545), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2234, 723), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2226, 680), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2230, 625), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallRockRect(2193, 598), "rock3", tallRockOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(2168, 564), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(2126, 522), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(2092, 491), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2070, 460), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(tallRockRect(2036, 440), "rock4", tallRockOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(2011, 433), "rock1", fatRockOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1961, 435), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1961, 379), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(1962, 329), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2006, 357), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(tallRockRect(2031, 319), "rock4", tallRockOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2069, 289), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(tallRockRect(2080, 243), "rock3", tallRockOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2076, 214), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2110, 168), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(tallRockRect(2147, 128), "rock4", tallRockOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(2189, 103), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(2203, 68), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(81, 221), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(158, 248), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(258, 365), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallRockRect(86, 488), "rock3", tallRockOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(209, 545), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(tallRockRect(103, 783), "rock4", tallRockOffset));
		location.getAllObjects().add(
				new Wall(tallRockRect(668, 318), "rock4", tallRockOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2006, 357), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(103, 783), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(700, 364), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(568, 750), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(568, 750), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(694, 420), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(673, 474), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(585, 209), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(631, 395), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(592, 444), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(563, 361), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(578, 505), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2341, 710), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2344, 652), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2328, 601), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2228, 551), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2205, 524), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2318, 517), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2193, 470), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2158, 413), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2105, 362), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2302, 427), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2180, 397), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2232, 339), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(2347, 355), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2238, 240), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2300, 241), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2340, 235), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2201, 44), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2136, 245), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(2176, 306), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2191, 156), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2266, 149), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2255, 108), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2280, 64), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2381, 139), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2274, 87), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(2366, 18), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(51, 180), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(90, 437), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(300, 208), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(67, 245), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(73, 306), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(89, 467), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(109, 576), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(126, 666), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(135, 740), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(147, 621), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(159, 143), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(171, 207), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(307, 778), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(287, 420), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(289, 602), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(314, 682), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(204, 315), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(722, 780), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(688, 252), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(832, 592), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(917, 752), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1024, 725), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1104, 742), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1232, 677), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1412, 726), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1461, 695), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1522, 745), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1609, 612), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(2013, 725), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(1911, 708), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1646, 55), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1666, 740), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1698, 621), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1684, 143), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(1702, 207), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1713, 458), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1721, 420), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1729, 602), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1738, 682), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1745, 315), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1754, 780), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1769, 252), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1785, 592), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(1791, 752), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1820, 725), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1836, 742), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1841, 677), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1856, 726), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1878, 695), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1888, 745), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1965, 612), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1929, 725), "tree2", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(1987, 708), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(fatRockRect(1647, 442), "rock2", fatRockOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1658, 337), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(tallTreeRect(1660, 259), "tree3", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1838, 166), "tree1", fatTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1777, 111), "tree4", tallTreeOffset));
		location.getAllObjects().add(
				new Wall(fatTreeRect(1836, 88), "tree2", fatTreeOffset));

		return location;
	}

	public static Location getCredits() {
		Location location = new Location("Credit Room");
		location.getAllObjects().add(
				new Decoration("Credits", new Point(45, 130), false, null));
		location.getAllObjects().add(
				new Wall(new Rectangle(270, 560, 600, 180), "Steak Table",
						new Point(0, 20)));
		return location;
	}
}
