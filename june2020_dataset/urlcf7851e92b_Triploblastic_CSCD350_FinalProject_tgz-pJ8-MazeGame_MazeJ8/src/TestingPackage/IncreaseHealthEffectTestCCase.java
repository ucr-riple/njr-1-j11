package TestingPackage;

import MazeGame.Player;
import junit.framework.TestCase;

public class IncreaseHealthEffectTestCCase extends TestCase {

	Player test;
	public void setUp(){
		test = Player.getInstance();
	}
	
	public void testApplyEffect() {
		int health = test.getHealth() - 1;
		test.decreaseHealth();
		test.decreaseHealth();
		test.increaseHealth();
		assertEquals(health, test.getHealth());
	}

}
