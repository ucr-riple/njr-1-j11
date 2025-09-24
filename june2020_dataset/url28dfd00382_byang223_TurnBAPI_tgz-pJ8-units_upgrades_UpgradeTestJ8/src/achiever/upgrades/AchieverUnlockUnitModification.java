package achiever.upgrades;

import units.Unit;
import attribute.Attribute;
import attribute.AttributeUnitTypes;

public class AchieverUnlockUnitModification extends AchieverAttributeModification<AttributeUnitTypes,Unit> {

    public AchieverUnlockUnitModification(AchieverUpgradable achiever, Unit unit) {
        super(achiever, unit, Attribute.UNITS);
    }
    
    @Override 
    public void modify() {
        getAttribute().augmentData(getData());
    }

}
