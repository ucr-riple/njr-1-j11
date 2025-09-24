package kth.game.othello.board;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class BoardHandlerTest {

	private Node[][] getMockedNodeMatrix() {
		Node[][] nodes = new Node[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				nodes[i][j] = mock(Node.class);
				when(nodes[i][j].getId()).thenReturn(NodeIdUtil.createNodeId(i, j));
				occupyMockedNode(nodes[i][j], null);
				when(nodes[i][j].getXCoordinate()).thenReturn(i);
				when(nodes[i][j].getYCoordinate()).thenReturn(j);
			}
		}

		return nodes;
	}

	private BoardImpl getMockedBoard(Node[][] nodes) {
		BoardImpl mockedBoard = mock(BoardImpl.class);

		List<Node> nodeList = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				nodeList.add(nodes[i][j]);
				when(mockedBoard.getNode(i, j)).thenReturn(nodes[i][j]);
				when(mockedBoard.getNode(NodeIdUtil.createNodeId(i, j))).thenReturn(nodes[i][j]);
				when(mockedBoard.hasNode(i, j)).thenReturn(true);
			}
		}
		when(mockedBoard.getNodes()).thenReturn(nodeList);

		return mockedBoard;
	}

	private void occupyNodeOnMockedBoard(Board board, int x, int y, String playerId) {
		occupyMockedNode(board.getNode(x, y), playerId);
	}

	private void occupyMockedNode(Node node, String playerId) {
		when(node.getOccupantPlayerId()).thenReturn(playerId);
		when(node.isMarked()).thenReturn(playerId != null);
	}

	private BoardHandler getSpecialEndGameBoardHandler(String player1, String player2) {
		Node[][] nodes = getMockedNodeMatrix();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				occupyMockedNode(nodes[i][j], player1);
			}
		}

		occupyMockedNode(nodes[3][7], null);
		occupyMockedNode(nodes[4][6], null);
		occupyMockedNode(nodes[4][7], null);
		occupyMockedNode(nodes[5][6], null);
		occupyMockedNode(nodes[5][7], player2);
		occupyMockedNode(nodes[6][7], null);

		BoardHistoryHandler mockedBoardHistoryHandler = mock(BoardHistoryHandler.class);
		return new BoardHandler(getMockedBoard(nodes), mockedBoardHistoryHandler);
	}

	private BoardHandler getInitialGameBoardHandler(String player1, String player2) {
		BoardHistoryHandler mockedBoardHistoryHandler = mock(BoardHistoryHandler.class);
		return new BoardHandler(getInitialGameBoard(player1, player2), mockedBoardHistoryHandler);
	}

	private BoardImpl getInitialGameBoard(String player1, String player2) {
		Node[][] nodes = getMockedNodeMatrix();
		occupyMockedNode(nodes[3][3], player2);
		occupyMockedNode(nodes[4][4], player2);
		occupyMockedNode(nodes[3][4], player1);
		occupyMockedNode(nodes[4][3], player1);
		BoardImpl board = getMockedBoard(nodes);
		return board;
	}

	private boolean nodeListContainsNodeId(List<Node> list, String nodeId) {
		for (Node node : list) {
			if (node.getId().equals(nodeId)) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void getValidMovesTest() {
		String player1 = "player1";
		String player2 = "player2";

		// Test initial game setup
		{
			BoardHandler boardHandler = getInitialGameBoardHandler(player1, player2);

			List<Node> validMoves = boardHandler.getValidMoves(player1);
			Assert.assertEquals(4, validMoves.size());
			Assert.assertTrue(nodeListContainsNodeId(validMoves, NodeIdUtil.createNodeId(2, 3)));
			Assert.assertTrue(nodeListContainsNodeId(validMoves, NodeIdUtil.createNodeId(3, 2)));
			Assert.assertTrue(nodeListContainsNodeId(validMoves, NodeIdUtil.createNodeId(4, 5)));
			Assert.assertTrue(nodeListContainsNodeId(validMoves, NodeIdUtil.createNodeId(5, 4)));

			occupyNodeOnMockedBoard(boardHandler.getBoard(), 2, 3, player1);
			occupyNodeOnMockedBoard(boardHandler.getBoard(), 3, 3, player1);
			validMoves = boardHandler.getValidMoves(player2);
			Assert.assertEquals(3, validMoves.size());
			Assert.assertTrue(nodeListContainsNodeId(validMoves, NodeIdUtil.createNodeId(2, 2)));
			Assert.assertTrue(nodeListContainsNodeId(validMoves, NodeIdUtil.createNodeId(4, 2)));
			Assert.assertTrue(nodeListContainsNodeId(validMoves, NodeIdUtil.createNodeId(2, 4)));

			occupyNodeOnMockedBoard(boardHandler.getBoard(), 4, 2, player2);
			occupyNodeOnMockedBoard(boardHandler.getBoard(), 4, 3, player2);
			validMoves = boardHandler.getValidMoves(player1);
			Assert.assertEquals(5, validMoves.size());
			Assert.assertTrue(nodeListContainsNodeId(validMoves, NodeIdUtil.createNodeId(5, 1)));
			Assert.assertTrue(nodeListContainsNodeId(validMoves, NodeIdUtil.createNodeId(5, 2)));
			Assert.assertTrue(nodeListContainsNodeId(validMoves, NodeIdUtil.createNodeId(5, 3)));
			Assert.assertTrue(nodeListContainsNodeId(validMoves, NodeIdUtil.createNodeId(5, 4)));
			Assert.assertTrue(nodeListContainsNodeId(validMoves, NodeIdUtil.createNodeId(5, 5)));
		}

		// Test special end game board
		{
			BoardHandler boardHandle = getSpecialEndGameBoardHandler(player1, player2);
			Assert.assertEquals(0, boardHandle.getValidMoves(player2).size());
		}
	}

	@Test
	public void getNumSwapsTest() {
		String player1 = "player1";
		String player2 = "player2";

		// Test initial game setup
		{
			BoardHandler boardHandler = getInitialGameBoardHandler(player1, player2);

			Assert.assertEquals(1, boardHandler.getNumNodesToSwap(player1, NodeIdUtil.createNodeId(2, 3)));
			Assert.assertEquals(1, boardHandler.getNumNodesToSwap(player1, NodeIdUtil.createNodeId(3, 2)));
			Assert.assertEquals(1, boardHandler.getNumNodesToSwap(player1, NodeIdUtil.createNodeId(4, 5)));
			Assert.assertEquals(1, boardHandler.getNumNodesToSwap(player1, NodeIdUtil.createNodeId(5, 4)));

		}

		// Test case when more than 1 swaps will occur
		{
			BoardHandler boardHandler = getInitialGameBoardHandler(player1, player2);
			occupyNodeOnMockedBoard(boardHandler.getBoard(), 2, 3, player2);

			Assert.assertEquals(2, boardHandler.getNumNodesToSwap(player1, NodeIdUtil.createNodeId(1, 3)));
			Assert.assertEquals(1, boardHandler.getNumNodesToSwap(player1, NodeIdUtil.createNodeId(3, 2)));
			Assert.assertEquals(1, boardHandler.getNumNodesToSwap(player1, NodeIdUtil.createNodeId(4, 5)));
			Assert.assertEquals(1, boardHandler.getNumNodesToSwap(player1, NodeIdUtil.createNodeId(5, 4)));
		}
	}

	@Test
	public void moveTest() {
		String player1 = "player1";
		String player2 = "player2";

		// Test initial game setup
		{
			BoardHandler boardHandler = getInitialGameBoardHandler(player1, player2);

			List<Node> swaps = boardHandler.move(player1, NodeIdUtil.createNodeId(2, 3));
			Assert.assertEquals(2, swaps.size());
			Assert.assertTrue(nodeListContainsNodeId(swaps, NodeIdUtil.createNodeId(2, 3)));
			Assert.assertTrue(nodeListContainsNodeId(swaps, NodeIdUtil.createNodeId(3, 3)));

			swaps = boardHandler.move(player2, NodeIdUtil.createNodeId(4, 2));
			Assert.assertEquals(2, swaps.size());
			Assert.assertTrue(nodeListContainsNodeId(swaps, NodeIdUtil.createNodeId(4, 2)));
			Assert.assertTrue(nodeListContainsNodeId(swaps, NodeIdUtil.createNodeId(4, 3)));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidMoveTest() {
		String player1 = "player1";
		String player2 = "player2";
		BoardHandler boardHandler = getInitialGameBoardHandler(player1, player2);
		boardHandler.move(player1, NodeIdUtil.createNodeId(2, 2));
	}

	@Test
	public void hasAValidMoveTest() {
		String player1 = "player1";
		String player2 = "player2";
		BoardHandler boardHandler = getSpecialEndGameBoardHandler(player1, player2);

		Assert.assertFalse(boardHandler.hasValidMove(player1));
		Assert.assertFalse(boardHandler.hasAnyAValidMove(Arrays.asList(player1, player2)));

		boardHandler = getInitialGameBoardHandler(player1, player2);

		Assert.assertTrue(boardHandler.hasValidMove(player1));
		Assert.assertTrue(boardHandler.hasAnyAValidMove(Arrays.asList(player1, player2)));

		Assert.assertTrue(boardHandler.hasAnyAValidMove(Arrays.asList("notPlayer1Id", player2)));
	}

	@Test
	public void undoTest() {
		BoardImpl mockedBoard = getInitialGameBoard("player1", "player2");
		BoardHistoryHandler mockedBoardHistoryHandler = mock(BoardHistoryHandler.class);
		BoardHandler boardHandler = new BoardHandler(mockedBoard, mockedBoardHistoryHandler);

		boardHandler.move("player1", NodeIdUtil.createNodeId(2, 3));

		List<Node> move = new ArrayList<>();
		move.add(mockedBoard.getNode(2, 3));
		move.add(mockedBoard.getNode(3, 3));
		verify(mockedBoardHistoryHandler, times(1)).save(move);

		boardHandler.move("player2", NodeIdUtil.createNodeId(4, 2));
		move = new ArrayList<>();
		move.add(mockedBoard.getNode(4, 2));
		move.add(mockedBoard.getNode(4, 3));
		verify(mockedBoardHistoryHandler, times(1)).save(move);
	}
}
