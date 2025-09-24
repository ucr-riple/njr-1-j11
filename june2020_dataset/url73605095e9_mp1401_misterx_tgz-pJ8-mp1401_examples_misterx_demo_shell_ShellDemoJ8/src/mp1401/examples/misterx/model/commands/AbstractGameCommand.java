package mp1401.examples.misterx.model.commands;

import mp1401.examples.misterx.model.game.Game;
import mp1401.examples.misterx.model.game.GameImpl;

public abstract class AbstractGameCommand implements GameCommand {
	
	protected Game getGame() {
		return GameImpl.getInstance();
	}

}
