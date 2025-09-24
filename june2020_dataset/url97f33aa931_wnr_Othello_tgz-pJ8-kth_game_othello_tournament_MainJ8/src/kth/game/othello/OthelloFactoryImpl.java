package kth.game.othello;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import kth.game.othello.board.*;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.ComputerPlayer;
import kth.game.othello.player.HumanPlayer;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.player.movestrategy.GreedyStrategy;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.player.turndecider.Rotation;
import kth.game.othello.player.turndecider.TurnDecider;
import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreImpl;
import kth.game.othello.score.ScoreItem;

/**
 * Implementation of the OthelloFactory interface.
 *
 * @author Mathias Lindblom
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class OthelloFactoryImpl implements OthelloFactory {
	@Override
	public Othello createComputerGame() {
		MoveStrategy greedyMoveStrategy = new GreedyStrategy();
		Player computer1 = new ComputerPlayer("computer1", "Bottler", greedyMoveStrategy);
		Player computer2 = new ComputerPlayer("computer2", "Bigbot", greedyMoveStrategy);
		return create2PlayerSquareOthelloGame(computer1, computer2);
	}

	@Override
	public Othello createHumanGame() {
		HumanPlayer human1 = new HumanPlayer("human1", "HeatoN");
		HumanPlayer human2 = new HumanPlayer("human2", "Kungen");
		return create2PlayerSquareOthelloGame(human1, human2);
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		MoveStrategy greedyMoveStrategy = new GreedyStrategy();
		Player human = new HumanPlayer("human", "HeatoN");
		Player computer = new ComputerPlayer("computer", "Bottler", greedyMoveStrategy);
		return create2PlayerSquareOthelloGame(human, computer);
	}

	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
		BoardHandler boardHandler = createOthelloBoardHandler(nodesData);
		PlayerHandler playerHandler = createPlayerHandler(players);
		Score score = createScore(boardHandler.getBoard().getNodes(), players);
		return new OthelloImpl(boardHandler, playerHandler, score);
	}

	private BoardHandler createOthelloBoardHandler(Set<NodeData> nodeData) {
		BoardImpl board = new BoardFactory().createBoard(nodeData);
		BoardHandler boardHandler = new BoardHandlerFactory().createOthelloBoardHandler(board);
		return boardHandler;
	}

	private OthelloImpl create2PlayerSquareOthelloGame(Player player1, Player player2) {
		List<Player> players = getPlayerList(player1, player2);
		PlayerHandler playerHandler = createPlayerHandler(players);
		BoardHandler boardHandler = createOthelloBoardHandler(new Square().getNodes(8, players));
		Score score = createScore(boardHandler.getBoard().getNodes(), players);
		return new OthelloImpl(boardHandler, playerHandler, score);
	}

	private PlayerHandler createPlayerHandler(List<Player> players) {
		List<String> playerIdList = players.stream().map(Player::getId).collect(Collectors.toList());
		TurnDecider turnDecider = new Rotation(playerIdList);
		return new PlayerHandler(players, turnDecider);
	}

	private Score createScore(List<Node> nodes, List<Player> players) {

		// Begin by calculating the initial score for all the players
		Map<String, Integer> initialScores = new HashMap<>();
		for (Player p : players) {
			initialScores.put(p.getId(), 0);
		}
		nodes.stream().filter(Node::isMarked).forEach(node -> {
			String playerId = node.getOccupantPlayerId();
			initialScores.put(playerId, (initialScores.containsKey(playerId)) ? initialScores.get(playerId) + 1 : 1);
		});

		// Create the score items with the initial score
		List<ScoreItem> scores = players.stream()
				.map(player -> new ScoreItem(player.getId(), initialScores.get(player.getId())))
				.collect(Collectors.toList());
		ScoreImpl score = new ScoreImpl(scores);

		// Here we make sure that score listens on each node
		for (Node node : nodes) {
			node.addObserver(score);
		}
		return score;
	}

	private List<Player> getPlayerList(Player... players) {
		List<Player> playerList = new ArrayList<>();
		Collections.addAll(playerList, players);
		return playerList;
	}
}
