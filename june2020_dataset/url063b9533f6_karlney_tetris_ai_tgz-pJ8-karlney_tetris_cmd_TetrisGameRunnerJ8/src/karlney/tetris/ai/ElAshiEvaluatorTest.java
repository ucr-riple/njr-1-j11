package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;
import karlney.tetris.core.PieceType;
import karlney.tetris.core.UnableToPlacePieceException;
import karlney.tetris.test.BoardBuilder;
import karlney.tetris.test.StaticPieceGenerator;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static karlney.tetris.test.BoardBuilder.createBoard;
import static karlney.tetris.test.PieceBuilder.createPiece;
import static org.junit.Assert.assertArrayEquals;


/**
 * Date: 2012-11-04
 * Time: 22:28
 *
 * @author karl.neyvaldt
 */
public class ElAshiEvaluatorTest {

    @Test
    public void testComplexBoard_ExpectIplacedAtWall(){
        Board boardBefore = BoardBuilder.createBoard(20, new int[][]{
                {1, 0, 1, 0, 1, 0, 0, 1, 0, 1},//
                {1, 0, 0, 1, 0, 1, 1, 1, 0, 0},//
                {0, 0, 1, 0, 0, 1, 1, 1, 1, 1},//
                {0, 0, 1, 0, 1, 1, 1, 1, 1, 0},//
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1},//
                {0, 0, 0, 0, 0, 1, 1, 0, 1, 0},//
                {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}});

        Board boardAfter = BoardBuilder.createBoard(20, new int[][]{
                {1, 0, 1, 0, 1, 0, 0, 1, 0, 1},//
                {1, 0, 0, 1, 0, 1, 1, 1, 0, 0},//
                {1, 0, 1, 0, 0, 1, 1, 1, 1, 1},//
                {1, 0, 1, 0, 1, 1, 1, 1, 1, 0},//
                {1, 0, 0, 0, 0, 1, 1, 0, 1, 0},//
                {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}});

        Piece piece = createPiece(PieceType.I, boardBefore);
        Piece out = new OnePieceNoPathCheckPiecePlacer()
                .bestPlacement(new ElAshiTetrisBoardEvaluator(), boardBefore, piece, null);
        assertEquals(-571.2945084331699,new ElAshiTetrisBoardEvaluator().score(boardAfter,out,1));
        assertEquals(out.getX(),-1);
        assertEquals(1,out.getOrientation());
        assertEquals(out.getY(),15);
    }

    /*
    @Test
    public void testFullSequence() throws UnableToPlacePieceException {
        Board board = BoardBuilder.createBoard(10,20, new int[][]{{0,0,0,0,0,0,0,0,0,0}});
        Board boardAfter = BoardBuilder.createBoard(10,20, new int[][]{
                {1,1,1,1,1,1,1,1,1,0,0,0},
                {1,1,0,1,1,1,1,1,1,0,0,0},
                {1,1,0,1,0,1,1,1,1,0,0,0},
                {1,0,0,1,1,1,1,0,1,0,0,0},
                {1,0,0,1,1,1,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0}
        });

        //[4, 5, 3, 3, 6, 1, 4, 5, 1, 2, 6],
        int[] seq = new int[]{4, 2, 5, 5,  3, 6, 4, 2, 6, 0, 3};

        StaticPieceGenerator gen = new StaticPieceGenerator(seq);

        for (int aSeq : seq) {
            Piece piece = gen.getNextPiece(board);
            Piece out = new OnePieceNoPathCheckPiecePlacer().bestPlacement(new ElAshiTetrisBoardEvaluator(), board, piece, null);
            board.placePieceOnBoard(out);
            board.removeFullRows();
        }


        for (int i=1; i<boardAfter.getCols();i++){
            for (int j=1; j<boardAfter.getRows();j++){
                System.out.println(i+" "+j);
                assertEquals(boardAfter.getSquare(i, j).isFilled(),board.getSquare(i, j).isFilled());
            }
        }

    }
    */

}
