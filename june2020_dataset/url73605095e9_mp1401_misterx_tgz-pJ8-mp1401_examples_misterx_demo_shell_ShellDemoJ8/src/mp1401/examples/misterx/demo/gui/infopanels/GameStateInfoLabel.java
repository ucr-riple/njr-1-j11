package mp1401.examples.misterx.demo.gui.infopanels;

import mp1401.examples.misterx.model.game.Game;
import mp1401.examples.misterx.model.observers.GameStateChangeObserver;

public class GameStateInfoLabel extends AbstractGameViewInfoLabel implements GameStateChangeObserver {

	private static final long serialVersionUID = -2741715964029041795L;
	private static final String LEGEND = "Current Game-State";
	
	private final Game game;
	
	public GameStateInfoLabel(Game game) {
		super(LEGEND);
		this.game = game;
		game.addGameStateChangeObserver(this);
		refreshText();
	}

	@Override
	public String getValue() {
		return game.getCurrentGameState().toString();
	}

	@Override
	public void gameStateChangeUpdate() {
		refreshText();
	}

}
