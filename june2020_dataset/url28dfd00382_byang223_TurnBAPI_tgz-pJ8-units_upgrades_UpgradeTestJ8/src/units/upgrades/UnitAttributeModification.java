package units.upgrades;

import attribute.Attribute;


/**
 * A modification that changes a unit's attribute
 * @author Alex Mariakakis
 *
 */
abstract public class UnitAttributeModification extends UnitDecorator {

	private Attribute attribute;
	
	public UnitAttributeModification(UnitUpgradable unit, int cost, String attr) {
	    super(unit, cost);
	    attribute = this.getDecoratedUnit().getAttribute(attr);
    }

	public Attribute getAttribute() {
	    return attribute;
    }

	public void setAttribute(Attribute attr) {
	    this.attribute = attr;
    }

}
