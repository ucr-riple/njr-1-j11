package assignments.frs.chap5;

import assignments.frs.chap5.Breakthrough.PieceType;

/** Implementation stub.

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Computer Science Department
     Aarhus University
   
   This source code is provided WITHOUT ANY WARRANTY either 
   expressed or implied. You may study, use, modify, and 
   distribute it for non-commercial purposes. For any 
   commercial use, see http://www.baerbak.com/
*/
public class BreakthroughImpl implements Breakthrough {
  private PlayerType playerInTurn = PlayerType.WHITE;
  private Board board = new Board();

	
  public PieceType getPieceAt( int row, int column ) {
    return board.get(row,column);
  }
  
  public PlayerType getPlayerInTurn() {
    return playerInTurn;
  }

  public PlayerType getWinner() {
    
	  for(int col = 0; col < 8; col++){
		  if( board.get(0, col)  == PieceType.WHITE){
			  return PlayerType.WHITE;
		  }
		  if( board.get(7, col) == PieceType.BLACK){
			  return PlayerType.BLACK;
		  }
	  }
	  return null;
  }

  public boolean isMoveValid(int fromRow, int fromColumn,
                             int toRow, int toColumn) {
	  
	  int rowsDiff = (playerInTurn == PlayerType.BLACK) ? (-1 * (fromRow - toRow)) : (fromRow - toRow);
	  int colsDiff = (fromColumn - toColumn) < 0 ? (-1 * (fromColumn - toColumn)) : (fromColumn - toColumn);
	  PieceType toSquare = board.get(toRow,toColumn);
	  PieceType fromSquare = board.get(fromRow,fromColumn);
	  
	  PieceType currentPlayerPieceType = (playerInTurn == PlayerType.WHITE) ?
			  PieceType.WHITE : PieceType.BLACK;
	  
	  if( currentPlayerPieceType != fromSquare){
		  return false;
	  }
	  if( rowsDiff > 1 || colsDiff > 1 ){ //Move more than one square
		  return false;
	  }
	  else if ( rowsDiff == 1 && colsDiff == 0 ){ //Move Forward
		  if (toSquare == PieceType.NONE){
			  return true;
		  }
	  }
	  else if ( rowsDiff == 1 && colsDiff == 1 ){ //Move Diagonally
		  if (toSquare != currentPlayerPieceType){
			  return true;
		  }
	  }

	  return false;  
	  
  }

  public void move(int fromRow, int fromColumn,
                   int toRow, int toColumn) throws InvalidMoveException {

	  if( isMoveValid(fromRow, fromColumn, toRow, toColumn) ){
		  
		  board.set(toRow, toColumn, board.get(fromRow,fromColumn));
		  board.set(fromRow,fromColumn, PieceType.NONE);
	  
		  //board.print();
		  //Toggle playerInTurn
		  playerInTurn = (playerInTurn == PlayerType.WHITE) ? PlayerType.BLACK : PlayerType.WHITE;
		  
	  }
	  else{
		  throw new InvalidMoveException();
	  }		  
  }
  
  
}

