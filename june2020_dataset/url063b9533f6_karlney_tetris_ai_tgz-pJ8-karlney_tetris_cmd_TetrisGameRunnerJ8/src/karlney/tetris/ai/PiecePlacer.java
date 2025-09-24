package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-08
 * Time: 21:20
 *
 * @author karl.neyvaldt
 */
public interface PiecePlacer {

    Piece bestPlacement(BoardEvaluator evaluator, Board board, Piece currentPiece, Piece nextPiece);

}
