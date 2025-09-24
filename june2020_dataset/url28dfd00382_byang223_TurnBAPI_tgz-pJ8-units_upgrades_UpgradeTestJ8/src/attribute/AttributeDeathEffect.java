package attribute;

import achievement.AchievementDeathEffectsMap;
import achiever.Achiever;

/**
 * Boolean attribute indicating alive or dead
 * Extension of AttributeAlive, results in Achievement effect
 * If unit is killed LevelMap will be affected
 * TRUE = ALIVE
 * FALSE = DEAD
 * @author aks
 *
 */
public class AttributeDeathEffect extends AttributeAlive {

    public AttributeDeathEffect(Achiever owner) {
        super(owner);
        super.attachAchievement(AchievementDeathEffectsMap.getAchievement());
    }
    
    @Override
    public String name() {
        return "DeathEffect";
    }



}
