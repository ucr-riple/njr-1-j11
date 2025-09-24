package kth.game.othello.tournament;

/**
 * A class encapsulating the tournament results of a player. Keeps track of the number of wins, losses and draws of the
 * player and provides methods for comparing different results.
 */
public class PlayerResult implements Comparable<PlayerResult> {

	private String name;
	private final int wins;
	private final int losses;
	private final int draws;

	/**
	 * Create a PlayerResult object with the specified results
	 * 
	 * @param name The name of the player
	 * @param wins The number of wins
	 * @param losses The number of losses
	 * @param draws The number of draws
	 */
	public PlayerResult(String name, int wins, int losses, int draws) {
		this.name = name;
		this.wins = wins;
		this.losses = losses;
		this.draws = draws;
	}

	/**
	 * Get the name of the player
	 * 
	 * @return The player name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the number of wins that the player has gotten
	 * 
	 * @return The number of wins
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * Get the number of losses that the player has gotten
	 * 
	 * @return The number of losses
	 */
	public int getLosses() {
		return losses;
	}

	/**
	 * Get the number of draws that the player has gotten
	 * 
	 * @return The number of draws
	 */
	public int getDraws() {
		return draws;
	}

	/**
	 * Return a new PlayerResult object with one more win
	 * 
	 * @return The new PlayerResult object
	 */
	public PlayerResult incrementWins() {
		return new PlayerResult(name, wins + 1, losses, draws);
	}

	/**
	 * Return a new PlayerResult object with one more loss
	 * 
	 * @return The new PlayerResult object
	 */
	public PlayerResult incrementLosses() {
		return new PlayerResult(name, wins, losses + 1, draws);
	}

	/**
	 * Return a new PlayerResult object with one more draw
	 * 
	 * @return The new PlayerResult object
	 */
	public PlayerResult incrementDraws() {
		return new PlayerResult(name, wins, losses, draws + 1);
	}

	/**
	 * Determines if the score of two PlayerResults are equal. Only wins and draws are taken into account.
	 * 
	 * @param pr Another PlayerResult object
	 * @return true if the scores are equal
	 */
	public boolean isScoreEqual(PlayerResult pr) {
		return wins == pr.wins && draws == pr.draws;
	}

	/**
	 * Compares the number of wins and draws of two PlayerResult objects. If they are both equal, lexicographical
	 * ordering is used
	 */
	@Override
	public int compareTo(PlayerResult pr) {
		if (wins > pr.wins)
			return -1;
		if (wins < pr.wins)
			return 1;
		if (draws > pr.draws)
			return -1;
		if (draws < pr.draws)
			return 1;
		return name.compareTo(pr.name);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PlayerResult))
			return false;
		PlayerResult pr = (PlayerResult) o;
		return wins == pr.wins && losses == pr.losses && draws == pr.draws && name.equals(pr.name);
	}

	@Override
	public int hashCode() {
		return 17 * wins + 31 * losses + 37 * draws + 53 * name.hashCode();
	}

}
