package TestingPackage;

import MazeGame.Player;
import junit.framework.TestCase;

public class PlayerTestCase extends TestCase {

	Player test;
	public void setUp(){
		test = Player.getInstance();
	}
	
	public void testSetStartLocation() {
		int x = 1; int y = 1;
		test.setStartLocation(x, y);
		assertEquals(1, test.getTileX());
		assertEquals(1, test.getTileY());
	}

	public void testGetPoints() {
		assertEquals(1, test.getPoints());
	}

	public void testGetKeys() {
		assertEquals(1, test.getKeys());
	}

	public void testGetHealth() {
		assertEquals(3, test.getHealth());
	}

	public void testGetTileX() {
		int x = 1; int y = 1;
		test.setStartLocation(x, y);
		assertEquals(1, test.getTileX());
	}

	public void testGetTileY() {
		int x = 1; int y = 1;
		test.setStartLocation(x, y);
		assertEquals(1, test.getTileY());
	}
	
	public void testDecreaseHealth(){
		test.decreaseHealth();
		assertEquals(2, test.getHealth());
		test.increaseHealth();
	}
	
	public void testIncreaseHealth(){
		test.decreaseHealth();
		test.increaseHealth();
		assertEquals(3, test.getHealth());
	}
	
	public void testAddKey(){
		test.addKey();
		assertEquals(2, test.getKeys());
		test.removeKey();
	}
	
	public void testRemoveKey(){
		test.removeKey();
		assertEquals(0, test.getKeys());
	}
	
	public void testAddPoints(){
		test.addPoints(1);
		assertEquals(1, test.getPoints());
		
	}
}
