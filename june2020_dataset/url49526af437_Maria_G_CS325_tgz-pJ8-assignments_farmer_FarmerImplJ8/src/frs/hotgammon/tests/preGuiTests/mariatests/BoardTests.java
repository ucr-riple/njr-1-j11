package frs.hotgammon.tests.preGuiTests.mariatests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import frs.hotgammon.Board;
import frs.hotgammon.common.BoardImpl;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Location;


public class BoardTests {

	Board board;
	
	@Before
	public void setUp(){
		board = new BoardImpl();
	}
	
	@Test
	public void shouldGetCount0AtStartOfGame() {

		assertEquals("Count should have been 0.", 0, board.getCountAt(Location.R1));
	}
	
	@Test
	public void shouldGetNoColorAtStartOfGame() {

		assertEquals("Color should have been NONE.", Color.NONE, board.getColorAt(Location.R1));
	}
	
	@Test
	public void shouldAddOneRedPieceToSquareAtIndex1() {
		boolean wasAdded = board.place(Color.RED, Location.R1.ordinal());
		assertTrue("Should have been able to add a red piece to a new board", wasAdded);
		assertEquals("Count should have been 1.", 1, board.getCountAt(Location.R1));
		assertEquals("Color should have been Red.", Color.RED, board.getColorAt(Location.R1));
	}
	
	@Test public void shouldBeAbleToMoveToNewLocation() {
	      board.place(Color.RED, Location.R1.ordinal());
		  assertTrue(board.move(Location.R1, Location.R2, Color.RED));
	  }

}
