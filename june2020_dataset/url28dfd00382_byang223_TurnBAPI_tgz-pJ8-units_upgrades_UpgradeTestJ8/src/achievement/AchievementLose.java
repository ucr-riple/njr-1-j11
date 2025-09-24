package achievement;

import modes.GameMode;
import observers.GameObserver;
import observers.ObserverLose;
import achiever.Achiever;
import attribute.AttributeUnitGroup;

public class AchievementLose extends Achievement<AttributeUnitGroup> {

    public static final String END_GAME_MESSAGE= "END GAME";
    
    private static final AchievementLose instance = new AchievementLose();
    
    private static ObserverLose myObserver;
    
    public static AchievementLose getAchievement() {
        return instance;
    }
    
    private AchievementLose () {
        super();
        myObserver = new ObserverLose();
        super.attachObserver(myObserver);
    }
    
    public static void setGameMode(GameMode mode) {
        myObserver.setGameMode(mode);
    }
    
    @Override
    public boolean satisfiesConditions() {
        return (super.getObservable().getUnitGroupSize() == 0);
    }

    @Override
    void notifyObserver(GameObserver o, Achiever achiever) {
        o.notifyObserver(achiever);
    }

    @Override
    String createAchievementMessage(Achiever achiever) {
        return this.END_GAME_MESSAGE;
    }
}
