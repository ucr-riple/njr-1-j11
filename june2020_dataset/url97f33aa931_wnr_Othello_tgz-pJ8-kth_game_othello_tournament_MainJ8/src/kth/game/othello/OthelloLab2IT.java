package kth.game.othello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Node;
import kth.game.othello.board.BoardHandlerFactory;
import kth.game.othello.board.factory.Diamond;
import kth.game.othello.player.ComputerPlayer;
import kth.game.othello.player.Player;
import kth.game.othello.player.Player.Type;
import kth.game.othello.player.movestrategy.GreedyStrategy;
import kth.game.othello.player.movestrategy.LowestStrategy;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.player.movestrategy.RandomStrategy;

import org.junit.Assert;
import org.junit.Test;

public class OthelloLab2IT {

	private MoveStrategy getNewMoveStrategy() {
		int randomNumber = new Random().nextInt(3);
		if (randomNumber == 0) {
			return new GreedyStrategy();
		}
		if (randomNumber == 1) {
			return new RandomStrategy();
		}
		if (randomNumber == 2) {
			return new LowestStrategy();
		}
		return null;
	}

	private OthelloFactory getOthelloFactory() {
		return new OthelloFactoryImpl();
	}

	private Player createComputer(String id) {
		return new ComputerPlayer(id, id, getNewMoveStrategy());
	}

	private void makeNumberOfComputerMoves(int numberOfMoves, Othello othello) {
		for (int i = 0; i < numberOfMoves; i++) {
			Assert.assertEquals(Type.COMPUTER, othello.getPlayerInTurn().getType());
			othello.move();
		}
	}

	private void makeAHumanMove(Othello othello, Player human) {
		for (Node node : othello.getBoard().getNodes()) {
			if (othello.isMoveValid(human.getId(), node.getId())) {
				othello.move(human.getId(), node.getId());
				return;
			}
		}
		throw new IllegalStateException();
	}

	@Test
	public void studyTheInitialScoreTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		String playerId = othello.getPlayers().get(0).getId();
		othello.start();
		Assert.assertEquals(2, othello.getScore().getPoints(playerId));
	}

	@Test
	public void studyTheScoreAfterAMoveTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		String playerId = othello.getPlayers().get(1).getId();
		othello.start(playerId);
		othello.move(playerId, othello.getBoard().getNode(5, 3).getId());
		Assert.assertEquals(4, othello.getScore().getPoints(playerId));
	}

	@Test
	public void twoHumanOnAClassicalBoardTest() {

		Othello othello = getOthelloFactory().createHumanGame();
		String playerId1 = othello.getPlayers().get(0).getId();
		String playerId2 = othello.getPlayers().get(1).getId();
		othello.start(othello.getPlayers().get(0).getId());

		// Make 8 human moves
		for (int i = 0; i < 8; i++) {
			makeAHumanMove(othello, othello.getPlayerInTurn());
		}

		// Check the score
		Assert.assertEquals(6, othello.getScore().getPoints(playerId1));
		Assert.assertEquals(6, othello.getScore().getPoints(playerId2));

		Assert.assertTrue(othello.isActive());
	}

	@Test
	public void threeComputersOnADiamondBoardTest() {
		List<Player> players = new ArrayList<>();
		String blackId = "black";
		String whiteId = "white";
		String orangeId = "orange";
		players.add(createComputer(blackId));
		players.add(createComputer(whiteId));
		players.add(createComputer(orangeId));
		Diamond diamond = new Diamond();
		Othello othello = getOthelloFactory().createGame(diamond.getNodes(11, players), players);
		othello.start();
		while (othello.isActive()) {
			othello.move();
		}

		HashMap<String, Integer> actualScore = new HashMap<>();
		actualScore.put(blackId, 0);
		actualScore.put(whiteId, 0);
		actualScore.put(orangeId, 0);
		othello.getBoard().getNodes().stream().filter(Node::isMarked).forEach(node -> {
			actualScore.put(node.getOccupantPlayerId(), actualScore.get(node.getOccupantPlayerId()) + 1);
		});

		// Check the score
		Assert.assertEquals(actualScore.get(blackId).intValue(), othello.getScore().getPoints(blackId));
		Assert.assertEquals(actualScore.get(whiteId).intValue(), othello.getScore().getPoints(whiteId));
		Assert.assertEquals(actualScore.get(orangeId).intValue(), othello.getScore().getPoints(orangeId));

		Assert.assertFalse(othello.isActive());
	}

	@Test
	public void twoComputerOnAClassicalBoardTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		othello.start(othello.getPlayers().get(0).getId());

		while (othello.isActive()) {
			// Make some moves
			for (int i = 0; othello.isActive() && i < 20; i++) {
				makeNumberOfComputerMoves(1, othello);
			}

			// Change one of the computers strategy
			othello.getPlayers().get(0).setMoveStrategy(getNewMoveStrategy());

		}
		Assert.assertFalse(othello.isActive());
	}
}
