package Questing;

import AMath.Calc;
import Avatar.SubjectiveType;
import Descriptions.GobLog;
import Questing.Quest.PatronedQuest;
import Questing.Quest.PatronedQuestFactory;
import Sentiens.*;
import Sentiens.Law.PersonalCommandment;

public class CreedQuests {
	public static PatronedQuestFactory getMinistryFactory() {return new PatronedQuestFactory(PriestQuest.class) {public Quest createFor(Clan c, Clan p) {return new PriestQuest(c, p);}};}

	// NORMAL MINISTRY CLASS SHOULD BE "PREACH COMMANDMENTS"
	
	public static class TweakCommandments extends PatronedQuest {
		public TweakCommandments(Clan P, Clan patron) {super(P, patron);}
		@Override
		public String description() {return "Moral Reflection";}

		@Override
		public void pursue() {
			CognitiveDissonance.doMorals(patron, Me);
			Me.MB.finishQ();
		}
		@Override
		public void avatarPursue() {
			avatarConsole().showChoices("Choose commandment to tweak", Me, Me.FB.commandments,
					SubjectiveType.NO_ORDER, new Calc.Listener() {
				@Override
				public void call(Object arg) {
					final PersonalCommandment c = ((PersonalCommandment) arg);
					final boolean newlyActive = !c.isSinful();
					c.setSinful(newlyActive);
					Me.addReport(GobLog.decidedMoral(c, newlyActive));
					Me.MB.finishQ();
				}
			});
		}
	}

	public static class PriestQuest extends PatronedQuest {
		public PriestQuest(Clan P, Clan patron) {super(P, patron);}
		@Override
		public String description() {return "Sacred Duty";}
		@Override
		public void pursue() {
			// choose between PersecuteCriminal, TweakCommandments
			replaceAndDoNewQuest(Me, new TweakCommandments(Me, patron));
		}
		
	}
	
}
