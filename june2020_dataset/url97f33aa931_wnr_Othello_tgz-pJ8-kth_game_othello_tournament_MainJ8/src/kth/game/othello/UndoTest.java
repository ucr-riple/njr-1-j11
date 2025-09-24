package kth.game.othello;

import kth.game.othello.board.BoardImpl;
import org.junit.Assert;
import org.junit.Test;

public class UndoTest {
	@Test
	public void undoTest() {
		Othello othello = (new OthelloFactoryImpl()).createComputerGame();
		BoardImpl preMove = ((BoardImpl) othello.getBoard()).copyWithoutObservers();

		othello.start();
		othello.move();
		othello.move();
		othello.move();
		Assert.assertNotEquals(preMove, othello.getBoard());

		othello.undo();
		othello.undo();
		othello.undo();

		Assert.assertEquals(preMove, othello.getBoard());
	}
}
