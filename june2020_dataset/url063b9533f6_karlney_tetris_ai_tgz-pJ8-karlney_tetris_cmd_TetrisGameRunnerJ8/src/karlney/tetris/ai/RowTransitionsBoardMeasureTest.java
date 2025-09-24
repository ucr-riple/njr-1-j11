package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.test.BoardBuilder;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static karlney.tetris.test.BoardBuilder.createBoard;
import static karlney.tetris.test.BoardBuilder.createEmptyBoard;

/**
 * Date: 2012-11-04
 * Time: 22:28
 *
 * @author karl.neyvaldt
 */
public class RowTransitionsBoardMeasureTest {

    @Test
    public void testEmptyBoard_Expect8(){
        Board board = createEmptyBoard(4, 4);
        assertEquals(8,BoardMeasuresUtil.getRowTransitions(board));
    }

    @Test
    public void testBoardWithoutHoles_Expect14(){
        Board board = createBoard(8, 6, new int[][]{{0,1,0,0,0,0,0,0}});
        assertEquals(14,BoardMeasuresUtil.getRowTransitions(board));
    }

    @Test
    public void testBoardWithHoles_Expect12(){
        Board board = createBoard(3,6, new int[][]{{1,0,1},{1,1,0},{0,1,1},{1,0,0}});
        assertEquals(12,BoardMeasuresUtil.getRowTransitions(board));
    }

    @Test
    public void testComplexBoard_Expect40(){

        Board board = BoardBuilder.createBoard(8, new int[][]{
                {1, 0, 1, 0, 1, 0, 0, 1, 0, 1},//
                {1, 0, 0, 1, 0, 1, 1, 1, 0, 0},//
                {1, 0, 1, 0, 0, 1, 1, 1, 1, 1},//
                {1, 0, 1, 0, 1, 1, 1, 1, 1, 0},//
                {1, 0, 0, 0, 0, 1, 1, 0, 1, 0},//
                {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}});

        assertEquals(40,BoardMeasuresUtil.getRowTransitions(board));
    }

}
