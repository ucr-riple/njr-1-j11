package frs.hotgammon.tests.preGuiTests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import frs.hotgammon.MonFactory;
import frs.hotgammon.common.GameImpl;
import frs.hotgammon.tests.preGuiTests.stubs.Fixed_BlackStarts_EpsilonMonFactory;
import frs.hotgammon.tests.preGuiTests.stubs.Fixed_BlackStarts_SemiMonFactory;
import frs.hotgammon.tests.preGuiTests.stubs.Fixed_RedStarts_EpsilonMonFactory;
import frs.hotgammon.tests.preGuiTests.stubs.Fixed_RedStarts_SemiMonFactory;

@RunWith(value = Parameterized.class)
public class RandomRollDeterminerTests {
	
	private GameImpl game;

	public RandomRollDeterminerTests(MonFactory factory){
		game = new GameImpl(factory);
		game.newGame();			
	}
	
	 @Parameters
	 public static Collection<MonFactory[]> data() {
		   MonFactory[][] data = new MonFactory[][] { 
			   {new Fixed_BlackStarts_EpsilonMonFactory()},
			   {new Fixed_RedStarts_EpsilonMonFactory()},
			   {new Fixed_BlackStarts_SemiMonFactory()},
			   {new Fixed_RedStarts_SemiMonFactory()},			   			   
		   };
		   return Arrays.asList(data); 
	 }

	@Test
	public void shouldDoubleMovesIfDoublesRolled() {
		game.nextTurn();
		game.nextTurn();
		game.nextTurn();
		assertEquals(4, game.getNumberOfMovesLeft());
	}
}
