package Game_World;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;

import Game_World_Objects.Chest;
import Game_World_Objects.Food;
import Game_World_Objects.Key;
import Game_World_Objects.Relic;
import Object_Interfaces.Containable;
import Object_Interfaces.GameObject;

public class GameWorld implements Serializable {
	/**
	 * The Class represents the model of the state of the gameworld at any time, this represents the
	 * 'board' on which the game is played. The GameWorld contains all the locations that make up the world,
	 * the avatars driven by players and all the GameWorld objects populating the world.
	 *
	 * @author Danesh Abeyratne
	 *
	 */

	// Fields:
	private GameEngine engine;
	private HashMap<String, Location> locations;// the level is read into this Map
	private HashMap<Avatar, Location> avatarLocations;// Keyset = Avatars, Values = Locations
	private ArrayList<Avatar> avatars;
	private int number;
	private boolean win = false;
	private int winner;

	// Constructors:
	public GameWorld(GameEngine engine) {// default constructor
		this.engine = engine;
		locations = new HashMap<String, Location>();
		avatarLocations = new HashMap<Avatar, Location>();
		avatars = new ArrayList<Avatar>();
	}

	public GameWorld() {
		locations = new HashMap<String, Location>();
		avatarLocations = new HashMap<Avatar, Location>();
		avatars = new ArrayList<Avatar>();
	}

	public GameWorld(HashMap<String, Location> locations,
			HashMap<Avatar, Location> avatarLocations, ArrayList<Avatar> avatars) {
		this.locations = locations;
		this.avatarLocations = avatarLocations;
		this.avatars = avatars;
	}

	/**
	 * Creates a new player in this world, and returns their UID
	 * @param String name
	 * @return uid of Player
	 */
	public int newPlayer(String name) {
		ArrayList<String> quests = new ArrayList<String>();
		Player p = new Player(name, quests);
		p.setNumber(number);
		number++;

		Avatar a = null;
		for(Avatar avatar : avatars){
			if(avatar.getName().equals(name)){
				a = avatar;
				String[] sprite = a.getPlayerSprite().split("-");
				a.setPlayerSprite(sprite[0]);
				a.setStepsize(4);
			}
		}

		if(a == null){
			a = new Avatar(name, this.getAvatars().size());
			a.setPlayerID(p.getNumber());
			a.setLocationName("Inside Home");
			a.setWorld(this);// add avatar to the first level
			locations.get("Inside Home").getAllObjects().add(a);
			p.setCharacter(a);
			avatars.add(a);
			//System.out.println(a);
			avatarLocations.put(a, locations.get("Inside Home"));
		}
		return avatars.size();
	}

	/**
	 * Disconnects a player from the game by removing them from the map of
	 * Avatars.
	 * @param uid
	 * @return boolean
	 */
	public boolean disconnectPlayer(String name) {
		Avatar a = null;
		for (Avatar av : avatars) {
			if (av.getName().equals(name)) {
				a = av;
			}
		}
		if(a != null){
			a.setPlayerSprite(a.getPlayerSprite() + "-transparent");
			a.setStepsize(0);
			return true;
		}
		return false;
	}

	/**
	 * Moves a player (name) in a direction, given by the int passed
	 * @param name Player to move
	 * @param comd Direction to move in
	 */
	public void move(String name, int comd) {
		if (comd == 37 || comd == 38 || comd == 39 || comd == 40) { // comd is a
			// movement,
			// so move
			// the
			// player
			for (Avatar a : avatars) {
				if (a.getName().equals(name)) {
					a.move(comd);
				}
			}
		}
	}

