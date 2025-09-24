package Client_Server;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import Client_Server.Packets.AddRelicObject;
import Client_Server.Packets.InteractDoor;
import Client_Server.Packets.*;
import Game_World.Avatar;
import Game_World.ExternalDoor;
import Game_World.InternalDoor;
import Game_World.Location;
import Game_World.Wall;
import Game_World_Objects.Chest;
import Game_World_Objects.Food;
import Game_World_Objects.Key;
import Game_World_Objects.Relic;
import Game_World_Objects.Torch;
import Graphics_Renderer.Tileset;
import Object_Interfaces.GameObject;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

/**
 * This class is used to register classes that will be
 * sent over the network with both the server and client.
 *
 * This must be done before any further communication occurs,
 * AND MUST BE DONE IN THE SAME ORDER ON BOTH CLIENT AND SERVER!
 *
 * @author Josh Brake, 300274198, brakejosh
 */
public class Register {

	/**
	 * Attempts to register the list of objects with the host
	 * (Client or Server).
	 *
	 * @param host Either a client or server being registered with
	 */
	public static void register(Object host) {
		if (host instanceof Server) {
			Kryo kyro = ((Server) host).getKryo();
			kyro.register(GameObject.class);
			kyro.register(NewPlayer.class);
			kyro.register(NewGameWorld.class);
			kyro.register(byte[].class);
			kyro.register(Location.class);
			kyro.register(ChatMessage.class);
			kyro.register(Move.class);
			kyro.register(Click.class);
			kyro.register(HashMap.class);
			kyro.register(Avatar.class);
			kyro.register(java.awt.Rectangle.class);
			kyro.register(Tileset.class);
			kyro.register(ArrayList.class);
			kyro.register(Wall.class);
			kyro.register(Chest.class);
			kyro.register(AddRelicObject.class);
			kyro.register(MoveAllObject.class);
			kyro.register(EatObject.class);
			kyro.register(PickupObject.class);
			kyro.register(DropObject.class);
			kyro.register(Food.class);
			kyro.register(Key.class);
			kyro.register(Relic.class);
			kyro.register(Torch.class);
			kyro.register(ExternalDoor.class);
			kyro.register(MoveThroughDoor.class);
			kyro.register(Point.class);
			kyro.register(InteractDoor.class);
			kyro.register(InternalDoor.class);
			kyro.register(int.class);
			kyro.register(boolean.class);
			kyro.register(WinningWorld.class);
		} else if (host instanceof Client) {
			Kryo kyro = ((Client) host).getKryo();
			kyro.register(GameObject.class);
			kyro.register(NewPlayer.class);
			kyro.register(NewGameWorld.class);
			kyro.register(byte[].class);
			kyro.register(Location.class);
			kyro.register(ChatMessage.class);
			kyro.register(Move.class);
			kyro.register(Click.class);
			kyro.register(HashMap.class);
			kyro.register(Avatar.class);
			kyro.register(java.awt.Rectangle.class);
			kyro.register(Tileset.class);
			kyro.register(ArrayList.class);
			kyro.register(Wall.class);
			kyro.register(Chest.class);
			kyro.register(AddRelicObject.class);
			kyro.register(MoveAllObject.class);
			kyro.register(EatObject.class);
			kyro.register(PickupObject.class);
			kyro.register(DropObject.class);
			kyro.register(Food.class);
			kyro.register(Key.class);
			kyro.register(Relic.class);
			kyro.register(Torch.class);
			kyro.register(ExternalDoor.class);
			kyro.register(MoveThroughDoor.class);
			kyro.register(Point.class);
			kyro.register(InteractDoor.class);
			kyro.register(InternalDoor.class);
			kyro.register(int.class);
			kyro.register(boolean.class);
			kyro.register(WinningWorld.class);
		}
	}
}
