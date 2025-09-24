package input;

import modes.TBGameMode;
/**
 * Class that inherits from UserEvents directly
 * for changing player's turns
 * @author prithvi
 *
 */
public class CyclePlayerEvent extends UserEvents {

	public CyclePlayerEvent(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

	public void performEvent(int mouseX, int mouseY) {
		getGame().cycleTurn();
	}
	
}
