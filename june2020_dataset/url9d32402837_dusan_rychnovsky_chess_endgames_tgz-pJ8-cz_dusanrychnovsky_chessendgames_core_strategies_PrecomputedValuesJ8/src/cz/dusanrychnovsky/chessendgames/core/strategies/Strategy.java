package cz.dusanrychnovsky.chessendgames.core.strategies;

import cz.dusanrychnovsky.chessendgames.core.Move;
import cz.dusanrychnovsky.chessendgames.core.Player;
import cz.dusanrychnovsky.chessendgames.core.Situation;

public abstract class Strategy
{
	/**
	 * Chooses the best move for the given player in the given situation.
	 * 
	 * @param situation
	 * @param player
	 * @return
	 */
	public abstract Move chooseMove(Situation situation, Player player);
}
