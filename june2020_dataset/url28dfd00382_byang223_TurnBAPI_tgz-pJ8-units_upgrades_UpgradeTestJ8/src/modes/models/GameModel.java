package modes.models;

import java.util.ArrayList;

import map.LevelMap;
import map.Tile;
import modes.GameMode;
import modes.selections.Selections;
import player.Player;
import units.Unit;
import units.interactions.ButtonSprite;
import units.interactions.Interaction;
import units.interactions.InteractionPassiveTurn;
import ai.state.AIstateController;
import attribute.Attribute;
import attribute.AttributeTurn;
import attribute.AttributeUnitGroup;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;

import environment.Transient;

/***
 * Wrapper for state fields. Mainly works with selected units and tiles inside the games.
 * This class is used to avoid passing the entire GameState into the controllers.
 * 
 * 4/12/12 - I see this class potentially being the State of the Game
 * 
 * @author bryanyang
 *
 * @author Prithvi Prabahar
 * -- Added more than just getters and setters for this class, at least for controls
 * -- Renamed GameState to GameModel
 * -- Abstracted out GameModel into separate models
 *
 */

public abstract class GameModel {

	private LevelMap map;
	
	/**
	 * Each GameModel is instantiated with the game's LevelMap, which it uses for much of
	 * the selection of various fields, from units to tiles to even commands
	 * @param m
	 */
	public GameModel(LevelMap m){
		map = m;
		if (getSelectedTile()==null) {
			setSelectedTile(map.getTileByCoords(0, 0));
		}
	}
	
	/**
	 * Abstract method that performs a select command based on the current model
	 * @param mouseX
	 * @param mouseY
	 */
	abstract public void select(int mouseX, int mouseY);
	/**
	 * Returns the relevant tile for selection and highlighting
	 * Either the tile for selection
	 * Or the tile for a destination movement
	 * @return
	 */
	abstract public Tile getTile();
	/**
	 * Handles highlighting tiles, primarily for
	 * StandbyModel
	 * WaitingModel
	 * @param mouseX
	 * @param mouseY
	 */
	abstract public void moveTile(int mouseX, int mouseY);
	
	
	//Below two methods are for StandbyModel and WaitingModel
	/**
	 * Used for finding tiles in map bounds
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	protected boolean isTileInBounds(int mouseX, int mouseY) {
		if (getTile().getTileCoordinateX() + mouseX < getMap().getDimX() && getTile().getTileCoordinateY() + mouseY < getMap().getDimY()
				&& getTile().getTileCoordinateX() + mouseX >= 0 && getTile().getTileCoordinateY() + mouseY >= 0) {
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Remove previous background's tile
	 */
	protected void unhighlightPreviousTile() {
    	getTile().removeBackground(Tile.SELECTED_TILE_BACKGROUND_IMAGE_RANK);
	}
	
	/**
	 * For cases where the selected tile becomes null,
	 * a necessary null check is made in order to
	 * avoid an exception and sets the default selected tile
	 * to the top left tile
	 */
	public void nullCheck() {
		if (getSelectedTile()==null) {
			setSelectedTile(getMap().getTileByCoords(0, 0));
			getSelectedTile().pushBackground(Tile.SELECTED_TILE_BACKGROUND_IMAGE_RANK);
		}
	}
	
	/**
	 * Superclass method shared by all child models
	 * because regardless of which model is being
	 * used, deselecting should be the same. This
	 * method is called primarily by input.DeselectEvents.
	 */
	public void deselectAll() {
		unhighlightSelected();
		unhighlightTiles();
		highlightPlayerUnits();
		setSelectedTile(null);
		setSelectedUnit(null);
		GameMode.setCurrState(GameMode.STANDBY);
	}
	
	/**
	 * Like deselectAll(), this method is also
	 * shared by all child models because changing player
	 * turns should have the same effect regardless of model.
	 * input.CyclePlayerEvent calls this, but other
	 * classes may find it useful to use this (i.e. AI).
	 */
	public void cycleTurn() {
	    applyPassiveEffects();
	    getCurrentPlayer().cycleUnitTurns();
	    getCurrentPlayer().getAttribute(Attribute.TURN).augmentData(1);
	    for (Sprite e: Selections.getEnvironmentGroup().getSprites()) {
	        if (e instanceof Transient) {
	            ((Transient) e).getAttribute(Attribute.TURN).augmentData(1);
	            ((Transient) e).isActive(((AttributeTurn) ((Transient) e).getAttribute(Attribute.TURN)).getData());
	        }   
	    }
		updateCurrentPlayer(updatePlayerIndex());
		unhighlightTiles();
		clearSelectedUnitAndTile();
		highlightPlayerUnits();
		GameMode.setCurrState(GameMode.STANDBY);
		
	    // update AIstateController on current player
		AIstateController.sendCurrentPlayerUpdate(getCurrentPlayer());
	}
	
