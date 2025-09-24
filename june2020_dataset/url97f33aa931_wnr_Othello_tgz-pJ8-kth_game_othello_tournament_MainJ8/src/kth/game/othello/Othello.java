package kth.game.othello;

import java.util.List;
import java.util.Observer;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.score.Score;

/**
 * This class represents an Othello game.
 *
 * @author tomasekholm
 */
public interface Othello {

	/**
	 * Adds an observer. The observer will be called when the game has finished.
	 *
	 * @param observer the observer
	 */
	public void addGameFinishedObserver(Observer observer);

	/**
	 * Adds an observer. The observers update will be called when a move has finished including the nodes that have
	 * changed by the move.
	 *
	 * @param observer the observer
	 */
	public void addMoveObserver(Observer observer);

	/**
	 * The board on which the game is played.
	 *
	 * @return the state of the board
	 */
	public Board getBoard();

	/**
	 * @return a unique id in the context of all Othello games.
	 */
	public String getId();

	/**
	 * Returns the nodes that will be swapped for a move at the given nodeId.
	 *
	 * @param playerId the id of the player making the move
	 * @param nodeId the id of the node where the move is made
	 * @return the list of nodes that will be swapped for the given move
	 */
	public List<Node> getNodesToSwap(String playerId, String nodeId);

	/**
	 * Get the player in turn or null if no player can move
	 *
	 * @return the player in turn
	 */
	public Player getPlayerInTurn();

	/**
	 * The players of the game. Computer players as well as human players.
	 *
	 * @return the list of players
	 */
	public List<Player> getPlayers();

	/**
	 * The score of the game
	 *
	 * @return the score
	 */
	public Score getScore();

	/**
	 * Determines if a player has any valid move.
	 *
	 * @param playerId the id of the player
	 * @return true if the player has a valid move
	 */
	public boolean hasValidMove(String playerId);

	/**
	 * Determines if the game is active or over
	 *
	 * @return false if the game is over
	 */
	public boolean isActive();

	/**
	 * Determines if a player is allowed to make a move at the given node.
	 *
	 * @param playerId the id of the player making the move
	 * @param nodeId the node id where the player wants to play
	 * @return true if the move is valid
	 */
	public boolean isMoveValid(String playerId, String nodeId);

	/**
	 * If the player in turn is a computer than this computer makes a move and updates the player in turn. All observers
	 * will be notified with the additional argument being the list of nodes that were swapped.
	 *
	 * @return the nodes that where swapped for this move, including the node where the player made the move
	 * @throws IllegalStateException if there is not a computer in turn
	 */
	public List<Node> move();

	/**
	 * Validates if the move is correct and if the player is in turn. If so, then the move is made which updates the
	 * board and the player in turn. All observers will be notified with the additional argument being the list of nodes
	 * that were swapped.
	 *
	 * @param playerId the id of the player that makes the move
	 * @param nodeId the id of the node where the player wants to move
	 * @return the nodes that where swapped for this move, including the node where the player made the move
	 * @throws IllegalArgumentException if the move is not valid, or if the player is not in turn
	 */
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException;

	/**
	 * Starts the game. The player in turn will be chosen randomly.
	 */
	public void start();

	/**
	 * Starts the game.
	 *
	 * @param playerId the id of the player that will start the game.
	 */
	public void start(String playerId);

	/**
	 * Undo the last move.
	 */
	public void undo();
}
