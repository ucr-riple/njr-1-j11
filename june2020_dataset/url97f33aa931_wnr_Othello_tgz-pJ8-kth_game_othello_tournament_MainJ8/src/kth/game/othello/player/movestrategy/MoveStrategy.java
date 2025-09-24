package kth.game.othello.player.movestrategy;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * The responsibility of this entity is to define a move strategy for a player
 *
 * @author Tomas Ekholm
 */
public interface MoveStrategy {

	/**
	 * @return the name of the strategy
	 */
	public String getName();

	/**
	 * Determines which node the given player will move at.
	 * 
	 * @param playerId the id of the player
	 * @param rules the rules of the game
	 * @param board the board
	 * @return the node where the player wants to move. If the player is not able to move then null is returned.
	 */
	public Node move(String playerId, Rules rules, Board board);

}
