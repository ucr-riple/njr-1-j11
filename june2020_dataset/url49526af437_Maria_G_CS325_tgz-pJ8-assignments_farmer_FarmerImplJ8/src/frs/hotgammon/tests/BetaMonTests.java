package frs.hotgammon.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Location;
import frs.hotgammon.common.GameImpl;
import frs.hotgammon.variants.movevalidators.CompleteMoveValidator;
import frs.hotgammon.variants.rolldeterminers.PairSequenceDeterminer;
import frs.hotgammon.variants.turndeterminers.AlternatingTurnDeterminer;
import frs.hotgammon.variants.winnerdeterminers.SixMoveWinnerDeterminer;

public class BetaMonTests {
	
	private GameImpl game;

	@Before
	public void setup() { 
		game = new GameImpl(new CompleteMoveValidator(), 
				new SixMoveWinnerDeterminer(), 
				new AlternatingTurnDeterminer(), 
				new PairSequenceDeterminer());
		game.newGame();
	}

	@Test
	public void redShouldOnlyMoveTowardRedInnerTable() {
		game.nextTurn();
		game.nextTurn();
		assertTrue(game.move(Location.B1, Location.B4));
		assertFalse(game.move(Location.B4,  Location.B1));
	}
	@Test
	public void blackShouldOnlyMoveTowardBlackInnerTable() {
		game.nextTurn();
		assertTrue(game.move(Location.R1,  Location.R3));
		assertFalse(game.move(Location.R3,  Location.R1));		
	}
	
	@Test
	public void shouldReportTwoDieLeftBeforeMove() {
		game.nextTurn();
		assertEquals(2, game.diceValuesLeft().length);
	}
	
	@Test 
	public void shouldReportOneDieLeftAfterSingleMove() {
		game.nextTurn();
		assertTrue(game.move(Location.R1,  Location.R3));
		assertEquals(1, game.diceValuesLeft().length);		
	}
	
	@Test 
	public void shouldReportZeroDieLeftAfterSingleMove() {
		game.nextTurn();
		assertTrue(game.move(Location.R1,  Location.R3));
		assertTrue(game.move(Location.R3,  Location.R4));	
		assertEquals(0, game.diceValuesLeft().length);		
	}
	
	@Test
	public void moveDistanceShouldEqualRoll() {
		game.nextTurn();
		int[] roll = game.diceValuesLeft();
		assertTrue(roll.length ==2);
		Location to = Location.findLocation(game.getPlayerInTurn(), Location.R1, roll[0]);
		assertTrue(game.move(Location.R1, to));
	}
	
	@Test
	public void shouldNotBeAbleToUseSameDieTwice() {
		game.nextTurn();
		assertTrue(game.move(Location.R1,  Location.R3));
		assertFalse(game.move(Location.R3,  Location.R5));	
	}
	
	@Test 
	public void shouldSendRedBlotToBar() {
		game.configure( null);
		game.configure( new GameImpl.Placement[] {
				new GameImpl.Placement(Color.BLACK, Location.R1),
				new GameImpl.Placement(Color.RED, Location.R2)
		});
		game.nextTurn();
		int prevCountRedBar = game.getCount(Location.R_BAR);
		assertTrue(game.move(Location.R1, Location.R2));
		assertTrue(Color.BLACK == game.getColor(Location.R2));
		assertEquals(1, game.getCount(Location.R2));
		assertEquals(prevCountRedBar+1, game.getCount(Location.R_BAR));
		
	}
	
	@Test 
	public void shouldSendBlackBlotToBar() {
	
		game.configure( new GameImpl.Placement[] {
				new GameImpl.Placement(Color.RED, Location.B1),
				new GameImpl.Placement(Color.BLACK, Location.B4)
		});
		game.nextTurn();
		game.nextTurn(); // Will generate 3,4
		int prevCountRedBar = game.getCount(Location.B_BAR);
		assertTrue(game.move(Location.B1, Location.B4));
		assertTrue(Color.RED == game.getColor(Location.B4));
		assertEquals(1, game.getCount(Location.B4));
		assertEquals(prevCountRedBar+1, game.getCount(Location.B_BAR));
		
		
	}
	
