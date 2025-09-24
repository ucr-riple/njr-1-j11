package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.*;
import kth.game.othello.rules.Rules;

/**
 * A really bad move strategy that will make the move that result in the least node swaps. If there are multiple
 * candidates one Node will just be picked.
 *
 * @author Mathias Lindblom
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class LowestStrategy implements MoveStrategy {
	/**
	 * Creates the lowest move strategy instance.
	 */
	public LowestStrategy() {

	}

	@Override
	public String getName() {
		return "Lowest";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		List<Node> validMoves = rules.getValidMoves(playerId);

		Node lowestNode = null;
		int lowestSwaps = Integer.MAX_VALUE;

		for (Node n : validMoves) {
			String nodeId = n.getId();
			int numSwaps = rules.getNumNodesToSwap(playerId, nodeId);
			if (numSwaps < lowestSwaps) {
				lowestSwaps = numSwaps;
				lowestNode = n;
			}
		}

		return lowestNode;
	}
}
