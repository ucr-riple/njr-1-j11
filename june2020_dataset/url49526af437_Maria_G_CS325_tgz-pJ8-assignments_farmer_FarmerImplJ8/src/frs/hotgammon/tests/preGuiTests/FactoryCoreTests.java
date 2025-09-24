package frs.hotgammon.tests.preGuiTests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import frs.hotgammon.MonFactory;
import frs.hotgammon.MoveValidator;
import frs.hotgammon.RollDeterminer;
import frs.hotgammon.TurnDeterminer;
import frs.hotgammon.WinnerDeterminer;
import frs.hotgammon.common.GameImpl;
import frs.hotgammon.common.GameImpl.Placement;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Location;
import frs.hotgammon.variants.factories.AlphaMonFactory;
import frs.hotgammon.variants.factories.BetaMonFactory;
import frs.hotgammon.variants.factories.DeltaMonFactory;
import frs.hotgammon.variants.factories.EpsilonMonFactory;
import frs.hotgammon.variants.factories.GammaMonFactory;
import frs.hotgammon.variants.factories.HandicapMonFactory;
import frs.hotgammon.variants.movevalidators.CompleteMoveValidator;
import frs.hotgammon.variants.movevalidators.SimpleMoveValidator;
import frs.hotgammon.variants.rolldeterminers.PairSequenceDeterminer;
import frs.hotgammon.variants.rolldeterminers.RandomRollDeterminer;
import frs.hotgammon.variants.turndeterminers.AceyDeuceyTurnDeterminer;
import frs.hotgammon.variants.turndeterminers.AlternatingTurnDeterminer;
import frs.hotgammon.variants.winnerdeterminers.BearOffWinnerDeterminer;
import frs.hotgammon.variants.winnerdeterminers.SixMoveWinnerDeterminer;

@RunWith(value = Parameterized.class)
public class FactoryCoreTests {

	private GameImpl game;
	
	
	public FactoryCoreTests(MonFactory factory) {
		game = new GameImpl(factory);
		game.newGame();		
	}
	
	 @Parameters
	 public static Collection<MonFactory[]> data() {
		   MonFactory[][] data = new MonFactory[][] { 
			   // AlphaMon		
			   {new AlphaMonFactory()},
			   // BetaMon
			   {new BetaMonFactory()},
			   // GammaMon
			   {new GammaMonFactory()},
			   // DeltaMon
			   {new DeltaMonFactory()},
			   // EpsilonMon
			   {new EpsilonMonFactory()},
			   // ZetaMon -- Identical to AlphaMon with DIFFERENT STARTING POSITION (p434)
			   //{ new SimpleMoveValidator(), new SixMoveWinnerDeterminer() , new AceyDeuceyTurnDeterminer()},
			   // HandicapMon?
			   {new HandicapMonFactory()},
				   			   
		   };
		   return Arrays.asList(data); 
	 }
	 
	 @Test
		public void shouldHaveNoPlayerInTurnAfterNewGame() {
			assertEquals(Color.NONE, game.getPlayerInTurn());
		}

	


		@Test
		public void shoudlBeTwoBlackCheckersOnR1() {
			assertEquals(2, game.getCount(Location.R1));
			assertEquals(Color.BLACK, game.getColor(Location.R1));
		}

		

	


		

		// Maria's Tests
		@Test
		public void shouldBeTwoBlackCheckersOnR1() {
			final int CHECKERS_ON_R1_AT_START = 2;
			assertEquals(CHECKERS_ON_R1_AT_START, game.getCount(Location.R1));
		}

	






		@Test
		public void shouldReturnProperCountForGivenSquare() {
			game.nextTurn();
			assertEquals("Count should be 2", 2, game.getCount(Location.B1));
		}

	



		// Dan's Test
		@Test
		public void shouldHaveTwoBlackCheckersOnR1() {
			game.nextTurn();
			assertTrue(game.getCount(Location.R1) == 2);
			assertTrue(game.getColor(Location.R1) == Color.BLACK);
		}

		

	

	


		/*
		 * @Test public void redShouldBeWinnerAfter6Turns() { game.nextTurn();
		 * assertTrue(game.winner() == Color.NONE); game.nextTurn();
		 * game.nextTurn(); game.nextTurn(); game.nextTurn(); game.nextTurn();
		 * game.nextTurn(); assertTrue(game.winner() == Color.RED); }
		 */

		@Test
		public void shouldNotBeAbleToMoveFromASpotWithoutCheckers() {
			game.nextTurn();
			assertFalse(game.move(Location.R2, Location.R3));
		}
		// Joe's Test
	


		/*
		 * This test is wrong.
		 * 
		 * @Test
		 * 
		 * public void shouldGetNextTurnAndDiceBe34(){
		 * 
		 * game.nextTurn();
		 * 
		 * game.nextTurn();
		 * 
		 * int [] currentDice = game.diceValuesLeft();
		 * 
		 * int firstDie = currentDice[0];
		 * 
		 * int secondDie = currentDice[1];
		 * 
		 * System.out.println(firstDie + " " + secondDie);
		 * 
		 * assertEquals( "dice should be 3-4", 3, secondDie);
		 * 
		 * assertEquals( "dice should be 3-4", 4, firstDie);
		 * 
		 * }
		 */

		/*
		 * Bad Test : Test diceThrown, not diceValuesLeft()
		 * 
		 * @Test
		 * 
		 * public void shouldGetNextTurnThreeTimesAndDiceBe56(){
		 * 
		 * game.nextTurn();
		 * 
		 * game.nextTurn();
		 * 
		 * game.nextTurn();
		 * 
		 * int [] currentDice = game.diceValuesLeft();
		 * 
		 * int firstDie = currentDice[0];
		 * 
		 * int secondDie = currentDice[1];
		 * 
		 * System.out.println(firstDie + " " + secondDie);
		 * 
		 * assertEquals( "dice should be 5-6", 5, secondDie);
		 * 
		 * assertEquals( "dice should be 5-6", 6, firstDie);
		 * 
		 * }
		 */

		/*
		 * @Test
		 * 
		 * public void shouldGetDice1And2OnOpeningTurn(){
		 * 
		 * game.nextTurn();
		 * 
		 * int [] currentDice = game.diceValuesLeft();
		 * 
		 * int firstDie = currentDice[0];
		 * 
		 * int secondDie = currentDice[1];
		 * 
		 * assertEquals( "dice should be 1-2", 1, secondDie);
		 * 
		 * assertEquals( "dice should be 1-2", 2, firstDie);
		 * 
		 * }
		 */

	
		// Marta's Tests
		@Test
		public void shouldHaveTwoBlackCheckersOnR1WhenNewGameStarts() {
			game.nextTurn();
			assertTrue(game.getCount(Location.R1) == 2);
		}

	

		



		

		@Test
		public void shouldHaveNoPlayerInTurnBeforeTheGameStarted() {
			assertTrue(game.getPlayerInTurn() == Color.NONE);
		}



		

		@Test
		public void newGameResetsTheBoardToInitial() {
			game.nextTurn();
			game.nextTurn();
			game.nextTurn();
			game.newGame();

			assertTrue(game.getCount(Location.R1) == 2);
			assertTrue(game.getCount(Location.B1) == 2);

			assertTrue(game.getColor(Location.R1) == Color.BLACK);
			assertTrue(game.getColor(Location.B1) == Color.RED);
		}

		






}

