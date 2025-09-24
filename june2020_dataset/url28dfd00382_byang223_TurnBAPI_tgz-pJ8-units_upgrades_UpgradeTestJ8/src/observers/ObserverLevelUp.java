package observers;

import achievement.Achievement;
import achiever.Achiever;
import achiever.upgrades.AchieverLevelModification;
import attribute.Attribute;
import attribute.AttributeLevel;

public class ObserverLevelUp extends GameObserver {
    
    public static final String LEVEL_UP_MESSAGE = "Earned level: ";

    @Override
    public void notifyObserverTemplate(Achiever achiever) {
        if (achiever.getAttribute(Attribute.LEVEL) != null) {
            AchieverLevelModification levelUp = new AchieverLevelModification(
                    achiever, 1);
            levelUp.modify();
        } else {
            System.out.println(Attribute.INCORRECT_ATTRIBUTE_ERROR);
        }
    }        
}
