package karlney.tetris.core;

public class PieceI extends PieceISZ {

    static final boolean[][] I_NORMAL = {{false,true,false,false} ,{false,true,false,false},{false,true,false,false} ,{false,true,false,false}};
    static final boolean[][] I_TILT   = {{false,false,false,false},{false,false,false,false},{true,true,true,true},{false,false,false,false}};

    public PieceI(int x, int y, Board board, Square[][] piece) {
        super(x,y,board,piece);
    }

    public PieceI(PieceI copy, int x, int y, int rotation, Board board) {
        super(copy, x, y, rotation, board);
    }

    protected Square[][] getRotatedShape(){
        if(isTilted()){
            return buildSquares(I_TILT,PieceType.I);
        }else{
            return buildSquares(I_NORMAL,PieceType.I);
        }
    }

    @Override
    public Piece getTranslatedCopy(int x, int y, int deltaRotation, Board board) {
        return new PieceI(this, x, y, deltaRotation, board);
    }
}