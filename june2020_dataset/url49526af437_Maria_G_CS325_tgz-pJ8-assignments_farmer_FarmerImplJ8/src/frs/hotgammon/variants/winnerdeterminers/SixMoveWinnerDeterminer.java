package frs.hotgammon.variants.winnerdeterminers;

import frs.hotgammon.WinnerDeterminer;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Game;

public class SixMoveWinnerDeterminer implements WinnerDeterminer{

	@Override
	public Color winner(int turnCount) {
		return (turnCount == 6) ? Color.RED : Color.NONE;
	}

	@Override
	public void setGame(Game game) {
		// Do Nothing	
	}

}
