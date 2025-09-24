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
public class ColumnTransitionsBoardMeasureTest {

    @Test
    public void testEmptyBoard_Expect4(){
        Board board = createEmptyBoard(4, 4);
        assertEquals(4,BoardMeasuresUtil.getColumnTransitions(board));
    }

    @Test
    public void testBoardWithoutHoles_Expect8(){
        Board board = createBoard(8, new int[][]{{0,1,1,0,0,0,0,0}});
        assertEquals(8,BoardMeasuresUtil.getColumnTransitions(board));
    }

    @Test
    public void testBoardWithHoles_Expect5(){
        Board board = createBoard(3, new int[][]{
                {1,0,1},
                {1,1,0},
                {0,0,0}});
        assertEquals(5,BoardMeasuresUtil.getColumnTransitions(board));
    }

    @Test
    public void testComplexBoard_Expect24(){

        Board board = BoardBuilder.createBoard(12, new int[][]{
                {1, 0, 1, 0, 1, 0, 0, 1, 0, 1},//
                {1, 0, 0, 1, 0, 1, 1, 1, 0, 0},//
                {1, 0, 1, 0, 0, 1, 1, 1, 1, 1},//
                {1, 0, 1, 0, 1, 1, 1, 1, 1, 0},//
                {1, 0, 0, 0, 0, 1, 1, 0, 1, 0},//
                {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}});

        assertEquals(24,BoardMeasuresUtil.getColumnTransitions(board));
    }
}
