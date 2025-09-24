package kth.game.othello.board.factory;

import java.util.List;
import java.util.Set;

import kth.game.othello.player.Player;

public class DoubleSquare {

	private Square square;

	public DoubleSquare() {
		square = new Square();
	}

	public DoubleSquare(Square square) {
		this.square = square;
	}

	/**
	 * This board is a double traditional board for two players.
	 * 
	 * @param players the list players of players, that must be two
	 * @param size the size of the board, must be even
	 * @return the nodes of the board
	 */
	public Set<NodeData> getNodes(int size, List<Player> players) {

		Set<NodeData> nodes = square.getNodes(size, players, 0, 0);
		nodes.addAll(square.getNodes(size, players, size + 1, 0));

		return nodes;
	}

}