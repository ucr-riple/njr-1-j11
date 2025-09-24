package frs.hotgammon.variants.movevalidators;

import frs.hotgammon.MoveValidator;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Game;
import frs.hotgammon.framework.Location;

public class PlayerDependentMoveValidator implements MoveValidator{
	private MoveValidator blackMoveValidator = new SimpleMoveValidator();
	private MoveValidator redMoveValidator = new CompleteMoveValidator();
	
	private Game game;
	
	public PlayerDependentMoveValidator() {
	}
	
	public PlayerDependentMoveValidator(Game game) {
		this.setGame(game);
	}
	
	@Override
	public boolean isValid(Location from, Location to) {
		Color player = this.game.getPlayerInTurn();
		
		if(player == Color.BLACK){
			return blackMoveValidator.isValid(from, to);
		}
		if(player == Color.RED){
			return redMoveValidator.isValid(from, to);			
		}
		
		return false;
	}
	
	@Override
	public void setGame(Game game) {
		this.game = game;
		blackMoveValidator.setGame(game);
		redMoveValidator.setGame(game);
	}

}
