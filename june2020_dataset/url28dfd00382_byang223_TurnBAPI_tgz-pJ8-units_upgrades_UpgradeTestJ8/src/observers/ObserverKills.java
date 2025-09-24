package observers;

import java.util.Stack;

import units.Unit;
import achievement.Achievement;
import achiever.Achiever;
import achiever.upgrades.AchieverExperienceModification;
import attribute.Attribute;
import attribute.AttributeExperience;
import attribute.AttributeKills;

public class ObserverKills extends GameObserver {

    private StringBuilder listOfKilledUnits;

    @Override
    public void notifyObserverTemplate(Achiever achiever) {
        Stack<Unit> recentlyKilled = ((AttributeKills) achiever.getAttribute(Attribute.KILLS)).getRecentKilledUnits();
        AchieverExperienceModification addExp;

        while (recentlyKilled.size() > 0) {

            Unit killed = recentlyKilled.pop();

            if (killed.getAttribute(Attribute.EXPERIENCE) != null) {
                addExp = new AchieverExperienceModification(achiever,
                        ((AttributeExperience) killed
                                .getAttribute(Attribute.EXPERIENCE))
                                .giveDeathExperience());
                addExp.modify();
            } else {
                System.out.println(Attribute.INCORRECT_ATTRIBUTE_ERROR);
            }
        }
    }

}
