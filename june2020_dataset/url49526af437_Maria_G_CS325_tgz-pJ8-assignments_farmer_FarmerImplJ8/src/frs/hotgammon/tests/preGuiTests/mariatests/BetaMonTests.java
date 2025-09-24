package frs.hotgammon.tests.preGuiTests.mariatests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import frs.hotgammon.common.GameImpl;
import frs.hotgammon.variants.movevalidators.CompleteMoveValidator;
import frs.hotgammon.variants.rolldeterminers.PairSequenceDeterminer;
import frs.hotgammon.variants.turndeterminers.AlternatingTurnDeterminer;
import frs.hotgammon.variants.winnerdeterminers.SixMoveWinnerDeterminer;
import frs.hotgammon.common.GameImpl.Placement;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Location;

public class BetaMonTests {

	GameImpl game;
	  /** Fixture */
	  @Before
	  public void setUp() {
	    game = new GameImpl(new CompleteMoveValidator(), new SixMoveWinnerDeterminer(), new AlternatingTurnDeterminer(), new PairSequenceDeterminer());
	    game.newGame();
	   
	  }
	  
	@Test
	public void piecesCanOnlyMoveInDirectionOfPlayersInnerTable() {
		game.nextTurn();
		assertTrue("Should have been able to move", game.move(Location.R1, Location.R2));
	} 
	
	@Test
	public void piecesCanOnlyMoveIfDistanceTravelledEqualsOneOfDiceValuesRolled() {
		game.nextTurn();
		assertTrue("Should have been able to move from R1 to R2", game.move(Location.R1, Location.R2));
		assertFalse("Should not have been able to move from R1 to R7", game.move(Location.R1, Location.R7));
	}

	@Test
	public void diceValuesRolledCanOnlyBeUsedOnce() {
		game.nextTurn();
		assertTrue("Should have been able to move from R1 to R2", game.move(Location.R1, Location.R2));
		assertFalse("Should not have been able to move from R2 to R3", game.move(Location.R2, Location.R3));
	}

	@Test
	public void shouldMoveOpponentToBarIfLandOnSquareWithOneOpponentPiece() {
		game.nextTurn();
		game.move(Location.B8, Location.B7);
		game.move(Location.B7, Location.B5);
		game.nextTurn();
		assertEquals(0, game.getCount(Location.B_BAR));
		assertTrue(game.move(Location.B1, Location.B5));
		assertEquals(1, game.getCount(Location.B_BAR));
		
	}
	
	@Test
	public void shouldNotBeAbleToMoveToSquareWithTwoOrMoreOpponentPieces() {
		game.nextTurn();
		assertFalse(game.move(Location.R12, Location.B12));		
	}

	@Test
	public void shouldHaveToMovePieceFromBarToOpponentsInnerTableWithValidDiceValueRolled() {
		game.nextTurn();
		game.move(Location.B8, Location.B7);
		game.move(Location.B7, Location.B5);
		game.nextTurn();
		assertTrue(game.move(Location.B1, Location.B5));
		game.nextTurn();
		assertFalse(game.move(Location.R1, Location.R7));
		assertFalse(game.move(Location.B_BAR, Location.B5));
		assertFalse(game.move(Location.B_BAR, Location.R3));
		assertTrue(game.move(Location.B_BAR, Location.R5));
	}
	

	@Test
	public void canNotBearOffWithPiecesOutsideOfInnerTable() {
		game.nextTurn();
		game.nextTurn();
		game.nextTurn();
		assertFalse(game.move(Location.B6, Location.B_BEAR_OFF));
	}
	

	@Test
	public void canNotBearOffWithLessThanDiceValueIfValidDiceValueMove() {
		game.configure(new Placement[]{
				new Placement(Color.BLACK, Location.B1),
				new Placement(Color.BLACK, Location.B1),
				new Placement(Color.BLACK, Location.B2)
		});
		game.nextTurn();
		assertTrue(game.move(Location.B1, Location.B_BEAR_OFF));
		assertFalse(game.move(Location.B1, Location.B_BEAR_OFF));
	}
	

	@Test
	public void canBearOffWithLessThanDiceValueIfNoValidDiceValueMove() {
		game.configure(new Placement[]{
				new Placement(Color.BLACK, Location.B1),
				new Placement(Color.BLACK, Location.B1)
		});
		
		game.nextTurn();
		game.nextTurn();
		game.nextTurn();
		assertTrue(game.move(Location.B1, Location.B_BEAR_OFF));
		assertTrue(game.move(Location.B1, Location.B_BEAR_OFF));
	}
}
