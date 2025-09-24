package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;
import karlney.tetris.core.PlayerInput;

import java.util.List;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-03
 * Time: 11:08
 *
 * @author karl.neyvaldt
 */
public interface PathFinder {

    List<PlayerInput> findPath(Board board, Piece currentPiece, Piece destination);

}
