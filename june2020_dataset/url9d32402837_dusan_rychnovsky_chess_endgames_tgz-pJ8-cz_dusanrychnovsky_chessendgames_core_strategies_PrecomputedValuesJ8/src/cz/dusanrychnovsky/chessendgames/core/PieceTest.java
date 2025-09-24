package cz.dusanrychnovsky.chessendgames.core;

import java.util.List;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public abstract class PieceTest
{
	protected boolean containsPosition(List<Move> moves, Position position)
	{
		for (Move move : moves) {
			if (position.equals(move.getTo())) {
				return true;
			}
		}
		
		return false;
	}
}