	@Test
	public void shouldRejectRedMoveDueToBlockedPoint() {
	
		game.configure( new GameImpl.Placement[] {
				new GameImpl.Placement(Color.RED, Location.B1),
				new GameImpl.Placement(Color.BLACK, Location.B4),
				new GameImpl.Placement(Color.BLACK, Location.B4)
		});
		game.nextTurn();
		game.nextTurn();
		assertFalse(game.move(Location.B1,  Location.B4));
	}
	@Test
	public void shouldRejectBlackMoveDueToBlockedPoint() {

		game.configure( new GameImpl.Placement[] {
				new GameImpl.Placement(Color.BLACK, Location.R1),
				new GameImpl.Placement(Color.RED, Location.R4),
				new GameImpl.Placement(Color.RED, Location.R4)
		});
		game.nextTurn();

		assertFalse(game.move(Location.R1,  Location.R4));
	}
	
	@Test
	public void shouldMoveBlackOffBarToRedInnerTable() {
	
		game.configure( new GameImpl.Placement[] {
				new GameImpl.Placement(Color.BLACK, Location.B_BAR),
				new GameImpl.Placement(Color.BLACK, Location.B_BAR)

		});
		game.nextTurn();
		assertTrue(game.move(Location.B_BAR,  Location.R1));
		assertTrue(game.move(Location.B_BAR,  Location.R2));
		assertEquals(0, game.getCount(Location.B_BAR));
		
	}
	
	@Test
	public void shouldNotMoveBlackOffBarToRedInnerTable() {
	
		game.configure( new GameImpl.Placement[] {
				new GameImpl.Placement(Color.BLACK, Location.B_BAR),
				new GameImpl.Placement(Color.BLACK, Location.B_BAR),
				new GameImpl.Placement(Color.RED, Location.R1),
				new GameImpl.Placement(Color.RED, Location.R1),
				new GameImpl.Placement(Color.RED, Location.R2),
				new GameImpl.Placement(Color.RED, Location.R2),
				new GameImpl.Placement(Color.RED, Location.R3),
				new GameImpl.Placement(Color.RED, Location.R3),
				new GameImpl.Placement(Color.RED, Location.R4),
				new GameImpl.Placement(Color.RED, Location.R4),
				new GameImpl.Placement(Color.RED, Location.R5),
				new GameImpl.Placement(Color.RED, Location.R5),
				new GameImpl.Placement(Color.RED, Location.R6),
				new GameImpl.Placement(Color.RED, Location.R6)

		});
		game.nextTurn();
		assertFalse(game.move(Location.B_BAR,  Location.R1));
		assertFalse(game.move(Location.B_BAR,  Location.R2));
		assertEquals(2, game.getCount(Location.B_BAR));
		
	}
	
	@Test
	public void shouldNotMoveOtherBlackWhenBlackOnBar() {

		game.configure( new GameImpl.Placement[] {
				new GameImpl.Placement(Color.BLACK, Location.B_BAR),
				new GameImpl.Placement(Color.BLACK, Location.B8)

		});
		game.nextTurn();
		assertFalse(game.move(Location.B8,  Location.B9));
		
	}
	
	@Test
	public void shouldNotMoveOtherRedWhenRedOnBar() {

		game.configure( new GameImpl.Placement[] {
				new GameImpl.Placement(Color.RED, Location.R_BAR),
				new GameImpl.Placement(Color.RED, Location.R8)

		});
		game.nextTurn();
		game.nextTurn();
		assertFalse(game.move(Location.B8,  Location.B11));
		
	}
	
	@Test
	public void redShouldBearOffWithAllCheckersInInnerTable() {

		game.configure( new GameImpl.Placement[] {
				new GameImpl.Placement(Color.RED, Location.R1),
				new GameImpl.Placement(Color.RED, Location.R2),
				new GameImpl.Placement(Color.RED, Location.R3),
				new GameImpl.Placement(Color.RED, Location.R1),
				new GameImpl.Placement(Color.RED, Location.R2),
				new GameImpl.Placement(Color.RED, Location.R3),
				new GameImpl.Placement(Color.RED, Location.R4),
				new GameImpl.Placement(Color.RED, Location.R2),
				new GameImpl.Placement(Color.RED, Location.R3),
				new GameImpl.Placement(Color.RED, Location.R1),
				new GameImpl.Placement(Color.RED, Location.R2),
				new GameImpl.Placement(Color.RED, Location.R3),
				new GameImpl.Placement(Color.RED, Location.R1),
				new GameImpl.Placement(Color.RED, Location.R2),
				new GameImpl.Placement(Color.RED, Location.R4)
		});
	
		game.nextTurn();
		game.nextTurn();
		assertTrue(game.move(Location.R3, Location.R_BEAR_OFF));
		assertTrue(game.move(Location.R4, Location.R_BEAR_OFF));
	}
	
