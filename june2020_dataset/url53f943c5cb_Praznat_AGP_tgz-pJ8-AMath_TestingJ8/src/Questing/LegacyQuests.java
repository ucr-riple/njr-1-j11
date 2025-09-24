package Questing;

import AMath.Calc;
import Avatar.SubjectiveType;
import Defs.P_;
import Ideology.*;
import Questing.Quest.PatronedQuest;
import Questing.Quest.PatronedQuestFactory;
import Sentiens.Clan;

public class LegacyQuests {
	public static PatronedQuestFactory getMinistryFactory() {return new PatronedQuestFactory(LegacyQuest.class) {public Quest createFor(Clan c, Clan p) {return new LegacyQuest(c, p);}};}

	private static final double RATE_MULTIPLIER = 0.1;
	
	public static class LegacyQuest extends PatronedQuest {

		public LegacyQuest(Clan P, Clan patron) {super(P, patron);}

		@Override
		public String description() {return "Build Legacy";}
		
		@Override
		public void pursue() {
			final boolean selfImproveChoice = decideToSelfImproveOrReenforceLegacy();
			if (selfImproveChoice) {
				replaceAndDoNewQuest(Me, QtoQuest(Me, Me.FB.randomValueInPriorityOtherThan(Values.LEGACY).pursuit()));
			} else {reenforceLegacy(Me.FB.randomValueInPriority());}
		}
		
		@Override
		public void avatarPursue() {
			avatarConsole().showChoices("Choose value to reenforce legacy for", Me, Values.All,
					SubjectiveType.VALUE_ORDER, new Calc.Listener() {
				@Override
				public void call(Object arg) {
					reenforceLegacy((Value)arg);
				}
			}, new Calc.Transformer<Value, String>() {
				@Override
				public String transform(Value v) {
					return v.description(Me) + ": " + Me.LB.improveDesc(v, rate());
				}
			});
		}
		
		private double rate() {return RATE_MULTIPLIER * (Me.FB.getPrs(P_.PROSE) + 1) / 16;}
		
		private void reenforceLegacy(Value val) {
			final ValuatableValue v = (ValuatableValue) val;
			final double rate = rate();
			ExpertiseQuests.practiceSkill(Me, P_.PROSE);
			final boolean success = Me.LB.reenforceIfPositive(v, rate);
			if (success) {success(Me);}
			else {failure(Me);}
		}
		
		private boolean decideToSelfImproveOrReenforceLegacy() {
			if (Me != patron) {return false;} // servants always reenforce patron legacy
			return Calc.pPercent(Me.getAge()); // TODO refine pls
		}
		
	}
}
