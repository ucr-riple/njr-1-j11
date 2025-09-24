package TestingPackage;

import MazeGame.Player;
import MazeRoomLogic.DoorRoomEnterBehavior;
import junit.framework.TestCase;

public class DoorRoomEnterBehaviorTestCase extends TestCase {

	Player test;
	public void setUp(){
		test = Player.getInstance();
	}
	
	DoorRoomEnterBehavior a = new DoorRoomEnterBehavior();
	
	public void testEnter() {
		while(test.getKeys() != 0){
			test.removeKey();
		}
		
		assertFalse(a.enter());
	}

}