	/**
	 * Adds a Relic GameObject to the Altar
	 * @param String name
	 */
	public void addRelic(String name){
		Avatar a = null;
		String loc = null;
		Location l = null;
		Altar alt = null;
		ArrayList<Relic> shadowPosessions = new ArrayList<Relic>();
		for (Avatar v : avatarLocations.keySet()) {
			if (v.getName().equals(name)) {
				a = v;
				loc = v.getLocationName();
			}
		}
		for(GameObject o : a.getPosessions()){
			if(o instanceof Relic){
				if(shadowPosessions.size() < 4)
					shadowPosessions.add((Relic) o);
			}
		}

		l = locations.get(loc);

		for(GameObject g: l.getAllObjects()){
			if(g.getName().equals("Quest Altar")){
				alt = (Altar) g;
			}
		}

		for(Relic r: shadowPosessions){
			if(r != null){
				int num = alt.addPiller(a.getPlayerID(), r);
				if(num != -1){
					win = true;
					winner = num;
					//System.out.println("GameWorld has registered a winner:" +num);
					winCondition(num);
				}
			}
		}
		for(Relic s: shadowPosessions){
			a.getPosessions().remove(s);
		}

	}
	// this method launches the win condition
	private void winCondition(int num){
		Location creditLoc = locations.get("Credit Room");
		int x = 0;
		for(Avatar a: avatars){
			Location avatarLoc = locations.get(a.getLocationName());
			locations.get("Inside Alter Room").getAllObjects().remove(a);
			creditLoc.getAllObjects().add(a);
			Rectangle r = new Rectangle(200+x,200,37,9);
			x= x + 100;
			a.setBoundingBox(r);
			a.setLocationName("Credit Room");
			avatarLocations.put(a, creditLoc);
		}
	}

	/**
	 * Moves all the Objects from a Chest into a player's avatar's posessions collection
	 * @param String name of Player
	 * @param GameObject Chest
	 */

	public void moveAll(String name, GameObject object) {
		Chest c = null;
		Avatar a = null;
		String loc = null;
		ArrayList<GameObject> shadowChest = new ArrayList<GameObject>();

		for (Avatar v : avatarLocations.keySet()) {
			if (v.getName().equals(name)) {
				a = v;
				loc = v.getLocationName();
			}
		}
		for (GameObject o : locations.get(loc).getAllObjects()) {
			if (o.getName().equals(object.getName())) {
				c = (Chest) o;//
			}
		}
		if(c.getContents().size() > 0){
			for(GameObject ob: c.getContents()){
				if(a.canContain((Containable) ob)){
					a.getPosessions().add((Containable) ob);
					shadowChest.add(ob);
				}
			}
		}
		for(GameObject obj: shadowChest){
			c.getContents().remove(obj);
		}

	}

	/**
	 * Enables an avatar to eat a piece of food.
	 * @param String name of Player
	 * @param GameObject Food
	 */
	public void eatFood(String name, GameObject object) {
		for (Avatar a : avatarLocations.keySet()) {
			if (a.getName().equals(name)) {
				a.eatFood((Food) object);
			}
		}
	}

	/**
	 * Enables an avatar to pick up a GameObject.
	 * @param String name of Player
	 * @param GameObject
	 */
	public void pickupItem(String name, GameObject object) {
		for (Avatar a : avatarLocations.keySet()) {
			if (a.getName().equals(name)) {
				a.pickUpObject(object);
			}
		}
	}

	/**
	 * Enables an avatar to drop a GameObject.
	 * @param String name of Player
	 * @param GameObject
	 */
	public void dropItem(String name, GameObject object) {
		for (Avatar a : avatarLocations.keySet()) {
			if (a.getName().equals(name)) {
				a.dropObject(object);
			}
		}
	}

