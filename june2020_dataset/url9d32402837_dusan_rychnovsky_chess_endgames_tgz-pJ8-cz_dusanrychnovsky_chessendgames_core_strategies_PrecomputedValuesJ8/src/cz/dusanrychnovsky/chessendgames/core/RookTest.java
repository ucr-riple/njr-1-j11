package cz.dusanrychnovsky.chessendgames.core;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import cz.dusanrychnovsky.chessendgames.core.Player.Color;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class RookTest extends PieceTest
{
	private static final Player player = Player.get(Color.WHITE);
	private static final Rook rook = new Rook(player);
	
	@Test
	public void generatesCorrectSetOfMovesWhenLocatedInCenterOfTheBoard()
	{
		Position position = Position.get(Column.CC, Row.R3);
		List<Move> result = rook.generateMoves(position);
		
		assertEquals(14, result.size());
		
		assertTrue(containsPosition(result, Position.get(Column.CC, Row.R8)));
		assertTrue(containsPosition(result, Position.get(Column.CC, Row.R7)));
		assertTrue(containsPosition(result, Position.get(Column.CC, Row.R6)));
		assertTrue(containsPosition(result, Position.get(Column.CC, Row.R5)));
		assertTrue(containsPosition(result, Position.get(Column.CC, Row.R4)));
		assertTrue(containsPosition(result, Position.get(Column.CC, Row.R2)));
		assertTrue(containsPosition(result, Position.get(Column.CC, Row.R1)));
		
		assertTrue(containsPosition(result, Position.get(Column.CA, Row.R3)));
		assertTrue(containsPosition(result, Position.get(Column.CB, Row.R3)));
		assertTrue(containsPosition(result, Position.get(Column.CD, Row.R3)));
		assertTrue(containsPosition(result, Position.get(Column.CE, Row.R3)));
		assertTrue(containsPosition(result, Position.get(Column.CF, Row.R3)));
		assertTrue(containsPosition(result, Position.get(Column.CG, Row.R3)));
		assertTrue(containsPosition(result, Position.get(Column.CH, Row.R3)));
	}
	

	@Test
	public void generatesCorrectSetOfMovesWhenLocatedInACornerOfTheBoard()
	{
		Position position = Position.get(Column.CA, Row.R1);
		List<Move> result = rook.generateMoves(position);
		
		assertEquals(14, result.size());
		
		assertTrue(containsPosition(result, Position.get(Column.CA, Row.R8)));
		assertTrue(containsPosition(result, Position.get(Column.CA, Row.R7)));
		assertTrue(containsPosition(result, Position.get(Column.CA, Row.R6)));
		assertTrue(containsPosition(result, Position.get(Column.CA, Row.R5)));
		assertTrue(containsPosition(result, Position.get(Column.CA, Row.R4)));
		assertTrue(containsPosition(result, Position.get(Column.CA, Row.R3)));
		assertTrue(containsPosition(result, Position.get(Column.CA, Row.R2)));
		
		assertTrue(containsPosition(result, Position.get(Column.CB, Row.R1)));
		assertTrue(containsPosition(result, Position.get(Column.CC, Row.R1)));
		assertTrue(containsPosition(result, Position.get(Column.CD, Row.R1)));
		assertTrue(containsPosition(result, Position.get(Column.CE, Row.R1)));
		assertTrue(containsPosition(result, Position.get(Column.CF, Row.R1)));
		assertTrue(containsPosition(result, Position.get(Column.CG, Row.R1)));
		assertTrue(containsPosition(result, Position.get(Column.CH, Row.R1)));
	}
}
