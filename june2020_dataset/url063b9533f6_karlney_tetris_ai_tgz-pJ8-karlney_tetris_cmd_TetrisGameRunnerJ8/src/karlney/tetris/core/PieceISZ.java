package karlney.tetris.core;

public abstract class PieceISZ extends AbstractPiece {

    private boolean tilted;

    public PieceISZ(int x, int y, Board board, Square[][] piece) {
        super(x,y, 0, board,piece);
        tilted=true;
    }

    public PieceISZ(PieceISZ copy, int x, int y, int rotation, Board board) {
        super(x,y, copy.getOrientation(), board,copy.shape.clone());
        this.tilted = copy.tilted;
        for (int i=0; i<rotation; i++){
            rotateNoCheck();
        }
    }

    @Override
    public synchronized boolean rotateIfPossible(){
        if (inputsAccepted){
            boolean rotationPossible = board.allowedPlacement(x, y, getRotatedShape());
            if(rotationPossible){
                shape = getRotatedShape();
                tilted=!tilted;
                rotation = (rotation+1)% getPossibleOrientations();
            }else{
                //If the piece is S or Z only try one step to the left and one to the right,
                // if the piece is an I then try one step to the left and 1 or 2 steps to the right
                int tries = getSize()==3?1:2;
                for (int i=-1; i<=tries; i+=1){
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
        tilted=!tilted;
        rotation = (rotation+1)% getPossibleOrientations();
    }

    @Override
    public int getPossibleOrientations() {
        return 2;
    }

    protected Square[][] buildSquares(boolean[][] shape, PieceType type){
        Square[][] sh= new Square[shape.length][shape.length];
        for (int i=0; i<shape.length;i++){
            for (int j=0; j<shape.length;j++){
                sh[i][j]=new Square(type,shape[i][j]);
            }
        }
        return sh;
    }

    protected abstract Square[][] getRotatedShape();

    protected synchronized boolean isTilted() {
        return tilted;
    }
}