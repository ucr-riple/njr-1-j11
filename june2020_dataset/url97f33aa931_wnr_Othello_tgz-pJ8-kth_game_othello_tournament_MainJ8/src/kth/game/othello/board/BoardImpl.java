package kth.game.othello.board;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A simple board implementation, allowing players to occupy nodes
 *
 * @author Mathias Lindblom
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class BoardImpl implements Board {
	private final HashMap<String, NodeImpl> nodes;

	/**
	 * Constructs a new BoardImpl containing the specified nodes
	 *
	 * @param nodes The nodes that the board should contain
	 */
	public BoardImpl(Collection<NodeImpl> nodes) {
		this.nodes = new HashMap<>();
		for (NodeImpl node : nodes) {
			this.nodes.put(node.getId(), node);
		}
	}

	@Override
	public int getMaxX() {
		return nodes.values().stream()
				.max((Node n1, Node n2) -> Integer.compare(n1.getXCoordinate(), n2.getXCoordinate())).get()
				.getXCoordinate();
	}

	@Override
	public int getMaxY() {
		return nodes.values().stream()
				.max((Node n1, Node n2) -> Integer.compare(n1.getYCoordinate(), n2.getYCoordinate())).get()
				.getYCoordinate();
	}

	/**
	 * The number of nodes on the board.
	 *
	 * @return the number of nodes
	 */
	public int getNumNodes() {
		return nodes.size();
	}

	@Override
	public Node getNode(int x, int y) {
		return getNode(NodeIdUtil.createNodeId(x, y));
	}

	/**
	 * Gets the node given a node id.
	 *
	 * @param nodeId The id of the node to return.
	 * @return The node that has the given node id.
	 * @throws java.lang.IllegalArgumentException if no node with the id exist in the board
	 */
	public Node getNode(String nodeId) {
		if (!nodes.containsKey(nodeId)) {
			throw new IllegalArgumentException("No node found with id: " + nodeId);
		}
		return nodes.get(nodeId);
	}

	/**
	 * Determines if the board contains a node with the specified id.
	 *
	 * @param nodeId The nodeId to search for
	 * @return True if a node with this id exists
	 */
	public boolean hasNode(String nodeId) {
		return nodes.containsKey(nodeId);
	}

	@Override
	public boolean hasNode(int x, int y) {
		return nodes.containsKey(NodeIdUtil.createNodeId(x, y));
	}

	/**
	 * Make a node on the board occupied by the specified player.
	 *
	 * @param nodeId The node to occupy
	 * @param playerId The occupying player
	 * @throws java.lang.IllegalArgumentException If the node does not exist on the board
	 */
	public void occupyNode(String nodeId, String playerId) {
		if (!nodes.containsKey(nodeId)) {
			throw new IllegalArgumentException("No node found with id: " + nodeId);
		}
		nodes.get(nodeId).setOccupantPlayerId(playerId);
	}

	/**
	 * Returns a new instance of a BoardImpl containing copies of all nodes present in this board. The node copies will
	 * not contain the observers added to the original nodes.
	 *
	 * @return A new BoardImpl instance of all copied nodes without node observers.
	 */
	public BoardImpl copyWithoutObservers() {
		return new BoardImpl(nodes.values().stream().map(NodeImpl::copyWithoutObservers).collect(Collectors.toList()));
	}

	@Override
	public List<Node> getNodes() {
		ArrayList<NodeImpl> res = new ArrayList<>(nodes.values());
		Collections.sort(res);
		return new ArrayList<>(res);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		BoardImpl board = (BoardImpl) o;

		if (nodes != null ? !nodes.equals(board.nodes) : board.nodes != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return nodes != null ? nodes.hashCode() : 0;
	}
}
