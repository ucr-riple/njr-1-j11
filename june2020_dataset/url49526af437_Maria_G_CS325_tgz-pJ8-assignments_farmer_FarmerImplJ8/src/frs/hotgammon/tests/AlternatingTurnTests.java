package frs.hotgammon.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Location;
import frs.hotgammon.MoveValidator;
import frs.hotgammon.RollDeterminer;
import frs.hotgammon.TurnDeterminer;
import frs.hotgammon.WinnerDeterminer;
import frs.hotgammon.common.GameImpl;
import frs.hotgammon.common.GameImpl.Placement;
import frs.hotgammon.variants.movevalidators.SimpleMoveValidator;
import frs.hotgammon.variants.movevalidators.CompleteMoveValidator;
import frs.hotgammon.variants.rolldeterminers.PairSequenceDeterminer;
import frs.hotgammon.variants.turndeterminers.AceyDeuceyTurnDeterminer;
import frs.hotgammon.variants.turndeterminers.AlternatingTurnDeterminer;
import frs.hotgammon.variants.winnerdeterminers.BearOffWinnerDeterminer;
import frs.hotgammon.variants.winnerdeterminers.SixMoveWinnerDeterminer;
@RunWith(value = Parameterized.class)
public class AlternatingTurnTests {
	
	
	private GameImpl game;
	
	
	public AlternatingTurnTests(MoveValidator validator, WinnerDeterminer winnerDeterminer, TurnDeterminer ntd, RollDeterminer rollDeterminer) {
		game = new GameImpl(validator, winnerDeterminer, ntd, rollDeterminer);
		game.newGame();		
	}
	
	 @Parameters
	 public static Collection<Object[]> data() {
	   Object[][] data = new Object[][] { { new SimpleMoveValidator(), new SixMoveWinnerDeterminer(), new AlternatingTurnDeterminer(), new PairSequenceDeterminer()},
			                              { new CompleteMoveValidator(), new SixMoveWinnerDeterminer() , new AlternatingTurnDeterminer(), new PairSequenceDeterminer()},
			                              { new SimpleMoveValidator(), new BearOffWinnerDeterminer() , new AlternatingTurnDeterminer(), new PairSequenceDeterminer()},
			                            
	   };
	   return Arrays.asList(data);
	 }
		@Test
		public void shouldBeAbleToRemovePlayerOfRightColor() {
			game.configure(null);
			game.configure(new Placement[] {
					new Placement(Color.RED, Location.B1)
			
			});
			game.nextTurn();
			game.nextTurn();
			assertTrue("Should be able to remove Red pieces.",
					game.move(Location.B1, Location.B4));
		}
		@Test
		public void shouldBeRedPlayerTurnAfterSecondNextTurn() {
			game.nextTurn();
			game.nextTurn();
			assertEquals(Color.RED, game.getPlayerInTurn());
		}
	@Test
	public void shouldBeZeroMovesAfterTwoConsecutive() {
		game.configure(new Placement[] {
				 new Placement(Color.RED, Location.R6),
				 new Placement(Color.RED, Location.R7)
		});
		
		game.nextTurn();
		game.nextTurn();
		assertTrue(game.move(Location.R6, Location.R3));
		assertTrue(game.move(Location.R7, Location.R3));
		assertEquals(0, game.getNumberOfMovesLeft());
	}

	@Test
	public void shouldBeRedTurnAfter2NextTurns() {
		game.nextTurn();
		game.nextTurn();
		assertEquals(game.getPlayerInTurn(), Color.RED);
		assertEquals(game.diceThrown()[0], 3);
		assertEquals(game.diceThrown()[1], 4);
	}
	@Test
	public void redShouldBeInTurn() {
		game.nextTurn();
		game.move(Location.R1, Location.R2);
		game.move(Location.R1, Location.R3);
		game.nextTurn();
		assertTrue(game.getPlayerInTurn() == Color.RED);
	}
	
	@Test
	public void redShouldBeAbleToMove() {
		game.configure(null);
		game.configure(new Placement[] {
				new Placement(Color.RED, Location.B1)
		});
		game.nextTurn();
		game.nextTurn();
		assertTrue(game.move(Location.B1, Location.B4));
	}
	
	@Test
	public void redPlayerIsInTurnAfterNextTurnIsInvokedTheSecondTime() {

		game.nextTurn();
		game.nextTurn();
		assertTrue(game.getPlayerInTurn() == Color.RED);

	}
	
	@Test
	public void shouldChangeThePlayerAfterEachTurn() {

		game.nextTurn();
		assertTrue(game.getPlayerInTurn() == Color.BLACK);
		game.nextTurn();
		assertTrue(game.getPlayerInTurn() == Color.RED);
		game.nextTurn();
		assertTrue(game.getPlayerInTurn() == Color.BLACK);
		game.nextTurn();
		assertTrue(game.getPlayerInTurn() == Color.RED);
	}
	
	@Test
	public void shouldNotBeAbleToMakeThreeMoves() {
		game.configure(null);
		game.configure(new GameImpl.Placement[] {
				 new GameImpl.Placement(Color.RED, Location.R7),
				 new GameImpl.Placement(Color.RED, Location.R6),
				 new GameImpl.Placement(Color.RED, Location.R6)
		});
		game.nextTurn();
		game.nextTurn();
		assertTrue(game.getPlayerInTurn() == Color.RED);
		assertTrue(game.move(Location.R7, Location.R4));
		assertTrue(game.move(Location.R6, Location.R2));
		assertFalse(game.move(Location.R2, Location.R1));
	}



}
