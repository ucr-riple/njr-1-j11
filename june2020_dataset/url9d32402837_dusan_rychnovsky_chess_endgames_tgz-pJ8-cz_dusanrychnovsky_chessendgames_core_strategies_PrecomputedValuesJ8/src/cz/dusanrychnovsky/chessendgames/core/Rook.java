package cz.dusanrychnovsky.chessendgames.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class Rook extends Piece
{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param player
	 */
	public Rook(Player player) {
		super(player);
	}

	@Override
	public List<Move> generateMoves(Position from)
	{
		List<Move> result = new ArrayList<Move>();
		
		List<Move> horizontalMoves = generateHorizontalMoves(from);
		result.addAll(horizontalMoves);
		
		List<Move> verticalMoves = generateVerticalMoves(from);
		result.addAll(verticalMoves);
		
		return result;
	}
	
	/**
	 * 
	 * @param from
	 * @return
	 */
	private List<Move> generateVerticalMoves(Position from)
	{
		List<Move> result = new ArrayList<Move>();
		
		for (Row row : Row.values())
		{
			if (!row.equals(from.getRow())) 
			{
				Position to = Position.get(from.getColumn(), row); 
				result.add(new Move(this, from, to));
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param from
	 * @return
	 */
	private List<Move> generateHorizontalMoves(Position from)
	{
		List<Move> result = new ArrayList<Move>();
		
		for (Column column : Column.values())
		{
			if (!column.equals(from.getColumn())) 
			{
				Position to = Position.get(column, from.getRow());
				result.add(new Move(this, from, to));
			}
		}
		
		return result;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + "Rook".hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Rook)) {
			return false;
		}
		
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return getPlayer().toString() + " Rook";
	}
}
