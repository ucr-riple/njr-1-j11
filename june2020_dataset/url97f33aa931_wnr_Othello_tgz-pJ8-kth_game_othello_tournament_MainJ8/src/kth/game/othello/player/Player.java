package kth.game.othello.player;

import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * A representation of player.
 *
 * @author Tomas Ekholm
 */
public interface Player {

	/**
	 * The different type of {@link Player}s
	 *
	 * @author Tomas Ekholm
	 */
	public enum Type {
		COMPUTER, HUMAN
	}

	/**
	 * The id is a unique identifier of this player in the context of all players
	 *
	 * @return the id
	 */
	public String getId();

	/**
	 * The current move strategy of the player
	 *
	 * @return the move strategy
	 * @throws UnsupportedOperationException if the player is of {@link Type} HUMAN
	 */
	public MoveStrategy getMoveStrategy();

	/**
	 * The name of the player
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * The {@link Type} of the player
	 *
	 * @return the type
	 */
	public Type getType();

	/**
	 * Sets a new move strategy on the player. The player must be of {@link Type} COMPUTER
	 *
	 * @param moveStrategy
	 * @throws UnsupportedOperationException if the player is of {@link Type} HUMAN
	 */
	public void setMoveStrategy(MoveStrategy moveStrategy);

}
