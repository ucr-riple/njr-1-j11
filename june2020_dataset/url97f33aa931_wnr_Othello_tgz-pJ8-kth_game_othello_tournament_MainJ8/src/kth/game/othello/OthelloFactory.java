package kth.game.othello;

import java.util.List;
import java.util.Set;

import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.Player;

/**
 * A factory for producing othello games.
 *
 * @author Tomas Ekholm
 */
public interface OthelloFactory {

	/**
	 * Creates an Othello game with two computer.
	 *
	 * @return An Othello game
	 */
	public Othello createComputerGame();

	/**
	 * Creates an Othello game with two humans.
	 *
	 * @return An Othello game
	 */
	public Othello createHumanGame();

	/**
	 * Creates an Othello game with one computer playing against one human.
	 *
	 * @return An Othello game
	 */
	public Othello createHumanVersusComputerGame();

	/**
	 * Creates an Othello game with the given players on a board that contains the given nodes
	 *
	 * @param nodesData the nodes of the board
	 * @param players the players
	 * @return An Othello game
	 */
	public Othello createGame(Set<NodeData> nodesData, List<Player> players);

}
