package TestingPackage;

import MazeGame.Player;
import junit.framework.TestCase;

public class UseKeyEffectTestCase extends TestCase {

	Player test;
	public void setUp(){
		test = Player.getInstance();
	}
	
	public void testApplyEffect() {
		test.removeKey();
		assertEquals(0, test.getKeys());
	}

}