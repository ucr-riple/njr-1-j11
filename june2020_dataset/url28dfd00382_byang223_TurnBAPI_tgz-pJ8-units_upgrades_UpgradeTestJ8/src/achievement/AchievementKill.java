package achievement;

import achiever.Achiever;
import attribute.Attribute;
import attribute.AttributeKills;
import observers.GameObserver;
import observers.ObserverKills;
import units.Unit;

public class AchievementKill extends Achievement<AttributeKills> {
    
    public static final String KILL_ACHIEVEMENT_BEGINNING_MESSAGE = "Total enemy units killed: ";
    
    private static final AchievementKill instance = new AchievementKill();
    
    public static AchievementKill getAchievement() {
        return instance;
    }
    
    private AchievementKill() {
        super();
        super.attachObserver(new ObserverKills());
    }

    @Override
    public boolean satisfiesConditions() {
        return super.getObservable().getRecentKilledUnits().size() > 0;
    }
    
    @Override
    public void notifyObserver(GameObserver o, Achiever achiever) {
        o.notifyObserver(achiever);        
    }

    @Override
    String createAchievementMessage(Achiever achiever) {
        StringBuilder message = new StringBuilder(Achievement.ACHIEVEMENT_TAG);
        message.append(KILL_ACHIEVEMENT_BEGINNING_MESSAGE);
        message.append(((AttributeKills)achiever.getAttribute(Attribute.KILLS)).getKillCount());
        return new String(message);
    }

}
