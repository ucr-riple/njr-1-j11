package input;

import modes.TBGameMode;
/**
 * Abstract super class for selecting commands
 * based on key strokes
 * @author prithvi
 *
 */
public abstract class CommandSelectEvents extends UserEvents {

	public CommandSelectEvents(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}

	/**
	 * Method to be implemented by children of this class
	 * for a specific command name
	 * @return
	 */
	protected abstract String getCommandName();

	/**
	 * Run selectCommand in GameMode
	 */
	public void performEvent(int mouseX, int mouseY) {
		getGame().selectCommand(getCommandName());
	}

}
