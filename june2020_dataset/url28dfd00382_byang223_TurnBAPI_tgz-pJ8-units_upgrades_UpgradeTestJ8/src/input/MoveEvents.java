package input;

import modes.TBGameMode;
/**
 * Abstract super class for moving the highlighted tile around
 * based on mouse and key inputs
 * @author prithvi
 *
 */
public abstract class MoveEvents extends UserEvents{

	public MoveEvents(TBGameMode game, int keyBinding) {
		super(game, keyBinding);
	}
	
	/**
	 * To be implemented, children of this class
	 * must return the change in tile position
	 * @param mouseX
	 * @return
	 */
	abstract protected int getDx(int mouseX);
	/**
	 * To be implemented, children of this class
	 * must return the change in tile position
	 * @param mouseY
	 * @return
	 */
	abstract protected int getDy(int mouseY);

	/**
	 * Checks for null, then moves the highlighted tile
	 * based on the change in position
	 */
	public void performEvent(int mouseX, int mouseY) {
		getGame().nullCheck();
		getGame().moveTile(getDx(mouseX), getDy(mouseY));
	}
}
