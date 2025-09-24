package Questing;

import AMath.Calc;
import Avatar.SubjectiveType;
import Defs.*;
import Descriptions.GobLog;
import Game.*;
import Markets.*;
import Questing.Quest.PatronedQuest;
import Questing.Quest.PatronedQuestFactory;
import Sentiens.*;

public class FaithQuests {
	
	public static PatronedQuestFactory getMinistryFactory() {return new PatronedQuestFactory(ContactQuest.class) {public Quest createFor(Clan c, Clan p) {return new ContactQuest(c, p);}};}

	/**
	 * bless patron : inc patron's holiness
	 * self : inc self's holiness
	 */
	public static class ContactQuest extends PatronedQuest {
		private ActOfFaith currAoF;
		private int timesLeft, mana;
		
		public ContactQuest(Clan P, Clan patron) {
			super(P, patron);
			timesLeft = patron.FB.getBeh(M_.PATIENCE) / 3 + 5;
		}

		@Override
		public String description() {return "Engage Spirituality";}

		@Override
		public void pursue() {
			if (currAoF == null) {decidePrayStyle(); return;}
			commonPursue();
		}
		
		@Override
		public void avatarPursue() {
			if (currAoF == null) {avatarDecidePrayStyle(); return;}
			commonPursue();
		}
		
		private void commonPursue() {
			if (timesLeft > 0) {
				 mana += currAoF.conduct(Me, patron);
				 timesLeft--;
			}
			else {
				Me.addReport(GobLog.pray(Me, patron, mana, currAoF));
				consumeMana(mana);
				if (Me != patron) {patron.addReport(GobLog.pray(Me, patron, mana, currAoF));}
				double avg = ((double)patron.getHoliness()) / patron.getTimesPrayed();
				patron.incTimesPrayed();
				if (mana < avg) {failure(Me);}
				else {success(Me);}
			}
		}
		
		private void consumeMana(int mana) {
			patron.chgHoliness(AGPmain.rand.nextInt(1 + Math.max(0, mana)));
		}
		private void decidePrayStyle() {
			int a = Calc.pPercent(Me.FB.getBeh(M_.MADNESS)) ?
					AGPmain.rand.nextInt(ACTS_OF_FAITH.length) : Me.FB.getBeh(M_.PRAYSTYLE) % ACTS_OF_FAITH.length;
			currAoF = ACTS_OF_FAITH[a];
		}
		private void avatarDecidePrayStyle() {
			avatarConsole().showChoices("Choose act of faith", Me, ACTS_OF_FAITH, SubjectiveType.NO_ORDER, new Calc.Listener() {
				@Override
				public void call(Object arg) {
					currAoF = (ActOfFaith) arg;
				}
			});
		}
		public ActOfFaith getCurrAof() {return currAoF;}
		
	}
	public static abstract class ActOfFaith {
		public void affect(Object arg) {}
		public abstract String desc();
		public abstract int conduct(Clan subject, Clan object);
		@Override
		public String toString() {return desc();}
	}
	public static abstract class GoodsAcquirableActOfFaith extends ActOfFaith implements GoodsAcquirable {}
	public static ActOfFaith INCANTATE = new ActOfFaith() {
		public String desc() {return "Unholy Trance";}
		public int conduct(Clan subject, Clan object) {
			return subject.FB.getBeh(M_.MADNESS) * object.FB.getBeh(M_.SUPERST) / 16; //0~16
		}
	};
	public static ActOfFaith RITUAL = new ActOfFaith() {
		public String desc() {return "Festive Dance Ritual";}
		public int conduct(Clan subject, Clan object) {
			final int danceSkill = subject.FB.getPrs(P_.DANCE);
			ExpertiseQuests.practiceSkill(subject, P_.DANCE);
			return danceSkill * object.FB.getBeh(M_.EXTROVERSION) / 8; //0~32
		}
	};
	public static ActOfFaith ONEWITHNATURE = new ActOfFaith() {
		public String desc() {return "Communion with Nature";}
		public int conduct(Clan subject, Clan object) {
			return object.FB.getBeh(M_.RESPENV); // TODO * %wilderness in prayee shire
		}
	};
	public static ActOfFaith ONEWITHGOD = new ActOfFaith() {
		public String desc() {return "Communion with God";}
		public int conduct(Clan subject, Clan object) {
			return 16 - object.FB.getBeh(M_.RESPENV); // TODO feed the statue in the temple...
		}
	};
	public static ActOfFaith SACRIFICE = new GoodsAcquirableActOfFaith() {
		private static final int NOBIDS4CAPTIVES = -1;
		private int numCaptives = NOBIDS4CAPTIVES;
		public String desc() {return "Ritual Sacrifice";}
		public int conduct(Clan subject, Clan object) {
			if (numCaptives > 0) {
				numCaptives = NOBIDS4CAPTIVES;
				return subject.FB.getBeh(M_.BLOODLUST); // TODO dont do if SIN
			}
			else if (numCaptives == NOBIDS4CAPTIVES) {
				numCaptives = 0;
				subject.myMkt(Misc.captive).liftOffer(subject); // TODO alterG...
			}
			return 0;
		}
		@Override
		public void affect(Object arg) {
			numCaptives += (Integer) arg;
		}
		@Override
		public boolean alterG(MktO origin, int num) {
			if(origin.getGood() == Misc.captive) {affect(num); return true;}
			else return false;
		}
	};
	private static final ActOfFaith[] ACTS_OF_FAITH = new ActOfFaith[] {
		INCANTATE, RITUAL, ONEWITHNATURE, ONEWITHGOD, SACRIFICE
	};
}
