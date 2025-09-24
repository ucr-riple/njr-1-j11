package input;

import modes.TBGameMode;
/**
 * Abstract super class for deselecting all
 * Children of this class deselectAll()
 * @author prithvi
 *
 */
public abstract class DeselectEvents extends UserEvents{

	public DeselectEvents(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

	public void performEvent(int mouseX, int mouseY) {
		getGame().deselectAll();
	}

}
