package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-03
 * Time: 11:07
 *
 * @author karl.neyvaldt
 */
public interface BoardEvaluator {

    double score(Board board, Piece piece, int linesRemoved);

}
