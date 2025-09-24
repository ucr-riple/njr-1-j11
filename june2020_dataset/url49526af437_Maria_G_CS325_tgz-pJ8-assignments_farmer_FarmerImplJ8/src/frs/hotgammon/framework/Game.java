package frs.hotgammon.framework;

import java.util.ArrayList;

/** This interface encapsulate all responsibilites of
    a Backgammon game. Please consult the book's
    project part for further descriptions.
 
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

public interface Game {

  // == mutator methods ==

  /** Reset the entire game to start from scratch. 
   * No player is in turn, and the game awaits a
   * call to nextTurn to start a game.
   */
  public void newGame();

  /** Tell the game instance that the user wants the game to
   * progress after the 'turn has finished' i.e. the player in
   * turn has exhausted his/her abilities for moving checkers.
   * The game responds by changing to the other player and rolling
   * the dice. At the beginning of the game (after 'newGame')
   * the dice are rolled and depending on the roll, the game
   * progresses. NOTE that if the dice rolled are equal in
   * value (like 1,1) then the game state is STILL in the
   * initialisation phase (no player is in turn); thus
   * you must repeatedly invoke nextTurn() until the
   * dice rolled are not equal. The first non-equal valued
   * dice rolled results in a player being defined as the
   * player to move first. If die 1 > die 2 then red starts;
   * otherwise it is black that moves first.
   * 
   * PRECONDITION: The player in turn has indeed exhausted the
   * his/her ability to make moves, i.e. this method
   * can only be called if getNumberOfMovesLeft() returns 0.
   */
  public void nextTurn();

  /** move one checker from one location to another. If the move is
   * invalid, then no change is made on the board. If moving to a
   * location occupied by a single opponent checke (standard
   * backgammon rules) then the opponent checker is moved to the bar.
   * @param from the location to move the checker from
   * @param to the location to move the checker to
   * @return false if the indicated move is illegal 
   */
  public boolean move(Location from, Location to);


  // accessor methods

  /** return the color of the player that is in turn i.e. is allowed
   *  to make a move or roll the dice. If no player is in
   * turn (before the game is started), NONE is returned.
   * @return the color of the player to move or roll next. 
   */
  public Color getPlayerInTurn();

  /** return the number of moves left in the current turn. I.e. if a
   * new turn has just begun and the dice rolled are [1,2] the return
   * value is 2. If dice rolled are [3,3] then the return value is 4
   * (if using standard backgammon rules). The number of moves left
   * are subject to the backgammon rules, that is if the player that
   * has just rolled cannot use the dice values at all (for instance
   * in situations where a checker is in the bar and the points are
   * blocked for the given rolled dice) then the return value is 0.
   * @return the number of moves left for the player-in-turn subject
   * to the rules of backgammon.
  */
  public int getNumberOfMovesLeft();

  /** return an integer array of size exactly 2 containing the
    * values of the dice thrown.
    * @return array of two integers defining the dice values rolled last.
    */
  public int[] diceThrown();

  /** return an integer array of size in range [0;4] containing
   * die values that have not yet been used to move a checker.
   * POSTCONDITION: The array is sorted so the largest die value
   * is first in the array.
   * @return int array of unused die values.
   */
  public int[] diceValuesLeft();

  /** return the winner of this game. 
   * @return the winner of the game; if game is still in progress, then
   * Color.NONE is returned.
   */
  public Color winner();

  // == ACCESSORS to the board 

  /** get the colour of the checkers on a given location.
   * @param location the location on the board to access
   * @return the color of the checkers on this location
   */
  public Color getColor(Location location);
  
  /** get the count of checkers of this location.
   * @param location the location to inspect
   * @return a integer value showing the number of checkers on this location.
   */
  public int getCount(Location location);
  

  /** add an observer to this game
   * @param observer the observer to notify in case of state changes.
   */
  public void addObserver(GameObserver observer);

  public ArrayList<GameObserver> getObservers();
}
