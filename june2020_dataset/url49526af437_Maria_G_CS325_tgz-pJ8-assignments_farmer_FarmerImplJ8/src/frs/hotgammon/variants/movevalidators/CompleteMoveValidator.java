package frs.hotgammon.variants.movevalidators;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frs.hotgammon.MoveValidator;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Game;
import frs.hotgammon.framework.GameObserver;
import frs.hotgammon.framework.Location;

public class CompleteMoveValidator implements MoveValidator {

	private Game game;
	
	public CompleteMoveValidator() {
	}
	
	public CompleteMoveValidator(Game game) {
		this.setGame(game);
	}
	
	@Override
	public boolean isValid(Location from, Location to) {
		Color playerInTurn = game.getPlayerInTurn();		
		
		int distanceTravelledWithDirection = (playerInTurn == Color.BLACK) ? Location.distance(from, to) : (-1 * Location.distance(from, to));//(playerInTurn == Color.BLACK) ? Location.distance(from, to) : -1 * Location.distance(from, to);// (playerInTurn == Color.BLACK) ? rawDistanceTravelled : -1 * (rawDistanceTravelled);
		
		
		//Common
		if(from == to){
			  return false;
		  }
		if ( !containsPlayerPiece(playerInTurn, from) ){//Can't move opponent's piece

			//Notify Observers
			  for( GameObserver gO : this.game.getObservers() ){
				  gO.setStatus("Invalid Move: Can not move opponent's piece. " + this.game.getPlayerInTurn().toString() + " has " + this.game.getNumberOfMovesLeft() + " moves left...");
			  }
			//
			  
			return false;
		}
		if ( isEmpty(from) ){ //Can't move piece if no pieces

			//Notify Observers
			  for( GameObserver gO : this.game.getObservers() ){
				  gO.setStatus("Invalid Move: Can not move from an empty location. " + this.game.getPlayerInTurn().toString() + " has " + this.game.getNumberOfMovesLeft() + " moves left...");
			  }
			//
			  
			return false;
		}
		if ( isLocationOccupiedByOpponent(playerInTurn, to)){// //toSquare can no be occupied by >1 opponent pieces

			//Notify Observers
			  for( GameObserver gO : this.game.getObservers() ){
				  gO.setStatus("Invalid Move: Can not move to an occupied location. " + this.game.getPlayerInTurn().toString() + " has " + this.game.getNumberOfMovesLeft() + " moves left...");
			  }
			//
			  
			  return false;				
		}
		
		
		if ( !isBarEmpty(playerInTurn) ){ 
			//If piece on bar, must move piece from bar.
			if ( from != getBar(playerInTurn) || getOpponentInnerTable(playerInTurn).indexOf(to) < 0 ){

				//Notify Observers
				  for( GameObserver gO : this.game.getObservers() ){
					  gO.setStatus("Invalid Move: Must move piece from Bar to opponent's inner table first. " + this.game.getPlayerInTurn().toString() + " has " + this.game.getNumberOfMovesLeft() + " moves left...");
				  }
				//
				  
				return false;
			}
		}
		if ( to == getBearOff(playerInTurn) ){
			if( areAllPiecesInPlayerInnerTable(playerInTurn) ){
				//Is the move valid with die rolls?, if not, is there a valid move? if not, is move value < die roll, if is true, if not false.
				if (!isMovingTowardsInnerTable(distanceTravelledWithDirection)){//Can only move towards player's inner table

					//Notify Observers
					  for( GameObserver gO : this.game.getObservers() ){
						  gO.setStatus("Invalid Move: Can not move to opponent's bear off. " + this.game.getPlayerInTurn().toString() + " has " + this.game.getNumberOfMovesLeft() + " moves left...");
					  }
					//
					  
					return false;
				}
				if ( !isValidDiceValue(distanceTravelledWithDirection) ){//Can only move if there is a corresponding dice value in roll
					if (validMoveDoesNotExistInInnerTable(playerInTurn)){
						return true;
					}
				}
				else{
					return true;
				}
			}

			//Notify Observers
			  for( GameObserver gO : this.game.getObservers() ){
				  gO.setStatus("Invalid Move: Can not move to bear off with pieces outside inner table. " + this.game.getPlayerInTurn().toString() + " has " + this.game.getNumberOfMovesLeft() + " moves left...");
			  }
			//
			  
			return false;
		}
		else{			
			if ( !isValidDiceValue(distanceTravelledWithDirection) ){//diceValuesLeft.indexOf(distanceTravelledWithDirection) < 0 ){ //Can only move if there is a corresponding dice value in roll

				//Notify Observers
				  for( GameObserver gO : this.game.getObservers() ){
					  gO.setStatus("Invalid Move: Can not travel distance that is not equal to an unused dice value. " + this.game.getPlayerInTurn().toString() + " has " + this.game.getNumberOfMovesLeft() + " moves left...");
				  }
				//
				  
				return false;
			}
			if (!isMovingTowardsInnerTable(distanceTravelledWithDirection)){//distanceTravelledWithDirection < 0){//Can only move towards player's inner table

				//Notify Observers
				  for( GameObserver gO : this.game.getObservers() ){
					  gO.setStatus("Invalid Move: Can not travel away from inner table. " + this.game.getPlayerInTurn().toString() + " has " + this.game.getNumberOfMovesLeft() + " moves left...");
				  }
				//
				  
				return false;
			}

		}
		return true;
	}
	

