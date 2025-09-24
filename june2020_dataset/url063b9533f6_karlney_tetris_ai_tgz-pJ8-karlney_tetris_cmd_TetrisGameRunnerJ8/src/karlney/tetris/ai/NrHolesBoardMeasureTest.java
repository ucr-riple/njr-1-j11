package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.test.BoardBuilder;
import org.junit.Test;

import static karlney.tetris.test.BoardBuilder.*;
import static junit.framework.Assert.*;

/**
 * Date: 2012-11-04
 * Time: 22:28
 *
 * @author karl.neyvaldt
 */
public class NrHolesBoardMeasureTest {

    @Test
    public void testEmptyBoard_Expect0(){
        Board board = createEmptyBoard(4, 4);
        assertEquals(0,BoardMeasuresUtil.getNrHoles(board));
    }

    @Test
    public void testBoardWithoutHoles_Expect0(){
        Board board = createBoard(3, 6, new int[][]{{1, 0, 1}, {1, 0, 1}, {1, 0, 0}, {0,0,0}});
        assertEquals(0,BoardMeasuresUtil.getNrHoles(board));
    }

    @Test
    public void testBoardWithHoles_Expect3(){
        Board board = createBoard(3,6, new int[][]{{1,0,1},{1,1,0},{0,1,1},{1,0,0}});
        assertEquals(3,BoardMeasuresUtil.getNrHoles(board));
    }

    @Test
    public void testBoardWithManyHoles_Expect9(){
        Board board = createBoard(4,6, new int[][]{
                {1,0,0,0},
                {1,1,0,0},
                {0,1,0,1},
                {1,0,0,0},
                {0,1,1,0},
                {0,0,0,0}});
        assertEquals(9,BoardMeasuresUtil.getNrHoles(board));
    }

    @Test
    public void testComplexBoard_Expect9(){

        Board board = BoardBuilder.createBoard(8, new int[][]{
                {1, 0, 1, 0, 1, 0, 0, 1, 0, 1},//
                {1, 0, 0, 1, 0, 1, 1, 1, 0, 0},//
                {1, 0, 1, 0, 0, 1, 1, 1, 1, 1},//
                {1, 0, 1, 0, 1, 1, 1, 1, 1, 0},//
                {1, 0, 0, 0, 0, 1, 1, 0, 1, 0},//
                {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}});

        assertEquals(9,BoardMeasuresUtil.getNrHoles(board));
    }

}
