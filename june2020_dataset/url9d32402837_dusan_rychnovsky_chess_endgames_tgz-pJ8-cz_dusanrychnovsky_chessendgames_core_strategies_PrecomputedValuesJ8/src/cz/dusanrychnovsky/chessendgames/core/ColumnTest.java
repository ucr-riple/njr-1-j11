package cz.dusanrychnovsky.chessendgames.core;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class ColumnTest
{
	@Test
	public void providesForwardIterator()
	{
		Column fromColumn = Column.CB;
		Column toColumn = Column.CD;
		
		Iterator<Column> it = fromColumn.to(toColumn);
		
		assertEquals(Column.CB, it.next());
		assertEquals(Column.CC, it.next());
		assertEquals(Column.CD, it.next());
		
		assertFalse(it.hasNext());
	}
	
	@Test
	public void providesBackwardIterator()
	{
		Column fromColumn = Column.CE;
		Column toColumn = Column.CB;
		
		Iterator<Column> it = fromColumn.to(toColumn);
		
		assertEquals(Column.CE, it.next());
		assertEquals(Column.CD, it.next());
		assertEquals(Column.CC, it.next());
		assertEquals(Column.CB, it.next());
		
		assertFalse(it.hasNext());
	}
	
	@Test
	public void providesTrivialIterator()
	{
		Column fromColumn = Column.CC;
		Column toColumn = fromColumn;
		
		Iterator<Column> it = fromColumn.to(toColumn);
		
		assertEquals(fromColumn, it.next());
		assertFalse(it.hasNext());
	}
	
	@Test
	public void canIterateFromTheFirstColumn()
	{
		Column firstColumn = Column.CA;
		Column toColumn = Column.CC;
		
		Iterator<Column> it = firstColumn.to(toColumn);
		
		assertEquals(Column.CA, it.next());
		assertEquals(Column.CB, it.next());
		assertEquals(Column.CC, it.next());
		
		assertFalse(it.hasNext());
	}

	@Test
	public void canIterateToTheLastColumn()
	{
		Column fromColumn = Column.CF;
		Column lastColumn = Column.CH;
		
		Iterator<Column> it = fromColumn.to(lastColumn);
		
		assertEquals(Column.CF, it.next());
		assertEquals(Column.CG, it.next());
		assertEquals(Column.CH, it.next());
		
		assertFalse(it.hasNext());
	}
}
