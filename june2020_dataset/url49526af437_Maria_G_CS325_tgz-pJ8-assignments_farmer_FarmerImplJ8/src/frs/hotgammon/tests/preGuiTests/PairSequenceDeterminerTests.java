package frs.hotgammon.tests.preGuiTests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import frs.hotgammon.MoveValidator;
import frs.hotgammon.RollDeterminer;
import frs.hotgammon.TurnDeterminer;
import frs.hotgammon.WinnerDeterminer;
import frs.hotgammon.common.GameImpl;
import frs.hotgammon.common.GameImpl.Placement;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Location;
import frs.hotgammon.variants.movevalidators.CompleteMoveValidator;
import frs.hotgammon.variants.movevalidators.SimpleMoveValidator;
import frs.hotgammon.variants.rolldeterminers.PairSequenceDeterminer;
import frs.hotgammon.variants.rolldeterminers.RandomRollDeterminer;
import frs.hotgammon.variants.turndeterminers.AceyDeuceyTurnDeterminer;
import frs.hotgammon.variants.turndeterminers.AlternatingTurnDeterminer;
import frs.hotgammon.variants.winnerdeterminers.BearOffWinnerDeterminer;
import frs.hotgammon.variants.winnerdeterminers.SixMoveWinnerDeterminer;

@RunWith(value = Parameterized.class)
public class PairSequenceDeterminerTests {


	private GameImpl game;
	
	
	public PairSequenceDeterminerTests(MoveValidator validator, WinnerDeterminer winnerDeterminer, TurnDeterminer ntd, RollDeterminer diceRollDeterminer) {
		game = new GameImpl(validator, winnerDeterminer, ntd, diceRollDeterminer);
		game.newGame();		
	}
	
	 @Parameters
	 public static Collection<Object[]> data() {
	   Object[][] data = new Object[][] { 
			   // AlphaMon		
			   { new SimpleMoveValidator(), new SixMoveWinnerDeterminer(), new AlternatingTurnDeterminer() , new PairSequenceDeterminer()},
			   // BetaMon
			   { new CompleteMoveValidator(), new SixMoveWinnerDeterminer() , new AlternatingTurnDeterminer() , new PairSequenceDeterminer()},
			   // GammaMon
			   { new SimpleMoveValidator(), new BearOffWinnerDeterminer() , new AlternatingTurnDeterminer() , new PairSequenceDeterminer()},
			   // DeltaMon
			   { new SimpleMoveValidator(), new SixMoveWinnerDeterminer() , new AceyDeuceyTurnDeterminer() , new PairSequenceDeterminer()},
			  		   
	   };
	   return Arrays.asList(data);
	 }
	 
		@Test
		public void shouldHave1BlackCheckerOnR1and1BlackCheckerOnR2AfterMoveFromR1toR2() {
			game.configure(new Placement[] {
				new Placement(Color.BLACK, Location.R1),
				new Placement(Color.BLACK, Location.R1)
			});
			game.nextTurn();
			game.move(Location.R1, Location.R2);
			assertTrue(game.getCount(Location.R1) == 1);
			assertTrue(game.getCount(Location.R2) == 1);
			assertTrue(game.getColor(Location.R1) == Color.BLACK);
			assertTrue(game.getColor(Location.R2) == Color.BLACK);
		}
		

		@Test
		public void shouldHave0NumberOfMovesForBlackPlayerAfterTheBlackMovedTwice() {

			game.nextTurn();
			game.move(Location.R1, Location.R2);
			game.move(Location.R1, Location.R3);

			assertTrue(game.getNumberOfMovesLeft() == 0);

		}

		

		@Test
		public void shouldHaveNoMoveLeft() {
			game.nextTurn();
			game.move(Location.R1, Location.R2);
			game.move(Location.R1, Location.R3);
			assertTrue(game.getNumberOfMovesLeft() == 0);
		}

		
		@Test
		public void ShouldBeBlackToGoFirst() {

			game.nextTurn();

			assertEquals("Black is first to go", Color.BLACK,
					game.getPlayerInTurn());

		}
		

		@Test
		public void shouldRunOutOfMoves() {
			// Needed to add nextTurn call
			game.configure(new Placement[] {
					new Placement(Color.BLACK, Location.R1),
					new Placement(Color.BLACK, Location.R1)
			});
			game.nextTurn();
			game.move(Location.R1, Location.R2);

			game.move(Location.R1, Location.R3);

			assertEquals(" no moves should be left ", 0,
					game.getNumberOfMovesLeft());

		}
		
		@Test
		public void newGameResetsPlayerToBlack() {

			game.nextTurn();
			game.nextTurn();
			game.nextTurn();
			game.newGame();
			game.nextTurn();
			assertTrue(game.getPlayerInTurn() == Color.BLACK);
		}
		
		@Test
		public void shouldHaveBlackPlayerInTurnAfterFirstRoll() {
			game.nextTurn(); // will throw [1,2] and thus black starts
			assertEquals(Color.BLACK, game.getPlayerInTurn());
		}
		
		
		@Test
		public void shouldNotBeAbleToMoveIfNotInTurn() {
			game.nextTurn();
			assertFalse(game.move(Location.B1, Location.B2));
			assertEquals(game.getNumberOfMovesLeft(), 2);
		}


		@Test
		public void shouldBeValidToMoveFromR1ToR2AtStartOfGame() {
			game.nextTurn();
			assertTrue(game.move(Location.R1, Location.R2));
		}
		
		@Test
		public void shouldBeInvalidToMoveFromR1ToB1AtStartOfGame() {
			game.nextTurn();
			assertFalse(game.move(Location.R1, Location.B1));
		}

		
		@Test
		public void shouldNotBeAbleToRemovePlayerOfWrongColor() {
			game.nextTurn();
			assertFalse("Should not be able to remove Red pieces.",
					game.move(Location.B1, Location.B2));
		}
		
