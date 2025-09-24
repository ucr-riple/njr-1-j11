package cz.dusanrychnovsky.chessendgames.core;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class RowTest
{
	@Test
	public void providesForwardIterator()
	{
		Row fromRow = Row.R2;
		Row toRow = Row.R4;
		
		Iterator<Row> it = fromRow.to(toRow);
		
		assertEquals(Row.R2, it.next());
		assertEquals(Row.R3, it.next());
		assertEquals(Row.R4, it.next());
		
		assertFalse(it.hasNext());
	}
	
	@Test
	public void providesBackwardIterator()
	{
		Row fromRow = Row.R5;
		Row toRow = Row.R2;
		
		Iterator<Row> it = fromRow.to(toRow);
		
		assertEquals(Row.R5, it.next());
		assertEquals(Row.R4, it.next());
		assertEquals(Row.R3, it.next());
		assertEquals(Row.R2, it.next());
		
		assertFalse(it.hasNext());
	}
	
	@Test
	public void providesTrivialIterator()
	{
		Row fromRow = Row.R3;
		Row toRow = fromRow;
		
		Iterator<Row> it = fromRow.to(toRow);
		
		assertEquals(fromRow, it.next());
		assertFalse(it.hasNext());
	}
	
	@Test
	public void canIterateFromTheFirstRow()
	{
		Row firstRow = Row.R1;
		Row toRow = Row.R3;
		
		Iterator<Row> it = firstRow.to(toRow);
		
		assertEquals(Row.R1, it.next());
		assertEquals(Row.R2, it.next());
		assertEquals(Row.R3, it.next());
		
		assertFalse(it.hasNext());
	}

	@Test
	public void canIterateToTheLastRow()
	{
		Row fromRow = Row.R6;
		Row lastRow = Row.R8;
		
		Iterator<Row> it = fromRow.to(lastRow);
		
		assertEquals(Row.R6, it.next());
		assertEquals(Row.R7, it.next());
		assertEquals(Row.R8, it.next());
		
		assertFalse(it.hasNext());
	}
}
