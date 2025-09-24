package kth.game.othello.score;

import java.util.List;
import java.util.Observer;

/**
 * The responsibility of this class is to control the score for the players. It must inform all observers when a score
 * has changed.
 * 
 * @author Tomas Ekholm
 */
public interface Score {

	/**
	 * Adds an observer to the score. The observer will be notified when a score has changed. In this case a
	 * {@link java.util.List} of {@link String}s with the ids of the players that had a change of the score will be
	 * sent.
	 * 
	 * @param observer an observer to the score
	 */
	public void addObserver(Observer observer);

	/**
	 * A list of the score of all players. The list is sorted in decreasing order regarding the score.
	 * 
	 * @return a map where the keys are the id of the players and the values are the score for that player.
	 */
	public List<ScoreItem> getPlayersScore();

	/**
	 * Get the score of a specific player
	 * 
	 * @param playerId the id of the player
	 * @return the score
	 */
	public int getPoints(String playerId);

}
