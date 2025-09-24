package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.*;
import kth.game.othello.rules.Rules;

/**
 * A really move strategy that will make the move by random. It will simply choose a random node among the possible
 * nodes to move to.
 *
 * @author Mathias Lindblom
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class RandomStrategy implements MoveStrategy {
	/**
	 * Creates the random move strategy instance.
	 */
	public RandomStrategy() {

	}

	@Override
	public String getName() {
		return "Random";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		List<Node> validMoves = rules.getValidMoves(playerId);

		if (validMoves.isEmpty()) {
			return null;
		}

		return validMoves.get(new java.util.Random().nextInt(validMoves.size()));
	}
}