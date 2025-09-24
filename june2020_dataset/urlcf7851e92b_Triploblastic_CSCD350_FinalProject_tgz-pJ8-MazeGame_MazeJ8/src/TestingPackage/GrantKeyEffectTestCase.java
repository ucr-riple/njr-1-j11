package TestingPackage;

import MazeGame.Player;
import junit.framework.TestCase;

public class GrantKeyEffectTestCase extends TestCase {

	Player test;
	public void setUp(){
		test = Player.getInstance();
	}
	
	public void testApplyEffect() {
		int k = test.getKeys() + 1;
		test.addKey();
		assertEquals(k, test.getKeys());
	}

}