	/**
	 * Method for performing passive interactions
	 * that should be called if in effect, not when
	 * a user event is called.
	 */
	private void applyPassiveEffects(){
	    ArrayList<Unit> unitlist = getCurrentPlayer().getPlayerUnits().getData();
	    for(Unit u:unitlist){
	        for(Interaction interaction:u.getInteractionList()){
	            if(InteractionPassiveTurn.class.isAssignableFrom(interaction.getClass())){  //if interaction is a passive turn interaction,
	                InteractionPassiveTurn passiveInteraction = (InteractionPassiveTurn) interaction;
	                passiveInteraction.performInteraction(this, u);
	            }
	        }
	    }
	}
	
	/**
	 * Updates the current player index
	 * @return
	 */
	private int updatePlayerIndex() {
		int currentindex = getPlayerList().indexOf(getCurrentPlayer());
		currentindex++;
		if (currentindex > getPlayerList().size() - 1)
			currentindex = 0;
		return currentindex;
	}

	/**
	 * Updates current player based on currentIndex
	 * @param currentindex
	 */
	private void updateCurrentPlayer(int currentindex) {
		setCurrentPlayer(getPlayerList().get(currentindex));
		((AttributeUnitGroup) getCurrentPlayer().getAttribute("UnitGroup")).refreshAttributesAndInteractions();
	}
	
	private void clearSelectedUnitAndTile() {
		setSelectedTile(null);
		if (getSelectedUnit() != null) {
			getSelectedUnit().setSelected(false);
			setSelectedUnit(null);
		}
	}
	
	private void unhighlightTiles() {
	    getMap().unHighlightEverything();
	}

	/**
	 * Highlight the current player's units
	 */
	public void highlightPlayerUnits() {
		ArrayList<Unit> units = ((ArrayList<Unit>) getCurrentPlayer().getAttribute("UnitGroup").getData());
		setSelectedTile(null);

		for (Sprite temp : units) {
			Unit u = (Unit) temp;
			if (u == null)
				continue;
			if (u.isActive()) {
				int x = u.getXTileLoc();
				int y = u.getYTileLoc();
				Tile tile = getMap().getTileByCoords(x, y);
				tile.pushBackground(Tile.CURRENT_PLAYER_OWNED_BACKGROUND_RANK);
			}
		}
	}
	
	private void unhighlightSelected() {
	    getSelectedTile().clearSelection();
	}
	
	
	/**
	 * Below is a block of code with all of the
	 * getters and setters for the private fields
	 * in GameModel, for access by the various child models.
	 * All make reference to the Selections class, which acts
	 * as a wrapper object for these common fields.
	 * 
	 */
	
	public void setSelectedUnit(Unit selectedUnit) {
		Selections.setSelectedUnit(selectedUnit);
	}

	public Unit getSelectedUnit() {
		return Selections.getSelectedUnit();
	}

	public void setSelectedTile(Tile selectedTile) {
		Selections.setSelectedTile(selectedTile);
	}

	public Tile getSelectedTile() {
		return Selections.getSelectedTile();
	}

	public void setSelectedDestination(Tile selectedDestination) {
		Selections.setSelectedDestination(selectedDestination);
	}

	public Tile getSelectedDestination() {
		return Selections.getSelectedDestination();
	}

	public void setSelectedButton(ButtonSprite selectedButton) {
		Selections.setSelectedButton(selectedButton);
	}

	public ButtonSprite getSelectedButton() {
		return Selections.getSelectedButton();
	}

	public void setSelectedAttribute(Attribute selectedAttribute) {
		Selections.setSelectedAtrribute(selectedAttribute);
	}

	public Attribute getSelectedAttribute() {
		return Selections.getSelectedAtrribute();
	}

	public void setButtonGroup(SpriteGroup buttonGroup) {
		Selections.setButtonGroup(buttonGroup);
	}

	public SpriteGroup getButtonGroup() {
		return Selections.getButtonGroup();
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		Selections.setPlayerList(playerList);
	}

	public void setEnvironmentGroup(SpriteGroup enviro) {
		Selections.setEnvironmentGroup(enviro);
	}
	
	public ArrayList<Player> getPlayerList() {
		return Selections.getPlayerList();
	}

	public void setCurrentPlayer(Player currentPlayer) {
		Selections.setCurrentPlayer(currentPlayer);
	}

	public Player getCurrentPlayer() {
		return Selections.getCurrentPlayer();
	}
	
	public LevelMap getMap() {
		return map;
	}
	
}
