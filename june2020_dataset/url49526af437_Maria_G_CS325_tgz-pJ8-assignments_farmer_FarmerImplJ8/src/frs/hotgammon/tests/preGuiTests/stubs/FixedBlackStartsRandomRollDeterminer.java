package frs.hotgammon.tests.preGuiTests.stubs;

import frs.hotgammon.RollDeterminer;

public class FixedBlackStartsRandomRollDeterminer implements RollDeterminer {

	private int[] diceRoll = new int[2];
	private final int[][] POSSIBLE_DICE_ROLLS = {{3,4}, {4,3}, {1,1}, {3,6}};
	private int index = 0;
		
	@Override
	public void rollDice() {
		diceRoll = POSSIBLE_DICE_ROLLS[index % 4];		
		
		if (diceRoll[0] == diceRoll[1]){
			diceRoll = new int[]{diceRoll[0], diceRoll[1], diceRoll[0], diceRoll[1]};
		}
		
		index++;
	}

	@Override
	public int[] getDiceRoll() {
		return diceRoll;
	}

	@Override
	public void reset() {
		diceRoll = new int[2];
		index = 0;
	}

}