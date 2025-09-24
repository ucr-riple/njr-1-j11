package kth.game.othello.board.factory;

/**
 * A representation of a node. Two nodes are equal if and only if there x- and y-coordinate are equal.
 *
 * @author Tomas Ekholm
 */
public class NodeData {

	private String occupantPlayerId;
	private int x;
	private int y;

	/**
	 * The x- and y-coordinate must be unique in the context of all nodes
	 *
	 * @param x the x-coordinate of the node
	 * @param y the y-coordinate of the node
	 */
	public NodeData(int x, int y) {
		this(x, y, null);
	}

	/**
	 * The x- and y-coordinate must be unique in the context of all nodes
	 *
	 * @param x the x-coordinate of the node
	 * @param y the y-coordinate of the node
	 * @param occupantPlayerId the id of the occupant player
	 */
	public NodeData(int x, int y, String occupantPlayerId) {
		this.x = x;
		this.y = y;
		this.occupantPlayerId = occupantPlayerId;
	}

	/**
	 * To get the player id of the occupant player
	 *
	 * @return the id of the occupant player or null if the node is not marked
	 */
	public String getOccupantPlayerId() {
		return occupantPlayerId;
	}

	/**
	 * The x-coordinate of this node
	 *
	 * @return the x-coordinate
	 */
	public int getXCoordinate() {
		return x;
	}

	/**
	 * The y-coordinate of this node
	 *
	 * @return the y-coordinate
	 */
	public int getYCoordinate() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		NodeData nodeData = (NodeData) o;

		if (x != nodeData.x)
			return false;
		if (y != nodeData.y)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		return result;
	}
}
