package kth.game.othello.player;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.player.turndecider.Rotation;
import kth.game.othello.player.turndecider.TurnDecider;

import kth.game.othello.rules.Rules;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class RotationTest {

	private static final String[] Ids = { "playerId1", "playerId2", "playerId3" };

	@Test
	public void initiateTest() {
		TurnDecider tr = new Rotation(Arrays.asList(Ids));

		tr.setFirstPlayerInTurn(Ids[2]);
		Assert.assertEquals(Ids[2], tr.getPlayerInTurn());

		// Now test with wrong ID
		tr.setFirstPlayerInTurn("Strange ID");
		Assert.assertEquals(null, tr.getPlayerInTurn());
	}

	@Test
	public void updateNextPlayerInTurnTest() {
		List<String> skippedList;

		Rules mockedRules = Mockito.mock(Rules.class);

		TurnDecider tr = createRotation(Ids[1], Ids);
		when(mockedRules.hasValidMove(Ids[0])).thenReturn(true);
		when(mockedRules.hasValidMove(Ids[1])).thenReturn(false);
		when(mockedRules.hasValidMove(Ids[2])).thenReturn(false);
		skippedList = tr.updatePlayerInTurn(mockedRules);
		Assert.assertEquals(Ids[0], tr.getPlayerInTurn());
		List<String> expectedList = new ArrayList<>();
		expectedList.add(Ids[2]);
		Assert.assertEquals(expectedList, skippedList);

		tr = createRotation(Ids[1], Ids);
		when(mockedRules.hasValidMove(Ids[0])).thenReturn(false);
		when(mockedRules.hasValidMove(Ids[1])).thenReturn(true);
		skippedList = tr.updatePlayerInTurn(mockedRules);
		Assert.assertEquals(Ids[1], tr.getPlayerInTurn());
		expectedList.clear();
		expectedList.add(Ids[2]);
		expectedList.add(Ids[0]);
		Assert.assertEquals(expectedList, skippedList);
	}

	@Test(expected = IllegalStateException.class)
	public void updatePlayerInTurnUninitializedExceptionTest() {
		TurnDecider tr = new Rotation(null);
		Rules mockedRules = Mockito.mock(Rules.class);
		tr.updatePlayerInTurn(mockedRules);
	}

	private TurnDecider createRotation(String startingPlayer, String... playerIds) {
		TurnDecider turnDecider = new Rotation(Arrays.asList(playerIds));
		turnDecider.setFirstPlayerInTurn(startingPlayer);
		return turnDecider;
	}
}
