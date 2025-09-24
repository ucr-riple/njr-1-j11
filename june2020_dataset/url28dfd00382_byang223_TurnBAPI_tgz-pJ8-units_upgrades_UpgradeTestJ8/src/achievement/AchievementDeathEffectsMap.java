package achievement;

import map.LevelMap;
import observers.GameObserver;
import observers.ObserverDeathEffectsMap;
import achiever.Achiever;
import attribute.Attribute;

public class AchievementDeathEffectsMap extends Achievement {

    private static final AchievementDeathEffectsMap instance = new AchievementDeathEffectsMap();

    public static AchievementDeathEffectsMap getAchievement() {
        return instance;
    }
    
    private AchievementDeathEffectsMap() {
        super();
        super.attachObserver(new ObserverDeathEffectsMap());
    }
    
    @Override
    public boolean satisfiesConditions() {
        return true;
    }

    @Override
    void notifyObserver(GameObserver o, Achiever achiever) {
        if (achiever instanceof LevelMap){
            o.notifyObserver((LevelMap) achiever);
        } else {
        }
    }

    @Override
    String createAchievementMessage(Achiever achiever) {
        return null;
    }

}
