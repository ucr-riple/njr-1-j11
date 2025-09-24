package Sentiens.Stress;

import Defs.*;
import Game.Job;
import Ideology.Value;
import Questing.ImmigrationQuests;
import Questing.Knowledge.KnowledgeBlock;
import Sentiens.Clan;
import Shirage.Shire;

public class Stressor {
	public static final int FEAR = 0;  //potential threat, detected by "patrolling" (ppl with high paranoia do more often)
	public static final int ANNOYANCE = 1;  //inability to succeed in quests
	public static final int INSULT = 2;  //something happens to lower your self worth
	public static final int LIFE_THREAT = 3;   //e.g. starvation
	public static final int HATRED = 4;  //immediately maxes out amygdala, calling enoughIsEnough()
	public static final int EXTREME_TRAUMA = 5;  //probably causes insanity
	public static final int PROMISE_BROKEN = 6;  //probably causes insanity
	public static final int COMPETITION_LOSS = 7;
	protected final int type;
	protected Object blamee;
	
	public Stressor() {type = -1;}
	public Stressor(int t, Blameable c) {type = t; blamee = c;}
	public Object getTarget() {return blamee;}
	public int getLevel(Clan doer) {
		// dont use useBeh please... it will be called several times in enoughIsEnough method
		switch (type) {
		case FEAR: return 2;
		case ANNOYANCE: return 1;
		case COMPETITION_LOSS: return 4; // competitiveness MEME?
		case INSULT: return doer.FB.getBeh(M_.VANITY) / 2;
		case LIFE_THREAT: return 3 + doer.FB.getBeh(M_.MIERTE);
		case HATRED: return 16; //must be higher than others to avoid getting relieved
		case EXTREME_TRAUMA: return 50; //pretty much never disappears
		case PROMISE_BROKEN: return doer.FB.getBeh(M_.STRICTNESS) / 3;
		default: return 0;
		}
	}
	public boolean respond(Clan responder) {  //TODO
		boolean success = true;
		if (blamee instanceof Clan) {
			if (type == HATRED) {}
			if (type == PROMISE_BROKEN) {}
			else {}
		}
		else if (blamee instanceof Shire) {
			Shire newShire = responder.myShire().getSomeNeighbor();
			if (responder.myShire() != newShire) {responder.MB.newQ(new ImmigrationQuests.EmigrateQuest(responder, newShire));}
		}
		else if (blamee instanceof Value) {

		}
		else if (blamee instanceof Job) {
			// TODO find new job
			final KnowledgeBlock kb = responder.getRelevantLibrary().findKnowledge(K_.JOBS);
			if (kb != null) kb.useKnowledge(responder);
		}
		return success;
	}
	
	public boolean sameAndLessThan(Clan doer, Stressor other) {
		return (other.getTarget() == blamee && getLevel(doer) <= other.getLevel(doer));
	}
	
	@Override
	public String toString() {return "type " + type + " @ " + blamee.toString();}

}