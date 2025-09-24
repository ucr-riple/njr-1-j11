package Questing.Knowledge;

import Defs.K_;
import Ideology.Value;
import Sentiens.Clan;

public class ValueObserver extends MapKnowledgeObserver<Clan, Value> {
	/** 
	 * observes all the values for one clan
	 */
	@Override
	public void observe(Clan c) {
		Value lastV = null; int x = 0;
		for (Value v : c.FB.valuesInPriority()) {
			if (v != lastV) {
				if(lastV != null) {
					final Integer oldX = map.get(v); // from possible previous Clans
					map.put(v, oldX == null ? x : oldX + x);
				} // next value
				lastV = v; x = 1;
			}
			else {x++;} // same value
		}
	}
	@Override
	public KnowledgeBlock<Value> createKnowledgeBlock(Clan creator) {
		return new Top3Block<Value>(creator, K_.POPVALS, map) {
			@Override
			protected void alterBrain(Clan user) {
				if (user.FB.upSanc((Value)x[0])) {return;} // if goldV is not already 1st, increase it, continue if already top
				final Value s = (Value)x[1]; final Value b = (Value)x[2];
				if (user.FB.getValue(1) != s) {user.FB.upSanc(s); return;} // if silver is not already 2nd, increase it
				if (user.FB.getValue(2) != b) {user.FB.upSanc(b); return;} // if bronze is not already 3nd, increase it
			}
		};
	}
}