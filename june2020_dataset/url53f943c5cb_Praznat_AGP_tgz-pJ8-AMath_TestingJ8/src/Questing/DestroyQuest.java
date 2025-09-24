package Questing;

import Defs.P_;
import Descriptions.GobLog;
import Questing.PersecutionQuests.PersecuteAbstract;
import Questing.Quest.TargetQuest;
import Questing.Quest.Unquenchable;
import Sentiens.*;
import Sentiens.Stress.Stressor;

public class DestroyQuest extends TargetQuest implements Unquenchable {
	private RelationCondition victoryCondition;
	private boolean unquenchable; // true if its some serious revenge shit
	public DestroyQuest(Clan P, Clan T, RelationCondition c) {super(P, T); victoryCondition = c;}

	@Override
	public void pursue() {
		// TODO Auto-generated method stub
		if (target == null || victoryCondition.meetsReq(Me, target)) {success();}
		boolean winorlose = Me.FB.getPrs(P_.COMBAT) > target.FB.getPrs(P_.COMBAT);
		Me.addReport(GobLog.handToHand(target, winorlose));
		((PersecuteAbstract) upQuest()).failDestroy(); 
		if (winorlose) {success(); target.AB.add(new Stressor(Stressor.INSULT, Me)); Me.FB.upPrest(P_.TYRRP);}
		else {Me.FB.downPrest(P_.TYRRP); failure(target);}
	}
	@Override
	public String description() {return "Destroy " + (target == null ? "Someone" : target.getNomen());}

	@Override
	public boolean isUnquenchable() {
		return unquenchable;
	}
}
