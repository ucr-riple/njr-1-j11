package kth.game.othello.player;

/**
 * The responsibility of the HumanPlayer is to represent a human player. It will be an extension of the Player class.
 */
public class HumanPlayer extends PlayerImpl {

	/**
	 * Constructs a human othello player structure object.
	 *
	 * @param id The id is a unique identifier of this player in the context of all players
	 * @param name The name of the player
	 */
	public HumanPlayer(String id, String name) {
		super(id, name, Type.HUMAN);
	}
}
