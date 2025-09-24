package Questing.Might;

import Questing.Quest;
import Questing.Might.WarQuest.Status;
import Sentiens.Clan;
import War.*;
import War.BattleField.EndStatus;


public class ManeuverForBattle extends Quest {
	private WarQuest root;
	private int timeToSetUp = 1;
	public ManeuverForBattle(Clan P, WarQuest root) {
		super(P);
		this.root = root;
	}
	@Override
	public void pursue() {
		if (root.status != Status.MANEUVERING) {finish(); Me.pursue(); return;}
		if (timeToSetUp > 0) {timeToSetUp--; return;}

		final Clan enemy = root.getTarget();
		if (Me.currentShire() != enemy.currentShire()) {Me.moveTowards(enemy.currentShire());} //TODO shoud be whole army
		fight();
	}
	private void fight() {
		final Clan enemy = root.getTarget();
		final WarQuest enemyWarQ = (WarQuest)enemy.MB.QuestStack.getOfType(WarQuest.class);
		BattleField.setupNewBattleField(Me, enemy, enemy.currentShire());
		final EndStatus myEndStatus = BattleField.INSTANCE.getEndStatus(Me);
		if (myEndStatus == EndStatus.VICTORIOUS) {
			WarQuest.onBattleEndStatus(root, enemyWarQ, BattleField.INSTANCE);
		} else {
			WarQuest.onBattleEndStatus(enemyWarQ, root,  BattleField.INSTANCE);
		}
		
		finish();
	}
	@Override
	public String description() {
		return "Organizing army for attack";
	}
}