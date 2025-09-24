package kth.game.othello.board;

public class NodeIdUtil {

	/**
	 * Constructs a node id by the x and y coordinates.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @return the id of the node given the x and y coordinates
	 */
	public static String createNodeId(int x, int y) {
		return x + ":" + y;
	}

}