	@Override
	public void setGame(Game game) {
		this.game = game;
	}
	
	private boolean isValidDiceValue(int distanceTravelledWithDirection){
		List<Integer> diceValuesLeft = getDiceValuesLeft();
		if ( diceValuesLeft.indexOf(distanceTravelledWithDirection) < 0 ){ //Can only move if there is a corresponding dice value in roll
			return false;
		}
		return true;
	}
	
	private boolean isMovingTowardsInnerTable(int distanceTravelledWithDirection){
		return (distanceTravelledWithDirection > -1);
	}
	
	private boolean containsPlayerPiece(Color player, Location loc){
		return (this.game.getColor(loc) == player && !isEmpty(loc) );
	}
	
	private List<Integer> getDiceValuesLeft(){
		List<Integer> diceValuesLeft = new ArrayList<Integer>();	
		int[] diceValuesLeftArr = this.game.diceValuesLeft();
		for (int i = 0; i < diceValuesLeftArr.length; i++){
			diceValuesLeft.add(diceValuesLeftArr[i]);
		}
		return diceValuesLeft;
	}
	
	private Location getBar(Color player) {
		return (player == Color.BLACK) ? Location.B_BAR : Location.R_BAR;
	}
	private Location getBearOff(Color player){
		return (player == Color.BLACK) ? Location.B_BEAR_OFF : Location.R_BEAR_OFF;
	}
	
	private boolean isEmpty(Location loc){ //COMPLETE
		return (this.game.getCount(loc) == 0);
	}
	
	private boolean isLocationOccupiedByOpponent(Color playerInTurn, Location loc){//COMPLETE?
		if (this.game.getCount(loc) > 1){ //Space Occupied if there are two or more opponent pieces.
			if ( this.game.getColor(loc) != playerInTurn && this.game.getColor(loc) != Color.NONE ){
				return true;
			}
		}
		return false;
	}
	
	private boolean isBarEmpty(Color player){//COMPLETE?
		//Location playerBar = (player == Color.BLACK) ? Location.B_BAR : Location.R_BAR;
		return (this.game.getCount(getBar(player)) == 0);
	}
	
	private List<Location> getInnerTable(Color player){

		List<Location> redInnerTable = Arrays.asList(Location.R1, Location.R2, Location.R3, Location.R4, Location.R5, Location.R6);
		List<Location> blackInnerTable = Arrays.asList( Location.B1, Location.B2, Location.B3, Location.B4, Location.B5, Location.B6);
		return (player == Color.BLACK) ? blackInnerTable : redInnerTable;
		
	}
	private List<Location> getOpponentInnerTable(Color player){

		List<Location> redInnerTable = Arrays.asList(Location.R1, Location.R2, Location.R3, Location.R4, Location.R5, Location.R6);
		List<Location> blackInnerTable = Arrays.asList( Location.B1, Location.B2, Location.B3, Location.B4, Location.B5, Location.B6);
		return (player == Color.BLACK) ? redInnerTable : blackInnerTable;
		
	}

	private List<Location> getOutsideInnerTable(Color player){
		List<Location> outsideRedInnerTable = Arrays.asList(
				Location.R7, Location.R8, Location.R9, Location.R10, Location.R11, Location.R12, 
				Location.B12, Location.B11, Location.B10, Location.B9, Location.B8, Location.B7,
				Location.B6, Location.B5, Location.B4, Location.B3, Location.B2, Location.B1);;
		List<Location> outsideBlackInnerTable = Arrays.asList(
				Location.R1, Location.R2, Location.R3, Location.R4, Location.R5, Location.R6, 
				Location.R7, Location.R8, Location.R9, Location.R10, Location.R11, Location.R12, 
				Location.B12, Location.B11, Location.B10, Location.B9, Location.B8, Location.B7);
		
		return (player == Color.BLACK) ? outsideBlackInnerTable : outsideRedInnerTable;
	}
	
	private boolean areAllPiecesInPlayerInnerTable(Color player){

		List<Location> outsidePlayerInnerTable = getOutsideInnerTable(player);
		
		for (int i = 0; i < 18; i++){
			if ( this.game.getCount(outsidePlayerInnerTable.get(i)) > 0 ){
				if ( this.game.getColor(outsidePlayerInnerTable.get(i)) == player ){
					return false;
				}
			}
		}
		
		return true;
	}

	private boolean validMoveDoesNotExistInInnerTable(Color player){
		List<Integer> diceValuesLeft = getDiceValuesLeft(); 
		
		List<Location> playerInnerTable = getInnerTable(player);
		
		for (int i = 0; i < 6; i++){
			if( this.game.getCount(playerInnerTable.get(i)) > 0 ){
				if ( diceValuesLeft.indexOf(Location.distance(playerInnerTable.get(i), getBearOff(player))) > -1 ){
					return false;
				}					
			}
		}
		
		
		return true;
		
	}
}
