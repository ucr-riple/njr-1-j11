package karlney.tetris.ai;

import karlney.tetris.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This PiecePlacer takes into consideration the board state and the current piece when evaluating where to place the latter
 * it uses a heuristics TODO..
 *
 * NOTE: This is a simplified movement and limits the AI quite much!
 *
 * <p/>
 * Date: 2012-11-03
 * Time: 16:17
 *
 * @author karl.neyvaldt
 */
public class OnePieceNoPathCheckPiecePlacer implements PiecePlacer{

    private static Logger log = LoggerFactory.getLogger(OnePieceNoPathCheckPiecePlacer.class);

    @Override
    public Piece bestPlacement(BoardEvaluator evaluator, Board board, Piece currentPiece, Piece nextPiece) {

        double bestUtility = Integer.MIN_VALUE;
        //TODO be a bit smarter when it comes to rotations and translations
        int y = 0;
        Piece best = currentPiece.getTranslatedCopy(currentPiece.getX(), currentPiece.getY(), 0, board);
        best.stepDownAFAP();
        try{
            for (int h=0; h<currentPiece.getPossibleOrientations(); h++){
                for (int x=-1; x< board.getCols(); x++){
                    Board boardCopy = new Board(board);
                    Piece tb = currentPiece.getTranslatedCopy(x, y, h, boardCopy);
                    tb.stepDownAFAP();
                    if (board.allowedPlacement(tb)){
                        boardCopy.placePieceOnBoard(tb);
                        int rows = boardCopy.removeFullRows();
                        double utility = evaluator.score(boardCopy,tb,rows);
                        if (utility>bestUtility){
                            bestUtility=utility;
                            best=tb.getTranslatedCopy(tb.getX(), tb.getY(), 0, board);
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error("Best placement crashed",e);
        }
        //log.debug(bestUtility+"");
        return best;
    }

}