	@Test
	public void redShouldNotBearOffWithCheckersNotInInnerTable() {
		game.configure( new GameImpl.Placement[] {
				new GameImpl.Placement(Color.RED, Location.R1),
				new GameImpl.Placement(Color.RED, Location.R2),
				new GameImpl.Placement(Color.RED, Location.R3),
				new GameImpl.Placement(Color.RED, Location.R4),
				new GameImpl.Placement(Color.RED, Location.R2),
				new GameImpl.Placement(Color.RED, Location.R3),
				new GameImpl.Placement(Color.RED, Location.R1),
				new GameImpl.Placement(Color.RED, Location.R2),
				new GameImpl.Placement(Color.RED, Location.R3),
				new GameImpl.Placement(Color.RED, Location.R1),
				new GameImpl.Placement(Color.RED, Location.R2),
				new GameImpl.Placement(Color.RED, Location.R10)
		});
	
		game.nextTurn();
		game.nextTurn();
		assertFalse(game.move(Location.R3, Location.R_BEAR_OFF));

		
	}
	
	@Test
	public void blackShouldBearOffWithAllCheckersInInnerTable() {

		game.configure( new GameImpl.Placement[] {
				new GameImpl.Placement(Color.BLACK, Location.B1),
				new GameImpl.Placement(Color.BLACK, Location.B2),
				new GameImpl.Placement(Color.BLACK, Location.B3),
				new GameImpl.Placement(Color.BLACK, Location.B1),
				new GameImpl.Placement(Color.BLACK, Location.B2),
				new GameImpl.Placement(Color.BLACK, Location.B3),
				new GameImpl.Placement(Color.BLACK, Location.B4),
				new GameImpl.Placement(Color.BLACK, Location.B2),
				new GameImpl.Placement(Color.BLACK, Location.B3),
				new GameImpl.Placement(Color.BLACK, Location.B1),
				new GameImpl.Placement(Color.BLACK, Location.B2),
				new GameImpl.Placement(Color.BLACK, Location.B3),
				new GameImpl.Placement(Color.BLACK, Location.B1),
				new GameImpl.Placement(Color.BLACK, Location.B2),
				new GameImpl.Placement(Color.BLACK, Location.B4)
		});
	
	
		game.nextTurn();
		assertTrue(game.move(Location.B2, Location.B_BEAR_OFF));
	}
	
	@Test
	public void blackShouldNotBearOffWithCheckersNotInInnerTable() {
		game.configure( new GameImpl.Placement[] {
				new GameImpl.Placement(Color.BLACK, Location.B1),
				new GameImpl.Placement(Color.BLACK, Location.B2),
				new GameImpl.Placement(Color.BLACK, Location.B3),
				new GameImpl.Placement(Color.BLACK, Location.B4),
				new GameImpl.Placement(Color.BLACK, Location.B2),
				new GameImpl.Placement(Color.BLACK, Location.B3),
				new GameImpl.Placement(Color.BLACK, Location.B1),
				new GameImpl.Placement(Color.BLACK, Location.B2),
				new GameImpl.Placement(Color.BLACK, Location.B3),
				new GameImpl.Placement(Color.BLACK, Location.B1),
				new GameImpl.Placement(Color.BLACK, Location.B2),
				new GameImpl.Placement(Color.BLACK, Location.B10)
		});
	
		game.nextTurn();
		assertFalse(game.move(Location.B2, Location.B_BEAR_OFF));
	}
	
	@Test
	public void shouldBearOffWithLessThanDieIfNoOtherMoveAvailable() {
		game.configure( new GameImpl.Placement[] {
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R_BEAR_OFF),
				new GameImpl.Placement(Color.RED, Location.R2)
		});
	
		game.nextTurn();
		game.nextTurn();
		assertTrue(game.getPlayerInTurn() == Color.RED);
		assertTrue(game.move(Location.R2, Location.R_BEAR_OFF));		
	}
}
