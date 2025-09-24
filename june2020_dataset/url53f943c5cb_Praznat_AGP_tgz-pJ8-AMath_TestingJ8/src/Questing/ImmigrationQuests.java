package Questing;

import Game.AGPmain;
import Sentiens.Clan;
import Shirage.Shire;

public class ImmigrationQuests {
	public static class EmigrateQuest extends Quest {
		private final Shire destination;
		
		public EmigrateQuest(Clan P, Shire shire) {
			super(P); destination = shire;
		}

		@Override
		public void pursue() {
			// TODO remove bids/offers from market? or no. but sell land. dunno
			Shire oldShire = Me.myShire();
			Me.setCurrentShire(destination);
			AGPmain.TheRealm.addToWaitingForImmigration(Me); // when it happens
			success(oldShire);
		}
		@Override
		public String description() {return "Emigrate to " + destination.getName();}
	}
	public static class FleeQuest extends Quest {

		public FleeQuest(Clan P) {super(P);}

		@Override
		public void pursue() {
			// TODO Auto-generated method stub
		}
		@Override
		public String description() {return "Flee from danger";}
	}
}
