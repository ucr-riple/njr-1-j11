package cz.dusanrychnovsky.chessendgames.core;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cz.dusanrychnovsky.chessendgames.core.Player.Color;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class SituationTest
{
	private King blackKing;

	private Player whitePlayer;	
	private King whiteKing;
	private Rook whiteRook;
	
	@Before
	public void setUp()
	{
		Player blackPlayer = Player.get(Color.BLACK);
		blackPlayer.removeAllPieces();
		
		blackKing = new King(blackPlayer);
		
		whitePlayer = Player.get(Color.WHITE);
		whitePlayer.removeAllPieces();
		
		whiteKing = new King(whitePlayer);
		whiteRook = new Rook(whitePlayer);
	}
	
	// ========================================================================
	// MOVE EXECUTION
	// ========================================================================
	
	@Test
	public void createsANewSituationResultingFromTheOriginalOneByApplyingTheMove()
	{
		Situation origSituation = new Situation();
		origSituation.addPiece(blackKing, Position.get(Column.CD, Row.R5));
		origSituation.addPiece(whiteKing, Position.get(Column.CF, Row.R3));
		origSituation.addPiece(whiteRook, Position.get(Column.CH, Row.R1));
		
		Move move = new Move(
			whiteRook,
			Position.get(Column.CH, Row.R1),
			Position.get(Column.CH, Row.R8)
		);
		
		Situation newSituation = Situation.get(origSituation, move);
		
		assertEquals(Position.get(Column.CD, Row.R5), newSituation.getPosition(blackKing));
		assertEquals(Position.get(Column.CF, Row.R3), newSituation.getPosition(whiteKing));
		assertEquals(Position.get(Column.CH, Row.R8), newSituation.getPosition(whiteRook));
	}
	
	// ========================================================================
	// IS CHECK?
	// ========================================================================
	
	@Test
	public void aSituationIsACheckIfTheKingIsAttackedByAnOpponentsFiggure()
	{
		Situation situation = new Situation();
		situation.addPiece(blackKing, Position.get(Column.CD, Row.R5));
		situation.addPiece(whiteKing, Position.get(Column.CF, Row.R3));
		situation.addPiece(whiteRook, Position.get(Column.CG, Row.R5));
		
		assertTrue(situation.isCheck(blackKing));
	}

	@Test
	public void aSituationIsNotACheckIfTheKingIsNotAttacked()
	{
		Situation situation = new Situation();
		situation.addPiece(blackKing, Position.get(Column.CD, Row.R7));
		situation.addPiece(whiteKing, Position.get(Column.CA, Row.R5));
		situation.addPiece(whiteRook, Position.get(Column.CE, Row.R3));
		
		assertFalse(situation.isCheck(blackKing));
	}
	
	@Test
	public void aSituationIsNotACheckIfAnotherFigureStandsBetweenTheKingAndTheAttackingFigure()
	{
		Situation situation = new Situation();
		situation.addPiece(blackKing, Position.get(Column.CD, Row.R7));
		situation.addPiece(whiteKing, Position.get(Column.CD, Row.R5));
		situation.addPiece(whiteRook, Position.get(Column.CD, Row.R3));
		
		assertFalse(situation.isCheck(blackKing));
	}

	// ========================================================================
	// IS STALEMATE?
	// ========================================================================
	
	@Test
	public void aSituationIsAStalemateIfTheKingIsNotInCheckButCannotMove()
	{
		Situation situation = new Situation();
		situation.addPiece(blackKing, Position.get(Column.CA, Row.R8));
		situation.addPiece(whiteKing, Position.get(Column.CB, Row.R6));
		situation.addPiece(whiteRook, Position.get(Column.CB, Row.R7));
		
		assertTrue(situation.isStalemate(blackKing));
	}

	@Test
	public void aSituationIsNotAStalemateIfTheKingCanMove()
	{
		Situation situation = new Situation();
		situation.addPiece(blackKing, Position.get(Column.CA, Row.R8));
		situation.addPiece(whiteKing, Position.get(Column.CB, Row.R6));
		situation.addPiece(whiteRook, Position.get(Column.CC, Row.R7));
		
		assertFalse(situation.isStalemate(blackKing));
	}
	
	// ========================================================================
	// IS CHECKMATE?
	// ========================================================================
	
	@Test
	public void aSituationIsACheckmateIfTheKingIsInCheckAndCannotMove()
	{
		Situation situation = new Situation();
		situation.addPiece(blackKing, Position.get(Column.CA, Row.R8));
		situation.addPiece(whiteKing, Position.get(Column.CB, Row.R6));
		situation.addPiece(whiteRook, Position.get(Column.CC, Row.R8));
		
		assertTrue(situation.isCheckmate(blackKing));
	}
	
	// ========================================================================
	// FINAL SITUATIONS AND THE RESPECTIVE RESULTS
	// ========================================================================
	
	@Test(expected = UnsupportedOperationException.class)
	public void nonFinalSituationsDoNotHaveResultsAssigned()
	{
		Situation situation = new Situation();
		situation.addPiece(blackKing, Position.get(Column.CD, Row.R4));
		situation.addPiece(whiteKing, Position.get(Column.CB, Row.R6));
		situation.addPiece(whiteRook, Position.get(Column.CE, Row.R5));
		
		assertFalse(situation.isFinal());
		situation.getResult();
	}
	
	@Test
	public void aSituationIsADrawIfOnlyTheTwoKingsRemainActive()
	{
		Situation situation = new Situation();
		situation.addPiece(blackKing, Position.get(Column.CD, Row.R4));
		situation.addPiece(whiteKing, Position.get(Column.CB, Row.R6));
		
		assertTrue(situation.isFinal());
		assertEquals(new Draw(), situation.getResult());
	}
	
	@Test
	public void aSituationIsADrawIfOneOfTheKingsIsInStalemate()
	{
		Situation situation = new Situation();
		situation.addPiece(blackKing, Position.get(Column.CA, Row.R8));
		situation.addPiece(whiteKing, Position.get(Column.CB, Row.R6));
		situation.addPiece(whiteRook, Position.get(Column.CB, Row.R7));
		
		assertTrue(situation.isFinal());
		assertEquals(new Draw(), situation.getResult());
	}
	
	@Test
	public void aSituationIsAWinForAPlayerIfTheOppositePlayerIsInCheckmate()
	{
		Situation situation = new Situation();
		situation.addPiece(blackKing, Position.get(Column.CA, Row.R8));
		situation.addPiece(whiteKing, Position.get(Column.CB, Row.R6));
		situation.addPiece(whiteRook, Position.get(Column.CC, Row.R8));
		
		assertTrue(situation.isFinal());
		assertEquals(new Win(whitePlayer), situation.getResult());
	}
	
	// ========================================================================
	// SUCCESSORS
	// ========================================================================
	
	@Test
	public void generatesSuccessorSituations()
	{
		Situation situation = new Situation();
		situation.addPiece(blackKing, Position.get(Column.CC, Row.R7));
		situation.addPiece(whiteKing, Position.get(Column.CB, Row.R5));
		situation.addPiece(whiteRook, Position.get(Column.CB, Row.R2));
		
		List<Situation> successors = situation.generateSuccessors(whitePlayer);
		assertEquals(16, successors.size());
	}
}
