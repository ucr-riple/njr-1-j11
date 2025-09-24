package achiever.upgrades;

import attribute.Attribute;
import attribute.AttributeExperience;

public class AchieverExperienceModification extends AchieverAttributeModification<AttributeExperience,Integer> {

    public AchieverExperienceModification(AchieverUpgradable achiever, Integer value) {
        super(achiever, value, Attribute.EXPERIENCE);
    }

}
