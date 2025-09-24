package attribute;

import achievement.AchievementLevelUp;
import achiever.Achiever;

public class AttributeExperience extends Attribute<Integer,Integer> {
    
    private int deathExperience;
    
    public AttributeExperience(Achiever achiever) {
        this(achiever,0);
    }
        
    public AttributeExperience(Achiever achiever, int exp) {
        super(achiever);
        super.setData(exp);
        super.attachAchievement(AchievementLevelUp.getAchievement());
        deathExperience = 100;
    }

    @Override
    public String name() {
        return "Experience";
    }
    
    public int giveDeathExperience() {
        return new Integer(deathExperience);
    }
    
    @Override
    public void augmentDataTemplate(Integer data) {
        super.setData(super.getData()+data);
    }

    @Override
    public void refresh() {
        
    }
}
