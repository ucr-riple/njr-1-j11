package kth.game.othello.player;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kth.game.othello.player.turndecider.TurnDecider;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PlayerHandlerTest {

	private static final String[] Ids = { "playerId1", "playerId2", "playerId3" };

	@Test
	public void initiateStartingPlayerTest() throws NoSuchFieldException, IllegalAccessException {
		TurnDecider trMock = Mockito.mock(TurnDecider.class);
		PlayerHandler ph = createPlayerHandler(trMock, Ids[0], Ids[1]);
		ph.setStartingPlayer(Ids[1]);
		verify(trMock).setFirstPlayerInTurn(Ids[1]);
	}

	@Test
	public void getPlayerTest() {
		PlayerHandler ph = createPlayerHandler(null, Ids);
		Assert.assertEquals(Ids[1], ph.getPlayer(Ids[1]).getId());
		Player returnedPlayer = ph.getPlayer("This is not an ID");
		Assert.assertEquals(null, returnedPlayer);
	}

	@Test
	public void getPlayersTest() {
		PlayerHandler ph = createPlayerHandler(null, Ids);
		List<Player> players = ph.getPlayers();
		List<String> playerIds = Arrays.asList(Ids);
		Assert.assertEquals(playerIds, ph.getPlayerIds(players));
		Assert.assertEquals(players, ph.getPlayers(playerIds));
	}

	private PlayerHandler createPlayerHandler(TurnDecider turnDecider, String... IDs) {
		List<Player> players = new ArrayList<>();
		for (int i = 0; i < IDs.length; i++) {
			Player playerMock = Mockito.mock(Player.class);
			when(playerMock.getId()).thenReturn(IDs[i]);
			players.add(playerMock);
		}
		return new PlayerHandler(players, turnDecider);
	}
}
