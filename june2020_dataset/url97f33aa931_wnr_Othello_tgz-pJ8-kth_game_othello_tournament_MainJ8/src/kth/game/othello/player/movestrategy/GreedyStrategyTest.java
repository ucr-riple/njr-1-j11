package kth.game.othello.player.movestrategy;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.BoardHandler;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;

import kth.game.othello.rules.Rules;
import org.junit.Assert;
import org.junit.Test;

public class GreedyStrategyTest {
	@Test
	public void moveMultipleEquallyGoodTest() {
		// Test initial game board (4 equally good moves)
		{
			List<Node> validMoves = new LinkedList<>();
			validMoves.add(new NodeImpl(2, 3));
			validMoves.add(new NodeImpl(3, 2));
			validMoves.add(new NodeImpl(4, 5));
			validMoves.add(new NodeImpl(5, 4));

			Rules mockedRules = mock(Rules.class);
			when(mockedRules.getNumNodesToSwap(anyString(), eq("2:3"))).thenReturn(1);
			when(mockedRules.getNumNodesToSwap(anyString(), eq("3:2"))).thenReturn(1);
			when(mockedRules.getNumNodesToSwap(anyString(), eq("4:5"))).thenReturn(1);
			when(mockedRules.getNumNodesToSwap(anyString(), eq("5:4"))).thenReturn(1);
			when(mockedRules.getValidMoves(anyString())).thenReturn(validMoves);

			GreedyStrategy greedyStrategy = new GreedyStrategy();

			Node move = greedyStrategy.move("player1", mockedRules, null);

			boolean found = false;
			for (Node n : validMoves) {
				if (n.equals(move)) {
					found = true;
					break;
				}
			}

			Assert.assertTrue(found);
		}
	}

	@Test
	public void moveOneBestTest() {
		List<Node> validMoves = new LinkedList<>();
		validMoves.add(new NodeImpl(1, 3));
		validMoves.add(new NodeImpl(2, 3));
		validMoves.add(new NodeImpl(4, 5));
		validMoves.add(new NodeImpl(5, 4));

		Rules mockedRules = mock(Rules.class);
		when(mockedRules.getNumNodesToSwap(anyString(), eq("1:3"))).thenReturn(2);
		when(mockedRules.getNumNodesToSwap(anyString(), eq("3:2"))).thenReturn(1);
		when(mockedRules.getNumNodesToSwap(anyString(), eq("4:5"))).thenReturn(1);
		when(mockedRules.getNumNodesToSwap(anyString(), eq("5:4"))).thenReturn(1);
		when(mockedRules.getValidMoves(anyString())).thenReturn(validMoves);

		GreedyStrategy greedyStrategy = new GreedyStrategy();

		Node move = greedyStrategy.move("player1", mockedRules, null);

		Assert.assertEquals("1:3", move.getId());
	}

	@Test
	public void moveNotPossibleTest() {
		Rules mockedRules = mock(Rules.class);
		when(mockedRules.getValidMoves(anyString())).thenReturn(new ArrayList<>());

		MoveStrategy greedyStrategy = new GreedyStrategy();

		Node move = greedyStrategy.move("player1", mockedRules, null);
		Assert.assertEquals(null, move);
	}
}
