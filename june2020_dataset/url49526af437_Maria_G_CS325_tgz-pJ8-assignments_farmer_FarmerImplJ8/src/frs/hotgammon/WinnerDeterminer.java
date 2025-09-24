package frs.hotgammon;

import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Game;

public interface WinnerDeterminer {

	public Color winner(int turnCount);
	public void setGame(Game game);
}
