package input;

import map.LevelMap;
import modes.TBGameMode;
/**
 * Abstract super class that general input events
 * should extend
 * @author prithvi
 *
 */
public abstract class UserEvents {

	/**
	 * Fields required for UserEvents to work
	 */
	private LevelMap map;
	//GameMode is necessary to have access to the current model
	private TBGameMode gameMode;
	// UserEvent's key binding
	private int keyBinding;
	
	public UserEvents(TBGameMode gameMode, int keyBinding) {
		this.gameMode = gameMode;
		map = this.gameMode.getMap();
		setKeyBinding(keyBinding);
	}
	
	/**
	 * Is this event if binding matches input
	 * @param event
	 * @return
	 */
	public boolean isLinkedToEvent(int event) {
		return getKeyBinding()==event;
	}

	/**
	 * If isLinkedToEvent() call this method
	 * @param mouseX
	 * @param mouseY
	 */
	public abstract void performEvent(int mouseX, int mouseY);
	
	/*
	 * Method for writing to key binding file
	 */
	public String toString() {
		return this.getClass().getCanonicalName()+" "+getKeyBinding();
	}
	
	/*
	 * Various getters and setters to access
	 * fields required for children of this class
	 */
	public int getKeyBinding() {
		return keyBinding;
	}

	public LevelMap getMap() {
		return map;
	}
	
	public TBGameMode getGame() {
		return gameMode;
	}

	public void setKeyBinding(int keyBinding) {
		this.keyBinding = keyBinding;
	}
	
}
