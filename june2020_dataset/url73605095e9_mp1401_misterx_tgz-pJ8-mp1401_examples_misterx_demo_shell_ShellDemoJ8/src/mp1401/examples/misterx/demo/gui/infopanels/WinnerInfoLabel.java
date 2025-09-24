package mp1401.examples.misterx.demo.gui.infopanels;

import mp1401.examples.misterx.model.game.Game;
import mp1401.examples.misterx.model.observers.GameStateChangeObserver;

public class WinnerInfoLabel extends AbstractGameViewInfoLabel implements GameStateChangeObserver {

	private static final String TEXT = "Winner";
	
	private static final long serialVersionUID = 2388657600841080459L;
	
	private Game game;

	public WinnerInfoLabel(Game game) {
		super(TEXT);
		this.game = game;
		this.game.addGameStateChangeObserver(this);
		refreshText();
	}

	@Override
	public String getValue() {
		return game.getWinner().toString();
	}

	@Override
	public void gameStateChangeUpdate() {
		refreshText();
	}

}
