package cz.dusanrychnovsky.chessendgames.core;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class PositionTest
{
	@Test
	public void canBePrintedInHumanReadableForm()
	{
		Position pos = Position.get(Column.CF, Row.R8);
		assertEquals("F8", pos.toString());
	}
}
