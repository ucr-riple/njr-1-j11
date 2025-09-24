package kth.game.othello.board.factory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kth.game.othello.player.Player;

public class Diamond {

	private Set<NodeData> getMarkedNodes(int size, List<Player> players) {
		String player1Id = players.get(0).getId();
		String player2Id = players.get(1).getId();
		String player3Id = players.get(2).getId();

		// middle index for the node coordinates
		int middleIndex = (size - 1) / 2;

		Set<NodeData> nodes = new HashSet<NodeData>();

		nodes.add(new NodeData(middleIndex - 1, middleIndex - 1, player1Id));
		nodes.add(new NodeData(middleIndex - 1, middleIndex, player2Id));
		nodes.add(new NodeData(middleIndex - 1, middleIndex + 1, player3Id));

		nodes.add(new NodeData(middleIndex, middleIndex - 1, player2Id));
		nodes.add(new NodeData(middleIndex, middleIndex, player3Id));
		nodes.add(new NodeData(middleIndex, middleIndex + 1, player1Id));

		nodes.add(new NodeData(middleIndex + 1, middleIndex - 1, player3Id));
		nodes.add(new NodeData(middleIndex + 1, middleIndex, player1Id));
		nodes.add(new NodeData(middleIndex + 1, middleIndex + 1, player2Id));

		return nodes;
	}

	/**
	 * This board has a shape of a diamond and can be played by three players.
	 * 
	 * @param players The list of players, that must be three
	 * @param size an odd number being the size of the board
	 * @return the nodes of the board
	 */
	public Set<NodeData> getNodes(int size, List<Player> players) {
		if ((size % 2) == 0) {
			throw new IllegalArgumentException("The size must be an odd number.");
		}
		if (players.size() != 3) {
			throw new IllegalArgumentException("The number of players must be three.");
		}

		Set<NodeData> nodes = getMarkedNodes(size, players);

		int maxIndex = size - 1;
		int middleIndex = maxIndex / 2;

		// Upper part
		for (int yIndex = 0; yIndex < middleIndex; yIndex++) {
			for (int xIndex = middleIndex - yIndex; xIndex <= (middleIndex + yIndex); xIndex++) {
				nodes.add(new NodeData(xIndex, yIndex));
			}
		}

		// Lower part
		for (int yIndex = middleIndex; yIndex <= maxIndex; yIndex++) {
			for (int xIndex = middleIndex - (maxIndex - yIndex); xIndex <= (middleIndex + (maxIndex - yIndex)); xIndex++) {
				nodes.add(new NodeData(xIndex, yIndex));
			}
		}

		return nodes;
	}
}
