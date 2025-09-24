package assignments.frs.chap5;

import org.junit.*;

import assignments.frs.chap5.Breakthrough.PieceType;
import static org.junit.Assert.*;

/** Initial test case class for Breakthrough
 
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
public class TestBreakthrough {
  Breakthrough game;
  /** Fixture */
  @Before
  public void setUp() {
    game = new BreakthroughImpl();
  }

  @Test
  public void shouldHaveBlackPawnOn00(){   
    assertEquals( "Black has pawn on (0,0)",
                  BreakthroughImpl.PieceType.BLACK, game.getPieceAt(0,0) );
  }

  @Test
  public void shouldBeginWithWhitePlayer(){   
    assertEquals( "'Player In Turn' should be White Player.",
                  BreakthroughImpl.PlayerType.WHITE, game.getPlayerInTurn() );
  }

  @Test
  public void shouldAlternatePlayersWithTurns() throws Exception{   
    assertEquals( "Turn 1, 'Player In Turn' should be White Player.",
                  BreakthroughImpl.PlayerType.WHITE, game.getPlayerInTurn() );
    game.move(6, 0, 5, 0);
    assertEquals( "Turn 2, 'Player In Turn' should be Black Player.",
            BreakthroughImpl.PlayerType.BLACK, game.getPlayerInTurn() );
  }

  @Test
  public void shouldMoveOnePieceOneSquareStraightTowardsHomeRow() throws Exception{
	  
    game.move(6, 0, 5, 0); //Move from (6,0) to (5,0)
    assertEquals( "Piece should have moved from (6,0).",
            BreakthroughImpl.PieceType.NONE, game.getPieceAt(6,0) );
    assertEquals( "Piece should have moved to (5,0).",
            BreakthroughImpl.PieceType.WHITE, game.getPieceAt(5,0) );

  }
  
  @Test
  public void shouldMoveOnePieceOneSquareDiagonallyRightTowardsHomeRow() throws Exception{
	  
    game.move(6, 0, 5, 1); //Move from (6,0) to (5,1)
    assertEquals( "Piece should have moved from (6,0).",
            BreakthroughImpl.PieceType.NONE, game.getPieceAt(6,0) );
    assertEquals( "Piece should have moved to (5,1).",
            BreakthroughImpl.PieceType.WHITE, game.getPieceAt(5,1) );

  }

  @Test
  public void shouldMoveOnePieceOneSquareDiagonallyLeftTowardsHomeRow() throws Exception{
	
    game.move(6, 1, 5, 0); //Move from (6,1) to (5,0)
    assertEquals( "Piece should have moved from (6,1).",
            BreakthroughImpl.PieceType.NONE, game.getPieceAt(6,1) );
    assertEquals( "Piece should have moved to (5,0).",
            BreakthroughImpl.PieceType.WHITE, game.getPieceAt(5,0) );

  }

  @Test (expected = Exception.class)
  public void shouldOnlyMoveOneSquarePerTurn() throws Exception{
	  
    game.move(6, 0, 4, 0); //Move from (6,1) to (5,0)
    
  }

  @Test (expected = InvalidMoveException.class)
  public void shouldNotMovePieceToOccupiedSquare() throws Exception{
	  
    game.move(7, 0, 6, 0); //Move from (7,0) to (6,0)

  }

  @Test
  public void shouldCaptureOpponentOnTheDiagonal() throws Exception{
	  
    game.move(6, 0, 5, 0); 
    game.move(1, 1, 2, 1); 
    game.move(5, 0, 4, 0); 
    game.move(2, 1, 3, 1); 
    game.move(4, 0, 3, 1); 
    assertEquals( "White should have captured Black Piece at (3,1).",
            BreakthroughImpl.PieceType.WHITE, game.getPieceAt(3,1) );
  }
  
  @Test
  public void shouldWinWhenMovePieceToHomeRow() throws Exception{
	  //TO DO
    game.move(6, 0, 5, 0);  //W
    game.move(1, 1, 2, 1);  //B
    game.move(5, 0, 4, 0);  //W
    game.move(2, 1, 3, 1);  //B
    game.move(4, 0, 3, 0);  //W
    game.move(3, 1, 4, 1);  //B
    game.move(3, 0, 2, 0);  //W
    game.move(4, 1, 5, 1);  //B
    game.move(2, 0, 1, 1);  //W
    game.move(1, 3, 2, 3);  //B
    game.move(1, 1, 0, 2);  //W

    assertEquals( "White should be the winner.",
            BreakthroughImpl.PlayerType.WHITE, game.getWinner() );
  }
  
  @Test (expected = InvalidMoveException.class)
  public void shouldNotAllowWhitePlayerToMoveBlackPiece() throws Exception{

	game.move(1, 1, 2, 1); //White Player move black piece from (1,1) to (2,1)

  }

  @Test (expected = InvalidMoveException.class)
  public void shouldNotAllowMoveSideways() throws Exception{

	game.move(6, 0, 5, 0); //W
	game.move(1, 1, 2, 1); //B
	game.move(5, 0, 5, 1); //W

	
  }
  @Test (expected = InvalidMoveException.class)
  public void shouldNotAllowMoveAwayFromHomeRow() throws Exception{

	game.move(6, 0, 5, 0); //W
	game.move(1, 1, 2, 1); //B
	game.move(5, 0, 6, 0); //W

  }
  
}
