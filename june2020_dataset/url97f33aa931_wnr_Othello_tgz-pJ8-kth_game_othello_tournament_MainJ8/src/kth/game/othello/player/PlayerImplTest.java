package kth.game.othello.player;

import kth.game.othello.player.movestrategy.MoveStrategy;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PlayerImplTest {
	class PlayerImplTester extends PlayerImpl {
		public PlayerImplTester(String id, String name, Type type) {
			super(id, name, type);
		}
	}

	@Test
	public void ConstructorTest() {
		{
			PlayerImpl player = new PlayerImplTester("", "", Player.Type.COMPUTER);
		}
		{
			PlayerImpl player = new PlayerImplTester("", "", Player.Type.COMPUTER);
		}
	}

	@Test
	public void getIdTest() {
		PlayerImpl player = new PlayerImplTester("id", null, null);
		Assert.assertEquals("id", player.getId());
	}

	@Test
	public void getNameTest() {
		PlayerImpl player = new PlayerImplTester(null, "john doe", null);
		Assert.assertEquals("john doe", player.getName());
	}

	@Test
	public void getType() {
		{
			PlayerImpl player = new PlayerImplTester(null, null, Player.Type.COMPUTER);
			Assert.assertEquals(Player.Type.COMPUTER, player.getType());
		}
		{
			PlayerImpl player = new PlayerImplTester(null, null, Player.Type.HUMAN);
			Assert.assertEquals(Player.Type.HUMAN, player.getType());
		}
	}

	@Test
	public void moveStrategyTest() {
		PlayerImpl player = new PlayerImplTester(null, null, Player.Type.COMPUTER);
		MoveStrategy mockedMoveStrategy = Mockito.mock(MoveStrategy.class);

		Assert.assertEquals(null, player.getMoveStrategy());
		player.setMoveStrategy(mockedMoveStrategy);
		Assert.assertEquals(mockedMoveStrategy, player.getMoveStrategy());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void getMoveStrategyHumanTest() {
		PlayerImpl player = new PlayerImplTester(null, null, Player.Type.HUMAN);
		player.getMoveStrategy();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void setMoveStrategyHumanTest() {
		PlayerImpl player = new PlayerImplTester(null, null, Player.Type.HUMAN);
		player.setMoveStrategy(null);
	}
}