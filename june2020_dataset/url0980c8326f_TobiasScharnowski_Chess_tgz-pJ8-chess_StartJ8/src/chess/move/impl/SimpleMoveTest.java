/**
 *
 */
package chess.move.impl;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import chess.ChessCoord;
import chess.PieceType;
import chess.Position;
import chess.impl.Coords;
import chess.impl.DefaultPosition;
import chess.piece.Piece;

/**
 * @author SCAR023
 *
 */
// TODO Add additional tests (undoMove, ...)
public class SimpleMoveTest {
    private Position testPos;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        testPos = new DefaultPosition(true);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Tests the basic move of a Piece from one position to another.
     */
    @Test
    public void testMove() {
        testPos = new DefaultPosition(true);
        final ChessCoord expectedPawnSrcCoord = Coords.coord(0, 1);
        final ChessCoord expectedPawnDestCoord = Coords.coord(0, 3);
        final Piece testPawn = testPos.getPieceAt(expectedPawnSrcCoord);
        // IMPROVE This particular test should be made part of a DefaultPosition
        // unit test.
        assertTrue("Pawn has to present in default position", testPawn != null
                && testPawn.getType().equals(PieceType.PAWN));
        SimpleMove pawnMove = new SimpleMove(expectedPawnSrcCoord,
                expectedPawnDestCoord);
        SimpleMoveTest.testSimpleMove(testPos, expectedPawnSrcCoord,
                expectedPawnDestCoord, pawnMove);
    }

    public static void testSimpleMove(final Position pos,
            final ChessCoord expectedSourceCoord,
            final ChessCoord expectedTargetCoord, final SimpleMove move) {
        final Piece movingPiece = pos.getPieceAt(expectedSourceCoord);
        pos.processMove(move);
        assertTrue(
                "Pieces not at expected positions after move",
                pos.getPieceAt(expectedSourceCoord) == null
                        && pos.getPieceAt(expectedTargetCoord).equals(
                                movingPiece));
        pos.undoLastMove();
        assertTrue(
                "Pieces not at expected positions after undoing move",
                pos.getPieceAt(expectedTargetCoord) == null
                        && pos.getPieceAt(expectedSourceCoord).equals(
                                movingPiece));
    }
}
