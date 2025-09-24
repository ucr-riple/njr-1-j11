package Client_Server;

import Game_World.ExternalDoor;
import Game_World.InternalDoor;
import Object_Interfaces.GameObject;

/**
 * These classes represent the different types of packets that can
 * be sent across the network. Each packet represents a type of
 * data being transferred from Client <--> Server.
 *
 * @author Josh Brake, 300274198, brakejosh
 *
 */
public class Packets {

	/**
	 * A chat message, containing the name of the player and
	 * their message. Sent Client -> Server
	 */
	public static class ChatMessage {
		String name, message;
	}

	/**
	 * A move, containing the name of the player and the key code
	 * of the key they pressed. Sent Client -> Server
	 */
	public static class Move {
		String name;
		int key;
	}

	/**
	 * Adds a player's relics from the inventory to the Altar.
	 * Sent Client -> Server
	 */
	public static class AddRelicObject {
		String name;
	}

	/**
	 * A click, containing the name of the player and the mouse
	 * key pressed, and the x and y coordinates of the click.
	 * Sent Client -> Server
	 */
	public static class Click {
		String name;
		int key, x, y;
	}

	/**
	 * A new player joining the game, containing their user name.
	 * Sent Client -> Server
	 */
	public static class NewPlayer {
		String name;
	}

	/**
	 * A new game world update, containing the changes from other users.
	 * Is serialized by GameWorld, sent, and constructed as a new GameWorld
	 * from the data structures inside. Sent Server -> Client
	 */
	public static class NewGameWorld {
		byte[] world;
	}

	/**
	 * An object that a player is attempting to eat. Must be
	 * checked by the master GameWorld that this player can
	 * eat this object, then performs the action. Sent
	 * Client -> Server
	 */
	public static class EatObject {
		String name;
		GameObject object;
	}

	/**
	 * An object that a player is attempting to pick up. Must be
	 * checked by the master GameWorld that this player can
	 * pick up this object, then performs the action. Sent
	 * Client -> Server
	 */
	public static class PickupObject {
		String name;
		GameObject object;
	}

	/**
	 * An object that a player is attempting to drop. Must be
	 * checked by the master GameWorld that this player can
	 * drop this object, then performs the action. Sent
	 * Client -> Server
	 */
	public static class DropObject {
		String name;
		GameObject object;
	}

	/**
	 * An ExternalDoor that a player is trying to move through.
	 * Sends the Door to the master GameWorld so the player can
	 * be moved between Locations. Sent Client > Server
	 */
	public static class MoveThroughDoor {
		String name;
		ExternalDoor door;
	}

	/**
	 * An InternalDoor that a player is either trying to open
	 * or close. Is sent to the master GameWorld with a code
	 * (1 for open, 0 for close) to see if they have the
	 * correct key, then opens it if they have. Sent
	 * Client -> Server
	 */
	public static class InteractDoor {
		String name;
		int action; //1 for open, 0 for close
		InternalDoor door;
	}

	/**
	 * Attempts to move all objects within the object into
	 * the player's inventory. Sent Client -> Server
	 */
	public static class MoveAllObject {
		String name;
		GameObject object;
	}

	/**
	 * A world that has had a winner in it. The only difference
	 * in this packet from normal NewGameWorld is this packet
	 * includes details of the winner (their player ID) and the
	 * won boolean.
	 */
	public static class WinningWorld {
		int player;
		byte[] world;
		boolean won;
	}
}
