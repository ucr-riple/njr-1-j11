package units.upgrades;

import attribute.AttributeMove;



public class MoveModification extends UnitAttributeModification {

	private int incrementValue;
	
	public MoveModification(UnitUpgradable unit, int cost, int value) {
	    super(unit, cost, "Move");
	    incrementValue = value;
    }
	
	// Decorator method
	@Override
    public void modify() {
		((AttributeMove) getAttribute()).decrementMoveCount(-incrementValue);
	}

	@Override
    public boolean checkCost() {
	    // TODO Auto-generated method stub
	    return false;
    }

	@Override
    public void applyCost() {
	    // TODO Auto-generated method stub
	    
    }

}
