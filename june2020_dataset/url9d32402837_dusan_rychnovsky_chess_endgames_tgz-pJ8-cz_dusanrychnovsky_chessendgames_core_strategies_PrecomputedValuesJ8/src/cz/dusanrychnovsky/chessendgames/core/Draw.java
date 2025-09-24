package cz.dusanrychnovsky.chessendgames.core;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class Draw extends Result
{
	@Override
	public int hashCode() {
		return "draw".hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Draw);
	}
	
	@Override
	public String toString() {
		return "It's a draw!";
	}
}
