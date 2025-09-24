package units.reactions;

import units.Unit;

/**
 * Used by the AttributeReactable class so that units can react to environments
 * @author lenajia
 *
 */
public abstract class Reaction {
	
	public abstract void apply (Unit u);
}
