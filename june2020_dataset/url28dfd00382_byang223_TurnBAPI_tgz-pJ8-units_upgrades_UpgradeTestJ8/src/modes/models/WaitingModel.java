package modes.models;

import map.LevelMap;
import map.Tile;
import modes.GameMode;

public class WaitingModel extends GameModel {

	public WaitingModel(LevelMap m) {
		super(m);
	}

	public Tile getTile() {
		return getSelectedDestination();
	}

	public void moveTile(int dx, int dy) {
		if (!isTileInBounds(dx, dy))
			return;
		updateSelectedDestination(dx, dy);
	}
	
	public void select(int mouseX, int mouseY) {
		performCommand();
	}
	
	private void performCommand() {
		getSelectedButton().getInteractionButton().performButton(this);
		getSelectedDestination().clearSelection();
	    setSelectedDestination(getSelectedTile());
	    setSelectedUnit(getSelectedTile().getUnit());
	    getSelectedTile().pushBackground(Tile.SELECTED_TILE_BACKGROUND_IMAGE_RANK);
		GameMode.setCurrState(GameMode.SELECTED);
	}
	
	private void updateSelectedDestination(int mouseX, int mouseY) {
		if (isTileInBounds(mouseX, mouseY)) {
			if (isTileHighlighted(mouseX, mouseY)) {
				if (getSelectedDestination() != null) {
					unhighlightPreviousDestination();
				}
				setSelectedDestination(getMap().getTileByCoords(
						getSelectedDestination().getTileCoordinateX() + mouseX,
						getSelectedDestination().getTileCoordinateY() + mouseY));
				getSelectedDestination().pushBackground(
						Tile.SELECTED_TILE_BACKGROUND_IMAGE_RANK);
			} else {
				if (getSelectedDestination() != null) {
					unhighlightPreviousDestination();
				}
				setSelectedDestination(getSelectedTile());
			}
		}
	}
	
	protected boolean isTileInBounds(int mouseX, int mouseY) {
		if (getTile().getTileCoordinateX() + mouseX < getMap().getDimX() && getTile().getTileCoordinateY() + mouseY < getMap().getDimY()
				&& getTile().getTileCoordinateX() + mouseX >= 0 && getTile().getTileCoordinateY() + mouseY >= 0)
			return true;
		else
			return false;
	}
	
	private boolean isTileHighlighted(int mouseX, int mouseY) {
		if (getMap().getTileByCoords(getTile().getTileCoordinateX() + mouseX, getTile().getTileCoordinateY() + mouseY).getHighestRank()>1)
			return true;
		return false;
	}
	
	private void unhighlightPreviousDestination() {
        	getSelectedDestination().removeBackground(Tile.SELECTED_TILE_BACKGROUND_IMAGE_RANK);
	}

}
