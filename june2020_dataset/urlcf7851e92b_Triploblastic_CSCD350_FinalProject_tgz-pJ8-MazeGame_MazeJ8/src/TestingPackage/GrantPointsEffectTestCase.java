package TestingPackage;

import MazeGame.Player;
import junit.framework.TestCase;

public class GrantPointsEffectTestCase extends TestCase {

	Player test;
	public void setUp(){
		test = Player.getInstance();
	}
	
	
	public void testApplyEffect() {
		int points = test.getPoints() + 5;
		test.addPoints(5);
		assertEquals(points, test.getPoints());
	}

}
