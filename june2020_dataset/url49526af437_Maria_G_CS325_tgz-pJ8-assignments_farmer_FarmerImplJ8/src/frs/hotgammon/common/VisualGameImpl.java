package frs.hotgammon.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frs.hotgammon.Board;
import frs.hotgammon.MonFactory;
import frs.hotgammon.MoveValidator;
import frs.hotgammon.RollDeterminer;
import frs.hotgammon.TurnDeterminer;
import frs.hotgammon.WinnerDeterminer;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Game;
import frs.hotgammon.framework.GameObserver;
import frs.hotgammon.framework.Location;



public class VisualGameImpl implements Game {
  private Board gameBoard;
  private Color playerInTurn;

  private List<Integer> diceRoll;
  protected int movesLeft;
  private int turns;
  private MoveValidator moveValidator;
  private WinnerDeterminer winnerDeterminer;
  private TurnDeterminer turnDeterminer;
  private RollDeterminer diceRollDeterminer;
  private ArrayList<GameObserver> observers = new ArrayList<GameObserver>();
  
  //Added

	private ArrayList<MoveRecord> turnMoves;
	private int turnMovesIdx;
  
  //

  
  public VisualGameImpl(MoveValidator mValidator, WinnerDeterminer wDeterminer, TurnDeterminer tDeterminer, RollDeterminer drDeterminer){
	  mValidator.setGame(this);
	  wDeterminer.setGame(this);
	  tDeterminer.setGame(this);
	  moveValidator = mValidator;
	  winnerDeterminer = wDeterminer;
	  turnDeterminer = tDeterminer;
	  diceRollDeterminer = drDeterminer;
  }
  
  public VisualGameImpl(MonFactory factory){
	  setFactory(factory);
  }
  
  public void setFactory(MonFactory factory){
	  factory.setGame(this);
	  moveValidator = factory.createMoveValidator();
	  winnerDeterminer = factory.createWinnerDeterminer();
	  turnDeterminer = factory.createTurnDeterminer();
	  diceRollDeterminer = factory.createRollDeterminer();
  }
  
  public void newGame() {
	  gameBoard = new BoardImpl();
	  diceRollDeterminer.reset();
	  playerInTurn = Color.NONE;
	  turns = 0;

	  configure(new Placement[] {
	    		// B1 = 2 red
	    		new Placement(Color.RED,Location.B1),
	    		new Placement(Color.RED,Location.B1),
	    		// B6 = 5 black
	    		new Placement(Color.BLACK,Location.B6),
	    		new Placement(Color.BLACK,Location.B6),
	    		new Placement(Color.BLACK,Location.B6),
	    		new Placement(Color.BLACK,Location.B6),
	    		new Placement(Color.BLACK,Location.B6),
	    		// B8 = 3 black
	    		new Placement(Color.BLACK,Location.B8),
	    		new Placement(Color.BLACK,Location.B8),
	    		new Placement(Color.BLACK,Location.B8),
	    		// B12 = 5 red
	    		new Placement(Color.RED,Location.B12),
	    		new Placement(Color.RED,Location.B12),
	    		new Placement(Color.RED,Location.B12),
	    		new Placement(Color.RED,Location.B12),
	    		new Placement(Color.RED,Location.B12),
	    		// R12 = 5 black
	    		new Placement(Color.BLACK,Location.R12),
	    		new Placement(Color.BLACK,Location.R12),
	    		new Placement(Color.BLACK,Location.R12),
	    		new Placement(Color.BLACK,Location.R12),
	    		new Placement(Color.BLACK,Location.R12),		
	    		// R8 = 3 red
	    		new Placement(Color.RED,Location.R8),
	    		new Placement(Color.RED,Location.R8),
	    		new Placement(Color.RED,Location.R8),		
	    		// R6 = 5 red
	    		new Placement(Color.RED,Location.R6),
	    		new Placement(Color.RED,Location.R6),
	    		new Placement(Color.RED,Location.R6),
	    		new Placement(Color.RED,Location.R6),
	    		new Placement(Color.RED,Location.R6),		
	    		// R1 = 2 black
	    		new Placement(Color.BLACK,Location.R1),
	    		new Placement(Color.BLACK,Location.R1),
	    });
	  
	//Notify Observers
	  notifyObserversOfNewStatus("New Game: Click the dice to roll. Player with the highest dice value goes first!");
	  notifyObserversOfDiceRolled(new int[]{1,1});
	//Added

		this.turnMoves = new ArrayList<MoveRecord>();
		this.turnMovesIdx = 0;
	  
	  //
	  
  }
  
