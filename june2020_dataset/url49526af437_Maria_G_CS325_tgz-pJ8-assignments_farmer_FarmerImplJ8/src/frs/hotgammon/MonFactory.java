package frs.hotgammon;

import frs.hotgammon.framework.Game;

public interface MonFactory {

	public MoveValidator createMoveValidator();
	public TurnDeterminer createTurnDeterminer();
	public WinnerDeterminer createWinnerDeterminer();
	public RollDeterminer createRollDeterminer();
	public void setGame(Game game);
	
}
