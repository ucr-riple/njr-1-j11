package TestingPackage;

import MazeRoomLogic.TrueRoomEnterBehavior;
import junit.framework.TestCase;

public class TrueRoomEnterBehaviorTestCase extends TestCase {

	TrueRoomEnterBehavior a = new TrueRoomEnterBehavior();
	
	public void testEnter() {
		assertTrue(a.enter());
	}

}
