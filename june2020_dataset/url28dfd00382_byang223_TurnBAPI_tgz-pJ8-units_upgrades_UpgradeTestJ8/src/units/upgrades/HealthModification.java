package units.upgrades;

import achiever.Achiever;
import attribute.AttributeExperience;
import attribute.AttributeHealth;

/**
 * Concrete example of UnitAttributeModification that changes
 * the unit's health
 * @author Alex Mariakakis
 *
 */
public class HealthModification extends UnitAttributeModification {

	private int incrementValue;
	
	public HealthModification(UnitUpgradable unit, int cost, int value) {
	    super(unit, cost, "Health");
	    incrementValue = value;
    }
	
	// Decorator method
	@Override
    public void modify() {
		((AttributeHealth) getAttribute()).increaseHP(incrementValue);
	}

	@Override
    public boolean checkCost() {
		Achiever owner = getOwner();
		if ((Integer) owner.getAttribute("Experience").getData() >= getUpgradeCost()) {
			return true;
		}
	    return false;
    }

	@Override
    public void applyCost() {
		Achiever owner = getOwner();
		AttributeExperience exp = (AttributeExperience) owner.getAttribute("Experience");
		exp.setData(((Integer) exp.getData()-getUpgradeCost()));
    }

}
