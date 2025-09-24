package frs.hotgammon.variants.movevalidators;

import frs.hotgammon.*;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Game;
import frs.hotgammon.framework.GameObserver;
import frs.hotgammon.framework.Location;

public class SimpleMoveValidator implements MoveValidator{

	private Game game;
	
	public SimpleMoveValidator(){
	}
	
	public SimpleMoveValidator(Game game) {
		this.setGame(game);
	}

	@Override
	public boolean isValid(Location from, Location to) {
		int fromCount = game.getCount(from);
		int toCount = game.getCount(to);
		Color fromColor = game.getColor(from);
		Color toColor = game.getColor(to);
		
		Color playerInTurn = game.getPlayerInTurn();
			
		if(from == to){
			  return false;
		  }
		if ( fromColor != playerInTurn ){ //Can't move opponent's piece
			//Notify Observers
			  for( GameObserver gO : this.game.getObservers() ){
				  gO.setStatus("Invalid Move: Can not move opponent's piece. " + this.game.getPlayerInTurn().toString() + " has " + this.game.getNumberOfMovesLeft() + " moves left...");
			  }
			//
			  
			return false;
		}
		if ( fromCount == 0 ){ //Can't move piece if no pieces
			//Notify Observers
			  for( GameObserver gO : this.game.getObservers() ){
				  gO.setStatus("Invalid Move: Can not move from an empty location. " + this.game.getPlayerInTurn().toString() + " has " + this.game.getNumberOfMovesLeft() + " moves left...");
			  }
			//
			  
			return false;
		}
		if ( toCount > 0 ){ //toSquare is not empty
			if (toColor != playerInTurn && toColor != Color.NONE){ //If to square is occupied by the opponent
				
				//Notify Observers
				  for( GameObserver gO : this.game.getObservers() ){
					  gO.setStatus("Invalid Move: Can not move to an occupied location. " + this.game.getPlayerInTurn().toString() + " has " + this.game.getNumberOfMovesLeft() + " moves left...");
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

	
}
