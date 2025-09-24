package frs.hotgammon.tests.preGuiTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import frs.hotgammon.common.GameImpl;
import frs.hotgammon.framework.Color;
import frs.hotgammon.tests.preGuiTests.stubs.Fixed_RedStarts_EpsilonMonFactory;

public class RedStartsEpsilonMonTests {
	
	private GameImpl game;

	@Before
	public void setup() { 
		game = new GameImpl(new Fixed_RedStarts_EpsilonMonFactory());
		game.newGame();
	}

	@Test
	public void redShouldBeFirstPlayer() {
		game.nextTurn();
		assertEquals(Color.RED, game.getPlayerInTurn());
	}
		
}
