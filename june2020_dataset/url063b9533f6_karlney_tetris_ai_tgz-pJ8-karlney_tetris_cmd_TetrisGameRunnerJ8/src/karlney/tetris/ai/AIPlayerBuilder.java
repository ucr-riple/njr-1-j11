package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.PieceGenerator;

/**
 * Date: 2012-11-05
 * Time: 14:50
 *
 * @author karl.neyvaldt
 */
public class AIPlayerBuilder {

    private AIPlayerBuilder(){}

    public static AIPlayer getInstantMoveOnePieceAIPlayer(Board board, PieceGenerator generator, BoardEvaluator evaluator, int level, int moveDelay){
        return new AIPlayer(
                board,
                generator,
                new OnePieceNoPathCheckPiecePlacer(),
                evaluator,
                new InstantMovePathFinder(),
                level,
                moveDelay);
    }

}
