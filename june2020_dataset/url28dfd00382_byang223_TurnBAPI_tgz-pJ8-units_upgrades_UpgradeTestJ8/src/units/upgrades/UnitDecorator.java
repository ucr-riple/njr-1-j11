package units.upgrades;


import java.util.ArrayList;

import units.interactions.Interaction;
import achiever.Achiever;
import attribute.Attribute;

/**
 * The abstract decorator that makes upgrades possible
 * @author Alex Mariakakis
 *
 */
abstract public class UnitDecorator implements UnitUpgradable, java.io.Serializable {

	private UnitUpgradable decoratedUnit;
	private int upgradeCost;
	private Achiever owner;
	
	public UnitDecorator(UnitUpgradable unit, int cost) {
		this.setDecoratedUnit(unit);
		// If a cost is not entered, make it 0 to avoid exceptions
		try {
			this.setUpgradeCost(cost);
		}
		catch (Exception e) {
			cost = 0;
		}
		owner = getOwner();
	}

    abstract public void modify();
    abstract public boolean checkCost();
    abstract public void applyCost();
	
	public UnitUpgradable getDecoratedUnit() {
	    return decoratedUnit;
    }

	public void setDecoratedUnit(UnitUpgradable decoratedUnit) {
	    this.decoratedUnit = decoratedUnit;
    }
	
	public int getUpgradeCost() {
	    return upgradeCost;
    }

	public void setUpgradeCost(int cost) {
	    this.upgradeCost = cost;
    }
	
	/**
	 * Decorator methods below
	 */
    public Attribute getAttribute(String attr) {
	    return getDecoratedUnit().getAttribute(attr);
    }
	
	public ArrayList<Interaction> getInteractionList() {
	    return getDecoratedUnit().getInteractionList();
    }

	public Achiever getOwner() {
	    return getDecoratedUnit().getOwner();
    }
	
}