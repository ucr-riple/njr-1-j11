package Government;

import java.util.*;

import Defs.Misc;
import Descriptions.GobName;
import Questing.Quest.TargetQuest;
import Sentiens.Clan;
import Sentiens.Stress.*;
import Shirage.Shire;

public class Order implements Blameable {
	

	private Clan ruler;
	private final byte[] founderName;
	private final boolean founderGender;
	private final Treasury treasury;
	private final Set<Clan> members;
	
	
	public static Order createBy(Clan creator) {
		Order newOrder = new Order(creator);
		creator.joinOrder(newOrder);
		return newOrder;
	}
	private Order(Clan creator) {
		ruler = creator;
		founderName = creator.getNameBytes();
		founderGender = creator.getGender();
		members = new HashSet<Clan>();
		treasury = new Treasury(members);
	}


	
	public Set<Clan> getMembers() {return members;}
	private void addMembers(Set<Clan> s) {members.addAll(s);} 
	public void addMember(Clan m) {members.add(m); m.setOrder(this);}
	public void removeMember(Clan m) {if (m == ruler) {selfDestruct(); return;} else {members.remove(m);}}
	public void selfDestruct() {members.clear();}
	
	public void moveTo(Clan clan, Order newOrder) {
		Set<Clan> movers = Ledger.getFollowers(clan, true, true);
		for (Clan mover : movers) {mover.setOrder(newOrder);}
		newOrder.addMembers(movers);
		this.members.removeAll(movers);
	}
	/** current ruler of order */
	public Clan getRuler() {return ruler;}
	public int size() {return members.size();}
	public int numShiresControlled() {return 0;} // TODO
	
	public Set<Clan> getFollowers(Clan leader, boolean includeMe, boolean includeSubs) {return Ledger.getFollowers(leader, includeMe, includeSubs);}
	public Set<Clan> getFollowers(Clan leader, Shire place, boolean includeMe, boolean includeSubs) {return Ledger.getFollowers(leader, place, includeMe, includeSubs);}
	
	public static String getName(byte founderName1, byte founderName2, boolean founderGender) {
		String S = GobName.firstName(founderName1, founderName2, founderGender);
		if (S.endsWith("a") || S.endsWith("n") || S.endsWith("m") || S.endsWith("l")) {}
		else if (S.endsWith("us")) {S = S.substring(0, S.length()-2) + "a";}
		return S;
	}
	private String getName() {
		return getName(founderName[0], founderName[1], founderGender);
	}
	public String getFollowerName() {
		String S = getName();
		if (S.endsWith("n") || S.endsWith("g")) {return S + "ese";}
		if (S.endsWith("e")) {return S + "se";}
		if (S.endsWith("l") || S.endsWith("m")) {return S + "ite";}
		if (S.endsWith("a")) {return S + "n";}
		if (S.endsWith("i") || S.endsWith("o") || S.endsWith("x")) {return S + "an";}
		if (S.endsWith("y")) {return S.substring(0, S.length()-1) + "ian";}
		return S + "ish";
	}
	public String getNationName() {
		return "Nation of " + getName();
	}
	public String getTitle(Clan clan) {
		if (clan == getRuler()) {
			int n = members.size();
			if (n  > 50) {return (clan.getGender() == Misc.FEMALE ? "Empress" : "Emperor");}
			if (n  > 20) {return (clan.getGender() == Misc.FEMALE ? "Queen" : "King");}
		}
		int n = clan.getMinionTotal();
		if (n  > 10) {return (clan.getGender() == Misc.FEMALE ? "Lady" : "Lord");}
		if (n  > 5) {return (clan.getGender() == Misc.FEMALE ? "Madam" : "Sir");}
		return "";
	}
	public Treasury getTreasury() {return treasury;}
	
	private void raiseFunds() { // should be various ways to do this
		// TODO probably should be done through Quest stuff?
	}
	public boolean requestMillet(Clan requester, int amt) {
		if (requester == ruler) {raiseFunds(); return false;}
		Clan boss = requester.getBoss(); // if requester is ruler, only way to get more money is by raising funds
		int treasury = boss.getMillet(); // for subjects, request money from direct boss
		boolean success = treasury >= amt || requestMillet(boss, amt); // if direct boss doesnt have enough, he will request from
		if (success) {
			// TODO should be some affects on Clan's measured Allegiance contribution as well as Stressors?
			boss.alterMillet(-amt);
			requester.alterMillet(amt);
			return true;
		}
		return false;
	}
	public boolean requestFeed(Clan requester) {
		return requestMillet(requester, Clan.DMC * Clan.MIN_DMC_RESERVE);
	}

	public boolean preferableOver(Clan pov) {
		return preferableOver(null, pov);
	}
	public boolean preferableOver(Order other, Clan pov) {
		return false; //TODO ???
	}
	
	public Order[] getRivalOrders() {
		Ledger.orders.clear();
		for (Clan c : TargetQuest.getReasonableCandidates(ruler)) {Order o = c.myOrder(); if (o != null) {Ledger.orders.add(c.myOrder());}}
		return Ledger.orders.toArray(new Order[0]);
	}
	public Order[] getNeighboringOrders() {
		Ledger.orders.clear();
		for (Shire s : ruler.myShire().getNeighbors(false)) {
			Clan gov = s.getGovernor();
			if (gov != null) {Ledger.orders.add(gov.myOrder());}
		}
		return (Order[])Ledger.orders.toArray();
	}
	
	public void acquireShire(Shire targetShire, Clan taker) {
		// TODO clans should each only be able to govern one (or numSpawn) shires
		// order needs to decide how to allocate governors
		targetShire.setGovernor(null);
		if (ruler.getGovernedShire() == null) { targetShire.setGovernor(ruler); return; }
		
		if (taker != null) {
			if (taker.getGovernedShire() == null) { targetShire.setGovernor(taker); }
			else for (Clan minion : getFollowers(taker, false, false)) {
					if (minion.getGovernedShire() == null) { targetShire.setGovernor(minion); return; }
			}
		}
		for (Clan minion : getFollowers(ruler, false, false)) {
			if (minion.getGovernedShire() == null) { targetShire.setGovernor(minion); return; }
		}
		// UNGOVERNED SHIRE! (could exist..)
	}

	
	
	private static class Ledger {
		private static Set<Clan> pop = new HashSet<Clan>();
		private static Set<Order> orders = new HashSet<Order>();

		public static Set<Clan> getFollowers(Clan leader, boolean includeMe, boolean includeSubs) {
			pop.clear();
			int N = leader.getMinionTotal() + (includeMe ? 1 : 0);
			for (Clan m : leader.myOrder().getMembers()) {
				if (N <= 0) {break;}
				if ((leader == m && includeMe) ||
						(includeSubs ? leader.isSomeBossOf(m) : leader.isDirectBossOf(m))) {N--; pop.add(m);}
			}
			return pop;
		}
		public static Set<Clan> getFollowers(Clan leader, Shire place, boolean includeMe, boolean includeSubs) {
			pop.clear();
			int N = leader.getMinionTotal() + (includeMe ? 1 : 0);
			for (Clan m : leader.myOrder().getMembers()) {
				if (N <= 0) {break;}
				if ((place == null || m.myShire() == place) && ((leader == m && includeMe) ||
						(includeSubs ? leader.isSomeBossOf(m) : leader.isDirectBossOf(m)))) {N--; pop.add(m);}
			}
			return pop;
		}
	}
	
	@Override
	public String toString() {return getNationName();}
	
}


