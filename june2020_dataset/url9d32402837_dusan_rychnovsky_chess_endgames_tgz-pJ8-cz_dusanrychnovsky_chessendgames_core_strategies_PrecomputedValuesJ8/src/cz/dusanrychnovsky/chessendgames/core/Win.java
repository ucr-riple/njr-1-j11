package cz.dusanrychnovsky.chessendgames.core;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class Win extends Result
{
	private final Player player;
	
	/**
	 * 
	 * @param player
	 */
	public Win(Player player) {
		this.player = player;
	}
	
	@Override
	public int hashCode() {
		return "win".hashCode() + player.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Win)) {
			return false;
		}
		
		Win other = (Win) obj;
		return player.equals(other.player);
	}
	
	@Override
	public String toString() {
		return player + " won!";
	}
}
