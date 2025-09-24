package achievement;

import modes.TBGameMode;
import achiever.Achiever;
import attribute.Attribute;
import observers.GameObserver;

public class AchievementTime extends Achievement {
    
    private static long requiredTime = 1000000;
    public static final String TIME_ACHIEVEMENT_MESSAGE = "Congrats! You've played for " + requiredTime + " ms!!";

    private static long currentTime;
    
    private static final AchievementTime instance = new AchievementTime();

    public static AchievementTime getAchievement() {
        return instance;
    }
    
    private AchievementTime() {
        super();
//        super.attachObserver(new ObserverTime());
    }

    @Override
    public boolean satisfiesConditions() {
        return false;
    }

    @Override
    public void notifyObserver(GameObserver o, Achiever achiever) {
//        o.notifyObserver(achiever);
    }
    
    public static void checkPlayTime(long playTime) {
        currentTime = playTime;
        if ((currentTime%Long.MAX_VALUE) >= requiredTime) {
            requiredTime = Long.MAX_VALUE;
        }
    }

    @Override
    String createAchievementMessage(Achiever achiever) {
        return Achievement.NO_ACHIEVEMENT_MESSAGE;
    }

}
