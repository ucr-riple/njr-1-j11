package Questing;

import Defs.M_;
import Descriptions.GobLog;
import Ideology.Value;
import Questing.Quest.TargetQuest;
import Sentiens.*;

public class MiscQuests {
	
	public static class CatchUpQuest extends TargetQuest {
		private final Value val;
		private int timesLeft;
		private CatchUpQuest(Clan P, Value v) {super(P); val = v; timesLeft = P.FB.getBeh(M_.PATIENCE) / 3 + 1;}
		public CatchUpQuest(Clan P, Clan T, Value v) {this(P, v); target = T;}
		@Override
		public void pursue() {
			if (val.compare(Me, Me, target) >= 0) {success(Me); return;}
			if (timesLeft-- < 0) {failure(Me); return;}
			Me.MB.newQ(Quest.QtoQuest(Me, ((Value) val).pursuit()));
		}
		@Override
		public String description() {
			return "Catch up " + val.description(Me) + " to " + target.getNomen();
		}
	}
	
	public static class PreachQuest extends TargetQuest {
		protected Clan patron;
		protected int tries;
		public PreachQuest(Clan me, Clan t) {this(me, t, me);}
		public PreachQuest(Clan me, Clan t, Clan p) {super(me, t); patron = p; tries = me.FB.getBeh(M_.PATIENCE) / 5 + 1;}

		@Override
		public void pursue() {
			final boolean success = Ideology.attemptPreach(Me, target, patron.FB);
			Me.addReport(GobLog.preach(target, success));
			if (success) {success(target); return;}
			if (--tries <= 0) {failure(Me); return;}
		}

		@Override
		public String description() {
			return "Influence " + target.getNomen() + "'s value system";
		}
	}
	
	
}
