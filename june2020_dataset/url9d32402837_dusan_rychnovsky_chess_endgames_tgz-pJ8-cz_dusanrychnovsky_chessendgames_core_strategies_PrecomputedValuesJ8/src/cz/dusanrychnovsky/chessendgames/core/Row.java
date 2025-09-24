package cz.dusanrychnovsky.chessendgames.core;

import java.util.Iterator;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public enum Row
{
	R1,
	R2,
	R3,
	R4,
	R5,
	R6,
	R7,
	R8;
	
	private static final Row[] values = values();
	
	/**
	 * 
	 * @param ordinal
	 * @return
	 */
	public static Row get(int ordinal)
	{
		try {
			return values[ordinal];
		}
		catch (ArrayIndexOutOfBoundsException ex) {
			throw new IllegalArgumentException(
				"Invalid row ordinal [" + ordinal + "]."
			);
		}
	}
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public boolean preceeds(Row other) {
		return this.ordinal() < other.ordinal();
	}
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public int distance(Row other) {
		return Math.abs(this.ordinal() - other.ordinal());
	}

	/**
	 * 
	 * @return
	 */
	public boolean isFirst() {
		return this.equals(Row.R1);
	}
	
	/**
	 * 
	 * @return
	 */
	public Row previous()
	{
		if (this.equals(Row.R1)) {
			throw new UnsupportedOperationException(
				"No previous row for row R1."
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
		return this.equals(Row.R8);
	}
	
	/**
	 * 
	 * @return
	 */
	public Row next()
	{
		if (this.equals(Row.R8)) {
			throw new UnsupportedOperationException(
				"No next row for row R8."
			);
		}
		
		int ordinal = ordinal();
		return values[ordinal + 1];
	}

	/**
	 * Returns an iterator over the range of rows delimited by the current
	 * and the given one, starting with the current row.
	 * 
	 * @param other
	 * @return
	 */
	public Iterator<Row> to(Row other)
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
	private class BackwardIterator implements Iterator<Row>
	{
		private final Row toRow;
		private Row currRow;
		
		/**
		 * 
		 * @param fromRow
		 * @param toRow
		 */
		public BackwardIterator(Row fromRow, Row toRow) 
		{
			if (fromRow.preceeds(toRow)) {
				throw new IllegalArgumentException(
					"Cannot iterate backwards from [" + fromRow + "] to [" + toRow + "]"
				);
			}
			
			this.toRow = toRow;
			this.currRow = fromRow;
		}
		
		@Override
		public boolean hasNext() {
			return (currRow != null);
		}

		@Override
		public Row next() 
		{
			Row result = currRow;
			
			if (currRow.equals(toRow)) {
				currRow = null;
			}
			else {
				currRow = currRow.previous();
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
	private class ForwardIterator implements Iterator<Row>
	{
		private final Row toRow;
		private Row currRow;
		
		/**
		 * 
		 * @param fromRow
		 * @param toRow
		 */
		public ForwardIterator(Row fromRow, Row toRow)
		{
			if (toRow.preceeds(fromRow)) {
				throw new IllegalArgumentException(
					"Cannot iterate forwards from [" + fromRow + "] to [" + toRow + "]."
				);
			}
			
			this.toRow = toRow;
			this.currRow = fromRow;
		}
		
		@Override
		public boolean hasNext() {
			return (currRow != null);
		}

		@Override
		public Row next() 
		{
			Row result = currRow;
			
			if (currRow.equals(toRow)) {
				currRow = null;
			}
			else {
				currRow = currRow.next();
			}
			
			return result;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}		
	}
}
