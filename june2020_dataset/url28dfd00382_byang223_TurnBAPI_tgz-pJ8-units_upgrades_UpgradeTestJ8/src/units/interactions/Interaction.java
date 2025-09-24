package units.interactions;
/**
 * Interaction superclass that governs interactions in the game.
 * @author Matthew
 *
 */
public abstract class Interaction implements java.io.Serializable {
    public abstract String toString();
    /**
     * Refreshes the interaction.  Called by PlayerUnitGroup.refreshAttributesAndInteractions() at the end of each turn.
     */
    public abstract void refresh();
}
