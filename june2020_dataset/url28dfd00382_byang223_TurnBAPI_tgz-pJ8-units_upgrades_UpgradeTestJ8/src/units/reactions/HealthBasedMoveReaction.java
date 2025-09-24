package units.reactions;

import attribute.AttributeHealth;
import attribute.AttributeMove;
import units.Unit;
import units.upgrades.MoveModification;

/**
 * Concrete example of a reaction.
 * Move count left is decremented even more if the health HP of unit is lower
 * @author lenajia
 *
 */
public class HealthBasedMoveReaction extends Reaction{

	@Override
	  public void apply(Unit u) {
			int health = ((AttributeHealth) u.getAttribute("Health")).getHP();
			new MoveModification (u, 0, -80/health).modify();
			
	    }
	
}
