package Client_Server;

import java.beans.IntrospectionException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import Client_Server.Packets.*;
import Game_World.GameEngine;
import Object_Interfaces.GameObject;
import User_Interface.ServerWindow;

/**
 * A single server that hosts an Adventure Game.
 * Handles the incoming connections from all clients, passes
 * their commands to the game world, then propagates the updated
 * game world to all client except the originator.
 *
 * @author Josh Brake, 300274198, brakejosh
 */
public class AGServer {

	private int port;
	private GameEngine engine;
	private ServerWindow serverWindow;
	private Server server;
	private Map<Connection, String> players;

	public AGServer(int port, final ServerWindow serverWindow, String gameFile) throws IOException {

		if (port < 1 || port > 65535) throw new NumberFormatException("Port out of range"); //port too high/low
		this.port = port;
		this.players = new HashMap<Connection, String>();
		this.serverWindow = serverWindow;
		if (gameFile == null) { //not loading from a file, make a fresh game
			this.engine = new GameEngine(1);
		} else {
			this.engine = new GameEngine(gameFile);
		}
		this.server = new Server(20480, 20480);

		server.start(); //start server thread
		server.bind(port, port); //bind on both TCP and UDP
		Register.register(server);
		server.addListener(new Listener() {

			@Override
			public void connected(Connection connection) {
				super.connected(connection);
				serverWindow.write("Received connection from " + connection.getRemoteAddressTCP());
			}

			@Override
			public void disconnected(Connection connection) {
				super.disconnected(connection);

				//write the info the the server and player windows
				serverWindow.write(players.get(connection) + " disconnected");
				ChatMessage cm = new ChatMessage();
				cm.name = "SERVER";
				cm.message = players.get(connection)  + " has left the game";
				for (Connection c : server.getConnections()) {
					if (!c.equals(connection)) {
						c.sendTCP(cm);
					}
				}

				//remove the player and their connection
				engine.getWorld().disconnectPlayer(players.get(connection));
				players.remove(connection);

				//send the new world out to remaining players
				NewGameWorld ngw = new NewGameWorld();
				ngw.world = engine.getWorld().toByteArray();
				for (Connection c : server.getConnections()) {
					c.sendTCP(ngw);
				}
			}

			public void received(Connection connection, Object object) {
				if (object instanceof NewPlayer) {
					NewPlayer np = (NewPlayer) object;
					players.put(connection, np.name);
					int uid = engine.getWorld().newPlayer(np.name);

					//write the join info to server and all players chat window
					serverWindow.write("Player " + uid + " (" + np.name + ") has joined the game");
					ChatMessage cm = new ChatMessage();
					cm.name = "SERVER";
					cm.message = "Player " + uid + " (" + np.name + ") has joined the game";
					for (Connection c : server.getConnections()) {
						if (!c.equals(connection)) {
							c.sendTCP(cm);
						}
					}

					//send this client's initial GameWorld
					NewGameWorld ngw = new NewGameWorld();
					ngw.world = engine.getWorld().toByteArray();
					connection.sendTCP(ngw);
				} else if (object instanceof ChatMessage) {
					ChatMessage cm = (ChatMessage) object;
					for (Connection c : server.getConnections()) {
						if (!c.equals(connection)) {
							c.sendTCP(cm);
						}
					}
				} else if (object instanceof Move) {
					Move m = (Move) object;
					engine.getWorld().move(m.name, m.key);
					//send new GameWorld to all clients
					NewGameWorld ngw = new NewGameWorld();
					ngw.world = engine.getWorld().toByteArray();
					for (Connection c : server.getConnections()) {
						if (!c.equals(connection)) {
							c.sendUDP(ngw);
						}
					}
				} else if (object instanceof Click) {
					Click cl = (Click) object;
					//engine.getWorld().interact(cl.name, cl.key, cl.x, cl.y);
					//send new GameWorld to all clients
					NewGameWorld ngw = new NewGameWorld();
					ngw.world = engine.getWorld().toByteArray();
					for (Connection c : server.getConnections()) {
						c.sendTCP(ngw);
					}
				} else if (object instanceof EatObject) {
					EatObject eat = (EatObject) object;
					GameObject go = eat.object;
					String name = eat.name;
					//pick up object
					engine.getWorld().eatFood(name, go);
					//send new GameWorld to all clients
					NewGameWorld ngw = new NewGameWorld();
					ngw.world = engine.getWorld().toByteArray();
					for (Connection c : server.getConnections()) {
						c.sendTCP(ngw);
					}
				} else if (object instanceof MoveAllObject) {
					MoveAllObject moveAll = (MoveAllObject) object;
					GameObject go = moveAll.object;
					String name = moveAll.name;
					//pick up object
					engine.getWorld().moveAll(name, go);
					//send new GameWorld to all clients
					NewGameWorld ngw = new NewGameWorld();
					ngw.world = engine.getWorld().toByteArray();
					for (Connection c : server.getConnections()) {
						c.sendTCP(ngw);
					}
				} else if (object instanceof AddRelicObject) {
					AddRelicObject add = (AddRelicObject) object;
					String name = add.name;
					engine.getWorld().addRelic(name);
					if (engine.getWorld().isWin()) {
						WinningWorld w = new WinningWorld();
						w.player = engine.getWorld().getWinner();
						w.world = engine.getWorld().toByteArray();
						w.won = true;
						for (Connection c : server.getConnections()) {
							c.sendTCP(w);
						}
					} else {
						//send new GameWorld to all clients
						NewGameWorld ngw = new NewGameWorld();

						ngw.world = engine.getWorld().toByteArray();
						for (Connection c : server.getConnections()) {
							c.sendTCP(ngw);
						}
					}
				} else if (object instanceof PickupObject) {
					PickupObject pickup = (PickupObject) object;
					GameObject go = pickup.object;
					String name = pickup.name;
					//pick up object
					engine.getWorld().pickupItem(name, go);
					//send new GameWorld to all clients
					NewGameWorld ngw = new NewGameWorld();
					ngw.world = engine.getWorld().toByteArray();
					for (Connection c : server.getConnections()) {
						c.sendTCP(ngw);
					}
				} else if (object instanceof DropObject) {
					DropObject drop = (DropObject) object;
					GameObject go = drop.object;
					String name = drop.name;
					//pick up object
					engine.getWorld().dropItem(name, go);
					//send new GameWorld to all clients
					NewGameWorld ngw = new NewGameWorld();
					ngw.world = engine.getWorld().toByteArray();
					for (Connection c : server.getConnections()) {
						c.sendTCP(ngw);
					}
				} else if (object instanceof MoveThroughDoor) {
					MoveThroughDoor mtd = (MoveThroughDoor) object;
					engine.getWorld().moveThroughDoor(mtd.name, mtd.door);
					//send new GameWorld to all clients
					NewGameWorld ngw = new NewGameWorld();
					ngw.world = engine.getWorld().toByteArray();
					for (Connection c : server.getConnections()) {
						c.sendTCP(ngw);
					}
				} else if (object instanceof InteractDoor) {
					InteractDoor id = (InteractDoor) object;
					if (id.action == 0) {
						engine.getWorld().closeDoor(id.name, id.door);
					} else {
						engine.getWorld().openDoor(id.name, id.door);
					}
					//send new GameWorld to all clients
					NewGameWorld ngw = new NewGameWorld();
					ngw.world = engine.getWorld().toByteArray();
					for (Connection c : server.getConnections()) {
						c.sendTCP(ngw);
					}
				}
			}
		});
		serverWindow.write("Server listening on TCP/UDP port " + port);
	}

	/**
	 * Saves the current game world to an XML file at the location given.
	 * The game world can be reassembled on a new AGServer, with the same
	 * players rejoining the game from where they quit if they like.
	 *
	 * @param filePath Directory to save the file to
	 * @throws IntrospectionException
	 * @throws FileNotFoundException
	 */
	public void saveGame(String filePath) throws FileNotFoundException, IntrospectionException {
		engine.saveGame(filePath);
	}
}
