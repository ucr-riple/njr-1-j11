package assignments.frs.chap5;

/** Tentative interface for a Breakthrough game.
    To minimize the interface, positions (squares) on the board
    are access by simple (row, column) integers, both in
    range (0..7). Black has his pawns on row 0 and 1 while
    white has his pawns on row 6 and 7.

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
public interface Breakthrough {

  /** Enumeration of the three types of 'pieces' that
      is possible on a given location on the chess board:
      black, white, or no piece */
  public static enum PieceType { BLACK, WHITE, NONE};
  /** Enumeration of the two types of players in the game,
      either white or black */
  public static enum PlayerType { BLACK, WHITE };

  /** Return the type of piece on a given (row,column) on
      the chess board.
      @return the type of piece on the location.
  */
  public PieceType getPieceAt( int row, int column );

  /** Return the player that is in turn, i.e. allowed
      to move.
      @return the player that may move a piece next 
  */
  public PlayerType getPlayerInTurn();

  /** Return the winner of the game.
      @return the winner of the game or null in case no winner
      has been found yet. */
  public PlayerType getWinner();

  /** Validate a move from a given location (fromRow, fromColumn) to a
      new location (toRow, toColumn). A move is invalid if you try to
      move your opponent's pieces or the move does not follow the
      rules, see the exercise specification.  PRECONDITION: the
      (row,column) coordinates are valid posititions, that is, all
      between (0..7).
      @return true if the move is valid, false otherwise
  */
  public boolean isMoveValid(int fromRow, int fromColumn,
                             int toRow, int toColumn);
  
  /** Move a piece from a given location (fromRow, fromColumn) to a
      new location (toRow, toColumn). PRECONDITION: the move is valid
      on the given board, that is, a previous call to isMoveValid was
      true.
 * @throws Exception 
  */
  public void move(int fromRow, int fromColumn,
               int toRow, int toColumn) throws Exception;
}

