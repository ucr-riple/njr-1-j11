package kth.game.othello.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import kth.game.othello.player.turndecider.TurnDecider;
import kth.game.othello.rules.Rules;

/**
 * A handler with the overall responsibility regarding the players in the game.
 *
 * @author Mathias Lindblom
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class PlayerHandler {
	private final List<Player> players;
	private final TurnDecider turnDecider;

	/**
	 * Constructor stores the players and the {@link kth.game.othello.player.turndecider.TurnDecider} to be used when
	 * deciding which player is the next one to make a move.
	 *
	 * @param players The list of players to play the game.
	 * @param turnDecider The {@link kth.game.othello.player.turndecider.TurnDecider} to be used by the handler.
	 */
	public PlayerHandler(List<Player> players, TurnDecider turnDecider) {
		this.players = players;
		this.turnDecider = turnDecider;
	}

	/**
	 * Sets the starting player according to the given player ID.
	 *
	 * @param playerId The player ID that corresponds to the starting player.
	 * @return The {@link Player} that was selected using the given player ID.
	 */
	public Player setStartingPlayer(String playerId) {
		turnDecider.setFirstPlayerInTurn(playerId);
		return getPlayerInTurn();
	}

	/**
	 * @return A copy of the list of players.
	 */
	public List<Player> getPlayers() {
		return new ArrayList<>(players);
	}

	/**
	 * @return A list of all playerIds
	 */
	public List<String> getPlayerIds() {
		return getPlayerIds(players);
	}

	/**
	 * Returns the player corresponding the given player ID.
	 *
	 * @param playerId The ID of the player.
	 * @return The {@link Player} matching the player ID.
	 */
	public Player getPlayer(String playerId) {
		Optional<Player> oPlayer = getPlayers().stream().filter(t -> playerId != null && t.getId().equals(playerId))
				.findAny();
		if (oPlayer.isPresent()) {
			return oPlayer.get();
		}
		return null;
	}

	/**
	 * Will update the next player in turn that is able to make a move. Will return a list containing the ID´ of the
	 * players that had their turn skipped in order from the the first skipped turn to the last. A turn is skipped when
	 * the player in turn is not able to make a move. If no player is skipped or if no player can make a move. The
	 * returned list will be empty.
	 *
	 * @param rules The rules to check for valid moves.
	 * @return A list of players that were in turn but were skipped since they could not make a move.
	 */
	public List<Player> updatePlayerInTurn(Rules rules) {
		return getPlayers(turnDecider.updatePlayerInTurn(rules));
	}

	/**
	 * @return The player in turn. Null if no player is in turn.
	 */
	public Player getPlayerInTurn() {
		return getPlayer(turnDecider.getPlayerInTurn());
	}

	/**
	 * @return The number of players.
	 */
	public int getNumPlayers() {
		return getPlayers().size();
	}

	/**
	 * @return A random player.
	 */
	public Player getRandomPlayer() {
		return players.get(new Random().nextInt(players.size()));
	}

	/**
	 * Converts a list of type {@link Player} to a list of player ID´s.
	 *
	 * @param players The list of players to convert to ID´s.
	 * @return A list of player ID's.
	 */
	public List<String> getPlayerIds(List<Player> players) {
		return players.stream().map(Player::getId).collect(Collectors.toList());
	}

	/**
	 * Converts a list of player ID´s to a list of type {@link Player}.
	 *
	 * @param playerIds The list of player ID´s to convert.
	 * @return The players that corresponded to the given ID´s.
	 */
	public List<Player> getPlayers(List<String> playerIds) {
		List<Player> playersInTurn = new ArrayList<>();
		for (int i = 0; i < playerIds.size(); i++) {
			playersInTurn.add(getPlayer(playerIds.get(i)));
		}
		return playersInTurn;
	}
}
