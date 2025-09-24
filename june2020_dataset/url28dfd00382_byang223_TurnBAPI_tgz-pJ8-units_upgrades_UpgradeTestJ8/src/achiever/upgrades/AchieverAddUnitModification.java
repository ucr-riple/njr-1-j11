package achiever.upgrades;

import units.Unit;
import achiever.Achiever;
import attribute.Attribute;
import attribute.AttributeUnitGroup;

public class AchieverAddUnitModification extends
        AchieverAttributeModification<AttributeUnitGroup,Unit> {

    public AchieverAddUnitModification(AchieverUpgradable achiever, Unit value) {
        super(achiever, value, Attribute.UNIT_GROUP);
        value.setOwner((Achiever) achiever);
    }

    public void changeUnit(Unit value) {
        value.setOwner((Achiever) super.getDecoratedAchiever());
        setData(value);
        
    }
}
