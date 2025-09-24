package TestingPackage;

import MazeRoomLogic.WallRoomEnterBehavior;
import junit.framework.TestCase;

public class WallRoomEnterBehaviorTestCase extends TestCase {
	
	WallRoomEnterBehavior a = new WallRoomEnterBehavior();

	public void testEnter() {
		assertFalse(a.enter());
	}

}