  public void nextTurn() {

	  //Check For Winner
	  if(isWinner()){
		  return;
	  }
	  
	  playerInTurn = turnDeterminer.nextTurn();
	  
	  //Roll Dice
	  diceRollDeterminer.rollDice();
	  int[] dRoll = diceRollDeterminer.getDiceRoll();
	  
	  if(turns == 0){
		  while(dRoll.length > 2){
			  diceRollDeterminer.rollDice();
			  dRoll = diceRollDeterminer.getDiceRoll();
		  }
		  playerInTurn = determineStartingPlayer(dRoll);
		
	  }
	  
	  //Create diceRoll 
	  if(dRoll.length == 2){
		  diceRoll = new ArrayList<Integer>(Arrays.asList(dRoll[0], dRoll[1])) ;
	  }
	  else{
		  diceRoll = new ArrayList<Integer>(Arrays.asList(dRoll[0], dRoll[1], dRoll[2], dRoll[3])) ;
	  }
	  
	  turns++;
	  movesLeft = diceRoll.size();
	  
	  String statusMessage = "";
	  if(turns == 1){
		  statusMessage = playerInTurn.toString() + " rolled the highest value! ";
	  }
	  
	  //Notify Observers
	  statusMessage += getPlayerInTurn().toString() + " has "+ getNumberOfMovesLeft() +" moves left...";
	  notifyObserversOfNewStatus(statusMessage);
	  notifyObserversOfDiceRolled(diceThrown());
	  

	  //Added
	  
	  this.turnMoves = new ArrayList<MoveRecord>();
	  for(int i = 0; i < movesLeft; i++){
		  this.turnMoves.add(new MoveRecord());
	  }
	  this.turnMovesIdx = 0;
	  
	  //

	  
  }
  
  private Color determineStartingPlayer(int[] dRoll){
	  return (dRoll[0] > dRoll[1]) ? Color.RED : Color.BLACK;
  }
  
  private void moveDuringConfigure(Location from, Location to){
	  Color checkerColor = (from == Location.R_BEAR_OFF) ? Color.RED : Color.BLACK;
	  gameBoard.move(from, to, checkerColor);
	  
	  //Notify Observers
	  notifyObserversOfCheckerMove(from, to);
  }
  
  private boolean isConfigureMove(Location from, Location to){
	  return (from == Location.R_BEAR_OFF || from == Location.B_BEAR_OFF) && turns == 0;
  }
  
