package units.interactions;

import map.LevelMap;
import map.Tile;
import modes.models.GameModel;
import serialization.SBufferedImage;
import units.Unit;

/**
 * Interaction subclass that represents button commands for units.  For instance, a unit has a move command that 
 * shows up as a button for the unit.
 * @author Matthew
 *
 */
public abstract class InteractionUnitButton extends Interaction implements java.io.Serializable {
    /**
     * Buffered Image that will fill the sprite of this unit's button command.
     * @return
     */
    public abstract SBufferedImage buttonImage();
    /**
     * function to call that performs the button's action, e.g., move a unit.  Takes the GameModel as an argument
     * so it has access to every current state feature in the game to perform the Interaction.
     * @param gameModel
     */
	public abstract void performButton(GameModel gameModel);

}
