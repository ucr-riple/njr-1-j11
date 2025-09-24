package Questing;

import Defs.*;
import Descriptions.GobLog;
import Ideology.*;
import Questing.Quest.FindTargetAbstract;
import Questing.Quest.PatronedQuest;
import Questing.Quest.PatronedQuestFactory;
import Questing.Quest.RelationCondition;
import Questing.Quest.TargetQuest;
import Questing.Might.MightQuests;
import Sentiens.Clan;
import Sentiens.Stress.*;

public class RomanceQuests {
	public static PatronedQuestFactory getMinistryFactory() {return new PatronedQuestFactory(BreedWithPatronQuest.class) {public Quest createFor(Clan c, Clan p) {return new BreedWithPatronQuest(c, p);}};}

	public static class BreedWithPatronQuest extends PatronedQuest {
		public BreedWithPatronQuest(Clan P, Clan patron) {super(P, patron);}
		@Override
		public void pursue() {
			replaceAndDoNewQuest(Me, new BreedQuest(Me));
		}
		@Override
		public String description() {return "Breed with " + patron.getNomen();}
	}
	
	public static class BreedQuest extends TargetQuest {
		private int courtsLeft = Misc.E;
		private int failsLeft = Misc.E;
		public BreedQuest(Clan P) {super(P); resetFails();}
		public BreedQuest(Clan P, Clan target) {this(P); this.target = target;}
		@Override
		public void pursue() {
			if (failsLeft <= 0) {failure(Values.COPULATION); return;}
			if (target == null) {Me.MB.newQ(new FindMate(Me)); failsLeft--; return;}  //failsleft-- cuz it means findmate failed
			if (courtsLeft == Misc.E) {courtsLeft = 1 + (15 - target.useBeh(M_.PROMISCUITY)) / 5;}
			if (courtsLeft <= 0) {Me.breed(target); success(Me); return;}
			Me.MB.newQ(new Compete4MateQuest(Me, target));
		}
		private void resetFails() {failsLeft = Me.useBeh(M_.PATIENCE) / 3 + 1;}
		public void courtSucceeded() {courtsLeft--;}
		public void courtFailed() {failsLeft--;}
		@Override
		public String shortName() {return "Breed";}
		@Override
		public String description() {return "Breed with " + (target==null ? "someone" : target.getNomen());}
	}

	public static class FindMate extends FindTargetAbstract implements RelationCondition {
		public FindMate(Clan P) {super(P);}
		@Override
		public boolean meetsReq(Clan POV, Clan target) {
			if (POV.getGender() == target.getGender()) return false;
			boolean success = POV.FB.randomValueInPriority().compare(POV, target, POV) > 0; // promiscuity?
			if (success) {((BreedQuest) upQuest()).resetFails();} //previous fails were for finding target
			return success;
		}
		@Override
		public String shortName() {return "Find Mate";}
		@Override
		public String description() {return "Find suitable mate";}
		@Override
		protected void onFailure() {
			failure(StressorFactory.createShireStressor(Me.myShire(), Values.COPULATION));
		}
		@Override
		protected String searchDesc() {return "mate";}
	}



	public static class Compete4MateQuest extends TargetQuest {
		public Compete4MateQuest(Clan P, Clan T) {super(P, T);}

		@Override
		public void pursue() {
			Clan rival = target.getSuitor();
			if (rival == null) {rival = target;}
			else if (rival == Me) {success(); return;}
			Value lostVal = null; double diff = 0; int biggestLostW = 0;
			for (int i = 0; i < (15 - target.FB.getBeh(M_.PROMISCUITY))/ 5 + 1; i++) {
				final Value v = target.FB.randomValueInPriority();
				final int w = target.FB.weightOfValue(v);
				double d = w * v.compare(target, Me, rival);
				if (d < 0) {if(w > biggestLostW) {lostVal = v; biggestLostW = w;}}
				else if (rival != target) {d *= (15 - target.useBeh(M_.PROMISCUITY)) / 15;} // penalize for non single, less promiscuous target
				diff += d;
			}
			Me.addReport(GobLog.compete4Mate(target, rival, diff));
			if (diff > 0) {success(target);}
			else {
				if (shouldConfrontRival(rival)) { // if i think im better than rival, then just attack him
					replaceAndDoNewQuest(Me, new MightQuests.ChallengeMightQuest(Me, Values.COPULATION));return;
				}
				else if (shouldPreachToTarget(rival)) { // if both me and rival think rival is better than me, try to preach
//					replaceAndDoNewQuest(Me, new PreachQuest(Me, Values.BEAUTY));return;
				}
				else if (lostVal != null) { // if i think rival is better but rival thinks i am better, try to catch up
					replaceAndDoNewQuest(Me, new MiscQuests.CatchUpQuest(Me, rival, lostVal));return;
					// and/or just change values to better match hers
				}
				failure(rival);
			}  // but try other tricks such as work or preach
		}
		/** I think I am better than rival */
		private boolean shouldConfrontRival(Clan rival) {
			return Me.FB.compareRespect(rival) > 0; //used to determine whether to continue to court or whether to attack/bribe rival
		}
		/** rival thinks he is better than me */
		private boolean shouldPreachToTarget(Clan rival) {
			return rival.FB.compareRespect(Me) > 0; //used to determine whether to continue to court or whether to work or preach etc
		}
		@Override
		public void success(Blameable blamee) {((BreedQuest) upQuest()).courtSucceeded(); super.success(blamee);}
		@Override
		public void failure(Blameable blamee) {((BreedQuest) upQuest()).courtFailed(); super.failure(blamee);}
		@Override
		public String shortName() {Clan rival = target.getSuitor(); return (rival==Me || rival==null ? "Court" : "Compete");}
		@Override
		public String description() {Clan rival = target.getSuitor(); return (rival==Me || rival==null ? "Court " + target.getNomen() : 
			"Steal " + target.getNomen() + "'s heart away from " + rival.getNomen());}
	}
	
}
