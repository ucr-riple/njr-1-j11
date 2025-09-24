package Questing;

import AMath.Calc;
import Defs.*;
import Descriptions.GobLog;
import Markets.*;
import Questing.Quest.PatronedQuest;
import Questing.Quest.PatronedQuestFactory;
import Sentiens.*;

public class SplendorQuests {
	public static PatronedQuestFactory getMinistryFactory() {return new PatronedQuestFactory(UpgradeDomicileQuest.class) {public Quest createFor(Clan c, Clan p) {return new UpgradeDomicileQuest(c, p);}};}
	
	public static class UpgradeDomicileQuest extends PatronedQuest implements GoodsAcquirable {
		private static int NOCONSTRBIDS = -1; //must be < 0
		private int numConstrs = NOCONSTRBIDS;
		private int turnsLeft, milletRecord;

		public UpgradeDomicileQuest(Clan P, Clan patron) {
			super(P, patron);
			turnsLeft = patron.FB.getBeh(M_.PATIENCE) / 3 + 5;
		}
		
		@Override
		public String description() {return "Upgrade Domicile";}

		@Override
		public void pursue() {
			if (buildForPatron()) {
				success();
			}
			else if (numConstrs == NOCONSTRBIDS) {
				numConstrs = 0;
				milletRecord = Me.getMillet();
				Me.myMkt(Misc.constr).liftOffer(Me);
			}
			else if (turnsLeft > 0) { turnsLeft --;}
			else { failure(Me.myShire()); }
		}
		
		public boolean alterG(MktO origin, int n) {
			if(origin.getGood() == Misc.constr) {numConstrs += n; return true;}
			else return false;
		}
		
		private boolean buildForPatron() {
			if (numConstrs <= 0) {return false;}
			Me.alterCumIncome(milletRecord - Me.getMillet()); // dont count this purchase as an operating loss
			milletRecord = 0;
			numConstrs = NOCONSTRBIDS;
			ExpertiseQuests.practiceSkill(Me, P_.ARTISTRY);
			final int max = 1 + Me.FB.getPrs(P_.ARTISTRY);
			final int producedSplendor = Calc.randBetween(max / 2, max);
			if (Me != patron) {Me.addReport(GobLog.build(patron, producedSplendor, true));}
			patron.addReport(GobLog.build(Me, producedSplendor, false));
			patron.chgSplendor(producedSplendor);
			patron.myShire().getGraphingInfo("spLeNdor").alterValue(producedSplendor);
			return true;
		}
		
	}
}