		@Test
		public void shouldBeAbleToMoveBlackR1toR2() {
			game.configure(new Placement[] {
				new Placement(Color.BLACK, Location.R1),	
				new Placement(Color.BLACK, Location.R1)
			});
			game.nextTurn();
			assertEquals(2, game.getCount(Location.R1));
			assertTrue(game.move(Location.R1, Location.R2));
			assertTrue(game.getCount(Location.R1) == 1);
			assertTrue(game.getCount(Location.R2) == 1);
			assertTrue(game.getNumberOfMovesLeft() == 1);

		}
		
		@Test
		public void shouldNotBeAbleToMoveBlackR1toB1() {
			game.nextTurn();
			assertFalse(game.move(Location.R1, Location.B1));
		}


		

		@Test
		public void shouldNotBeAbleToMoveWithNoMovesLeft() {
			game.nextTurn();
			game.move(Location.R1, Location.R2);
			game.move(Location.R1, Location.R3);
			assertFalse(game.move(Location.R2, Location.R3));
		}
		
		@Test
		public void moveR1toB1isInvalidAsThereIsAnOpponentThere() {
			game.nextTurn();
			assertFalse(game.move(Location.R1, Location.B1));
			assertTrue(game.getColor(Location.B1) == Color.RED);
		}
		

		@Test
		public void R1ShouldBeNoneAndR3BlackAfterMoveFromR1ToR3() {

			game.nextTurn();
			assertTrue(game.move(Location.R1, Location.R2));
			assertTrue(game.move(Location.R1, Location.R3));
			assertTrue(game.getColor(Location.R1) == Color.NONE);
			assertTrue(game.getColor(Location.R3) == Color.BLACK);

		}

		
		@Test
		public void shouldHaveBlackOnR1andBlackOnB2AndOneMoreLeft() {
			game.configure( new Placement[] { 
					new Placement(Color.BLACK, Location.R1),
					new Placement(Color.BLACK, Location.R1),
					
				});

			assertEquals(2, game.getCount(Location.R1));
			assertEquals(Color.BLACK, game.getColor(Location.R1));
			game.nextTurn();
			assertTrue(game.move(Location.R1, Location.R2));
			assertEquals(1, game.getCount(Location.R2));
			assertEquals(Color.BLACK, game.getColor(Location.R2));
			assertEquals(1, game.getCount(Location.R1));
			assertEquals(Color.BLACK, game.getColor(Location.R1));

			assertEquals(1, game.getNumberOfMovesLeft());
		}
		
		
		private boolean occupiedBy(Color color, Location loc) {
			return game.getCount(loc) > 0 && game.getColor(loc) == color;
		}
		
		@Test
		public void shouldNotBeAbleToPlaceBlackOnRedOccupiedSquare() {
			game.nextTurn();
			int prevCount = game.getCount(Location.R1);
			if (occupiedBy(Color.RED, Location.B1)) {
				assertFalse(game.move(Location.R1, Location.B1));
				assertEquals(game.getCount(Location.R1), prevCount);
			} else {
				assertTrue(game.move(Location.R1, Location.B1));
				assertEquals(game.getCount(Location.R1), prevCount - 1);

			}
		}
		
				
		@Test
		public void shouldNotBeAbleToPlaceRedOnBlackOccupiedSquare() {
			game.nextTurn();
			game.nextTurn();
			assertFalse(game.move(Location.B1, Location.R1));

		}
		

		
		@Test
		public void shouldBeNoMovesLeftAfterMovingTwoBlackCheckersFromR1toR2() {
			game.nextTurn();
			game.move(Location.R1, Location.R2);
			game.move(Location.R1, Location.R3);

			assertEquals(0, game.getNumberOfMovesLeft());
		}
		

		@Test
		public void shouldNotBeAbleToPlaceTwoDifferentColorsOnSameSquare() {
			game.nextTurn();
			game.move(Location.B6, Location.B2);
			game.nextTurn();
			assertFalse(game.move(Location.B1, Location.B2));
		}
		

		

		@Test
		public void shouldBeAbleToPlaceTwoSameColorPiecesOnSameSquare() {
			//Added
			game.configure(new Placement[]{
	    		new Placement(Color.BLACK,Location.R1),
	    		new Placement(Color.BLACK,Location.R2)
			});
			//
			game.nextTurn();
			game.move(Location.R1, Location.R3);
			assertTrue(game.move(Location.R2, Location.R3));
		}
		


		@Test
		public void shouldHaveOneMoveLeftForBlackPlayerAfterMoveFromR1ToR2() {

			game.nextTurn();
			game.move(Location.R1, Location.R2);
			assertTrue(game.getPlayerInTurn() == Color.BLACK);
			System.out.println(game.getNumberOfMovesLeft());
			assertTrue(game.getNumberOfMovesLeft() == 1);
		}

		

		@Test
		public void shouldNotBeAbleToPlaceTwoDifferentCheckersInTheSameContainer() {

			game.nextTurn();
			assertFalse(game.move(Location.R12, Location.B1));
		}

		@Test
		public void shouldBeAbleToPlaceTheSameCheckersInOneContainer() {
			game.configure(new Placement[] {
					new Placement(Color.BLACK, Location.R1),
					new Placement(Color.BLACK, Location.R2)
			});
			game.nextTurn();
			
			assertTrue(game.move(Location.R1, Location.R3));
			assertTrue(game.move(Location.R2, Location.R3));
		}

		
		

		
		
		
	 

}
