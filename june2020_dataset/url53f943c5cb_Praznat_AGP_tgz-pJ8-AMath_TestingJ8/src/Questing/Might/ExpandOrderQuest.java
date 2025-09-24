package Questing.Might;

import Defs.SK_;
import Descriptions.GobLog;
import Game.Library;
import Ideology.Values;
import Questing.Quest;
import Questing.Knowledge.*;
import Questing.Knowledge.KnowledgeQuests.KnowledgeQuest;
import Questing.Might.MightQuests.ChallengeMightQuest;
import Sentiens.Clan;
import Shirage.Shire;

public class ExpandOrderQuest extends Quest {
	
	private Shire targetShire;

	public ExpandOrderQuest(Clan P) {
		super(P);
		// TODO patience?
	}

	@Override
	public void pursue() {
		if (targetShire == null || Me == targetShire.getGovernor()) {
			chooseTargetShire();
			return;
		}
		Clan targetGovernor = targetShire.getGovernor();
		if (targetGovernor != null) {
			ChallengeMightQuest cmq = new ChallengeMightQuest(Me,Values.MIGHT);
			cmq.setTarget(targetGovernor);
			replaceAndDoNewQuest(Me, cmq);
			return;
		} else {
			Me.myOrder().acquireShire(targetShire, Me); // no one currently owns the shire
		}
	}
	
	private void sendScout(KnowledgeType k) {
		Clan selectedScout = Me; // default go myself
		for (Clan c : Me.myOrder().getFollowers(Me, null, false, true)) {
			boolean isScouting = c.MB.QuestStack.getOfType(KnowledgeQuest.class) != null;
			if (!isScouting) {
				selectedScout = c; // TODO better selection criteria...
				break;
			}
		}
		selectedScout.MB.newQ(new KnowledgeQuest(selectedScout, Me, k));
	}
	
	/**
	 * 1. check library for targets
	 * 2. if no targets in library, send scouts
	 * 3. if myShire is not taken, take myShire
	 */
	@SuppressWarnings("rawtypes")
	private void chooseTargetShire() {
		Shire myShire = Me.myShire();
		Library library = myShire.getLibrary();
		SK_ k = SK_.valToSK.get(Me.FB.randomValueInPriority());
		KnowledgeBlock kb = library.findKnowledge(k);
		if (kb != null) {kb.useKnowledge(Me);}
		else {sendScout(k);}
		Clan myShireGov = myShire.getGovernor();
		if (targetShire == null && (myShireGov == null || myShireGov.myOrder() != Me.myOrder())) {
			targetShire = myShire;
		}
		if (targetShire == null) { Me.addReport(GobLog.nowhereToAttack()); }
	}
	
	public void considerOptions(Object[] shireOptions, int[] goodnesses, int num) {
		double bestProfit = Double.MIN_VALUE;
		for (int i = 0; i < num; i++) {
			Shire shire = (Shire) shireOptions[i];
			if (shire.getGovernor() == Me) continue;
			int distance = shire.distanceFrom(Me.myShire());
			double goodness = goodnesses[i];
			// profit = goodness - cost = goodness/distance - distance
			// cost = goodness - goodness/distance + distance
			double cost = distance + goodness - goodness / distance; // TODO estimate cost of attacking shire
			// TODO cost should take into account distance of shire! you should probably have to fight everyone on the way
			double profit = goodness - cost;
			if (profit > bestProfit) {
				bestProfit = profit; targetShire = shire;
			}
		}
	}
	
	@Override
	public String description() {
		return "Expand Order";
	}
	
}
