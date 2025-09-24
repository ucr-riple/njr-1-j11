package observers;

import java.util.TreeMap;

import units.Unit;
import achievement.Achievement;
import achiever.Achiever;
import achiever.upgrades.AchieverUnlockUnitModification;
import attribute.Attribute;

public class ObserverUnlockUnits extends GameObserver {
    
    private TreeMap<Integer,Unit> unlockablesMap;
    private int nextUnlockLevel;
    
    public ObserverUnlockUnits(TreeMap<Integer,Unit> unlockablesMap) {
        this.unlockablesMap = unlockablesMap;
    }
    
    public void setUnlockLevel(int nextUnlockLevel) {
        this.nextUnlockLevel = nextUnlockLevel;
    }
 
    @Override
    public void notifyObserverTemplate(Achiever achiever) {
        if (achiever.getAttribute(Attribute.UNIT_GROUP) != null) {
            AchieverUnlockUnitModification unlockUnit = new AchieverUnlockUnitModification(
                    achiever, unlockablesMap.get(nextUnlockLevel));
            unlockUnit.modify();
        } else {
            System.out.println(Attribute.INCORRECT_ATTRIBUTE_ERROR);
        }
    }
}
