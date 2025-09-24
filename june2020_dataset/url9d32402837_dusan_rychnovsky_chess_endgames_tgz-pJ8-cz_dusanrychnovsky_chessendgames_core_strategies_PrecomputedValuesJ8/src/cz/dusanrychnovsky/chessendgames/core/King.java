package cz.dusanrychnovsky.chessendgames.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class King extends Piece
{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param player
	 */
	public King(Player player) {
		super(player);
	}

	@Override
	public List<Move> generateMoves(Position from)
	{
		List<Move> result = new ArrayList<Move>();
		
		Column column = from.getColumn();
		Row row = from.getRow();
		
		Position to;
		
		if (!row.isFirst())
		{
			if (!column.isFirst()) 
			{
				to = Position.get(column.previous(), row.previous());
				result.add(new Move(this, from, to));
			}
			
			to = Position.get(column, row.previous());
			result.add(new Move(this, from, to));
			
			if (!column.isLast())
			{
				to = Position.get(column.next(), row.previous());
				result.add(new Move(this, from, to));
			}
		}
		
		if (!column.isLast()) 
		{
			to = Position.get(column.next(), row);
			result.add(new Move(this, from, to));
		}
		
		if (!row.isLast())
		{
			if (!column.isLast())
			{
				to = Position.get(column.next(), row.next());
				result.add(new Move(this, from, to));
			}
			
			to = Position.get(column, row.next());
			result.add(new Move(this, from, to));
			
			if (!column.isFirst()) 
			{
				to = Position.get(column.previous(), row.next());
				result.add(new Move(this, from, to));
			}
		}
		
		if (!column.isFirst()) 
		{
			to = Position.get(column.previous(), row);
			result.add(new Move(this, from, to));
		}
		
		return result;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + "King".hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof King)) {
			return false;
		}
		
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return getPlayer().toString() + " King";
	}
}
