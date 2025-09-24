package modes.models;


import java.util.ArrayList;

import map.LevelMap;
import map.Tile;
import modes.GameMode;
import units.Unit;

/**
 * Child model of GameModel for moving the tile for selection;
 * upon selection, switches to SelectedModel
 * Similar to WaitingModel, but subtle differences in selection
 * required different models with no additional super class under
 * GameModel. However, some commands are abstracted out.
 * @author prithvi
 *
 */
public class StandbyModel extends GameModel {
	
	public StandbyModel(LevelMap m){
		super(m);
	}
	
	/**
	 * Return selected tile
	 */
	public Tile getTile() {
		return getSelectedTile();
	}

	/**
	 * Move tile here updates selected tile
	 * based only on bounds of map
	 */
	public void moveTile(int dx, int dy) {
		if (!isTileInBounds(dx, dy))
			return;
		updateSelectedTile(dx, dy);
	}
	
	/**
	 * On selection, switches to SelectedModel
	 * And sets selected tile and unit
	 */
	public void select(int mouseX, int mouseY) {
		selectTile();
	}
	
	private void selectTile() {
		if (getSelectedTile() != null) {
			if (getSelectedTile().getUnit() != null
					&& ((ArrayList<Unit>) getCurrentPlayer().getAttribute("UnitGroup").getData()).contains(
							getSelectedTile().getUnit())) {
				clickOccupiedTile();
		        GameMode.setCurrState(GameMode.SELECTED);
			}
			else{
	             setSelectedUnit(null);
			}
		}
	}
	
	private void clickOccupiedTile() {
		if (getSelectedUnit() != null) {
			 getSelectedUnit().setSelected(false);
		}
		updateSelectedUnit();
	}
	
	private void updateSelectedUnit() {
		setSelectedUnit(getSelectedTile().getUnit());
	}
	
	private void updateSelectedTile(int mouseX, int mouseY) {
		if (isTileInBounds(mouseX, mouseY)) {
			if (getSelectedTile() != null) {
				unhighlightPreviousTile();
			}
			setSelectedTile(getMap().getTileByCoords(getSelectedTile()
					.getTileCoordinateX() + mouseX, getSelectedTile()
					.getTileCoordinateY() + mouseY));
			getSelectedTile().pushBackground(Tile.SELECTED_TILE_BACKGROUND_IMAGE_RANK);
		}
	}
	
}
