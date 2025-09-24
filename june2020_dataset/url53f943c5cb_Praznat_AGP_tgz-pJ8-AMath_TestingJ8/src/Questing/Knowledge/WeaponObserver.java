package Questing.Knowledge;

import Defs.*;
import Game.AGPmain;
import Government.Order;
import Sentiens.Clan;

public class WeaponObserver extends ListKnowledgeObserver<Order, G_> {
	int[] scores = new int[G_.values().length];
	{for (G_ g : KnowledgeQuests.equip) {plot.add(new DataPoint<G_>(g, 0));}}
	/** 
	 * observes all the weapons for all the people in the order
	 */
	@Override
	public void observe(Order o) {
		int battleP = 0;
		for (Clan m : o.getMembers()) {
			for (G_ g : KnowledgeQuests.equip) {int gi = g.ordinal(); if (m.getAssets(gi) > 0) {scores[gi]++;}}
			battleP += m.FB.getPrs(P_.BATTLEP);
		}
		for (G_ g : KnowledgeQuests.equip) {scores[g.ordinal()] *= battleP;}
		int i = 0;
		for (DataPoint<G_> dp : plot) {
			if (dp.x != KnowledgeQuests.equip[i++]) throw new IllegalStateException("bad ordering in WeaponObserver");
			dp.y += scores[dp.x.ordinal()] * battleP;
		}
	}
	@Override
	public KnowledgeBlock<G_> createKnowledgeBlock(Clan creator) {
		return new Top3Block<G_>(creator, K_.WEAPONMEMS, plot) {
			@Override
			protected void alterBrain(Clan user) {
				G_ bestEquip = (G_)x[0];
				if (y[0] == 0) bestEquip = KnowledgeQuests.equip[AGPmain.rand.nextInt(KnowledgeQuests.equip.length)]; // pick random equip if no pref
				int g = bestEquip.ordinal();
				if (user.getAssets(g) > 0) {return;} // do nothing if already have ur weapon
				user.myShire().getMarket(g).buyFair(user);
			}
		};
	}
}
