package karlney.tetris.ai;

import karlney.tetris.core.*;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static karlney.tetris.test.BoardBuilder.createBoard;
import static karlney.tetris.test.PieceBuilder.createPiece;

/**
 * Date: 2012-11-04
 * Time: 22:28
 *
 * @author karl.neyvaldt
 */
public class LandingHeightBoardMeasureTest {


    @Test
    public void testWithPieceIhorizEmptyBoard_Expect0() throws UnableToPlacePieceException {
        Board board = createBoard(6, new int[][]{{0,0,0,0,0}});
        Piece pi = createPiece(PieceType.I,board);
        pi.stepDownAFAP();
        board.placePieceOnBoard(pi);
        assertEquals(0.0,BoardMeasuresUtil.getLandingHeight(board,pi));
    }     //Confirmed

    @Test
    public void testWithPieceJemptyBoard_Expect1() throws UnableToPlacePieceException {
        Board board = createBoard(7, new int[][]{{0,0,0,0,0,0}});
        Piece pi = createPiece(PieceType.J,board);
        pi.moveDown();
        pi.rotateIfPossible();
        pi.stepDownAFAP();
        board.placePieceOnBoard(pi);
        assertEquals(1.0,BoardMeasuresUtil.getLandingHeight(board,pi));
    } //Confirmed


    @Test
    public void testWithPieceIvert1_Expect15() throws UnableToPlacePieceException {
        Board board = createBoard(7, new int[][]{{0,0,0,0,0}});
        Piece pi = createPiece(PieceType.I,board);
        pi.moveDown();
        pi.rotateIfPossible();
        pi.stepDownAFAP();
        board.placePieceOnBoard(pi);
        assertEquals(1.5, BoardMeasuresUtil.getLandingHeight(board, pi));
    }  //Confirmed


    @Test
    public void testWithPieceIvert2_Expect25() throws UnableToPlacePieceException {
        Board board = createBoard(7, new int[][]{{1,1,1,1,0}});
        Piece pi = createPiece(PieceType.I,board);
        pi.moveDown();
        pi.rotateIfPossible();
        pi.stepDownAFAP();
        board.placePieceOnBoard(pi);
        assertEquals(2.5,BoardMeasuresUtil.getLandingHeight(board,pi));
    }  //Confirmed


    /* An empty T */
    @Test
    public void testWithPieceTemptyBoard_Expect05() throws UnableToPlacePieceException {
        Board board = createBoard(6, new int[][]{{0,0,0,0,0,0}});
        Piece pi = createPiece(PieceType.T,board);
        pi.moveDown();
        pi.rotateIfPossible();
        pi.rotateIfPossible();
        pi.stepDownAFAP();
        board.placePieceOnBoard(pi);
        assertEquals(0.5,BoardMeasuresUtil.getLandingHeight(board,pi));
    } //Confirmed


    /* A T that fits in in vertical */
    @Test
    public void testWithPieceTfitsVertical_Expect2() throws UnableToPlacePieceException {
        Board board = createBoard(6, new int[][]{{0,1,1,1,1},{0,0,0,1,0}});
        Piece pi = createPiece(PieceType.T,board);
        pi.moveDown();
        pi.rotateIfPossible();
        pi.moveSideWays(PlayerInput.RIGHT);
        pi.moveSideWays(PlayerInput.RIGHT);
        pi.moveSideWays(PlayerInput.RIGHT);
        pi.stepDownAFAP();
        assertEquals(2.0,BoardMeasuresUtil.getLandingHeight(board,pi));
    }  //Confirmed


    /* A T that fits in in horiz */
    @Test
    public void testWithPieceTfitsHoriz_Expect05() throws UnableToPlacePieceException {
        Board board = createBoard(6, new int[][]{{1,0,1,1,1}});
        Piece pi = createPiece(PieceType.T,board);
        pi.moveDown();
        pi.moveSideWays(PlayerInput.LEFT);
        pi.moveSideWays(PlayerInput.LEFT);
        pi.stepDownAFAP();
        assertEquals(0.5,BoardMeasuresUtil.getLandingHeight(board,pi));
    }  //Confirmed


    /*
      L with bar in upper pos and point downwards, point is landed on piece, rest is 0's
    */

    @Test
    public void testWithPieceLlandOnPile_Expect25() throws UnableToPlacePieceException {
        Board board = createBoard(6, new int[][]{{1,0,0,0,0},{1,0,0,0,0}});
        Piece pi = createPiece(PieceType.L,board);
        pi.moveDown();
        pi.moveSideWays(PlayerInput.LEFT);
        pi.moveSideWays(PlayerInput.LEFT);
        pi.stepDownAFAP();
        assertEquals(2.5,BoardMeasuresUtil.getLandingHeight(board,pi));
    }  //Confirmed


    /*
      L with bar in upper pos and point downwards, point is landed on piece, rest is filled
    */
    @Test
    public void testWithPieceLlandOnPile2_Expect25() throws UnableToPlacePieceException {
        Board board = createBoard(6, new int[][]{{1,1,1,1,0},{1,1,1,1,0},{0,1,1,1,0}});
        Piece pi = createPiece(PieceType.L,board);
        pi.moveDown();
        pi.moveSideWays(PlayerInput.LEFT);
        pi.moveSideWays(PlayerInput.LEFT);
        pi.stepDownAFAP();
        board.placePieceOnBoard(pi);
        assertEquals(2.5,BoardMeasuresUtil.getLandingHeight(board,pi));
    }  //Confirmed


}
