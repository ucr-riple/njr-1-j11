package frs.hotgammon.tests.preGuiTests.stubs;

import frs.hotgammon.MonFactory;
import frs.hotgammon.MoveValidator;
import frs.hotgammon.RollDeterminer;
import frs.hotgammon.TurnDeterminer;
import frs.hotgammon.WinnerDeterminer;
import frs.hotgammon.framework.Game;
import frs.hotgammon.variants.movevalidators.CompleteMoveValidator;
import frs.hotgammon.variants.movevalidators.PlayerDependentMoveValidator;
import frs.hotgammon.variants.rolldeterminers.PairSequenceDeterminer;
import frs.hotgammon.variants.rolldeterminers.RandomRollDeterminer;
import frs.hotgammon.variants.turndeterminers.AlternatingTurnDeterminer;
import frs.hotgammon.variants.winnerdeterminers.BearOffWinnerDeterminer;
import frs.hotgammon.variants.winnerdeterminers.SixMoveWinnerDeterminer;

public class Fixed_RedStarts_SemiMonFactory implements MonFactory {
	   
	private Game game;
	
	@Override
	public MoveValidator createMoveValidator() {
		return new CompleteMoveValidator(game);
	}

	@Override
	public TurnDeterminer createTurnDeterminer() {
		return new AlternatingTurnDeterminer(game);
	}

	@Override
	public WinnerDeterminer createWinnerDeterminer() {
		return new BearOffWinnerDeterminer(game);
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
