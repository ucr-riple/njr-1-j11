package Game_World;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import Game_World_Objects.Food;
import Game_World_Objects.Torch;
import Game_World_Objects.Trap;
import Graphics_Renderer.GraphicsRenderer;
import Graphics_Renderer.Tileset;
import Object_Interfaces.Containable;
import Object_Interfaces.Container;
import Object_Interfaces.GameObject;

public class Avatar implements Container, GameObject {
	/**
	 * The Class represents the Avatar object that represents the Player in the GameWorld.
	 * The Avatar is a Container (Avatar has a backpack) which can hold up to 16 items.
	 * The Avatar can move and interact with the GameWorld.
	 *
	 * @author Danesh Abeyratne
	 *
	 */
	private String name;// the name of the player
	private String playerSprite;
	private Player owner;// the player object for this Avatar
	private List<Containable> posessions;
	private int playerID;
	private Rectangle boundingBox;
	private GameWorld world;
	private int stepState = 1; // 0 = Idle and 1 through 3 = mid step
	private int direction = 0; // 0 = South, 1 = West, 2 = North and 3 = East
	private int health = 100; // the avatar's initial health state
	private Location currentLocation;
	private String locationName;
	private int stepsize = 4;// the number of pixels the avatar moves

	public Avatar(String name, int numPlayers) {
		this.name = name;
		int num = numPlayers % 4 + 1;
		playerSprite = "player" + num; // randomNum;
		boundingBox = new Rectangle(200 + (numPlayers * 100), 200, 37, 9);
		posessions = new ArrayList<Containable>(16);

	}

	public Avatar() {} // default constructor for XML

	/**
	 * Move command dependent on keystroke
	 *
	 * @param int comd
	 */
	public void move(int comd) {
		stepState++;

		// comd will be a keyevent
		Rectangle next = (Rectangle) this.getBoundingBox().clone();
		if (comd == 38) {// up arrow
			direction = 2;
			next.translate(0, -stepsize);
		}
		if (comd == 39) {// right arrow
			direction = 3;
			next.translate(stepsize, 0);
		}
		if (comd == 37) {// left arrow
			direction = 1;
			next.translate(-stepsize, 0);
		}
		if (comd == 40) {// down arrow
			direction = 0;
			next.translate(0, stepsize);
		}
		int damageTaken = 0;
		boolean isBlocked = false;
		Location currentLocation = world.getLocations().get(locationName);
		for (GameObject obj : currentLocation.getAllObjects()) {
			if (obj.isOverlapping(next)) {
				if (obj instanceof Trap) {
					damageTaken += ((Trap) obj).getDamage();
				} else
					isBlocked = true;// exits this method
			}
		}

		health -= damageTaken;
		if (damageTaken != 0)
			//System.out.println(String.format("Damage taken: %d, current health is %d", damageTaken,health));
		if (health <= 0) {
			//System.out.println("Avatar died");
			Location startLocation = world.getLocations().get("Inside Home");
			startLocation.getAllObjects().add(this);
			currentLocation.getAllObjects().remove(this);
			boundingBox = new Rectangle(200, 200, 37, 9);
			this.currentLocation = startLocation;
			locationName = "Inside Home";
			health = 100;
			world.getAvatarLocations().put(this, startLocation);
			return;
		}

		if (!isBlocked)
			boundingBox = next;
	}

	/**
	 * mouse click
	 *
	 * @param comd
	 * @param x
	 * @param y
	 */
//	public void interact(int comd, int x, int y) {
//		switch (comd) {
//		case 1: // left click
//			break;
//		case 2: // middle click
//			break;
//		case 3: // right click
//			break;
//		}
//	}

	/**
	 * Enables an Avatar to replenish health by eating food.
	 * @param Food f
	 */
	public void eatFood(Food f) {
		if (health < 100) {
			int val = (f.getValue() + 6) * 3;
			//System.out.println("Healing: " + val);
			health += val;
			health = Math.min(100, health);
			//System.out.println("Before:" + val);

			GameObject c = null;
			for (GameObject con : this.posessions) {
				if (con instanceof Food
						&& ((Food) con).getValue() == f.getValue()) {
					c = con;
				}
			}
			this.posessions.remove(c);
		}
	}

