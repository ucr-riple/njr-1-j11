package attribute;

import achievement.AchievementUnlockUnit;
import achiever.Achiever;

public class AttributeLevel extends Attribute<Integer,Integer> {
    
    public AttributeLevel(Achiever achiever) {
        this(achiever,0);
    }
    
    public AttributeLevel(Achiever achiever,int lvl) {
        super(achiever);
        super.setData(lvl);
//        super.attachAchievement(AchievementUnlockUnit.getAchievement());
    }
    
    @Override
    public String name() {
        return "AchieverLevel";
    }

    @Override
    public void augmentDataTemplate(Integer data) {
        super.setData(super.getData()+data);
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        
    }
    

}
