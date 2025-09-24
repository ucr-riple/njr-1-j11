package achievement;

import java.util.TreeMap;
import java.util.TreeSet;

import observers.GameObserver;
import observers.ObserverUnlockUnits;
import units.CharmanderUnit;
import units.ElectrodeUnit;
import units.HoohUnit;
import units.LugiaUnit;
import units.MewUnit;
import units.SnorlaxUnit;
import units.Unit;
import achiever.Achiever;
import attribute.AttributeLevel;

public class AchievementUnlockUnit<A extends Achiever> extends Achievement<AttributeLevel> {

    public static String NO_MORE_UNLOCKABLES_MESSAGE = "NO MORE UNLOCKABLES";
    public static final String UNLOCK_UNIT_ACHIEVEMENT_BEGINNING_MESSAGE = "Unlocked: ";
        
    private TreeMap<Integer,Unit> unlockablesMap;
    private TreeSet<Integer> unlockLevels;
    private int nextUnlockLevel;
    
    private ObserverUnlockUnits myObserver;
    
    private static final AchievementUnlockUnit instance = new AchievementUnlockUnit();

    public static AchievementUnlockUnit getAchievement() {
        return instance;
    }
    
    private AchievementUnlockUnit() {
        super();
        unlockablesMap = new TreeMap<Integer,Unit>();
        unlockLevels = new TreeSet<Integer>();
        
        addUnlockableUnit(1, new ElectrodeUnit());
        addUnlockableUnit(2, new CharmanderUnit());
        addUnlockableUnit(3, new SnorlaxUnit());
        addUnlockableUnit(4, new MewUnit());
        addUnlockableUnit(5, new LugiaUnit());
        addUnlockableUnit(6, new HoohUnit());

        myObserver = new ObserverUnlockUnits(this.unlockablesMap);
        super.attachObserver(myObserver);
        
    }

    public void addUnlockableUnit(int unlockLevel, Unit unlockable) {
        this.unlockLevels.add(unlockLevel);
        this.unlockablesMap.put(unlockLevel, unlockable);
    }
    
    @Override
    public void notifyObserver(GameObserver o, Achiever achiever) {
        if (unlockLevels.size() > 0) {
            setNextUnlockable();
            o.notifyObserver(achiever);
        } else {
        }
    }

    @Override
    public boolean satisfiesConditions() {
        return super.getObservable().getData() >= nextUnlockLevel;
    }
    
    public void setNextUnlockable() {
        nextUnlockLevel = unlockLevels.pollFirst();
        myObserver.setUnlockLevel(nextUnlockLevel);
    }

    @Override
    String createAchievementMessage(Achiever achiever) {
        StringBuilder message = new StringBuilder(Achievement.ACHIEVEMENT_TAG);
        message.append(UNLOCK_UNIT_ACHIEVEMENT_BEGINNING_MESSAGE);
        message.append(unlockablesMap.get(nextUnlockLevel).unitName());
        return new String(message);
    }

}
