package input;

import modes.TBGameMode;
/**
 * Abstract super class for selecting
 * @author prithvi
 *
 */
public abstract class SelectEvents extends UserEvents{

	public SelectEvents(TBGameMode gameMode, int keyBinding) {
		super(gameMode, keyBinding);
	}
	
	public void performEvent(int mouseX, int mouseY) {
		getGame().select(mouseX, mouseY);
	}
	
}
