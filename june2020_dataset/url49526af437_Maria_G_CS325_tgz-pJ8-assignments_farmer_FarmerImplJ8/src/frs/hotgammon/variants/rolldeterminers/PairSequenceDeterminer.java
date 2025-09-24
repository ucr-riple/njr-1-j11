package frs.hotgammon.variants.rolldeterminers;

import frs.hotgammon.RollDeterminer;

public class PairSequenceDeterminer implements RollDeterminer{

	
	private final int[][] DICE_ROLLS = {  {1,2}, {3,4}, {5,6} };
	private int diceRollIdx = 2;
	
	@Override
	public void rollDice() {
		diceRollIdx = ((diceRollIdx < 2) ? diceRollIdx + 1 : 0);
	}

	@Override
	public int[] getDiceRoll() {
		return DICE_ROLLS[diceRollIdx];
	}

	@Override
	public void reset() {
		diceRollIdx = 2;
	}

}
