package karlney.tetris.test;

import karlney.tetris.core.*;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-05
 * Time: 13:00
 *
 * @author karl.neyvaldt
 */
public class PieceBuilder {

    private static PieceGenerator gen = new RandomPieceGenerator();

    public static Piece createPiece(PieceType type, Board board){
        Piece p;
        do{
            p = gen.getNextPiece(board);
        }while (type != p.getType());
        return p;
    }

}
