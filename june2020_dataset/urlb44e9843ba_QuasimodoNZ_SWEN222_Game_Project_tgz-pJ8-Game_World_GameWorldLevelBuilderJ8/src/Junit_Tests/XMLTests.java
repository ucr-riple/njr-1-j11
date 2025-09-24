package Junit_Tests;

import static org.junit.Assert.fail;

import java.beans.IntrospectionException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import Data_Storage.XMLReader;
import Data_Storage.XMLWriter;
import Game_World.Avatar;
import Game_World.GameWorld;
import Game_World.Location;

/**
 * Tests for the XMLWriter and XMLReader classes
 * @author Alix Schultze 300170774 hoschurayn
 */
public class XMLTests {
	
	/**
	 * Writing a GameWorld to XML
	 */
	@Test
	public void testWrite(){
		GameWorld gw = getGameWorld();
		try{
			XMLWriter writer = new XMLWriter("testWrite");
			writer.write(gw);
			writer.close();
		} catch(FileNotFoundException e){
			e.printStackTrace();
			fail("XMLWriter, testWrite.xml not found");
		} catch(IntrospectionException e){
			e.printStackTrace();
			fail("XMLWriter, introspection failure");
		}
	}
	
	/**
	 * Reading a GameWorld back from XML
	 * Assumes 'testWrite.xml' exists
	 */
	@Test
	public void testRead(){
		try{
			XMLReader reader = new XMLReader("testWrite.xml");
			assert (reader.readObject() instanceof GameWorld);
			reader.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
			fail("XMLReader, testWrite.xml not found");
		}
	}
	
	/**
	 * Reading from a file that doesn't exist
	 */
	@Test
	public void testBadRead(){
		try{
			XMLReader reader = new XMLReader("nonExistent");
			reader.close();
			fail("XMLReader should not have been able to read nonexistent file");
		} catch (FileNotFoundException e){
		}
	}
	
	/**
	 * Writes a GameWorld object and rereads it.
	 * The retrieved copy should be equal to the original.
	 */
	@Test
	public void testGameWorld() throws FileNotFoundException, IntrospectionException{
		GameWorld original = getGameWorld();
		
		XMLWriter writer = new XMLWriter("testGameWorld2");
		writer.write(original);
		writer.close();
		
		XMLReader reader = new XMLReader("testGameWorld2.xml");
		GameWorld retrieved = reader.readGameWorld();
		reader.close();
		
		assert(original.equals(retrieved));
	}
	
	/**
	 * Returns a very basic GameWorld for testing
	 * @return
	 */
	private GameWorld getGameWorld(){
		HashMap<String, Location> locations = new HashMap<String, Location>();
		HashMap<Avatar, Location> avatarLocations = new HashMap<Avatar, Location>();
		ArrayList<Avatar> avatars = new ArrayList<Avatar>();
		
		Location testLocation = new Location("Test Location");
		Avatar testAvatar = new Avatar("Slim", 1);
		
		locations.put("testLocation", testLocation);
		avatarLocations.put(testAvatar, testLocation);
		avatars.add(testAvatar);
		
		return new GameWorld(locations, avatarLocations, avatars);
	}

}
