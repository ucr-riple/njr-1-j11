package karlney.tetris.core;

public class PieceZ extends PieceISZ {

    static final boolean[][] Z_NORMAL = {{false,true,false},  {false,true,true}, {false,false,true}};
    static final boolean[][] Z_TILT = {{false,false,false},  {false,true,true}, {true,true,false}};

    public PieceZ(int x, int y, Board board, Square[][] piece) {
        super(x,y,board,piece);
    }

    public PieceZ(PieceZ copy, int x, int y, int rotation, Board board) {
        super(copy, x, y, rotation, board);
    }

    protected Square[][] getRotatedShape(){
        if(isTilted()){
            return buildSquares(Z_TILT,PieceType.Z);
        }else{
            return buildSquares(Z_NORMAL,PieceType.Z);
        }
    }

    @Override
    public Piece getTranslatedCopy(int x, int y, int deltaRotation, Board board) {
        return new PieceZ(this, x, y, deltaRotation, board);
    }

}