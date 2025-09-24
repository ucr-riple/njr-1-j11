package AMath;

import Defs.M_;
import Ideology.Values;
import Questing.*;
import Sentiens.Clan;
import Shirage.Shire;

public class TestRomance extends Testing {
	
	private static Shire s;
	private static Clan me;
	private static Clan lover;
	private static Clan rival;
	
	public static void doAllRomanceTests() {
		testCantFindLover();
		testHappilyEverAfter();
		testOneSidedLove();
		
		restoreFilteredCensuses();
	}
	
	private static void resetLovers() {
		reset();
		s = testRealm.getClan(0).myShire();
		me = s.getCensus(0);
		me.setGender(true);
		lover = s.getCensus(1);
		lover.setGender(false);
		rival = s.getCensus(2);
		rival.setGender(true);
		me.setActive(true);
		lover.setActive(true);
		rival.setActive(true);
		me.FB.setBeh(M_.PROMISCUITY, 7);
		me.FB.setBeh(M_.TEMPER, 15);
		lover.FB.setBeh(M_.PROMISCUITY, 7);
		rival.FB.setBeh(M_.PROMISCUITY, 7);
		lover.MB.newQ(new Quest.DefaultQuest(lover));
		rival.MB.newQ(new Quest.DefaultQuest(rival));
		filterCensus(s, me, lover, rival);
	}

	private static void testCantFindLover() {
		resetLovers();
		makePuritan(me, Values.SPIRITUALITY, Values.WEALTH);
		me.chgHoliness(1000000);
		me.alterMillet(1000000);
		me.MB.newQ(new RomanceQuests.BreedQuest(me));
		me.FB.setBeh(M_.PATIENCE, 15);
		pursueUntilDone(me);
		affirm(me.getSuitor() == null);
		affirm(me.AB.containsStressor(s));
	}

	private static void testHappilyEverAfter() {
		resetLovers();
		makePuritan(me, Values.COPULATION, Values.KNOWLEDGE);
		makePuritan(lover, Values.SPIRITUALITY, Values.WEALTH);
		me.chgHoliness(1000000);
		me.alterMillet(1000000);
		lover.breed(rival); // to inc copulation value pts
		lover.setSuitor(null);
		lover.incKnowledgeAttribution();
		me.MB.newQ(new RomanceQuests.BreedQuest(me));
		me.FB.setBeh(M_.PATIENCE, 15);
		pursueUntilDone(me);
		// small chance of failure if coincidentally doesnt "see" lover in FindMate quest
		affirmSoft(me.getSuitor() == lover);
		affirmSoft(lover.getSuitor() == me);
		affirmSoft(me.getNumOffspring() == 1);
		affirmSoft(!me.AB.containsStressor(s));
		affirmSoft(!me.AB.containsStressor(me));
		affirmSoft(!me.AB.containsStressor(lover));
	}
	
	private static void testOneSidedLove() {
		resetLovers();
		makePuritan(me, Values.COPULATION, Values.KNOWLEDGE);
		makePuritan(lover, Values.SPIRITUALITY, Values.WEALTH);
		lover.chgHoliness(1000000);
		lover.alterMillet(1000000);
		lover.breed(rival); // to inc copulation value pts
		lover.setSuitor(null);
		lover.incKnowledgeAttribution();
		me.MB.newQ(new RomanceQuests.BreedQuest(me));
		me.FB.setBeh(M_.PATIENCE, 15);
		pursueUntilDone(me);
		affirm(me.getSuitor() == null);
		affirm(lover.getSuitor() == null);
		affirm(me.getNumOffspring() == 0);
		affirm(me.AB.containsStressor(lover));
	}
}