  public boolean move(Location from, Location to) { 
	  	 
	  ///If from BearOff During Configure call, allow
	  if(isConfigureMove(from, to)){
		  moveDuringConfigure(from, to);
		  return true;
	  }

		//ADDED
		  //If move is the reverse of a move previously made during this turn, allow.
		  Move m = new Move(from,to);
		  if(isMoveBack(m)){
			  executeMoveBack(m);
			  return true;
		  }
		  //ADDED
		  

	  //Check for Valid Moves, if none, set movesLeft to 0.
	  if(!validMovesExist()){
		  movesLeft = 0;
		//Notify Observers
		  notifyObserversOfCheckerMove(from, from);
		  notifyObserversOfNewStatus(getPlayerInTurn().toString() + " has no valid moves left...");
		//
		  return false;
	  }
	  
	  ///
	  if (movesLeft == 0){
		//Notify Observers
		  notifyObserversOfCheckerMove(from, from);
		  notifyObserversOfNewStatus("Invalid Move: " + getPlayerInTurn().toString() + " has 0 moves left...");
		//
		  return false;
	  }
	  if (moveValidator.isValid(from, to)){
		  
		  if(gameBoard.getCountAt(to) == 1 && gameBoard.getColorAt(to) != playerInTurn){
			  moveOpponentToBar(to);
		  }
		  boolean moveValue = gameBoard.move(from, to, playerInTurn);
		  
		  if(moveValue == true){
			  movesLeft--;
			  
			  removeDiceValueUsed(from, to);
			  
			  //Notify Observers
			  notifyObserversOfCheckerMove(from, to);
			  notifyObserversOfNewStatus("Valid Move: " + getPlayerInTurn().toString() + " has "+ getNumberOfMovesLeft() +" moves left...");
			  //
			  
			  //ADDED
			  
			  this.turnMoves.get(this.turnMovesIdx).setMove(new Move(from,to));
			  this.turnMovesIdx++;
			  
			  //
		  }
		  else{
				//Notify Observers
				  notifyObserversOfCheckerMove(from, from);
				  notifyObserversOfNewStatus("Invalid Move on GameBoard: " + from.toString() + " to " + to.toString() + ". " + getPlayerInTurn().toString() + " has "+ getNumberOfMovesLeft() +" moves left...");
		  }
		  return moveValue;
	  }

		//Notify Observers
		  for( GameObserver gO : this.observers ){
			  gO.checkerMove(from, from);
		  }
		  notifyObserversOfCheckerMove(from, from);
		//
	  return false;
	  
  }
  
 
  //Notify Observers Methods
  private void notifyObserversOfDiceRolled(int[] diceRolled){
	  for( GameObserver gO : this.observers ){
		  gO.diceRolled(diceRolled);
	  }
  }
  private void notifyObserversOfNewStatus(String status){
	  for( GameObserver gO : this.observers ){
		  gO.setStatus(status);
	  }
  }
  private void notifyObserversOfCheckerMove(Location from, Location to){
	  for( GameObserver gO : this.observers ){
		  gO.checkerMove(from, to);
	  }
  }
  private void notifyObserversOfGameOver(){
	  for( GameObserver gO : this.observers ){
		  gO.gameOver();
	  }
  }
  
  private boolean validMovesExist(){

	 List<Integer> diceOptions = diceRoll;
		 
	 //Check bar
	 Location barOfPlayerInTurn = getBarOfPlayerInTurn();
	 int barCount = getCount(barOfPlayerInTurn);
	 if(barCount > 0){
		 for(int j = 0; j < diceOptions.size(); j++){
			 Location to = getToLocationFromBar(diceOptions.get(j));
			 if(moveValidator.isValid(barOfPlayerInTurn, to)){
				 return true;
			 }
		 }
	 }
	 //Check Rest of Locations if Bar is empty
	 else{
		 for(Location frmLoc : Location.values()){
			 if(getCount(frmLoc) > 0 && getColor(frmLoc) == playerInTurn){
				 for(int j = 0; j < diceOptions.size(); j++){
					 Location to = Location.findLocation(playerInTurn, frmLoc, diceOptions.get(j));
					 if(moveValidator.isValid(frmLoc, to)){
						 return true;
					 }
				 }
			 }
		 }
		 
	 }
	//No valid moves:
	return false;
	  
  }
  
  private Location getBarOfPlayerInTurn(){
	  return (playerInTurn == Color.BLACK)? Location.B_BAR : Location.R_BAR; 
  }
  
