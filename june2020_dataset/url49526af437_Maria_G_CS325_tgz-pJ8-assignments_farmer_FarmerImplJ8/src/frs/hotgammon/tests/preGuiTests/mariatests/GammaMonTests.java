package frs.hotgammon.tests.preGuiTests.mariatests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import frs.hotgammon.common.GameImpl;
import frs.hotgammon.common.GameImpl.Placement;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Location;
import frs.hotgammon.variants.movevalidators.SimpleMoveValidator;
import frs.hotgammon.variants.rolldeterminers.PairSequenceDeterminer;
import frs.hotgammon.variants.turndeterminers.AlternatingTurnDeterminer;
import frs.hotgammon.variants.winnerdeterminers.BearOffWinnerDeterminer;

public class GammaMonTests {

	private GameImpl game;

	@Before
	public void setup() {
		game = new GameImpl(new SimpleMoveValidator(), new BearOffWinnerDeterminer(), new AlternatingTurnDeterminer(), new PairSequenceDeterminer());
		game.newGame();
	}

	@Test
	public void shouldBeWinnerIsBlack() {
		game.configure(new Placement[] { //Add 15 BLACK pieces to the B_BEAR_OFF
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF),
			new Placement(Color.BLACK, Location.B_BEAR_OFF)
		});

		assertEquals(Color.BLACK, game.winner());
	}

	@Test
	public void shouldBeWinnerIsNone() {
		assertEquals(Color.NONE, game.winner());
	}

	@Test
	public void shouldBeWinnerIsRed() {
		game.configure(new Placement[] {//Add 15 RED pieces to the R_BEAR_OFF
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF),
			new Placement(Color.RED, Location.R_BEAR_OFF) 
			});

		assertEquals(Color.RED, game.winner());
	}

}
