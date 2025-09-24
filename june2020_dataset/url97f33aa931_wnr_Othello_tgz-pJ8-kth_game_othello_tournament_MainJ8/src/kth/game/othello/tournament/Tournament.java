package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactoryImpl;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.ComputerPlayer;
import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.score.Score;
import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

/**
 * Class implementing a tournament for different move strategies. Can be used to just get the results of the tournament,
 * or to show the games in a gui first.
 */
public class Tournament {

	public static final int BOARD_SIZE = 8;
	public static final int TIME_BETWEEN_SWAPS = 50;
	public static final int TIME_BETWEEN_MOVES = 300;

	private List<MoveStrategy> strategies;

	public Tournament(List<MoveStrategy> strategies) {
		this.strategies = strategies;
	}

	/**
	 * Play this tournament and return the result
	 * 
	 * @param showView Determines whether the tournament should be displayed in a gui
	 * @return The results of the different players/strategies, ordered after score
	 */
	public List<PlayerResult> play(boolean showView) {
		List<Player> players = new ArrayList<>();
		for (int i = 0; i < strategies.size(); i++) {
			players.add(new ComputerPlayer("cpu" + i, strategies.get(i).getName(), strategies.get(i)));
		}

		List<PlayerResult> results = new ArrayList<>();
		for (Player player : players) {
			results.add(new PlayerResult(player.getName(), 0, 0, 0));
		}

		for (int i = 0; i < players.size(); i++) {
			for (int j = 0; j < players.size(); j++) {
				if (i == j)
					continue;
				int result = playGame(players.get(i), players.get(j), showView);
				if (result == -1) {
					results.set(i, results.get(i).incrementWins());
					results.set(j, results.get(j).incrementLosses());
				} else if (result == 1) {
					results.set(i, results.get(i).incrementLosses());
					results.set(j, results.get(j).incrementWins());
				} else {
					results.set(i, results.get(i).incrementDraws());
					results.set(j, results.get(j).incrementDraws());
				}
			}
		}

		Collections.sort(results);
		return results;
	}

	/**
	 * Helper method for playing a game. Returns -1 if the first player wins, 1 if the second player wins and 0 if it's
	 * a draw.
	 */
	private int playGame(Player player1, Player player2, boolean showView) {
		List<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
		Set<NodeData> nodeData = new Square().getNodes(BOARD_SIZE, players);
		Othello othello = new OthelloFactoryImpl().createGame(nodeData, players);
		if (showView) {
			OthelloView view = OthelloViewFactory.create(othello, TIME_BETWEEN_SWAPS, TIME_BETWEEN_MOVES);
			view.start(player1.getId());
		} else {
			othello.start(player1.getId());
			while (othello.isActive()) {
				othello.move();
			}
		}

		Score score = othello.getScore();
		int score1 = score.getPoints(player1.getId());
		int score2 = score.getPoints(player2.getId());
		if (score1 > score2)
			return -1;
		if (score1 < score2)
			return 1;
		return 0;
	}

}
