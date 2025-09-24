package karlney.tetris.core;

public class PieceO extends AbstractPiece {

    public PieceO(int x, int y,  Board board, Square[][] piece) {
        super(x,y, 0, board,piece);
    }

    public PieceO(PieceO copy, int x, int y, Board board) {
        super(copy,x,y,copy.getOrientation(),board);
    }

    @Override
    public void rotateNoCheck() {}

    @Override
    public Piece getTranslatedCopy(int x, int y, int deltaRotation, Board board) {
        return new PieceO(this, x, y, board);
    }

    @Override
    public int getPossibleOrientations() {
        return 1;
    }

    @Override
    public boolean rotateIfPossible() {
        return false;
    }

}
