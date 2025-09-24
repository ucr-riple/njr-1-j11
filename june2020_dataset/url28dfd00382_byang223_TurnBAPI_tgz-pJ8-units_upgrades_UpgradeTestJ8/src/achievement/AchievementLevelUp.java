package achievement;

import observers.GameObserver;
import observers.ObserverLevelUp;
import achiever.Achiever;
import attribute.Attribute;
import attribute.AttributeExperience;

public class AchievementLevelUp extends Achievement<AttributeExperience> {

    public static final String LEVEL_UP_MESSAGE = "Earned level: ";
    
    private int requiredExp = 100;

    private static final AchievementLevelUp instance = new AchievementLevelUp();

    public static AchievementLevelUp getAchievement() {
        return instance;
    }

    private AchievementLevelUp() {
        super();
        super.attachObserver(new ObserverLevelUp());
    }
    
    
    @Override
    public boolean satisfiesConditions() {
        return super.getObservable().getData() >= requiredExp;
    }

    @Override
    void notifyObserver(GameObserver o, Achiever achiever) {
        o.notifyObserver(achiever);        
    }

    @Override
    String createAchievementMessage(Achiever achiever) {
        StringBuilder message = new StringBuilder(Achievement.ACHIEVEMENT_TAG);
        message.append(LEVEL_UP_MESSAGE);
        message.append(achiever.getAttribute(Attribute.LEVEL).getData());
        return new String(message);
    }
    
   
    
}
