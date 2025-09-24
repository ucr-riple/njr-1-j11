package kth.game.othello.board.factory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kth.game.othello.player.Player;

public class Castle {

	private Square square;

	public Castle() {
		square = new Square();
	}

	public Castle(Square square) {
		this.square = square;
	}

	/**
	 * This board has a shape of a castle and can be played by two players.
	 * 
	 * @param players The list of players, that must be three
	 * @return the nodes of the board
	 */
	public Set<NodeData> getNodes(List<Player> players) {
		if (players.size() != 2) {
			throw new IllegalArgumentException("The number of players must be two.");
		}

		Set<NodeData> nodes = square.getNodes(6, players, 1, 1);

		nodes.addAll(getCornerNodes(3, 0, 0));
		nodes.addAll(getCornerNodes(3, 5, 0));
		nodes.addAll(getCornerNodes(3, 0, 5));
		nodes.addAll(getCornerNodes(3, 5, 5));

		return nodes;
	}

	private Set<NodeData> getCornerNodes(int size, int startX, int startY) {
		Set<NodeData> nodes = new HashSet<NodeData>();
		for (int x = startX; x < size; x++) {
			for (int y = startY; y < size; y++) {
				nodes.add(new NodeData(x, y));
			}
		}
		return nodes;
	}

}
