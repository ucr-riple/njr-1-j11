package kth.game.othello.board;

import kth.game.othello.rules.Rules;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A handler implementing common actions on an othello board. The board handler is responsible of mutating the board.
 *
 * @author Mathias Lindblom
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class BoardHandler implements Rules {
	private final BoardImpl board;
	private final BoardHistoryHandler boardHistoryHandler;

	/**
	 * Constructs a Othello board handler.
	 *
	 * @param board The board to handle.
	 * @param boardHistoryHandler The board history instance that will handle undoing moves.
	 */
	public BoardHandler(BoardImpl board, BoardHistoryHandler boardHistoryHandler) {
		this.board = board;
		this.boardHistoryHandler = boardHistoryHandler;
	}

	/**
	 * Gets the board that the handler is operating on.
	 *
	 * @return The board that the handler is operating on.
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Makes a move in the Othello board by occupying the given node id with the player given the player id. Will occupy
	 * the given node and swap all possible nodes. If the move is invalid, an exception is thrown.
	 * 
	 * @param playerId The player id to occupy the given node.
	 * @param nodeId The node id to be occupied by the given player.
	 * @return A list of swapped nodes as a result of the move (including the given placed node).
	 * @throws IllegalArgumentException if the move is not valid.
	 */
	public List<Node> move(String playerId, String nodeId) {
		Node node = board.getNode(nodeId);
		List<Node> swaps = getNodesToSwap(playerId, nodeId);

		if (swaps.isEmpty()) {
			throw new IllegalArgumentException("The move is invalid.");
		}

		// The first node should be the one we moved to.
		swaps.add(0, node);

		boardHistoryHandler.save(swaps);

		for (Node n : swaps) {
			board.occupyNode(n.getId(), playerId);
		}

		return swaps;
	}

	/**
	 * Will undo the previously added move. Will set the occupant player id's of the previously changed nodes to the
	 * player id's before the move was performed. The observers of the nodes swapped will be notified as usual. If there
	 * are no moves to undo, nothing will happen.
	 */
	public void undo() {
		boardHistoryHandler.undo();
	}

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		List<Node> swaps = new LinkedList<>();

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				swaps.addAll(getSwapsDirection(playerId, board.getNode(nodeId), i, j));
			}
		}
		return new LinkedList<>(swaps);
	}

	@Override
	public int getNumNodesToSwap(String playerId, String nodeId) {
		return getNodesToSwap(playerId, nodeId).size();
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		Node node = board.getNode(nodeId);
		return !node.isMarked() && !getNodesToSwap(playerId, node.getId()).isEmpty();
	}

	@Override
	public boolean hasValidMove(String playerId) {
		return !getValidMoves(playerId).isEmpty();
	}

	@Override
	public boolean hasAnyAValidMove(List<String> playerIds) {
		for (String playerId : playerIds) {
			if (hasValidMove(playerId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Node> getValidMoves(String playerId) {
		return board.getNodes().stream().filter(node -> isMoveValid(playerId, node.getId()))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Computes a list of nodes that will be swapped if the given player will move to the given node. The algorithm will
	 * start from the given node and step in the given x and y direction. If no swaps are possible the method will
	 * return an empty list. Otherwise the list of the swapped nodes (except the start node).
	 *
	 * @param playerId The player id that will occupy the given node.
	 * @param node The node that the player will occupy. This will be the start node.
	 * @param iStep The step the algorithm should take in the x-axis (-1, 0 or 1).
	 * @param jStep The step the algorithm should take in the y-axis (-1, 0, or 1).
	 * @return A list of all nodes swapped in the direction given by iStep and jStep, starting from given node and that
	 *         it is playerId that will occupy the start node.
	 */
	private List<Node> getSwapsDirection(String playerId, Node node, int iStep, int jStep) {
		List<Node> swaps = new LinkedList<>();

		for (int x = node.getXCoordinate() + iStep, y = node.getYCoordinate() + jStep; board.hasNode(x, y); x += iStep, y += jStep) {
			Node n = board.getNode(x, y);
			if (!n.isMarked()) {
				return new LinkedList<>();
			}

			if (n.getOccupantPlayerId().equals(playerId)) {
				return swaps;
			}

			swaps.add(n);
		}

		return new LinkedList<>();
	}
}
