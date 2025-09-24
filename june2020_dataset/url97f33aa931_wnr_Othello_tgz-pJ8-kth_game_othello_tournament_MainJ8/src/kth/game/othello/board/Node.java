package kth.game.othello.board;

import java.util.Observer;

/**
 * The responsibility of a node is to keep information of which player is occupying it. It must inform all observers
 * when it is occupied by another player. In this case the update method will be called with this node and the previous
 * occupying player id or null if the node did not have any previous occupying player.
 *
 * @author Tomas Ekholm
 */
public interface Node {

	/**
	 * Adds an observer to this node.
	 *
	 * @param observer an observer of this node
	 */
	public void addObserver(Observer observer);

	/**
	 * The unique identifier of a node
	 *
	 * @return the id
	 */
	public String getId();

	/**
	 * To get the player id of the occupant player
	 *
	 * @return the id of the occupant player or null if the node is not marked
	 */
	public String getOccupantPlayerId();

	/**
	 * The x-coordinate of this node
	 *
	 * @return the x-coordinate
	 */
	public int getXCoordinate();

	/**
	 * The y-coordinate of this node
	 *
	 * @return the y-coordinate
	 */
	public int getYCoordinate();

	/**
	 * Determines of the node is occupied by any player
	 *
	 * @return true if the node is occupied by any player
	 */
	public boolean isMarked();

}
