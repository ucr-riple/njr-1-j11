package kth.game.othello;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import kth.game.othello.board.Node;
import kth.game.othello.board.BoardHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class OthelloImplTest {
	private static final String PLAYER_ID = "playerId1";
	private static final String NODE_ID = "nodeId";

	@Test
	public void moveTest() throws NoSuchFieldException, IllegalAccessException {
		BoardHandler obhMock = mock(BoardHandler.class);
		PlayerHandler phMock = mock(PlayerHandler.class);

		OthelloImpl othello1 = new OthelloImpl(obhMock, phMock, null);

		Player playerMock = mock(Player.class);
		String playerId1 = "playerId1";
		String nodeId = "nodeId";
		List<Node> nodeList = new ArrayList<>();
		nodeList.add(mock(Node.class));

		when(obhMock.move(playerId1, nodeId)).thenReturn(nodeList);
		when(phMock.getPlayerInTurn()).thenReturn(playerMock);
		when(playerMock.getId()).thenReturn(playerId1);

		List<Node> returnList = othello1.move(playerId1, nodeId);
		Assert.assertEquals(returnList, nodeList);
		verify(phMock).updatePlayerInTurn(obhMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void moveWithNoPlayerInTurnExceptionTest() {
		PlayerHandler phMock = mock(PlayerHandler.class);
		OthelloImpl othello = new OthelloImpl(null, phMock, null);
		when(phMock.getPlayerInTurn()).thenReturn(null);
		othello.move("playerId", "nodeId");
	}

	@Test(expected = IllegalArgumentException.class)
	public void moveWrongPlayerExceptionTest() {
		PlayerHandler phMock = mock(PlayerHandler.class);
		Player playerMock = mock(Player.class);
		OthelloImpl othello = new OthelloImpl(null, phMock, null);

		when(phMock.getPlayerInTurn()).thenReturn(playerMock);
		when(playerMock.getId()).thenReturn("playerId1");
		othello.move("playerId2", "nodeId");
	}

	@Test
	public void gameFinishedObserversTest() {
		OthelloImpl othello = getMockedOthelloGame();
		Observer observer1 = mock(Observer.class);
		Observer observer2 = mock(Observer.class);
		othello.addGameFinishedObserver(observer1);
		othello.addGameFinishedObserver(observer2);
		when(othello.isActive()).thenReturn(true);

		othello.move(PLAYER_ID, NODE_ID);
		verify(observer1, never()).update(othello, null);
		verify(observer2, never()).update(othello, null);

		when(othello.isActive()).thenReturn(false);

		othello.move(PLAYER_ID, NODE_ID);
		verify(observer1, times(1)).update(othello, null);
		verify(observer2, times(1)).update(othello, null);

		Assert.assertFalse(othello.isActive());
	}

	@Test
	public void moveObserversTest() {
		OthelloImpl othello = getMockedOthelloGame();
		Observer observer1 = mock(Observer.class);
		Observer observer2 = mock(Observer.class);
		othello.addMoveObserver(observer1);
		othello.addMoveObserver(observer2);

		List<Node> swappedNodes = othello.move(PLAYER_ID, NODE_ID);
		verify(observer1, times(1)).update(othello, swappedNodes);
		verify(observer2, times(1)).update(othello, swappedNodes);
	}

	private OthelloImpl getMockedOthelloGame() {
		BoardHandler obhMock = mock(BoardHandler.class);
		PlayerHandler phMock = mock(PlayerHandler.class);

		OthelloImpl othello = new OthelloImpl(obhMock, phMock, null);

		Player playerMock = mock(Player.class);
		String playerId1 = PLAYER_ID;
		String nodeId = NODE_ID;
		List<Node> nodeList = new ArrayList<>();
		nodeList.add(mock(Node.class));

		when(obhMock.move(playerId1, nodeId)).thenReturn(nodeList);
		when(phMock.getPlayerInTurn()).thenReturn(playerMock);
		when(playerMock.getId()).thenReturn(playerId1);

		return othello;
	}
}