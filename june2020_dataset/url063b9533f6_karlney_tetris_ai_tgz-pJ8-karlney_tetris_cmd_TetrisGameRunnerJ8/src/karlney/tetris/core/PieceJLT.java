package karlney.tetris.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PieceJLT extends AbstractPiece {

    private static Logger log = LoggerFactory.getLogger(PieceJLT.class);


    public PieceJLT(int x, int y, Board board, Square[][] piece) {
        super(x,y, 0, board,piece);
    }

    public PieceJLT(PieceJLT copy, int x, int y, int rotation, Board board) {
        super(copy,x,y,rotation,board);
    }

    @Override
    public Piece getTranslatedCopy(int x, int y, int deltaRotation, Board board) {
        return new PieceJLT(this, x, y, deltaRotation, board);
    }

    @Override
    public int getPossibleOrientations() {
        return 4;
    }

    @Override
    public synchronized boolean rotateIfPossible(){
        if (inputsAccepted){
            boolean rotationPossible = board.allowedPlacement(x, y, getRotatedShape());
            if(rotationPossible){
                shape = getRotatedShape();
                rotation = (rotation+1)% getPossibleOrientations();
            }else{
                for (int i=-1; i<=1; i+=2){
                    rotationPossible = board.allowedPlacement(x+i, y, getRotatedShape());
                    if (rotationPossible){
                        x+=i;
                        shape = getRotatedShape();
                        rotation = (rotation+1)% getPossibleOrientations();
                        break;
                    }
                }
            }
            return rotationPossible;
        }else{
            return false;
        }
    }


    @Override
    public synchronized void rotateNoCheck(){
        shape = getRotatedShape();
        rotation = (rotation+1)% getPossibleOrientations();
    }

    /**
     * @return the shape matrix for this block rotated 90 degrees
     */
    private Square[][] getRotatedShape(){
        Square[][] shapeClone= new Square[getSize()][getSize()];
        for(int x=0; x<3; x++){
            for(int y=0; y<3; y++){
                shapeClone[x][y]= shape[y][2-x];
            }
        }

        int b = 2;
        //TGM rotation (bottom is always preserved)
        if (!shapeClone[0][b].isFilled() && !shapeClone[1][b].isFilled() && !shapeClone[2][b].isFilled()){
            Square[][] tgm = new Square[getSize()][getSize()];

            for(int x=0; x<3; x++){
                tgm[x][0] = shapeClone[x][2];
            }

            for(int x=0; x<3; x++){
                for(int y=0; y<2; y++){
                    tgm[x][y+1] =  shapeClone[x][y];
                }
            }

            shapeClone = tgm;
        }
        /*
        //The pieces needs to be rotated counter clockwise, therefore this is needed
        Square[][] out= new Square[getSize()][getSize()];
        for (int i=0; i<3;i++){
            for(int x=0; x<getSize(); x++){
                for(int y=0; y<getSize(); y++){
                    out[x][y]= shapeClone[2-x][2-y];
                }
            }
        }
        */
        return shapeClone;
    }

}
