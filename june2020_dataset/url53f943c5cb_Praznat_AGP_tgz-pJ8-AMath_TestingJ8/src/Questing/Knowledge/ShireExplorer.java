package Questing.Knowledge;

import Defs.SK_;
import Government.Order;
import Ideology.Value;
import Questing.Might.ExpandOrderQuest;
import Sentiens.Clan;
import Shirage.Shire;

public class ShireExplorer extends MapKnowledgeObserver<Shire, Shire> {
	private final SK_ skType;
	private final Clan explorer;
	
	public ShireExplorer(SK_ type, Clan explorer) {
		this.skType = type; this.explorer = explorer;
	}

	@Override
	public void observe(Shire s) {
		map.put(s, evaluateShire(s));
	}
	
	private int evaluateShire(Shire s) {
		int result = 0;
		switch(skType) {
		case POPULOUSSHIRES:
			return s.getPopsize();
		case WEALTHYSHIRES:
			return (int) Math.min(s.getNetAssetValue(explorer, false), Long.MAX_VALUE);
		case LEARNEDSHIRES:
			return s.getLibrary().getAmountOfKnowledge();
		case FRIENDLYSHIRES:
			Order myOrder = explorer.myOrder();
			if (myOrder == null) return 0;
			int numFriendlies = 0;
			for (Clan c : s.getCensus()) if (myOrder == c.myOrder()) numFriendlies++;
			return numFriendlies;
		// the following are pretty much equivalent to the default case
//		case HOLYSHIRES:
//			for (Clan c : s.getCensus()) result += c.getHoliness();
//			return result;
//		case GRANDIOSESHIRES:
//			for (Clan c : s.getCensus()) result += c.getHoliness();
//			return result;
		default:
			Value v = SK_.skToVal.get(skType);
			for (Clan c : s.getCensus()) result += v.compare(explorer, c, explorer);
			return result;
		}
	}

	@Override
	public KnowledgeBlock<Shire> createKnowledgeBlock(Clan creator) {
		
		return new Top3Block<Shire>(creator, skType, map) {
			@Override
			protected void alterBrain(Clan user) {
				ExpandOrderQuest eoq = (ExpandOrderQuest) user.MB.QuestStack.getOfType(ExpandOrderQuest.class);
				if (eoq == null) return; // TODO there must be other ways to use this knowledge...
				eoq.considerOptions(x, y, 3);
			}
			@Override
			public String toString() {
				String intro = skType == SK_.POPULOUSSHIRES ? "POPULATION" : SK_.skToVal.get(skType).toString().toUpperCase();
				return intro + " " + super.toString();
			}
			@Override
			protected String descO(Shire s) {return s.getName();}
		};
	}

}