	/**
	 * Enables an Avatar to drop a gameObject
	 * @param GameObject obj
	 */
	public void dropObject(GameObject obj) {
		Location loc = world.getLocations().get(locationName);
		Rectangle r = obj.getRect();
		r.setLocation((int) (boundingBox.getX() + 10),
				(int) (boundingBox.getY() + 10));
		obj.setRect(r);
		Containable c = null;
		for (Containable con : this.posessions) {
			if (con.getName().equals(obj.getName())) {
				c = con;
			}
		}
		if (c != null) {
			this.posessions.remove(c);
			loc.getAllObjects().add(obj);
		}
	}

	/**
	 * Enables an Avatar to pick up a gameObject
	 * @param GameObject obj
	 */
	public void pickUpObject(GameObject obj) {
		if (obj instanceof Containable && checkIfClose(obj)) {
			Location loc = world.getLocations().get(locationName);
			GameObject obje = null;
			for (GameObject o : loc.getContents()) {
				if (o.equals(obj)
						&& canContain((Containable) obj)) {
					obje = o;

				}

			}
			if (obje != null) {
				loc.getAllObjects().remove(obje);
				this.posessions.add((Containable) obje);
			}
		}
	}

	private boolean checkIfClose(GameObject o) {
		if (Math.abs((o.getRect().getCenterX() - boundingBox.getCenterX())) < 50
				&& Math.abs((o.getRect().getCenterY() - boundingBox
						.getCenterY())) < 50) {
			return true;
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {

		g.drawImage(
				GraphicsRenderer.getTileset(
						playerSprite
								+ ((/* YOUR CONDITION */true) ? ""
										: "-transparent")).getTile(direction,
						(stepState / 3) % 4),
				(int) boundingBox.getCenterX() - 24, boundingBox.y - 37, null);
	}

	@Override
	public int compareTo(GameObject other) {
		return boundingBox.y - other.getRect().y;
	}

	@Override
	public Image toIcon() {
		return GraphicsRenderer.getTileset(playerSprite).getTile(0);
	}

	@Override
	public boolean containsPoint(Point p) {
		return boundingBox.contains(p);
	}

	@Override
	public boolean isOverlapping(Rectangle rect) {
		return false;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Rectangle getRect() {
		return boundingBox;
	}

	@Override
	public boolean canContain(Containable item) {
		if (item instanceof Containable) {
			return posessions.size() < 16;
		}
		return false;
	}

	@Override
	public List<Containable> getItems() {
		return posessions;
	}

	@Override
	public int getInventorySize() {
		return 16;
	}

	public int getPlayerID() {
		return this.playerID;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public List<Containable> getPosessions() {
		return posessions;
	}

	public void setPosessions(List<Containable> posessions) {
		this.posessions = posessions;
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	public int getStepState() {
		return stepState;
	}

	public void setStepState(int stepState) {
		this.stepState = stepState;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public GameWorld getWorld() {
		return world;
	}

	public void setWorld(GameWorld world) {
		this.world = world;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + playerID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Avatar other = (Avatar) obj;
		if (playerID != other.playerID)
			return false;
		return true;
	}

	public boolean hasTorch() {
		for (GameObject obj : posessions)
			if (obj instanceof Torch)
				return true;
		return false;
	}

	public String getPlayerSprite() {
		return playerSprite;
	}

	public void setPlayerSprite(String playerSprite) {
		this.playerSprite = playerSprite;
	}

	public int getStepsize() {
		return stepsize;
	}

	public void setStepsize(int stepsize) {
		this.stepsize = stepsize;
	}

	@Override
	public boolean setRect(Rectangle r) {
		this.boundingBox = r;
		return true;
	}

	@Override
	public String getDescription() {
		return "Avatar";
	}

}
