package frs.hotgammon.variants.turndeterminers;

import frs.hotgammon.TurnDeterminer;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Game;

public class AceyDeuceyTurnDeterminer implements TurnDeterminer{

	private Game game;
	
	public AceyDeuceyTurnDeterminer(){
	}
	
	public AceyDeuceyTurnDeterminer(Game game){
		this.setGame(game);
	}
	
	@Override
	public Color nextTurn() {
		if (this.game.diceThrown()[0] == 1 && this.game.diceThrown()[1] == 2){
			return this.game.getPlayerInTurn();
		}
		else{
			return (this.game.getPlayerInTurn() == Color.BLACK) ? Color.RED : Color.BLACK;
		}
	}

	@Override
	public void setGame(Game game) {
		this.game = game;
		
	}

}
