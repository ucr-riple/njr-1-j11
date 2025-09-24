package karlney.tetris.test;

import karlney.tetris.core.Board;
import karlney.tetris.core.PieceType;
import karlney.tetris.core.Square;

/**
 * Date: 2012-11-04
 * Time: 22:53
 *
 * @author karl.neyvaldt
 */
public class BoardBuilder {

    public static Board createEmptyBoard(int cols, int rows) {
        return new Board(cols,rows);
    }

    public static Board createBoard(int cols, int rows, int[][] bottomRows) {
        Board b = new Board(cols,rows);
        for (int j=0; j<bottomRows.length;j++){
            for (int i=0;i<cols;i++){
                if (bottomRows[j][i]==1){
                    b.addSquare(i+1,rows-j,new Square(PieceType.I,true));
                }
            }
        }
        return b;
    }

    public static Board createBoard(int rows, int[][] bottomRows) {
        return createBoard(bottomRows[0].length,rows,bottomRows);
    }
}
