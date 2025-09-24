package achiever.upgrades;

import attribute.Attribute;
import attribute.AttributeLevel;

public class AchieverLevelModification extends AchieverAttributeModification<AttributeLevel,Integer> {

    public AchieverLevelModification(AchieverUpgradable achiever, Integer value) {
        super(achiever, value, Attribute.LEVEL);
    }

}
