package cz.dusanrychnovsky.chessendgames.core;

import java.util.Iterator;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public enum Column
{
	CA,
	CB,
	CC,
	CD,
	CE,
	CF,
	CG,
	CH;
	
	private static final Column[] values = values();
	
	/**
	 * 
	 * @param ordinal
	 * @return
	 */
	public static Column get(int ordinal)
	{
		try {
			return values[ordinal];
		}
		catch (ArrayIndexOutOfBoundsException ex) {
			throw new IllegalArgumentException(
				"Invalid column ordinal [" + ordinal + "]."
			);
		}
	}
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public boolean preceeds(Column other) {
		return ordinal() < other.ordinal();
	}
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public int distance(Column other) {
		return Math.abs(ordinal() - other.ordinal());
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isFirst() {
		return this.equals(Column.CA);
	}
	
	/**
	 * 
	 * @return
	 */
	public Column previous()
	{
		if (isFirst()) {
			throw new UnsupportedOperationException(
				"No previous column for column CA."
			);
		}
		
		int ordinal = ordinal();
		return values[ordinal - 1];
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isLast() {
		return this.equals(Column.CH);
	}
	
	/**
	 * 
	 * @return
	 */
	public Column next()
	{
		if (isLast()) {
			throw new UnsupportedOperationException(
				"No next column for column CH."
			);
		}
		
		int ordinal = ordinal();
		return values[ordinal + 1];
	}
	
	/**
	 * Returns an iterator over the range of columns delimited by the current
	 * and the given one, starting with the current column.
	 * 
	 * @param other
	 * @return
	 */
	public Iterator<Column> to(Column other)
	{
		if (preceeds(other)) {
			return new ForwardIterator(this, other);
		}
		else {
			return new BackwardIterator(this, other);
		}
	}
	
	/**
	 * 
	 * @author Dušan Rychnovský
	 *
	 */
	private class BackwardIterator implements Iterator<Column>
	{
		private final Column toColumn;
		private Column currColumn;
		
		/**
		 * 
		 * @param fromColumn
		 * @param toColumn
		 */
		public BackwardIterator(Column fromColumn, Column toColumn) 
		{
			if (fromColumn.preceeds(toColumn)) {
				throw new IllegalArgumentException(
					"Cannot iterate backwards from [" + fromColumn + "] to [" + toColumn + "]"
				);
			}
			
			this.toColumn = toColumn;
			this.currColumn = fromColumn;
		}
		
		@Override
		public boolean hasNext() {
			return (currColumn != null);
		}

		@Override
		public Column next() 
		{
			Column result = currColumn;
			
			if (currColumn.equals(toColumn)) {
				currColumn = null;
			}
			else {
				currColumn = currColumn.previous();
			}

			return result;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}		
	}
	
	/**
	 * 
	 * @author Dušan Rychnovský
	 *
	 */
	private class ForwardIterator implements Iterator<Column>
	{
		private final Column toColumn;
		private Column currColumn;
		
		/**
		 * 
		 * @param fromColumn
		 * @param toColumn
		 */
		public ForwardIterator(Column fromColumn, Column toColumn)
		{
			if (toColumn.preceeds(fromColumn)) {
				throw new IllegalArgumentException(
					"Cannot iterate forwards from [" + fromColumn + "] to [" + toColumn + "]."
				);
			}
			
			this.toColumn = toColumn;
			this.currColumn = fromColumn;
		}
		
		@Override
		public boolean hasNext() {
			return (currColumn != null);
		}

		@Override
		public Column next() 
		{
			Column result = currColumn;
			
			if (currColumn.equals(toColumn)) {
				currColumn = null;
			}
			else {
				currColumn = currColumn.next();
			}
			
			return result;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}		
	}
}
