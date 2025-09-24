package frs.hotgammon.tests.preGuiTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import frs.hotgammon.common.GameImpl;
import frs.hotgammon.framework.Color;
import frs.hotgammon.tests.preGuiTests.stubs.Fixed_BlackStarts_EpsilonMonFactory;

public class BlackStartsEpsilonMonTests {
	
	private GameImpl game;

	@Before
	public void setup() { 
		game = new GameImpl(new Fixed_BlackStarts_EpsilonMonFactory());
		game.newGame();
	}

	@Test
	public void blackShouldBeFirstPlayer() {
		game.nextTurn();
		assertEquals(Color.BLACK, game.getPlayerInTurn());
	}
		
}
