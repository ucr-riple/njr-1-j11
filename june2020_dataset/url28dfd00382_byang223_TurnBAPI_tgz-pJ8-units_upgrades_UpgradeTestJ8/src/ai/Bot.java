package ai;

import map.LevelMap;
import modes.models.GameModel;
import player.Player;
import units.Unit;
import units.interactions.Interaction;
import achievement.AchievementLose;
import achievement.AchievementUnlockUnit;
import ai.state.AIstateController;
import attribute.AttributeAchievementList;
import attribute.AttributeExperience;
import attribute.AttributeKills;
import attribute.AttributeLevel;
import attribute.AttributeTurn;
import attribute.AttributeUnitGroup;
import attribute.AttributeUnitTypes;

/**
 * serves as the composite to the StrategyAI class each instance of a Bot sets
 * the Strategy of which AI technique that Bot implements
 * 
 * @author Shenghui
 */

public class Bot extends Player {

	StrategyAI strategy;
	AIstateController myStateController;
	GameModel myModel;

	public Bot(String name) {
		super(name);
		myStateController = new AIstateController();
		myStateController.initalizeController("HumanTurnState", myModel);
		
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

	public void setGameModel(GameModel m) {
		this.myModel = m;
	}

	public String getType() {
		return "BOT";
	}

	public void setStrategy(StrategyAI strategy) {
		this.strategy = strategy;
	}

	public void completeTurn(LevelMap map, GameModel myModel) {
		strategy.completeTurn(map, this, myModel);
	}

	public StrategyAI getStrategy() {
		return strategy;
	}

	public void setControllerBotState() {
		myStateController.setState("BotTurnState");
	}

	public void doMove(Unit u, Interaction i) {

	}
}