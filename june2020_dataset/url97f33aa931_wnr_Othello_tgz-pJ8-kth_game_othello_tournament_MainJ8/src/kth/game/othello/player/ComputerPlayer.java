package kth.game.othello.player;

import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * The responsibility of the ComputerPlayer is to represent a computer player. It will be an extension of the Player
 * class.
 */
public class ComputerPlayer extends PlayerImpl {
	/**
	 * Constructs a computer othello player structure object.
	 *
	 * @param id The id is a unique identifier of this player in the context of all players
	 * @param name The name of the player
	 * @param moveStrategy The {@link MoveStrategy} of the player
	 */
	public ComputerPlayer(String id, String name, MoveStrategy moveStrategy) {
		super(id, name, Player.Type.COMPUTER, moveStrategy);
	}
}
