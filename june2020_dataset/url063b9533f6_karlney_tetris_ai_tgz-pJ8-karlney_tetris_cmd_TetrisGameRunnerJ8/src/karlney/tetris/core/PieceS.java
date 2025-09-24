package karlney.tetris.core;

public class PieceS extends PieceISZ {

    static final boolean[][] S_NORMAL = {{false,false,true},  {false,true,true}, {false,true,false}};
    static final boolean[][] S_TILT = {{false,false,false},  {true,true,false}, {false,true,true}};

    public PieceS(int x, int y, Board board, Square[][] piece) {
        super(x,y,board,piece);
    }

    public PieceS(PieceS copy, int x, int y, int rotation, Board board) {
        super(copy, x, y, rotation, board);
    }

    protected Square[][] getRotatedShape(){
        if(isTilted()){
            return buildSquares(S_TILT,PieceType.S);
        }else{
            return buildSquares(S_NORMAL,PieceType.S);
        }
    }

    @Override
    public Piece getTranslatedCopy(int x, int y, int deltaRotation, Board board) {
        return new PieceS(this, x, y, deltaRotation, board);
    }

}