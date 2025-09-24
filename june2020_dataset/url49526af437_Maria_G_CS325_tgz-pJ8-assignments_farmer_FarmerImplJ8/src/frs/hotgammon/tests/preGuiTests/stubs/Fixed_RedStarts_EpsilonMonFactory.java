package frs.hotgammon.tests.preGuiTests.stubs;

import frs.hotgammon.MonFactory;
import frs.hotgammon.MoveValidator;
import frs.hotgammon.RollDeterminer;
import frs.hotgammon.TurnDeterminer;
import frs.hotgammon.WinnerDeterminer;
import frs.hotgammon.framework.Game;
import frs.hotgammon.variants.movevalidators.SimpleMoveValidator;
import frs.hotgammon.variants.rolldeterminers.RandomRollDeterminer;
import frs.hotgammon.variants.turndeterminers.AlternatingTurnDeterminer;
import frs.hotgammon.variants.winnerdeterminers.SixMoveWinnerDeterminer;

public class Fixed_RedStarts_EpsilonMonFactory implements MonFactory {
	  
	private Game game;
		
	@Override
	public MoveValidator createMoveValidator() {
		return new SimpleMoveValidator(game);
	}

	@Override
	public TurnDeterminer createTurnDeterminer() {
		return new AlternatingTurnDeterminer(game);
	}

	@Override
	public WinnerDeterminer createWinnerDeterminer() {
		return new SixMoveWinnerDeterminer();
	}

	@Override
	public RollDeterminer createRollDeterminer() {
		return new FixedRedStartsRandomRollDeterminer();
	}

	@Override
	public void setGame(Game game) {
		this.game = game;
	}

}
