package AMath;

import Defs.*;
import Game.Job;
import Ideology.Values;
import Questing.Knowledge.KnowledgeQuests;
import Sentiens.Clan;
import Shirage.Shire;

public class TestKnowledge extends Testing {
	private static Shire s;
	private static Clan a;
	private static Clan b;
	
	private static void resetKnowledge() {
		reset();
		s = testRealm.getClan(0).myShire();
		a = s.getCensus(0);
		b = s.getCensus(1);
		a.setActive(true);
		b.setActive(true);
		makePuritan(a, Values.WEALTH, Values.KNOWLEDGE);
	}
	
	public static void testWealthKnowledge() {
		resetKnowledge();
		// test a discovers new knowledge
		a.FB.setBeh(M_.PATIENCE, 15);
		a.setJob(Job.FARMER);
		b.setJob(Job.LOBOTOMIST);
		b.alterMillet(1000);
		a.MB.newQ(new KnowledgeQuests.KnowledgeQuest(a, a, K_.JOBS));
		pursueUntilDone(a);
		affirm(a.getJob() == Job.LOBOTOMIST);
		affirm(a.MB.QuestStack.isEmpty());
		affirm(a.getKnowledgeAttribution() == 1);
		// test a uses library
		a.setJob(Job.FARMER);
		a.MB.newQ(new KnowledgeQuests.KnowledgeQuest(a, a, K_.JOBS));
		a.setActive(true);
		a.pursue();
		affirm(a.getJob() == Job.LOBOTOMIST);
		affirm(a.getKnowledgeAttribution() == 2);
		affirm(a.MB.QuestStack.isEmpty());
	}
}
