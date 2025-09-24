package modes.models;

import map.LevelMap;
import map.Tile;
import modes.GameMode;
import units.interactions.ButtonSprite;
import units.interactions.ButtonTargetRequired;

import com.golden.gamedev.object.Sprite;
/**
 * Model subclass of GameModel that handles events when
 * a tile and/or unit is selected
 * @author prithvi
 *
 */
public class SelectedModel extends GameModel {

    public SelectedModel(LevelMap m) {
        super(m);
    }

    /**
     * This select command is called by the MouseSelectEvent
     * and handles clicking on a button
     */
    public void select(int mouseX, int mouseY) {
        selectCommand(mouseX, mouseY);
    }

    /**
     * For this method, the relevant tile for
     * the selected model is the currently
     * selected tile
     */
    public Tile getTile() {
        return getSelectedTile();
    }

    /**
     * While a tile is selected, moving or highlighting
     * tiles should not be enabled
     */
    public void moveTile(int mouseX, int mouseY) {
        return;
    }

    /**
     * This method handles command selection based
     * on mouse location
     * and button press
     * using generic parameters
     * @param params
     */
    public void selectCommand(Object...params) {
        Sprite[] myButtons = getButtonGroup().getSprites();
        for (Sprite sprite : myButtons) {
            if (sprite == null)
                continue;
            if (wasClicked(sprite, params)) {
                setSelectedButton((ButtonSprite) sprite);
                if (ButtonTargetRequired.class
                        .isAssignableFrom(getSelectedButton()
                                .getInteractionButton().getClass())) {
                	highlightTilesForCommand();
                } else {// move that does not require a target
                    getSelectedButton().getInteractionButton().performButton(
                            this);
                }
            }

        }
    }

    /**
     * Based on params, uses the correct algorithm
     * for finding the selected command
     * @param sprite
     * @param params
     * @return
     */
    private boolean wasClicked(Sprite sprite, Object...params) {
		if (params[0].getClass().equals(String.class)) {
			return ((ButtonSprite) sprite).getInteractionButton().toString()
					.equals(params[0]);
		} else {
			double xLoc = sprite.getX();
			double yLoc = sprite.getY();
			double w = (double) sprite.getWidth();
			double h = (double) sprite.getHeight();
			if ((Integer) params[0] > xLoc && (Integer) params[0] < (xLoc + w)) {
				if ((Integer) params[1] > yLoc && (Integer) params[1] < (yLoc + h)) {
					return true;
				}
			}
		}
		return false;
    }
	

	private void highlightTilesForCommand() {
		GameMode.setCurrState(GameMode.WAITING_FOR_DESTINATION);
               ((ButtonTargetRequired) getSelectedButton().getInteractionButton()).highlightTargetableTiles(
		    this);
               setSelectedDestination(getSelectedTile());
	}

}
