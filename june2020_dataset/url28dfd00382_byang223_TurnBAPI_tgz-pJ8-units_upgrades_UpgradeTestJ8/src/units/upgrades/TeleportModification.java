package units.upgrades;

import map.LevelMap;
import achiever.Achiever;
import attribute.AttributeExperience;

/**
 * Concrete example of MapModification that changes reproduces the
 * unit at a specific location
 * @author Alex Mariakakis
 *
 */
public class TeleportModification extends MapModification {
	
	private int x, y;
	
	public TeleportModification(UnitUpgradable unit, int cost, LevelMap map, int xLoc, int yLoc) {
	    super(unit, cost, map);
	    x = xLoc;
	    y = yLoc;
    }
	
	// Decorator method
	@Override
	public void modify() {
		LevelMap map = getMap();
		map.getTileByCoords(x, y).setUnit(this.getDecoratedUnit());
	}
	
	@Override
    public boolean checkCost() {
		Achiever owner = getOwner();
		if ((Integer) ((AttributeExperience) owner.getAttribute("Experience")).getData() >= getUpgradeCost()) {
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
