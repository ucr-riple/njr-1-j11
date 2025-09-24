package karlney.tetris.test;


import karlney.tetris.core.AbstractPieceGenerator;
import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;
import karlney.tetris.core.PieceType;

public class StaticPieceGenerator  extends AbstractPieceGenerator {

    private int[] sequence;
    private int nr = 0;

    public StaticPieceGenerator(int[] sequence) {
        this.sequence = sequence;
    }

    @Override
    public Piece getNextPiece(Board board){
        if (sequence!=null && nr>=sequence.length) {
            PieceType type = PieceType.valueOf(sequence[nr]);
            nr++;
            return getPiece(type,board);
        }else{
            return getPiece(PieceType.I,board);
        }
    }

}
