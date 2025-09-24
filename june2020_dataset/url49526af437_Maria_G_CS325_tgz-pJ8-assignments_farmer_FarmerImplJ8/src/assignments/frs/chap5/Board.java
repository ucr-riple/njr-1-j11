package assignments.frs.chap5;

import assignments.frs.chap5.Breakthrough.PieceType;

public class Board {
	  
	  private PieceType[][] gameBoard = new PieceType[8][8];

	  public Board(){
	  for (int col = 0; col < 8; col++){
		  for (int row = 0; row < 2; row++){
			  gameBoard[row][col] = PieceType.BLACK;
		  }
		  for (int row = 2; row < 6; row++){
			  gameBoard[row][col] = PieceType.NONE;
		  }

		  for (int row = 6; row < 8; row++){
			  gameBoard[row][col] = PieceType.WHITE;
		  }
	  }
	  }
	  
	  public PieceType get(int row, int col){
		  return gameBoard[row][col];
	  }
	  
	  public void set(int row, int col, PieceType pt){
		  gameBoard[row][col] = pt;
	  }
	  
	  public void print(){
		  System.out.println("GAMEBOARD ::: ");
		  for (int i = 0; i < 8; i++){
			  for(int j = 0; j < 8; j++){
				  if(gameBoard[i][j] == PieceType.WHITE){
					  System.out.print("("+i+","+j+")"+"W ");
				  }else if(gameBoard[i][j] == PieceType.BLACK){
					  System.out.print("("+i+","+j+")"+"B ");
				  }else if(gameBoard[i][j] == PieceType.NONE){
					  System.out.print("("+i+","+j+")"+"_ ");
				  }
			  }
			  System.out.println();
		  }
		  System.out.println();
		  System.out.println();
	  }

}
