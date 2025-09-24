package cz.dusanrychnovsky.chessendgames.core.strategies;

import cz.dusanrychnovsky.chessendgames.core.Column;
import cz.dusanrychnovsky.chessendgames.core.Move;
import cz.dusanrychnovsky.chessendgames.core.Piece;
import cz.dusanrychnovsky.chessendgames.core.Player;
import cz.dusanrychnovsky.chessendgames.core.Position;
import cz.dusanrychnovsky.chessendgames.core.Rook;
import cz.dusanrychnovsky.chessendgames.core.Row;
import cz.dusanrychnovsky.chessendgames.core.Situation;

public class Dummy extends Strategy
{
	@Override
	public Move chooseMove(Situation situation, Player player)
	{
		Rook rook = getRook(player);
		
		Position from = situation.getPosition(rook);
		Column fromColumn = from.getColumn();
		Row fromRow = from.getRow();
		
		if (!from.getColumn().isLast())
		{
			Position to = Position.get(fromColumn.next(), fromRow);
			if (!situation.isOccupied(to)) {
				return new Move(rook, from, to);
			}
		}
		
		if (!from.getColumn().isFirst())
		{
			Position to = Position.get(fromColumn.previous(), fromRow);
			if (!situation.isOccupied(to)) {
				return new Move(rook, from, to);
			}
		}
		
		throw new IllegalArgumentException("Cannot move! Surrounded!");
	}
	
	private Rook getRook(Player player)
	{
		for (Piece piece : player.getPieces())
		{
			if (piece instanceof Rook) {
				return (Rook) piece;
			}
		}
		
		throw new IllegalArgumentException(
			"The given player does not have a rook available!"
		);
	}
}
