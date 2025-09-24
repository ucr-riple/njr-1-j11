package Questing.Wealth;

import java.util.*;

import Defs.*;
import Descriptions.*;
import Game.Labor;
import Markets.*;
import Sentiens.Clan;

public class TradeMemory {
	private static final G_[] GS = G_.values();
	
	private final Map<G_, TradeInventorySlot> buySlots = new HashMap<G_, TradeInventorySlot>();
	private final Map<G_, TradeInventorySlot> sellSlots = new HashMap<G_, TradeInventorySlot>();
	private final int expCost, expValue;
	
	public TradeMemory(Labor labor, Clan doer) {
		int[] in = labor.expIn(doer);
		int[] out = labor.expOut(doer);
		expCost = in[0];
		expValue = out[0];
		putInSlots(in, buySlots);
		putInSlots(out, sellSlots);
	}
	
	public double estimateProfit(Clan doer) {
		// TODO env stuff, skill stuff
		return doer.confuse((double)expValue - (double)expCost); // (double) cuz of weird shit with MAX_INTEGER
	}
	
	public void placeBuys(Clan c) {
		Collection<Map.Entry<G_, TradeInventorySlot>> entries
			= new LinkedList<Map.Entry<G_, TradeInventorySlot>>(buySlots.entrySet());
		for (Map.Entry<G_, TradeInventorySlot> entry : entries) {
			entry.getValue().liftNecessaryOffer(entry.getKey().ordinal(), c);
		}
	}
	public void placeSells(Clan c) {
		for (Map.Entry<G_, TradeInventorySlot> entry : sellSlots.entrySet()) {
			entry.getValue().produceAndSellFair(entry.getKey().ordinal(), c);
		}
	}
	
	public boolean addToInventory(int good, int n) {
		final G_ g = GS[good];
		TradeInventorySlot slot = buySlots.get(g);
		if (slot == null) return false;
		final boolean complete = slot.get(n);
		if (complete) {buySlots.remove(g);}
		return true;
	}
	
	public boolean noMoreInputsNeeded() {
		return buySlots.isEmpty();
	}

	public Map<G_, TradeInventorySlot> getBuySlots() {
		return buySlots;
	}
	
	private static void putInSlots(int[] gs, Map<G_, TradeInventorySlot> slots) {
		int i = 1;
		while (true) {
			int g = gs[i++];
			if (g == Misc.E) break;
			G_ g_ = GS[g];
			TradeInventorySlot slot = slots.get(g_);
			if (slot == null) slots.put(g_, slot = new TradeInventorySlot());
			slot.needNum++;
		}
	}
}

class TradeInventorySlot {
	int haveNum, needNum;
	private boolean outstanding;
	
	boolean get(int num) {
		haveNum += num;
		return haveNum >= needNum;
	}
	void liftNecessaryOffer(int g, Clan c) {
		// places a new offer already if there wasnt already one
		// TODO if is avatar, manually edit px
		if (!outstanding) {
			c.myMkt(g).liftOffer(c);
			outstanding = true;
		}
	}
	void produceAndSellFair(int g, Clan c) {
		boolean isXWeapon = checkXWeapon(g, c);
		if (!isXWeapon) {c.addReport(GobLog.produce(g));}
		final MktAbstract market = c.myMkt(g);
		market.gainAsset(c);
		market.sellFair(c);
	}
	boolean hasOutstanding() {return outstanding;}
	
	private static boolean checkXWeapon(int g, Clan c) {
		if (g == Misc.sword || g == Misc.mace) {
			short x = XWeapon.craftNewWeapon(g, c.FB.getPrs(P_.SMITHING));
			if (x != XWeapon.NULL) {
				g = Misc.xweapon;
				c.addReport(GobLog.produce(x));
				((XWeaponMarket) c.myMkt(Misc.xweapon)).setUpTmpXP(x);
				return true;
			}
		}
		return false;
	}
}