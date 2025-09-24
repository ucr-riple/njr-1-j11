package Avatar;

import java.util.Comparator;

import Game.Act;
import Ideology.Value;
import Questing.Quest.GradedQuest;
import Sentiens.Clan;


@SuppressWarnings("rawtypes")
public class SubjectiveComparator implements Comparator {
	private Clan POV;
	public void setPOV(Clan pov) {POV = pov;}
	private Comparator<Object> comparator;
	@SuppressWarnings("unchecked")
	public void setComparator(Comparator c) {comparator = (Comparator) c;}
	

	
	public final Comparator<Act> ACT_PROFIT_ORDER = new Comparator<Act>() {
		@Override
		public int compare(Act o1, Act o2) {
			return (int) Math.signum(o2.estimateProfit(POV) - o1.estimateProfit(POV));
		}
	};
	public final Comparator<Clan> RESPECT_ORDER = new Comparator<Clan>() {
		@Override
		public int compare(Clan o1, Clan o2) {
			int x = (int) Math.signum(POV.FB.randomValueInPriority().compare(POV, o2, o1));
			return (x != 0 ? x : (int) Math.signum(o1.getID() - o2.getID()));
		}
	};
	public final Comparator<Value> VALUE_ORDER = new Comparator<Value>() {
		@Override
		public int compare(Value o1, Value o2) {
			return POV.FB.compareValues(o1, o2);
		}
	};
	public final Comparator<GradedQuest> QUEST_ORDER = new Comparator<GradedQuest>() {
		@Override
		public int compare(GradedQuest o1, GradedQuest o2) {
			int x = (int) Math.signum(o2.getRating() - o1.getRating());
			return x == 0 ? 1 : x;
		}
	};
	public final Comparator<Object> NO_ORDER = new Comparator<Object>() {
		@Override
		public int compare(Object o1, Object o2) {
			return 1;
		}
	};
	@Override
	public int compare(Object o1, Object o2) {
		return comparator.compare(o1, o2);
	}

	
}
