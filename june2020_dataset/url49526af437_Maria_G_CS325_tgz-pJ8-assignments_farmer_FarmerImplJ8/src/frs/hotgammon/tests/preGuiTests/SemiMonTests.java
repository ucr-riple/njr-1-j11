package frs.hotgammon.tests.preGuiTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import frs.hotgammon.common.GameImpl;
import frs.hotgammon.framework.Location;
import frs.hotgammon.tests.preGuiTests.stubs.Fixed_BlackStarts_SemiMonFactory;
import frs.hotgammon.tests.preGuiTests.stubs.Fixed_RedStarts_SemiMonFactory;
import frs.hotgammon.variants.movevalidators.CompleteMoveValidator;
import frs.hotgammon.variants.rolldeterminers.PairSequenceDeterminer;
import frs.hotgammon.variants.turndeterminers.AlternatingTurnDeterminer;
import frs.hotgammon.variants.winnerdeterminers.SixMoveWinnerDeterminer;

public class SemiMonTests {

	private GameImpl game = new GameImpl(new Fixed_BlackStarts_SemiMonFactory());
	
	@Before
	public void setup() { 
		game.newGame();
	}

	@Test
	public void redShouldOnlyMoveTowardRedInnerTableIfFixedBlack() {
		game.nextTurn();
		game.nextTurn();
		assertTrue(game.move(Location.B1, Location.B4));
		assertFalse(game.move(Location.B4,  Location.B1));
	}
	
	@Test
	public void blackShouldOnlyMoveTowardBlackInnerTableIfFixedBlack() {
		game.nextTurn();
		assertTrue(game.move(Location.R1,  Location.R5));
		assertFalse(game.move(Location.R5,  Location.R2));		
	}

	@Test
	public void moveShouldBeValidDieRollIfFixedBlack() {
		game.nextTurn();
		assertFalse(game.move(Location.R1,  Location.R6));	
	}

	@Test
	public void redShouldOnlyMoveTowardRedInnerTableIfFixedRed() {
		game.setFactory(new Fixed_RedStarts_SemiMonFactory());
		game.nextTurn();
		assertTrue(game.move(Location.B1, Location.B4));
		assertFalse(game.move(Location.B4,  Location.B1));
	}
	
	@Test
	public void blackShouldOnlyMoveTowardBlackInnerTableIfFixedRed() {
		game.setFactory(new Fixed_RedStarts_SemiMonFactory());
		game.nextTurn();
		game.nextTurn();
		assertTrue(game.move(Location.R1,  Location.R5));
		assertFalse(game.move(Location.R5,  Location.R2));		
	}

	@Test
	public void moveShouldBeValidDieRollIfFixedRed() {
		game.setFactory(new Fixed_RedStarts_SemiMonFactory());
		game.nextTurn();
		game.nextTurn();
		assertFalse(game.move(Location.R1,  Location.R6));	
	}
	
}
