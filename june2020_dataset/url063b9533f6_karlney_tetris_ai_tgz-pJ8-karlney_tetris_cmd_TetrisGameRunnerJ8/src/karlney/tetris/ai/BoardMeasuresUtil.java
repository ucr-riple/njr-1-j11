package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;
import karlney.tetris.core.PieceType;
import karlney.tetris.core.Square;

/**
 * TODO javadoc
 *
 * AI planning methods
 *
 * <p/>
 * Date: 2012-11-04
 * Time: 10:01
 *
 * @author karl.neyvaldt
 */
public class BoardMeasuresUtil {

    /*public double getNearlyFullRows(){
       double out=0;
       int lastrow=0;
       for(int j=ROW-getMaxHeight() ; j<ROW; j++){
          int block=0;
          for(int i=COL; i>0; i--){
             if ( gameField[i][j].isFilled())
                block++;
          }
          out=out+block-lastrow;
          lastrow=block;
       }
       return out;
    }*/

    /*
    public double getBlockDensity(){
        return (getNrOfBlocks()+0.0)/(getMaxHeight()*10.0);
    }
    */

    /*
    private int getBlocksInRow(int j){
        int nr=0;
        for(int i= cols; i>0; i--){
            if ( board[i][j].isFilled()){
                nr++;
            }
        }
        return nr;
    }

    public int getMaxHeight(){
        for(int j=0; j< rows; j++){
            for(int i= cols; i>0; i--){
                if ( board[i][j].isFilled()){
                    return rows -j;
                }
            }
        }
        return 0;
    }


    public int getNrOfBlocks(){
        int nr=0;
        for(int j= rows; j>0; j--){
            for(int i= cols; i>0; i--){
                if ( board[i][j].isFilled()){
                    nr++;
                }
            }
        }
        return nr;
    }

    public double checkForSequences(){
        double out=0;
        for(int j= rows; j>0; j--){
            int seq=0;
            for(int i= cols; i>0; i--){
                if ( board[i][j].isFilled()){
                    seq++;
                }else{
                    out+= Math.pow(2,seq)-1;
                }
            }
        }
        return out;
    }


    /*
1. Landing Height: The height where the piece is put (= the height of the column + (the height of the piece / 2))

  var lh = last_move.landing_height + ((last_move.piece.length - 1) / 2);

     */
    public static double getLandingHeight(Board board, Piece piece) {
        return getColumnHeight(board,piece)+((getPieceHeight(piece)-1)/2.0);
    }

    private static int getEmptyTopRows(Piece piece) {
        boolean filled = false;
        for (int i=0; i<piece.getSize();i++){
            filled = filled || piece.isFilled(i,0);
        }
        return filled?0:1;
    }

    private static double getColumnHeight(Board board,Piece piece) {
        int y = piece.getY()+getEmptyTopRows(piece);
        return board.getRows()-y-getPieceHeight(piece)+1;
    }

    private static double getPieceHeight(Piece piece) {
        int start = -1;
        for (int j=0;j<piece.getSize();j++){
            for (int i=0; i<piece.getSize();i++){
                if (start<0 && piece.isFilled(i,j)){
                    start=j;
                }
            }
        }
        int end = -1;
        for (int j=piece.getSize();j>0;j--){
            for (int i=0; i<piece.getSize();i++){
                if (end<0 && piece.isFilled(i,j-1)){
                    end=j-1;
                }
            }
        }
        return end-start+1;
    }


    /*
3. Row Transitions: The total number of row transitions. A row transition occurs when an empty cell is adjacent to a filled cell on the same row and vice versa.
    */
    public static int getRowTransitions(Board board) {
        int nr=0;
        for (int j=board.getRows(); j>0; j--){
            for (int i=0; i<board.getCols()+1; i++)	{
                if (board.isFilled(i,j)!=board.isFilled(i+1,j)){
                    nr++;
                }
            }
        }
        return nr;
    }

    /*
4. Column Transitions: The total number of column transitions. A column transition occurs when an empty cell is adjacent to a filled cell on the same column and vice versa.
     */
    public static int getColumnTransitions(Board board) {
        int nr=0;
        for (int j=board.getRows()+1; j>1; j--){
            for (int i=1; i<board.getCols()+1; i++)	{
                if (board.isFilled(i,j-1)!=board.isFilled(i,j)){
                    nr++;
                }
            }
        }
        return nr;
    }

    /*
5. Number of Holes: A hole is an empty cell that has at least one filled cell above it in the same column.
    */
    public static int getNrHoles(Board board){
        int nr=0;
        for (int i=0; i<=board.getCols(); i++)	{
            int last=0;
            for (int j= board.getRows(); j>0; j--){
                if (board.isFilled(i, j)){
                    nr+=last;
                    last=0;
                }
                else{
                    last++;
                }
            }
        }
        return nr;
    }

    /*
6. Well Sums: A well is a succession of empty cells such that their left cells and right cells are both filled.

function GetWellSums(board, num_columns) {
  var well_sums = 0;

  for (var i = 1; i < num_columns - 1; ++i) {
    for (var j = board.length - 1; j >= 0; --j) {
      if ((((board[j] >> i) & 1) == 0) &&
        (((board[j] >> (i - 1)) & 1) == 1) &&
        (((board[j] >> (i + 1)) & 1) == 1)) {
        // Found well cell, count it + the number of empty cells below it.

        ++well_sums;
        for (var k = j - 1; k >= 0; --k) {
          if (((board[k] >> i) & 1) == 0) {
            ++well_sums;
          } else {
            break;
          }
        }
      }
    }
  }

    */
    public static int getWellSums(Board board) {
        int nr=0;
        for (int j= board.getRows(); j>0; j--){
            for (int i=1; i<=board.getCols()+1; i++){
                if (!board.getSquare(i,j).isFilled() && board.getSquare(i-1,j).isFilled() && board.getSquare(i+1,j).isFilled()){
                    //Found well cell, count it plus the number of empty cells below it
                    nr++;
                    for (int k = j+1; k<=board.getRows(); k++){
                        if (!board.getSquare(i,k).isFilled()) {
                            nr++;
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        return nr;
    }

}
