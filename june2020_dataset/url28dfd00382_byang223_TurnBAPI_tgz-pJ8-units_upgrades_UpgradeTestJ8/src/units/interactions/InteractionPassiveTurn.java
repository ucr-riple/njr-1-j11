package units.interactions;

import modes.models.GameModel;
import units.Unit;

/**
 * Basic passive ability that calls the performInteraction function every time
 * the unit's player ends his turn.
 * 
 * @author Matthew
 * 
 */
public abstract class InteractionPassiveTurn extends Interaction {
    /**
     * Performs the selected interaction onto the GameModel. The specific unit
     * with the passive must be passed because the unit is not selected, so
     * GameModel has no knowledge of it.
     * 
     * @param g
     * @param unitWithPassive
     */
    public abstract void performInteraction(GameModel g, Unit unitWithPassive);

}
