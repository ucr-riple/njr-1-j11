package frs.hotgammon.variants.turndeterminers;

import frs.hotgammon.TurnDeterminer;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Game;

public class AlternatingTurnDeterminer implements TurnDeterminer{

	private Game game;
	
	public AlternatingTurnDeterminer(){
	}
	
	public AlternatingTurnDeterminer(Game game){
		this.setGame(game);
	}
	
	@Override
	public Color nextTurn() {
		return (this.game.getPlayerInTurn() == Color.BLACK) ? Color.RED : Color.BLACK;
	}

	@Override
	public void setGame(Game game) {
		this.game = game;		
	}

}
