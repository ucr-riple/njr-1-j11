package cz.dusanrychnovsky.chessendgames.core;

import cz.dusanrychnovsky.chessendgames.core.strategies.Strategy;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class Game
{
	private final Strategy strategy;
	
	private Situation currentSituation;
	private final Player computer;
	
	/**
	 * 
	 * @param strategy
	 * @param currentSituation
	 * @param computer
	 */
	public Game(Strategy strategy, Situation currentSituation, Player computer) {
		this.strategy = strategy;
		this.currentSituation = currentSituation;
		this.computer = computer;
	}


	/**
	 * Performs the given move for the human player, if it is valid. Otherwise
	 * throws an {@link IllegalArgumentException}.
	 * 
	 * @param move
	 * @return
	 */
	public Situation doMove(Move move)
	{
		if (move.getPiece().getPlayer().equals(computer)) {
			throw new IllegalArgumentException(
				"Move [" + move + "] is not valid with regards to [" + currentSituation + "]."
			);
		}
		
		if (!move.isValid(currentSituation)) {
			throw new IllegalArgumentException(
				"Move [" + move + "] is not valid with regards to [" + currentSituation + "]."
			);
		}
		
		currentSituation = Situation.get(currentSituation, move);
		return currentSituation;
	}
	
	/**
	 * Chooses and performs the next move for the computer player.
	 * 
	 * @return
	 */
	public Situation playMove()
	{
		Move move = strategy.chooseMove(currentSituation, computer);
		
		currentSituation = Situation.get(currentSituation, move);
		return currentSituation;
	}
	
	/**
	 * 
	 * @param situation
	 */
	public void setCurrentSituation(Situation situation) {
		currentSituation = situation;
	}
	
	/**
	 * 
	 * @return
	 */
	public Situation getCurrentSituation() {
		return currentSituation;
	}
}
