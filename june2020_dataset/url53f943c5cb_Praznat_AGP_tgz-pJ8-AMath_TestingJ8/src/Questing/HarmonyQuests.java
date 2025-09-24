package Questing;

import Sentiens.Clan;
import Sentiens.Stress.Stressor;

public class HarmonyQuests {

	public static class RelieveStressQuest extends Quest { // not patroned
		public RelieveStressQuest(Clan P) {
			super(P);
		}
		@Override
		public void pursue() {
			Stressor target = Me.AB.largestStressor();
			boolean success = target.respond(Me);
			if (success) {   //respond to this one
				Me.AB.relieveFrom(target);
				success();
			} else {
				failure(target);
			}
		}
		@Override
		public String description() {
			return "Reduce stress";
		}

	}
	
	// TODO patroned harmony quest: reduce Order stress & threats
}
