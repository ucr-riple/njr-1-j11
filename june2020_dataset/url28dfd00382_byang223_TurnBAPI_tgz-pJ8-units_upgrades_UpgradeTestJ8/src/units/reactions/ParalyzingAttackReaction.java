package units.reactions;

import attribute.AttributeAttack;
import attribute.AttributeHealth;
import units.Unit;
import units.upgrades.MoveModification;

/**
 * Concrete example of a reaction. 
 * Paralyzes the unit so that the unit cannot attack
 * @author lenajia
 *
 */
public class ParalyzingAttackReaction extends Reaction{

	@Override
    public void apply(Unit u) {
		
		((AttributeAttack) u.getAttribute("Attack")).setAttacksLeft(0);
	
    }

}
