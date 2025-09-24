package frs.hotgammon.tests.preGuiTests.mariatests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import frs.hotgammon.common.GameImpl;
import frs.hotgammon.framework.Color;
import frs.hotgammon.variants.movevalidators.SimpleMoveValidator;
import frs.hotgammon.variants.rolldeterminers.PairSequenceDeterminer;
import frs.hotgammon.variants.turndeterminers.AceyDeuceyTurnDeterminer;
import frs.hotgammon.variants.winnerdeterminers.SixMoveWinnerDeterminer;

public class DeltaMonTests {


	private GameImpl game;

	@Before
	public void setup() { 
		game = new GameImpl(new SimpleMoveValidator(), new SixMoveWinnerDeterminer(), new AceyDeuceyTurnDeterminer(), new PairSequenceDeterminer());
		game.newGame();
	}
	
	@Test
	public void shouldRepeatSamePlayerInTurnIf1_2IsRolled() {
		game.nextTurn(); //1_2
		assertEquals(Color.BLACK, game.getPlayerInTurn());
		game.nextTurn(); //3_4
		assertEquals(Color.BLACK,  game.getPlayerInTurn()); 
		game.nextTurn(); //5_6
		assertEquals(Color.RED,  game.getPlayerInTurn());
		game.nextTurn(); //1_2
		assertEquals(Color.BLACK, game.getPlayerInTurn());
		game.nextTurn(); //3_4
		assertEquals(Color.BLACK,  game.getPlayerInTurn()); 
		game.nextTurn(); //5_6
		assertEquals(Color.RED,  game.getPlayerInTurn());
	}


}
