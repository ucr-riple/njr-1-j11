package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class BoardImplTest {

	private BoardImpl create8x8Board() {
		ArrayList<NodeImpl> nodes = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				nodes.add(new NodeImpl(i, j));
			}
		}
		return new BoardImpl(nodes);
	}

	@Test
	public void getNodesTest() {
		ArrayList<NodeImpl> inputNodes = new ArrayList<>();
		inputNodes.add(new NodeImpl(2, 3));
		inputNodes.add(new NodeImpl(2, 2));
		inputNodes.add(new NodeImpl(3, 3));
		inputNodes.add(new NodeImpl(4, 0));
		inputNodes.add(new NodeImpl(0, 4));
		BoardImpl board = new BoardImpl(inputNodes);
		List<Node> nodes = board.getNodes();
		Assert.assertEquals(inputNodes.size(), nodes.size());
		for (int i = 0; i < nodes.size() - 1; i++) {
			Node n1 = nodes.get(i);
			Node n2 = nodes.get(i + 1);
			Assert.assertTrue(n1.getXCoordinate() < n2.getXCoordinate() || n1.getXCoordinate() == n2.getXCoordinate()
					&& n1.getYCoordinate() < n2.getYCoordinate());
		}
	}

	@Test
	public void occupyTest() {
		String player = "Playaah";
		BoardImpl board = create8x8Board();
		Assert.assertEquals(null, board.getNode(0, 2).getOccupantPlayerId());
		board.occupyNode(NodeIdUtil.createNodeId(0, 2), player);
		Assert.assertEquals(player, board.getNode(0, 2).getOccupantPlayerId());
	}

	@Test
	public void hasNodeTest() {
		BoardImpl board = create8x8Board();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Assert.assertTrue(board.hasNode(x, y));
				Assert.assertTrue(board.hasNode(NodeIdUtil.createNodeId(x, y)));
			}
		}
		Assert.assertFalse(board.hasNode(8, 8));
		Assert.assertFalse(board.hasNode(NodeIdUtil.createNodeId(8, 8)));
	}

	@Test
	public void getNodeTest() {
		BoardImpl board = create8x8Board();

		// getNode(x, y)
		{
			Assert.assertEquals(NodeIdUtil.createNodeId(1, 1), board.getNode(1, 1).getId());
			Assert.assertEquals(NodeIdUtil.createNodeId(0, 0), board.getNode(0, 0).getId());
			Assert.assertEquals(NodeIdUtil.createNodeId(2, 3), board.getNode(2, 3).getId());
		}

		// getNode(nodeId)
		{
			Assert.assertEquals(NodeIdUtil.createNodeId(1, 1), board.getNode(NodeIdUtil.createNodeId(1, 1)).getId());
			Assert.assertEquals(NodeIdUtil.createNodeId(0, 0), board.getNode(NodeIdUtil.createNodeId(0, 0)).getId());
			Assert.assertEquals(NodeIdUtil.createNodeId(6, 7), board.getNode(NodeIdUtil.createNodeId(6, 7)).getId());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void outOfRangeTest() {
		BoardImpl board = create8x8Board();
		board.getNode(7, 8);
	}

	@Test
	public void copyWithoutObservers() {
		// Without observers
		{
			BoardImpl board = create8x8Board();
			BoardImpl copy = board.copyWithoutObservers();
			Assert.assertEquals(board, copy);
		}
	}

	@Test
	public void getMaxXTest() {
		Board board = create8x8Board();
		Assert.assertEquals(7, board.getMaxX());
	}

	@Test
	public void getMaxYTest() {
		List<NodeImpl> nodes = new ArrayList<>();
		nodes.add(new NodeImpl(0, 1));
		nodes.add(new NodeImpl(14, 0));
		Board board = new BoardImpl(nodes);
		Assert.assertEquals(1, board.getMaxY());
	}
}
