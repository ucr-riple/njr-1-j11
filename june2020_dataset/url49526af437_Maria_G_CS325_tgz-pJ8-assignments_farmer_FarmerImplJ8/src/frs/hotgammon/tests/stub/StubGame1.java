package frs.hotgammon.tests.stub;

import java.util.ArrayList;

import frs.hotgammon.framework.*;

/** A testing stub for visual testing of
 * the hotgammon graphical user interface.
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
public class StubGame1 implements Game {

  public StubGame1() {
    newGame();
  }

  ArrayList<GameObserver> observers = new ArrayList<GameObserver>();
  
  // Here I only have one red and one
  // black checker on the board!
  Location loneRiderHere1, loneRiderHere2;

  // toggle between player in turn
  boolean tictac = true;
  // moves left to make for a player
  int movesLeft;

  public void newGame() {
    movesLeft = 2;
    turn = 0;

    loneRiderHere1 = Location.B12;
    loneRiderHere2 = Location.R1;
  }

  // count turns, used to simulate dice rolling
  int turn;
  public void nextTurn() {
    turn++;
    movesLeft = 2;
    tictac = !tictac;
    System.out.println("nextTurn: " + turn);
    
  //Notify Observers
	  for( GameObserver gO : this.observers ){
		  gO.diceRolled(diceThrown());
	  }
  }

  /** for testing purposes location B3 and R3 are
   * invalid to move to.
   */
  public boolean move(Location from, Location to) {
    if (to != Location.B3 && to != Location.R3) {
      System.out.println("GAME: moving from " + from + " to " + to);
      if ( from == loneRiderHere1 ) { 
        loneRiderHere1 = to; 
      } else if ( from == loneRiderHere2 ) {
        loneRiderHere2 = to; 
      }
    } else {
      System.out.println("GAME: Moving to B3/R3 is illegal (testing purposes)");
    //Notify Observers
	  for( GameObserver gO : this.observers ){
		  gO.checkerMove(from, from);
	  }
      return false;
    }
    movesLeft--;
    //Notify Observers
	  for( GameObserver gO : this.observers ){
		  gO.checkerMove(from, to);
	  }
    return true;
  }

  // accessor methods
  public Color getPlayerInTurn() {
    if (movesLeft == 0)
      return Color.NONE;
    if (tictac)
      return Color.RED;
    return Color.BLACK;
  }

  public int getNumberOfMovesLeft() {
    return movesLeft;
  }

  public int[] diceThrown() {
    switch (turn % 5) {
      case 1 :
        return new int[] { 2, 3 };
      case 2 :
        return new int[] { 4, 4 };
      case 3 :
        return new int[] { 3, 6 };
      case 4 :
        return new int[] { 6, 1 };
      case 0 :
        return new int[] { 5, 3 };
      default :
        return new int[] { 2, 2 };
    }
  }

  public int[] diceValuesLeft() {
    int[] v = { 1 };
    return v;
  }

  public Color winner() {
    return Color.NONE;
  }

  // Board 
  public Color getColor(Location location) {
    if ( location == loneRiderHere1 ) return Color.BLACK;
    if ( location == loneRiderHere2 ) return Color.RED;
    return Color.NONE;
  }

  public int getCount(Location location) {
    int sum = 0;
    if ( location == loneRiderHere1 ) sum++;
    if ( location == loneRiderHere2 ) sum++;
    return sum;
  }

  public void addObserver(GameObserver gl) { 
	  this.observers.add(gl);
  }

@Override
public ArrayList<GameObserver> getObservers() {
	return this.observers;
}
}