	/**
	 * Enables an avatar to move from one Location to another in the GameWorld.
	 * @param String name of Player
	 * @param GameObject
	 */
	public synchronized void moveThroughDoor(String name, ExternalDoor door) {
		boolean locked = door.isLocked();
		String endLocationName = door.getEndLocationName();
		Point endLocationPoint = door.getEndLocationPoint();
		int direction = door.getDirection();

		for (Avatar a : avatarLocations.keySet()) {
			if (a.getName().equals(name)) {
				if (!locked && door.avatarIsClose(a)) {// if the door is not locked and the
					// Avatar is about 1cm from the door.
					GameWorld avatarWorld = a.getWorld();
					Location avatarLocation = a.getWorld().getAvatarLocations().get(a);
					avatarWorld.getAvatarLocations().remove(a);
					avatarWorld.getAvatarLocations().put(a,avatarWorld.getLocations().get(endLocationName));
					// move the Avatar from the old location's contents to the new location's contents.
					avatarLocation.getAllObjects().remove(a);
					avatarWorld.getLocations().get(endLocationName).getAllObjects().add(a);
					a.setCurrentLocation(this.getLocations().get(endLocationName));
					a.setLocationName(endLocationName);
					// Reset Avatar's boundingbox to a a point in the new location
					Rectangle r = a.getBoundingBox();
					r.setLocation(endLocationPoint);
					a.setLocationName(endLocationName);
					//System.out.println(a.getLocationName());
					a.setBoundingBox(r);
					a.setDirection(direction);

				}
				return;
			}
		}
	}

	/**
	 * Enables an avatar to close an internal door.
	 * @param String name of Player
	 * @param GameObject
	 */
	public void closeDoor(String name, InternalDoor door) {
		Avatar a = null;
		for(Avatar b: avatars){
			if(b.getName().equals(name)){
				a = b;
			}
		}
		if(a!= null){
			String locName = a.getLocationName();
			String doorName = door.getName();
			Location l = locations.get(locName);
			for(GameObject g: l.getAllObjects()){
				if(g.getName().equals(doorName)){
					InternalDoor d = (InternalDoor) g;
					d.setLocked(true);
				}
			}
		}
	}

	/**
	 * Enables an avatar to open an internal door.
	 * @param String name of Player
	 * @param GameObject
	 */
	public void openDoor(String name, InternalDoor door) {

		Avatar a = null;
		for(Avatar b: avatars){
			if(b.getName().equals(name)){
				a = b;
			}
		}
		if(a!= null){
			String locName = a.getLocationName();
			String doorName = door.getName();
			Location l = locations.get(locName);
			for(GameObject g: l.getAllObjects()){
				if(g.getName().equals(doorName)){
					InternalDoor d = (InternalDoor) g;
					d.setLocked(false);
				}
			}
		}
	}

	/**
	 * Converts this GameWorld to a byte array to send over network
	 *
	 * @return a byteArray
	 */
	public byte[] toByteArray() {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this.locations);
			oos.writeObject(this.avatarLocations);
			oos.writeObject(this.avatars);
			oos.flush();
			oos.close();
			bos.close();
			bytes = bos.toByteArray();
		} catch (IOException e) {
			System.err.println("Error sending the GameWorld to bytes!");
			e.printStackTrace();
		}
		return bytes;
	}

	/**
	 * Creates a new GameWorld (typically an updates version from server) from a
	 * byte array
	 *
	 * @param bytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static GameWorld fromByteArray(byte[] bytes) {
		GameWorld newWorld = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			newWorld = new GameWorld(
					(HashMap<String, Location>) ois.readObject(),
					(HashMap<Avatar, Location>) ois.readObject(),
					(ArrayList<Avatar>) ois.readObject());
		} catch (StreamCorruptedException e) {
			return null;
		} catch (IOException e) {
			System.err.println("Error recieving the GameWorld from bytes!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found in from bytes");
			e.printStackTrace();
		}
		return newWorld;
	}

	public GameEngine getEngine() {
		return engine;
	}

	public void setEngine(GameEngine engine) {
		this.engine = engine;
	}

	public HashMap<String, Location> getLocations() {
		return locations;
	}

	public void setLocations(HashMap<String, Location> rooms) {
		this.locations = rooms;
	}

	public HashMap<Avatar, Location> getAvatarLocations() {
		return avatarLocations;
	}

	public void setAvatarLocations(HashMap<Avatar, Location> avatarLocations) {
		this.avatarLocations = avatarLocations;
	}

	public ArrayList<Avatar> getAvatars() {
		return avatars;
	}

	public void setAvatars(ArrayList<Avatar> avatars) {
		this.avatars = avatars;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}



}