  private Location getToLocationFromBar(int dValue){
	  List<Location> redInnerTable = Arrays.asList(Location.R1, Location.R2, Location.R3, Location.R4, Location.R5, Location.R6);
	  List<Location> blackInnerTable = Arrays.asList( Location.B1, Location.B2, Location.B3, Location.B4, Location.B5, Location.B6);
	  
	  List<Location> destinationInnerTable = (playerInTurn == Color.BLACK) ? redInnerTable : blackInnerTable;
		
	  return destinationInnerTable.get(dValue - 1);
	  
  }
  
  public Color getPlayerInTurn() { return playerInTurn; }
  public int getNumberOfMovesLeft() { return movesLeft; }
  public int[] diceThrown() { return diceRollDeterminer.getDiceRoll(); }//DICE_ROLLS[diceRollIdx]; }
  public int[] diceValuesLeft() { 
	  int[] diceRollArr = new int[diceRoll.size()];
	  for (int i = 0; i < diceRollArr.length; i++){
		  diceRollArr[i] = diceRoll.get(i);
	  }
	  return diceRollArr;}
  public Color winner() { return winnerDeterminer.winner(turns); }
  public Color getColor(Location location) { return gameBoard.getColorAt(location); }
  public int getCount(Location location) { return gameBoard.getCountAt(location); }
  
  private void removeDiceValueUsed(Location from, Location to){
	  int diceValueUsed = (playerInTurn == Color.BLACK) ? Location.distance(from, to) : (-1 * Location.distance(from, to));//(playerInTurn == Color.BLACK) ? rawDistanceTravelled : -1 * (rawDistanceTravelled);
	  int remIdx = diceRoll.indexOf(diceValueUsed);
	  if (remIdx < 0){
		  remIdx = 0;
	  }
	  diceRoll.remove(remIdx);
	  
	  //Added
	  this.turnMoves.get(this.turnMovesIdx).setDieValueUsed(diceValueUsed);
	  //
  }
  
  private void moveOpponentToBar(Location opponentLoc){
	  Color colorOfOpponent = opponentColor();

	  Location otherPlayerBar = otherPlayerBar();
	  gameBoard.place(colorOfOpponent, otherPlayerBar.ordinal());
	  gameBoard.remove(colorOfOpponent, opponentLoc.ordinal());
	  
	  //Notify Observers
	  notifyObserversOfCheckerMove(opponentLoc, otherPlayerBar);
	  //

	  //Added
	  Move aMove = new Move(opponentLoc, otherPlayerBar);
	  this.turnMoves.get(this.turnMovesIdx).setAssociatedMoveToBar(aMove);
	  //
	  
  }
  
  protected Location otherPlayerBar(){
	  Color colorOfOpponent = opponentColor();

	  return (colorOfOpponent == Color.BLACK) ? Location.B_BAR : Location.R_BAR;
	  
  }
  
  private Color opponentColor(){
	  if(playerInTurn == Color.NONE){
		  return Color.NONE;
	  }
	  return (playerInTurn == Color.RED) ? Color.BLACK : Color.RED;
  }
  
  	static public class Placement {
	    public Location location;
	    public Color    player;
	    public Placement(Color player, Location location) {
	        this.player = player;
	        this.location = location;
	    }
	}
  	
	public void configure(Placement[] placements) {
		gameBoard = new BoardImpl();
	    for (int i = 0; i < placements.length; i++) {
	        //gameBoard.place(placements[i].player, placements[i].location.ordinal());	
	    	Location from = getPlayerBearOff(placements[i].player);
	    	gameBoard.place(placements[i].player, from.ordinal());
	        move(from, placements[i].location);	        
	    }
	}
	
	private Location getPlayerBearOff(Color player){
		return ( player == Color.BLACK) ? Location.B_BEAR_OFF : Location.R_BEAR_OFF;
	}

	@Override
	public void addObserver(GameObserver observer) {
		this.observers.add(observer);		
	}
	
	public ArrayList<GameObserver> getObservers(){
		return this.observers;
	}
	
