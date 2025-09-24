package kth.game.othello.tournament;

import org.junit.Assert;
import org.junit.Test;

public class PlayerResultTest {

	@Test
	public void testCompareTo() {
		{
			PlayerResult pr1 = new PlayerResult("a", 3, 2, 1);
			PlayerResult pr2 = new PlayerResult("b", 3, 2, 1);
			Assert.assertEquals(-1, pr1.compareTo(pr2));
		}
		{
			PlayerResult pr1 = new PlayerResult("a", 3, 2, 1);
			PlayerResult pr2 = new PlayerResult("b", 3, 2, 2);
			Assert.assertEquals(1, pr1.compareTo(pr2));
		}
		{
			PlayerResult pr1 = new PlayerResult("a", 2, 0, 6);
			PlayerResult pr2 = new PlayerResult("b", 3, 5, 0);
			Assert.assertEquals(1, pr1.compareTo(pr2));
		}
	}

	@Test
	public void testIsScoreEqual() {
		{
			PlayerResult pr1 = new PlayerResult("a", 3, 2, 1);
			PlayerResult pr2 = new PlayerResult("b", 3, 2, 1);
			Assert.assertEquals(true, pr1.isScoreEqual(pr2));
		}
		{
			PlayerResult pr1 = new PlayerResult("a", 3, 2, 1);
			PlayerResult pr2 = new PlayerResult("b", 3, 2, 2);
			Assert.assertEquals(false, pr1.isScoreEqual(pr2));
		}
	}
}
