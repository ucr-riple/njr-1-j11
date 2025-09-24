package TestingPackage;

import MazeGame.Player;
import junit.framework.TestCase;

public class DecreaseHealthEffectTestCase extends TestCase {

	Player test;
	public void setUp(){
		test = Player.getInstance();
	}
	
	public void testApplyEffect() {
		int one = test.getHealth() - 1;
		test.decreaseHealth();
		assertEquals(one, test.getHealth());
	}

}