	private boolean isWinner(){
		Color winner = winner();
		if(!winner.equals(Color.NONE)){
			//Notify Observers
			  notifyObserversOfNewStatus(winner + " is the WINNER!");
			  notifyObserversOfGameOver();
			  //
			return true;
		}

		return false;
	}

	  
	private boolean isMoveBack(Move m){
		if ( getIdxOfInTurnMoves(m) == -1 ){
			return false;
		}
		return true;
	}
	
	private int getIdxOfInTurnMoves(Move m){
			for(int i = 0; i < this.turnMoves.size(); i++){
				MoveRecord mR = this.turnMoves.get(i);

				Move mRMove = mR.getMove();
				if(mRMove != null){
					if(mRMove.isReverse(m)){
						return i;
					}
				}
			}
		return -1;
	}
	 
	private void executeMoveBack(Move m){
		int idx = getIdxOfInTurnMoves(m);
		MoveRecord mRec = this.turnMoves.get(idx);
		
		gameBoard.move(m.getFrom(), m.getTo(), getColor(m.getFrom()));
		
		//Add back used diceValue
		diceRoll.add(mRec.getDieValueUsed());
		
		//Add +1 to movesLeft
		movesLeft++;
		
		//Notify Observers
		  //Move Back
		  notifyObserversOfCheckerMove(m.to, m.from);
		  //Move Back from Bar if needed
		  Move associatedMove = mRec.getAssociatedMoveToBar();
		  if(associatedMove != null){
			  notifyObserversOfCheckerMove(associatedMove.to, associatedMove.from);		  
		  }
		  
		  notifyObserversOfNewStatus("Checker returned to previous position, " + getPlayerInTurn() + " has " + getNumberOfMovesLeft() + " moves left...");
		//
		  
		  //Remove mRec
		  removeMoverRecordAt(idx);
		
	}
	
	private void removeMoverRecordAt(int idx){
		this.turnMoves.remove(idx);
		this.turnMoves.add(new MoveRecord());
		this.turnMovesIdx--;
		
	}
	
	  
	  
	  
	public static class Move{
		  
		  private Location from;
		  private Location to;
		  
		  public Move(Location from, Location to){
			  this.from = from;
			  this.to = to;
		  }
		  
		  public Location getTo() {
			return to;
		}

		public Location getFrom() {
			return from;
		}

		public boolean isEqual(Move reverse) {
			 return (reverse.from == from) && (reverse.to == to);
		  }


		  public boolean isReverse(Move reverse) {
				 return (reverse.from == to) && (reverse.to == from);
		  }
		  
		public boolean isEqual(Location otherFrom, Location otherTo){
			  return (otherFrom == from) && (otherTo == to);
		  }
		  
		  public boolean isReverse(Location otherFrom, Location otherTo){
			  return (otherFrom == to) && (otherTo == from);
		  }
		  
		  public Move reverse(){
			  return new Move(to, from);
		  }
		  
		  public String toString(){
			  return "("+from.toString()+","+to.toString()+")";
		  }
		  
	  }
		
	 public static class MoveRecord{
		  
		  private Move move;
		  private Move associatedMoveToBar;
		  private int dieValueUsed;
		  
		  public MoveRecord(){
		  }
		  
		  public MoveRecord(Move move, Move associatedMove, int dieValue){
			  this.move = move;
			  this.associatedMoveToBar = associatedMove;
			  this.dieValueUsed = dieValue;
		  }
		  
		  public void setMove(Move m){
			  this.move = m;
		  }

		  public void setAssociatedMoveToBar(Move m){
			  this.associatedMoveToBar = m;
		  }

		  public void setDieValueUsed(int dV){
			  this.dieValueUsed = dV;
		  }
		  
		  public Move getMove(){
			  return this.move;
		  }

		  public Move getAssociatedMoveToBar(){
			  return this.associatedMoveToBar;
		  }

		  public int getDieValueUsed(){
			  return this.dieValueUsed;
		  }
	  }
		



}

