package Client_Server;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import Client_Server.Packets.*;
import Game_World.Avatar;
import Game_World.ExternalDoor;
import Game_World.GameWorld;
import Game_World.InternalDoor;
import Game_World.Location;
import Object_Interfaces.GameObject;
import User_Interface.ApplicationWindow;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Represents a client playing the game. The client is responsible
 * for maintaining the connection to the server, sending key presses
 * to the server, and receiving game updates.
 *
 * @author Josh Brake, 300274198, brakejosh
 *
 */
public class AGClient {

	private Client client;
	private String playerName;
	private ApplicationWindow host;
	private GameWorld world;
	private int recCount;

	public AGClient(String addr, int port, String name,
			final ApplicationWindow host) throws IOException {

		this.playerName = name;
		this.host = host;
		this.client = new Client(8196, 20480);

		new Thread(client).start();; //start the client thread
		client.connect(100000, addr, port, port); //bind to addr on TCP and UDP ports, with a buffer size of 100,000
		Register.register(client);

		//print initial ping to screen
		client.updateReturnTripTime();
		host.getInfoText().setText(((Integer) client.getReturnTripTime()).toString() + "ms");

		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				recCount++;
				//every 50 received objects, update the ping
				if (recCount % 50 == 0) {
					client.updateReturnTripTime();
					host.getInfoText().setText(((Integer) connection.getReturnTripTime()).toString() + "ms");
				}
				if (object.getClass() == NewGameWorld.class) {
					NewGameWorld ngw = (NewGameWorld) object;
					world = GameWorld.fromByteArray(ngw.world);
					tellGamePanelRepaint();
				} else if (object.getClass() == ChatMessage.class) {
					ChatMessage cm = (ChatMessage) object;
					host.appendChatArea(cm.name, cm.message);
				} else if (object.getClass() == WinningWorld.class) {
					WinningWorld w = (WinningWorld) object;
					world = GameWorld.fromByteArray(w.world);
					world.setWin(w.won);
					world.setWinner(w.player);
					tellGamePanelRepaint();
				}
			}
		});
		registerPlayer(playerName);
	}

	/**
	 * Register a new player with the GameWorld hosted by the server.
	 *
	 * @param name Player name of the new player
	 */
	private void registerPlayer(String name) {
		NewPlayer np = new NewPlayer();
		np.name = name;
		client.sendTCP(np);
	}

	/**
	 * Sends a KeyEvent from this client to the server.
	 * The KeyEvent is sent as in int and interpreted by the server
	 * based on the int value.
	 *
	 * @param ev the KeyEvent of the user to be sent to the server
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void sendAction(String name, KeyEvent ev) throws IOException, InterruptedException {
		Move m = new Move();
		m.name = name;
		m.key = ev.getKeyCode();
		world.move(name, ev.getKeyCode());
		tellGamePanelRepaint();
		client.sendTCP(m);
	}

	/**
	 * Sends a MouseEvent from this client to the server.
	 * The MouseEvent is sent as in int (which button was clicked)
	 * accompanied by the x and y coordinates of the mouse click.
	 *
	 * @param ev the MouseEvent of the user to be sent to the server
	 * @throws IOException
	 */
	public void sendAction(String name, MouseEvent ev) throws IOException {
		Click c = new Click();
		c.name = name;
		c.key = ev.getButton();
		c.x = ev.getX();
		c.y = ev.getY();
		client.sendTCP(c);
	}

	/**
	 * Sends the given text to all the other chatAreas via there client
	 *
	 * @param text message to be sent
	 * @throws IOException Invalid Text
	 */
	public void sendChat(String name, String text) throws IOException {
		ChatMessage cm = new ChatMessage();
		cm.name = name;
		cm.message = text;
		client.sendTCP(cm);
	}

	/**
	 * Moves this player through an ExternalDoor, changing his location.
	 * Sends the door to the server so the master GameWorld can register
	 * the change in game state and send it out to everyone.
	 *
	 * @param door ExternalDoor to be moved through
	 */
	public void moveThroughDoor(ExternalDoor door) {
		MoveThroughDoor mtd = new MoveThroughDoor();
		mtd.name = playerName;
		mtd.door = door;
		client.sendTCP(mtd);
	}

	/**
	 * Attempts to close the InternalDoor. The player, door and action
	 * are then forwarded to Server to see whether this player has the
	 * right key to close the door.
	 *
	 * @param door InternalDoor trying to be closed.
	 */
	public void closeDoor(InternalDoor door) {
		InteractDoor id = new InteractDoor();
		id.name = playerName;
		id.action = 0; //close
		id.door = door;
		client.sendTCP(id);
	}

	/**
	 * Attempts to open the InternalDoor. The player, door and action
	 * are then forwarded to Server to see whether this player has the
	 * right key to open the door.
	 *
	 * @param door InternalDoor trying to be opened.
	 */
	public void openDoor(InternalDoor door) {
		InteractDoor id = new InteractDoor();
		id.name = playerName;
		id.action = 1; //close
		id.door = door;
		client.sendTCP(id);
	}

	/**
	 * Sends a string to the app window's information box. This
	 * string contains information about data sent/recieved.
	 *
	 * @param info The network information as a string
	 */
	public void sendInfo(String info) {
		this.host.getInfoText().setText(info);
	}

	/**
	 * Asks the Game panel to repaint, after an updated world as been
	 * received.
	 */
	public void tellGamePanelRepaint() {
		host.redraw(world);
	}

	/**
	 * Attempts to pick an object up from the ground. Sends the object
	 * and the player to Server, which then ensures the player is within
	 * range and can pick up the object.
	 *
	 * @param o GameObject trying to be picked up.
	 */
	public void pickupObject(GameObject o) {
		PickupObject pickup = new PickupObject();
		pickup.name = playerName;
		pickup.object = o;
		client.sendTCP(pickup);
	}

	/**
	 * Attempts to drop an object from a player's inventory onto the ground.
	 *
	 * @param object Object to be dropped
	 */
	public void dropObject(GameObject object){
		DropObject drop = new DropObject();
		drop.name = playerName;
		drop.object = object;
		client.sendTCP(drop);
	}

	/**
	 * Attempts to eat a Food item in the player's inventory. The Food
	 * is consumed and heals the player, depending on what type of food
	 * it is.
	 *
	 * @param object Food to be eaten
	 */
	public void eatObject(GameObject o){
		EatObject eat = new EatObject();
		eat.name = playerName;
		eat.object = o;
		client.sendTCP(eat);
	}

	/**
	 * Returns the GameObject at the location of the x and y, or null
	 * if there is no item there
	 *
	 * @param x relative to the top left corner of the render window
	 * @param y relative to the top left corner of the render window
	 * @return A GameObject if there is one, else null
	 */
	public GameObject getGameObject(int x, int y) {
		Avatar a = null;
		for (Avatar av : world.getAvatars()) {
			if (av.getName().equals(playerName)) {
				a = av;
			}
		}

		Location loc = world.getAvatarLocations().get(a);
		for (GameObject o : loc.getAllObjects()) {
			if (o.containsPoint(new Point(x, y))) {
				return o;
			}
		}
		return null;
	}

	/**
	 * Returns the GameObject at the item location in the players inventory,
	 * or null if there is no item there.
	 *
	 * @param Item location in the inventory
	 * @return The GameObject at that position
	 */
	public GameObject getInventoryItem(int itemlocation){
		Avatar a = null;
		for (Avatar av : world.getAvatars()) {
			if (av.getName().equals(playerName)) {
				a = av;
			}
		}
		try {
			return a.getPosessions().get(itemlocation);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Adds all the relics in a players inventory to the altar
	 */
	public void addRelicsToAltar(){
		AddRelicObject addRelic = new AddRelicObject();
		addRelic.name = playerName;
		client.sendTCP(addRelic);
	}

	/**
	 * Moves all the items in the object to the player inventory. The
	 * object must be a container.
	 *
	 * @param o Container to remove items from
	 */
	public void moveAllToInventory(GameObject o){
		MoveAllObject moveAll = new MoveAllObject();
		moveAll.name = playerName;
		moveAll.object = o;
		client.sendTCP(moveAll);
	}

	public GameWorld getWorld() {
		return this.world;
	}
}
