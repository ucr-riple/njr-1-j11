package chess.move.impl;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import chess.ChessCoord;
import chess.Position;
import chess.impl.Coords;
import chess.impl.DefaultPosition;
import chess.move.Move;

public class PGNParserTest {
    private Position testPos;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        testPos = new DefaultPosition(true);
    }

    @Test
    public void testSimpleMoveWithoutAmbiguity() {
        ChessCoord expectedSourceCoord = Coords.coordByAlgebraic("b1");
        ChessCoord exptectedTargetCoord = Coords.coordByAlgebraic("c3");
        String moveNotation = "Nc3";
        PGNParser parser = new PGNParser();
        Move parsedMove = parser.parseMove(testPos, moveNotation);
        // assertTrue("Parsing did not yield the correct move type",
        // parsedMove instanceof SimpleMove);
        // assertTrue(
        // "Parsing did not yield the correct source and destination coordinates",
        // parsedMove.getSourceCoord().sameAs(expectedSourceCoord)
        // && parsedMove.getTargetCoord().sameAs(
        // exptectedTargetCoord));
        SimpleMoveTest.testSimpleMove(testPos, expectedSourceCoord,
                exptectedTargetCoord, (SimpleMove) parsedMove);
    }

    @Test
    public void testSimpleMoveWithAmbiguity() {
        fail("not yet implemented!");
    }
}
