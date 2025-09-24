package War;

import java.util.Set;

import Questing.Quest;
import Questing.Might.FormArmyQuest;
import Sentiens.Clan;

public class WarLogic {

	public static boolean decideMove(Clan decider, Set<Clan> deciderArmy, Clan enemy) {
		Set<FormArmyQuest> enemyArmy = null;
		final Quest targetTopQuest = enemy.MB.QuestStack.peek();
		if (targetTopQuest != null && FormArmyQuest.class.isAssignableFrom(targetTopQuest.getClass())) {enemyArmy = ((FormArmyQuest) targetTopQuest).getArmy();}
		
		int estEnemyImmediateStr = (enemyArmy != null ? enemyArmy.size() : 1);
		int estEnemyMinionStr = enemy.getMinionTotal();
		int estEnemyOrderStr = enemy.myOrder().size();
		
		return true;
	}
	
}
