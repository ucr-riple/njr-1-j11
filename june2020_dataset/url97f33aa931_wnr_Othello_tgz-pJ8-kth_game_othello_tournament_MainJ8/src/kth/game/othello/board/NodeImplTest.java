package kth.game.othello.board;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Observer;

import org.junit.Assert;
import org.junit.Test;

public class NodeImplTest {

	private static final String PLAYER_1 = "player1";
	private static final String PLAYER_2 = "player2";

	@Test
	public void constructorTest() {
		{
			NodeImpl node = new NodeImpl(PLAYER_1, 3, 5);
			Assert.assertEquals(PLAYER_1, node.getOccupantPlayerId());
			Assert.assertEquals(3, node.getXCoordinate());
			Assert.assertEquals(5, node.getYCoordinate());
			Assert.assertTrue(node.isMarked());
		}
		{
			NodeImpl node = new NodeImpl(5, 3);
			Assert.assertEquals(null, node.getOccupantPlayerId());
			Assert.assertEquals(5, node.getXCoordinate());
			Assert.assertEquals(3, node.getYCoordinate());
			Assert.assertFalse(node.isMarked());
		}
	}

	@Test
	public void occupyTest() {
		NodeImpl node = new NodeImpl(PLAYER_1, 0, 0);
		node.setOccupantPlayerId(PLAYER_2);
		Assert.assertEquals(PLAYER_2, node.getOccupantPlayerId());
		Assert.assertTrue(node.isMarked());
		node.setOccupantPlayerId(null);
		Assert.assertEquals(null, node.getOccupantPlayerId());
		Assert.assertFalse(node.isMarked());
	}

	@Test
	public void compareToTest() {
		NodeImpl n1 = new NodeImpl(3, 3);
		NodeImpl n2 = new NodeImpl(3, 4);
		NodeImpl n3 = new NodeImpl(0, 10);
		Assert.assertEquals(0, n1.compareTo(n1));
		Assert.assertEquals(-1, n1.compareTo(n2));
		Assert.assertEquals(1, n2.compareTo(n1));
		Assert.assertEquals(1, n1.compareTo(n3));
		Assert.assertEquals(-1, n3.compareTo(n2));
	}

	@Test
	public void nodeIDTest() {
		{
			NodeImpl node = new NodeImpl(2, 7);
			Assert.assertEquals("2:7", node.getId());
		}
	}

	@Test
	public void observableTest() {
		Observer observer = mock(Observer.class);

		NodeImpl node = new NodeImpl(2, 7);
		node.addObserver(observer);
		node.notifyObservers(null);
		node.setOccupantPlayerId("player1");

		verify(observer).update(node, null);

		node.setOccupantPlayerId("player2");
		verify(observer).update(node, "player1");

		node.setOccupantPlayerId(null);
		verify(observer).update(node, "player2");

		node.setOccupantPlayerId(null);
		verify(observer, times(1)).update(node, null); // This method should not have been called (thats why the times
														// is 1 for the previous call)
	}

	@Test
	public void copyWithoutObserversTest() {
		// Without observers
		{
			NodeImpl node = new NodeImpl("player1", 3, 2);
			NodeImpl copy = node.copyWithoutObservers();
			Assert.assertEquals(copy, node);
		}

		// With observers
		{
			Observer observer = mock(Observer.class);
			NodeImpl node = new NodeImpl("player1", 3, 2);
			node.addObserver(observer);
			NodeImpl copy = node.copyWithoutObservers();
			Assert.assertEquals(copy, node);
			Assert.assertEquals(0, copy.countObservers());
		}
	}

}
