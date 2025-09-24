package attribute;

import map.LevelMap;
import achievement.AchievementDeathEffectsMap;
import achiever.Achiever;

/**
 * Boolean attribute for LevelMap
 * @author aks
 *
 */
public class AttributeMapModifiable extends Attribute<Boolean,Boolean> {

    public AttributeMapModifiable(Achiever owner) {
        super(owner);
        super.setData(false);
        super.attachAchievement(AchievementDeathEffectsMap.getAchievement());
    }

    @Override
    public String name() {
        return Attribute.MAP_MODIFIABLE;
    }

    @Override
    public void augmentDataTemplate(Boolean dataElement) {
        super.setData(dataElement);
    }
    
    @Override
    public void refresh() {
        
    }

}
