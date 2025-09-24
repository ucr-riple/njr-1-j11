package Descriptions;

import Defs.Misc;
import Game.*;
import Sentiens.Clan;

public class MinisterNames {
	public static interface MinistryNamer {
		public String nameFor(Clan c);
	}
	
//	JUDGE, NOBLE, HISTORIAN, PHILOSOPHER, GUILDMASTER, SORCEROR, VIZIER, GENERAL, TREASURER, COURTESAN, APOTHECARY, ARCHITECT

	private static boolean isGovt(Clan clan) {
		final Clan boss = clan.getBoss();
		return boss.myShire().getGovernor() == boss;
	}
	
	public static final String getMinistryName(Ministry m, Clan c) {
		if (m == Job.JUDGE) {
			return isGovt(c) ? "Judge" : ("Priest" + (c.getGender() == Misc.FEMALE ? "ess" : ""));
		}
		else if (m == Job.NOBLE) {
			return isGovt(c) ? "Noble" : "Minion";
		}
		else if (m == Job.HISTORIAN) {
			return isGovt(c) ? "Historian" : "Bard";
		}
		else if (m == Job.PHILOSOPHER) {
			return isGovt(c) ? "Wiseman" : "Scribe";
		}
		else if (m == Job.TUTOR) {
			return isGovt(c) ? "Grand tutor" : "Tutor";
		}
		else if (m == Job.SORCEROR) {
			return isGovt(c) ? "Sorcer" + (c.getGender() == Misc.FEMALE ? "ess" : "or") : "Shaman";
		}
		else if (m == Job.VIZIER) {
			return isGovt(c) ? "Vizier" : "Bureaucrat";
		}
		else if (m == Job.GENERAL) {
			return isGovt(c) ? "General" : "Soldier";
		}
		else if (m == Job.TREASURER) {
			return isGovt(c) ? "Treasurer" : "Accountant";
		}
		else if (m == Job.COURTESAN) {
			return isGovt(c) ? "Courtesan" : "Consort";
		}
		else if (m == Job.APOTHECARY) {
			return isGovt(c) ? "Apothecary" : "Healer";
		}
		else if (m == Job.ARCHITECT) {
			return isGovt(c) ? "Architect" : "Sculptor";
		}
		else {return "Servant";}
	}
	
	
}
