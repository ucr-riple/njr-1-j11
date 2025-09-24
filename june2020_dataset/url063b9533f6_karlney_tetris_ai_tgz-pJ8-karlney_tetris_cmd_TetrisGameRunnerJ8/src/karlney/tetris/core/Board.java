package karlney.tetris.core;


/**
 * TODO javadoc
 */
public class Board {

    public static final int DEFAULT_ROWS =20;
    public static final int DEFAULT_COLS =10;

    private int rows;
    private int cols;

    private Square[][] board;

    public Board(){
        this(DEFAULT_COLS,DEFAULT_ROWS);
    }

    /**
     * TODO javadoc
     * @param cols
     * @param rows
     */
    public Board(int cols, int rows){
        this.cols = cols;
        this.rows = rows;
        board = new Square[cols +2][rows +2];

        for (int i=0;i<cols+2;i++){
            board[i][0]=new Square(PieceType.BOARD,true);
            board[i][rows+1]=new Square(PieceType.BOARD,true);
        }
        for (int i=0;i<rows+2;i++){
            board[0][i]=new Square(PieceType.BOARD,true);
            board[cols+1][i]=new Square(PieceType.BOARD,true);
        }

        for (int j=1; j<rows+1;j++)
            for (int i=1; i<cols+1; i++)
                board[i][j]=new Square(PieceType.BOARD,false);
    }

    /**
     * TODO javadoc
     * @param copy
     */
    public Board(Board copy){
        this.cols = copy.cols;
        this.rows = copy.rows;
        board = new Square[cols +2][rows +2];
        for (int i=0;i<cols+2;i++){
            System.arraycopy(copy.board[i], 0, board[i], 0, rows + 2);
        }
    }

    /**
     * TODO javadoc
     * @param x
     * @param y
     * @param square
     */
    public void addSquare(int x, int y, Square square){
        board[x][y]=square;
    }



    /**
     * TODO javadoc
     * @param piece
     * @return
     */
    public void placePieceOnBoard(Piece piece) throws UnableToPlacePieceException {
        for(int i=0; i<piece.getSize(); i++){
            for(int j=0; j<piece.getSize(); j++){
                if(piece.isFilled(i,j)){
                    try{
                        board[i+piece.getX()][j+piece.getY()]= piece.getSquare(i,j);
                    }catch (Exception e){
                        throw new UnableToPlacePieceException("Could not place square on x="+i+piece.getX()+" y="+j+piece.getY(),e);
                    }
                }
            }
        }
    }


    /**
     * This method checks for full rows and removes them.
     *
     * @return the number of removed rows
     */
    public int removeFullRows(){
        int removed=0;
        for(int j= rows; j>0; j--){
            boolean fullRow = true;
            for(int i= cols; i>0; i--){
                fullRow = (fullRow && board[i][j].isFilled());
            }
            if(fullRow){
                removeRow(j);
                j++;
                removed++;
            }
        }

        return removed;
    }


    private void removeRow(int j){
        for(int i=1; i<= cols; i++){
            board[i][j]=new Square(PieceType.BOARD,false);
        }
        moveDown(j);
    }


    private void moveDown(int row){
        for(int j=row; j>1; j--){
            for(int i=1; i<=cols; i++){
                board[i][j]= board[i][j-1];
            }
        }
    }

    /**
     * TODO javadoc
     * @param piece
     * @return
     */
    public boolean allowedPlacement(Piece piece){
        return allowedPlacement(piece.getX(), piece.getY(), piece.getShape());
    }

    /**
     * TODO javadoc
     * @param x
     * @param y
     * @param shape
     * @return
     */
    public boolean allowedPlacement(int x, int y, Square[][] shape) {
        for(int j=0; j<shape.length; j++){
            for(int i=0; i<shape.length; i++){
                try{
                    if(board[x+i][y+j].isFilled() && shape[i][j].isFilled())
                        return false;
                }catch(IndexOutOfBoundsException e){
                    //it is ok for the bounding box to be outside boundaries
                }
            }
        }
        return true;
    }


    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Square getSquare(int x, int y) {
        return board[x][y];
    }

    public boolean isFilled(int x, int y) {
        return board[x][y].isFilled();
    }

}
