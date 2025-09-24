package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;

/**
 * <p>
 http://ielashi.com/el-tetris-an-improvement-on-pierre-dellacheries-algorithm/

 There are six features in total, formally outlined as follows:

 1. Landing Height: The height where the piece is put (= the height of the column + (the height of the piece / 2))
 2. Rows eliminated: The number of rows eliminated.
 3. Row Transitions: The total number of row transitions. A row transition occurs when an empty cell is adjacent to a filled cell on the same row and vice versa.
 4. Column Transitions: The total number of column transitions. A column transition occurs when an empty cell is adjacent to a filled cell on the same column and vice versa.
 5. Number of Holes: A hole is an empty cell that has at least one filled cell above it in the same column.
 6. Well Sums: A well is a succession of empty cells such that their left cells and right cells are both filled.

 * </p>
 *
 * <p>
 *  The evaluation function is a linear sum of all the above features. The weights of each feature were set and determined using particle swarm optimization.
The following table shows the feature number (as outlined above) and its weight respectively:
FEATURE #	WEIGHT
1	-4.500158825082766
2	3.4181268101392694
3	-3.2178882868487753
4	-9.348695305445199
5	-7.899265427351652
6	-3.3855972247263626
 * </p>
 *
 * Date: 2012-11-03
 * Time: 19:56
 *
 * @author karl.neyvaldt
 */

/*
Different PSO libs:

http://jswarm-pso.sourceforge.net/

http://cilib.net/docs/dev/windows-configurations.html
 */
public class ElAshiTetrisBoardEvaluator implements BoardEvaluator{

    double[] w = new double[]{0.0,
            -4.500158825082766,
            3.4181268101392694,
            -3.2178882868487753,
            -9.348695305445199,
            -7.899265427351652,
            -3.3855972247263626};

    @Override
    public double score(Board board, Piece piece, int rows) {
        double lh = BoardMeasuresUtil.getLandingHeight(board,piece);
        int re = rows;
        int rt = BoardMeasuresUtil.getRowTransitions(board);
        int ct = BoardMeasuresUtil.getColumnTransitions(board);
        int ho = BoardMeasuresUtil.getNrHoles(board);
        int ws = BoardMeasuresUtil.getWellSums(board);
        return w[1]*lh+w[2]*re+w[3]*rt+w[4]*ct+w[5]*ho+w[6]*ws;
    }



}
