package Questing.Might;

import java.util.Set;

import Defs.*;
import Descriptions.*;
import Descriptions.GobLog.Reportable;
import Questing.Might.WarQuest.Status;
import Sentiens.Clan;

public class FormOwnArmyQuest extends FormArmyQuest {
	private int lastArmySize = 0;
	private int timesLeft;
	public FormOwnArmyQuest(Clan P, WarQuest root) {
		super(P, root);
		root.getArmy().add(this);
		timesLeft = Me.FB.getBeh(M_.PATIENCE) / 4 + 2;
	}
	@Override
	public void pursue() {
		if (getRoot().status != Status.FORMING) {finish(); Me.pursue(); return;}
		if (!checkArmyStatus()) {return;} // really?
		Set<FormArmyQuest> army = getRoot().getArmy();
		if (army.isEmpty()) {throw new IllegalStateException("damni ttt this check supposed to happen in super.pursue() already!");}
		if (isReadyToFight(army)) {
			getRoot().beReady();
			success();
			return;
		}
		super.pursue();
		if (army.size() <= lastArmySize) timesLeft--;
		if (timesLeft <= 0) { // GIVE UP ( this stuff should be in the upper quest as it will be different between attack and defense
			// TODO build resources?
			giveUp();
			return;
		}
		lastArmySize = army.size();
	}
	private void giveUp() { // TODO should be run away, regroup, sue for peace? loseWar is too much..
		Clan target = getRoot().getTarget();
		WarQuest targetClanQ = (WarQuest)target.MB.QuestStack.getOfType(WarQuest.class);
		targetClanQ.winWar();

		Reportable log = GobLog.backedDown(Me, target);
		Me.addReport(log); // could be run away?
		target.addReport(log); // could be run away?
		getRoot().loseWar();
	}
	private Set<FormArmyQuest> enemyArmy(Clan enemy) {
		FormArmyQuest enemyFa = (FormArmyQuest)enemy.MB.QuestStack.getOfType(FormArmyQuest.class);
		return enemyFa != null ? enemyFa.getArmy() : null;
	}
	private boolean isReadyToFight(Set<FormArmyQuest> myArmy) {
		final Clan target = getRoot().getTarget();
		return isReadyToFight(enemyArmy(target), target, myArmy, Me, timesLeft);
	}
	/** 
	 * TODO there should be many different ways of deciding this
	 * default is: compare current advantage to potential advantage and wait if current is less than potential
	 * and still have time left, only attacking if above confidence threshold
	 * */
	private static boolean isReadyToFight(Set<FormArmyQuest> enemyArmy, Clan enemy, Set<FormArmyQuest> myArmy, Clan me, int timeLeft) {
		double currentAdvantage = (double)(myArmy.size() +1) / ((enemyArmy != null ? enemyArmy.size() : 1) +1); // the +1s shouldnt be necessary
		// TODO what if enemy is my minion or boss?
		double ultimatePotentialAdvantage = ((double)me.getMinionTotal() + 1) / (enemy.getMinionTotal() + 1); // these +1s are good
		double confidenceFactor = (double)(me.FB.getPrs(P_.BATTLEP) + 1) / (enemy.FB.getPrs(P_.BATTLEP) + 1) *
				(me.FB.getBeh(M_.CONFIDENCE) + 15) / (me.FB.getBeh(M_.PARANOIA) + 15); //TODO overly simple
		
		if (timeLeft <= 1) { // cant wait anymore
			return currentAdvantage * confidenceFactor >= 1;
		}
		return currentAdvantage >= ultimatePotentialAdvantage && currentAdvantage * confidenceFactor >= 1;
	}
	public Set<FormArmyQuest> getArmy() {return getRoot().getArmy();}
	@Override
	public String description() {
		return "Form army for self";
	}
}
