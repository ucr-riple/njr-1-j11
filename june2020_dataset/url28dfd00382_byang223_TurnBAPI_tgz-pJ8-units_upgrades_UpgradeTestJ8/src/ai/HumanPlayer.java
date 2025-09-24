package ai;

import player.Player;
import achievement.AchievementLose;
import achievement.AchievementUnlockUnit;
import attribute.AttributeAchievementList;
import attribute.AttributeExperience;
import attribute.AttributeKills;
import attribute.AttributeLevel;
import attribute.AttributeTurn;
import attribute.AttributeUnitGroup;
import attribute.AttributeUnitTypes;

/**
 * Human subclass that is used in GameMode. Instance of this object is used in
 * place of Bot in order to play as a human player.
 * 
 * @author Shenghui
 */

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
        AttributeUnitGroup unitGroup = new AttributeUnitGroup(this);
        unitGroup.attachAchievement(AchievementLose.getAchievement());
        addAttribute(unitGroup);
        addAttribute(new AttributeExperience(this));
        addAttribute(new AttributeUnitTypes(this));
        
        AttributeLevel level = new AttributeLevel(this);
        level.attachAchievement(AchievementUnlockUnit.getAchievement());
        addAttribute(level);
        addAttribute(new AttributeKills(this));
        addAttribute(new AttributeAchievementList(this));
        addAttribute(new AttributeTurn(this));
        
    }


	public String getType() {
		return "HUMAN";
	}

}
