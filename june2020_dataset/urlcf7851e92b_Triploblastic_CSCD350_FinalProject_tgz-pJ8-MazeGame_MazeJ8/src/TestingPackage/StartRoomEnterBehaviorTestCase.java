package TestingPackage;

import MazeRoomLogic.StartRoomEnterBehavior;
import junit.framework.TestCase;

public class StartRoomEnterBehaviorTestCase extends TestCase {

	StartRoomEnterBehavior a = new StartRoomEnterBehavior();
	
	public void testEnter() {
		assertTrue(a.enter());
	}

}
