package Questing;

import Descriptions.GobLog;
import Questing.Quest.FindTargetAbstract;
import Questing.Quest.RelationCondition;
import Questing.Quest.TransactionQuest;
import Questing.Might.WarQuest;
import Sentiens.Clan;

public class PersecutionQuests {

	private static final RelationCondition EXILECONDITION = new RelationCondition() {
		@Override
		public boolean meetsReq(Clan POV, Clan target) {
			return POV.myShire() != target.myShire();
		}
	};

	public static abstract class PersecuteAbstract extends TransactionQuest {
		public PersecuteAbstract(Clan P) {
			super(P);
		}

		public void failDestroy() {
			timesLeft--;
		}

		@Override
		public String shortName() {
			return "Persecute";
		}

		protected abstract FindTargetAbstract findWhat();

		protected abstract void setContractDemand();

		protected void setContractOffer() {
			contract().threatenLifeAndProperty();
		}

		protected void successCase() {
			this.success(target);
		} // relieves previous anger from target

		protected void failCase() {
			Me.MB.newQ(WarQuest.start(Me, target));
		}

		protected void report(boolean success) {
			Me.addReport(GobLog.converted(target, success));
			target.addReport(GobLog.wasConverted(Me, success));
		}
	}

	public static class PersecuteHeretic extends PersecuteAbstract {
		public PersecuteHeretic(Clan P) {
			super(P);
		}

		@Override
		protected void setContractDemand() {
			contract().demandRepentance();
		}

		@Override
		public String description() {
			return "Persecute "
					+ (target == null ? "Heretic" : target.getNomen());
		}

		@Override
		protected FindTargetAbstract findWhat() {
			return new FindHeretic(Me);
		}
	}

	public static class PersecuteInfidel extends PersecuteAbstract {
		public PersecuteInfidel(Clan P) {
			super(P);
		}

		@Override
		protected void setContractDemand() {
			contract().demandAllegiance();
		}

		@Override
		public String description() {
			return "Persecute "
					+ (target == null ? "Infidel" : target.getNomen());
		}

		@Override
		protected FindTargetAbstract findWhat() {
			return new FindInfidel(Me);
		}
	}

	public static class FindHeretic extends FindTargetAbstract {
		public FindHeretic(Clan P) {
			super(P);
		}

		@Override
		public boolean meetsReq(Clan POV, Clan target) {
			boolean success = target.FB.getDeusInt() != POV.FB.getDeusInt();
			Me.addReport(GobLog.findSomeone((success ? target : null),
					"heretic"));
			return success;
		}
		@Override
		protected void onFailure() {
			finish();
		}
		@Override
		protected String searchDesc() {return "heretic";}

		@Override
		public String description() {
			return "Find Heretic";
		}
	}

	public static class FindInfidel extends FindTargetAbstract {
		public FindInfidel(Clan P) {
			super(P);
		}

		@Override
		public boolean meetsReq(Clan POV, Clan target) {
			boolean success = target.myOrder() != POV.myOrder();
			Me.addReport(GobLog.findSomeone((success ? target : null),
					"infidel"));
			return success;
		}
		@Override
		protected String searchDesc() {return "infidel";}

		@Override
		public String description() {
			return "Find Infidel";
		}

		@Override
		protected void onFailure() {
			finish();
		}
	}
}
