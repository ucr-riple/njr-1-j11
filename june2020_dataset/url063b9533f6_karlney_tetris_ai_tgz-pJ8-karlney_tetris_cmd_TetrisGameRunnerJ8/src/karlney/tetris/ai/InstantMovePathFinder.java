package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;
import karlney.tetris.core.PlayerInput;

import java.util.Arrays;
import java.util.List;

/**
 * This PathFinder sets the action to INSTANT_MOVE which is useful for AI training
 * <p/>
 * Date: 2012-11-03
 * Time: 16:14
 *
 * @see PlayerInput
 *
 * @author karl.neyvaldt
 */
public class InstantMovePathFinder implements PathFinder{

    @Override
    public List<PlayerInput> findPath(Board board, Piece currentPiece, Piece destination) {
        return Arrays.asList(PlayerInput.INSTANT_MOVE);
    }

}
