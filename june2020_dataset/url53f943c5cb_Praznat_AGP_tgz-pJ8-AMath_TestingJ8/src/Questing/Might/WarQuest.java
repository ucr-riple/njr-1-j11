package Questing.Might;

import java.util.*;

import Descriptions.GobLog;
import Questing.*;
import Questing.Quest.RootQuest;
import Sentiens.Clan;
import Sentiens.Stress.*;
import Shirage.Shire;
import War.*;
import War.BattleField.EndStatus;

public class WarQuest extends RootQuest implements InvolvesArmy {
	
		enum Status {FORMING, MANEUVERING, REGROUPING, VICTORIOUS, DEFEATED};
		Status status = Status.FORMING;
		
		private Clan target;
		private final Set<Clan> enemies = new HashSet<Clan>();
		private Set<FormArmyQuest> army = new HashSet<FormArmyQuest>();
		
		private Quest branch;

		public static Quest start(Clan P, Clan target) {
			target.MB.newQ(new WarQuest(target, P));
			
			WarQuest preexisting = (WarQuest)P.MB.QuestStack.getOfType(WarQuest.class);
			if (preexisting != null) {
				preexisting.enemies.add(target);
				preexisting.chooseCurrentTarget();
				P.addReport(GobLog.warStarted(target, preexisting.enemies));
				return preexisting;
			}
			else P.addReport(GobLog.warStarted(target, null)); // no other enemies
			return new WarQuest(P, target);
		}
		private WarQuest(Clan P, Clan target) {
			super(P); this.target = target;
		}
		private void chooseCurrentTarget() { // TODO figure out which enemy to prioritise
			for (Clan enemy : enemies) target = enemy;
			status = Status.MANEUVERING;
		}
		@Override
		public Set<FormArmyQuest> getArmy() {return army;}
		@Override
		public void setArmy(Set<FormArmyQuest> army) {this.army = army;}
		
		private void disband() {
			if (army == null) {return;} // maybe opponent gave up before you could even form army
			for (FormArmyQuest fa : army) {
				fa.specialDelete();//disband everybody
			}
			Me.addReport(GobLog.disbanded());
			army.clear();
		}
		
		public void winWar() {
			status = Status.VICTORIOUS;
			Me.AB.relieveFrom(new Stressor(Stressor.HATRED, target));
			endWar();
			// TODO force demands one conquered foe!! where is it?
		}
		public void loseWar() {
			status = Status.DEFEATED;
			Me.AB.add(new Stressor(Stressor.HATRED, target));
			endWar();
		}
		private void endWar() {
			Me.addReport(GobLog.warEnded(target, enemies));
			enemies.remove(target);
			if (enemies.isEmpty()) {disband();}
			else {chooseCurrentTarget();}
		}
		
		private static final Set<Clan> removedLosers = new HashSet<Clan>();
		public static void onBattleEndStatus(WarQuest winnerQ, WarQuest loserQ, BattleField battleField) {
			final Clan winner = winnerQ.getDoer();
			final Shire battleShire = BattleField.getShire();
			
			removedLosers.clear();
			for (FormArmyQuest faq : loserQ.getArmy()) {
				final Clan loser = faq.getDoer();
				// winner takes shire if it belongs to a loser
				Shire loserShire = faq.getDoer().getGovernedShire();
				if (battleShire == loserShire) {
					winner.myOrder(true).acquireShire(battleShire, winner);
				}
				switch (battleField.getEndStatus(loser)) {
				case CAPTURED:
					// TODO no idea here
					removedLosers.add(loser);
					break;
				case DEAD:
					removedLosers.add(loser);
					loser.die();
					loser.AB.add(StressorFactory.createBloodVengeanceStressor(winner));
					break;
				}
			}
			loserQ.getArmy().removeAll(removedLosers);
			
			boolean warIsOver = battleField.getEndStatus(loserQ.getDoer()) != EndStatus.RETREATED;
			if (warIsOver) {
				winnerQ.winWar();
				loserQ.loseWar();
			}
		}
		
		public void beReady() {status = Status.MANEUVERING;}
		
		private static void onDisbandedArmy(Clan clan, WarQuest.Status status) {
			clan.MB.finishQ();
		}
		@Override
		protected void cleanup() {
			if (branch != null) return;
			switch (status) {
			case FORMING:
//				Me.MB.newQ(new FormOwnArmyQuest(Me, this));
				branch = new FormOwnArmyQuest(Me, this);
				break;
			case MANEUVERING:
//				Me.MB.newQ(new ManeuverForBattle(Me, this));
				branch = new ManeuverForBattle(Me, this);
				break;
			default: if (army.isEmpty()) {onDisbandedArmy(Me, status); break;}
			}
		}
		@Override
		protected String desc() {
			String s = "Warring with " + target.getNomen() + " " + status;
			return s;
		}
		public Clan getTarget() {
			return target;
		}

	
}
