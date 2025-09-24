package Game_World;

import java.awt.Point;
import java.awt.Rectangle;
import java.beans.IntrospectionException;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashSet;

import Data_Storage.XMLReader;
import Data_Storage.XMLWriter;
import Game_World_Objects.Chest;
import Game_World_Objects.Key;
import Junit_Tests.Class_Examples;
import Object_Interfaces.GameObject;

public class GameEngine implements Serializable {
	/**
	 * The GameWorld loading engine.
	 * This will act as the interface to the whole Game_World package, loading a GameWorld from
	 * an XML file or using a constructor.
	 * @author Danesh Abeyratne
	 *
	 */

	private String xmlFile;
	private int level = 1;
	private GameWorld world;
	private HashSet<Player> players;

	public GameEngine(){

		if(level == 1){
			//in = (FileInputStream) this.getClass().getClassLoader().getResourceAsStream("/GameWorld_Resources/level1test.xml");
			xmlFile = "levelOne.xml"; //"src/GameWorld_Resources/levelOne.xml";
			//xmlFile = "src/GameWorld_Resources/level1test.xml";
		}
		else if(level == 2){
			xmlFile = "level2.xml";
		}

		XMLReader reader;
		try {
			reader = new XMLReader(xmlFile);
			world = reader.readGameWorld();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		world.setEngine(this);
		players = new HashSet<Player>();

	}

	public GameEngine(String filePath){
		XMLReader reader;
		try {
			reader = new XMLReader(filePath);
			world = reader.readGameWorld();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		world.setEngine(this);
		players = new HashSet<Player>();
	}

	public GameEngine(int i){

		world = GameWorldLevelBuilder.getLevelOne();
		world.setEngine(this);
		players = new HashSet<Player>();
		Location l = BaseLocations.getInsideHome();

	}

	public void saveGame(String filePath) throws FileNotFoundException, IntrospectionException{

		if(filePath == null){
			XMLWriter writer = new XMLWriter("savedGameWorld");
			writer.write(world);
			writer.close();
		}
		else{
			XMLWriter writer = new XMLWriter(filePath);
			writer.write(world);
			writer.close();
		}
	}

	public void disconnectPlayer (int uid) {
		players.remove(uid - 1);
	}

	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public GameWorld getWorld() {
		return world;
	}

	public void setWorld(GameWorld world) {
		this.world = world;
	}

	public HashSet<Player> getPlayers() {
		return players;
	}

	public void setPlayers(HashSet<Player> players) {
		this.players = players;
	}




}
