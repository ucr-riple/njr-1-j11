package kth.game.othello.board;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import kth.game.othello.board.factory.NodeData;

/**
 * A factory for producing Othello Boards.
 *
 * @author Mathias Lindblom
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class BoardFactory {
	/**
	 * Create a board using the specified node data
	 *
	 * @param nodeDataSet A set containing that make up the board
	 * @return A board
	 */
	public BoardImpl createBoard(Set<NodeData> nodeDataSet) {
		return new BoardImpl(convertNodeDataSet(nodeDataSet));
	}

	private static NodeImpl convertNodeData(NodeData nodeData) {
		return new NodeImpl(nodeData.getOccupantPlayerId(), nodeData.getXCoordinate(), nodeData.getYCoordinate());
	}

	private static Collection<NodeImpl> convertNodeDataSet(Set<NodeData> nodeDataSet) {
		return nodeDataSet.stream().map(BoardFactory::convertNodeData).collect(Collectors.toList());
	}

}
