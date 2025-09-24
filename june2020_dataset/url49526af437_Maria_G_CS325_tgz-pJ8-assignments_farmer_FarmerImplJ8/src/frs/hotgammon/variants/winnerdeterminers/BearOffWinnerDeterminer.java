package frs.hotgammon.variants.winnerdeterminers;

import frs.hotgammon.WinnerDeterminer;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Game;
import frs.hotgammon.framework.Location;

public class BearOffWinnerDeterminer implements WinnerDeterminer{

	private Game game;
	
	public BearOffWinnerDeterminer(){
	}
	
	public BearOffWinnerDeterminer(Game game){
		this.setGame(game);
	}
	
	@Override
	public Color winner(int turnCount) {
		Color playerInTurn = (this.game.getPlayerInTurn() == Color.NONE) ? Color.BLACK : this.game.getPlayerInTurn();
		Color opponent = (playerInTurn == Color.BLACK) ? Color.RED : Color.BLACK;
		
		Location playerInTurnBearOff = (playerInTurn == Color.BLACK) ? Location.B_BEAR_OFF : Location.R_BEAR_OFF;
		Location opponentBearOff = (playerInTurn == Color.BLACK) ? Location.R_BEAR_OFF : Location.B_BEAR_OFF;
		
		if ( this.game.getCount(playerInTurnBearOff) == 15 ){
			return playerInTurn;
		}
		if ( this.game.getCount(opponentBearOff) == 15 ){
			return opponent;
		}
		
		return Color.NONE;
	}

	@Override
	public void setGame(Game game) {
		this.game = game;
	}


}
