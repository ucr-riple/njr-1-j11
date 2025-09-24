package kth.game.othello.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import kth.game.othello.board.Node;

/**
 * Implements the Score interface.
 *
 * @author Mathias Lindblom
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class ScoreImpl extends Observable implements Score, Observer {
	private final HashMap<String, ScoreItem> playerScores;

	/**
	 * Constructs a Score instance that will observe given nodes for changes and will update the player scores according
	 * to node changes.
	 * 
	 * @param playerScoreItems A list of the player score items that the Score instance should handle. This list must
	 *            contain all players that are able to occupy any of the given nodes
	 */
	public ScoreImpl(List<ScoreItem> playerScoreItems) {
		playerScores = new HashMap<>();

		for (ScoreItem scoreItem : playerScoreItems) {
			playerScores.put(scoreItem.getPlayerId(), scoreItem);
		}
	}

	@Override
	public List<ScoreItem> getPlayersScore() {
		return new ArrayList<>(playerScores.values());
	}

	@Override
	public int getPoints(String playerId) {
		ScoreItem scoreItem = playerScores.get(playerId);

		if (scoreItem == null) {
			// TODO: Should this not throw? Interface says it doesn't throw :(
			return 0;
		}

		return scoreItem.getScore();
	}

	@Override
	public void update(Observable o, Object arg) {
		onNodeChanged((Node) o, (String) arg);
	}

	/**
	 * Will update the score of the players affected by a node swap. Also notifies the observers of the score instance
	 * which players had their scores modified.
	 * 
	 * @param node The node that were swapped
	 * @param previousOccupantPlayerId The previous player occupying the node (can be null if no previous player
	 *            occupied it)
	 */
	private void onNodeChanged(Node node, String previousOccupantPlayerId) {
		List<String> modifiedScorePlayers = new ArrayList<>();

		String newOccupantPlayerId = node.getOccupantPlayerId();

		if (newOccupantPlayerId != null) {
			addToScore(newOccupantPlayerId, 1);
			modifiedScorePlayers.add(newOccupantPlayerId);
		}

		if (previousOccupantPlayerId != null) {
			addToScore(previousOccupantPlayerId, -1);
			modifiedScorePlayers.add(previousOccupantPlayerId);
		}

		setChanged();
		notifyObservers(modifiedScorePlayers);
	}

	/**
	 * Adds the given value to the ScoreItem that belongs to the given playerId.
	 *
	 * @param playerId The id of the player score item to update. Must be a valid player id.
	 * @param value The value to add to the player score item (can be any integer value).
	 */
	private void addToScore(String playerId, int value) {
		ScoreItem currentScore = playerScores.get(playerId);
		playerScores.put(playerId, new ScoreItem(playerId, currentScore.getScore() + value));
	}
}
