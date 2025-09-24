package kth.game.othello.rules;

import kth.game.othello.board.Node;

import java.util.List;

/**
 * The responsibility of the Rules is to define when a player can make a move and in that case also determine what nodes
 * to swap at the board.
 * 
 * @author Tomas Ekholm
 * @author Lucas Wiener
 * @author Mathias Lindblom
 * @author Erik Odenman
 */
public interface Rules {

	/**
	 * Returns the nodes that will be swapped for a move at the given nodeId.
	 * 
	 * @param playerId the id of the player making the move
	 * @param nodeId the id of the node where the move is made
	 * @return the list of nodes that will be swapped for the given move
	 */
	public List<Node> getNodesToSwap(String playerId, String nodeId);

	/**
	 * Returns the number of nodes that will be swapped for a move at the given nodeId.
	 *
	 * @param playerId the id of the player making the move
	 * @param nodeId the id of the node where the move is made
	 * @return the number of nodes that will be swapped for the given move
	 */
	public int getNumNodesToSwap(String playerId, String nodeId);

	/**
	 * Determines if a player is allowed to make a move at the given node.
	 * 
	 * @param playerId the id of the player making the move
	 * @param nodeId the node id where the player wants to play
	 * @return true if the move is valid
	 */
	public boolean isMoveValid(String playerId, String nodeId);

	/**
	 * Determines if a player has any valid move.
	 *
	 * @param playerId the id of the player
	 * @return true if the player has a valid move
	 */
	public boolean hasValidMove(String playerId);

	/**
	 * Check to see if there exist at least one valid move for at least one of the given playerIds
	 *
	 * @param playerIds The id's of the players to check if a valid move exists.
	 * @return True if at least one valid move was found for any player
	 */
	public boolean hasAnyAValidMove(List<String> playerIds);

	/**
	 * Returns the valid nodes that can be moved to by the given player id.
	 *
	 * @param playerId The player that should be checked for valid moves
	 * @return All valid nodes the player can move to
	 */
	public List<Node> getValidMoves(String playerId);

}
