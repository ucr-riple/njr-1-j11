package War;

import Defs.Misc;
import Sentiens.Clan;

public class CombatDefs {


	
	public static FormationType[] formationTypes = setupFormationDefinitions();
	public static final FormationType SCORPION = new FormationType();
	public static final FormationType WEDGE = new FormationType();
	public static final FormationType BOX = new FormationType();
	
	private static FormationType[] setupFormationDefinitions() {
		SCORPION.vanguard.setPreferences(0, FODDER, PHALANX, FOOT);
		SCORPION.lwing.setPreferences(10, DONKEY_KNIGHT, CHARGERS, CAVALRY, MISSILE_CAVALRY);
		SCORPION.rwing.setPreferences(10, DONKEY_KNIGHT, CHARGERS, CAVALRY, MISSILE_CAVALRY);
		SCORPION.center.setPreferences(5, PHALANX, FOOT);
		SCORPION.rear.setPreferences(5, MISSILE, PHALANX);

		WEDGE.vanguard.setPreferences(2, DONKEY_KNIGHT, CHARGERS, PHALANX);
		WEDGE.lwing.setPreferences(0, FODDER);
		WEDGE.rwing.setPreferences(0, FODDER);
		WEDGE.center.setPreferences(3, CHARGERS, PHALANX, FOOT);
		WEDGE.rear.setPreferences(4, PHALANX, FODDER);
		WEDGE.vanguard.setSeatOfCommand(WEDGE);

		BOX.vanguard.setPreferences(5, PHALANX, DONKEY_KNIGHT, FOOT, FODDER);
		BOX.lwing.setPreferences(5, PHALANX, DONKEY_KNIGHT, FOOT, FODDER);
		BOX.rwing.setPreferences(5, PHALANX, DONKEY_KNIGHT, FOOT, FODDER);
		BOX.center.setPreferences(2, MISSILE_CAVALRY, MISSILE);
		BOX.rear.setPreferences(3, MISSILE, CAVALRY);
		
		return new FormationType[] {SCORPION, WEDGE, BOX};
	}
	
	static class FormationType {
		private Part seatOfCommand;
		private Part vanguard, lwing, rwing, center, rear;
		private int priority = 0;
		public FormationType () {
			vanguard = new Part(); lwing = new Part(); rwing = new Part(); center = new Part(); rear = new Part();
			rear.setSeatOfCommand(this);  //default
		}
		public Part getSeatOfCommand() {return seatOfCommand;}
		
		static class Part {
			private int advancement; //starting proximity to enemy
			private int size; //preferred size (units allocated to part based on this)
			private SoldierType[] typePreference;
			private void setPreferences(int s, SoldierType... st) {size = s; typePreference = st;}
			private void setSeatOfCommand(FormationType parent) {parent.seatOfCommand = this;}
		}
	}
	
	
	static class SoldierType {
		Armor armor;
		Edge edge;
		Range range;
		Mount mount;
		private static SoldierType create() {return new SoldierType();}
		private SoldierType light() {armor = Armor.LIGHT; return this;}
		private SoldierType heavy() {armor = Armor.HEAVY; return this;}
		private SoldierType foot() {mount = Mount.INFANTRY; return this;}
		private SoldierType mounted() {mount = Mount.CAVALRY; return this;}
		private SoldierType melee() {range = Range.MELEE; return this;}
		private SoldierType missile() {range = Range.MISSILE; return this;}
		private SoldierType gun() {range = Range.MISSILE; edge = Edge.BLUNT; return this;}
		private SoldierType bow() {range = Range.MISSILE; edge = Edge.SHARP; return this;}
		
		public boolean canFulfill(Clan clan) {
			if (Armor.HEAVY == armor && clan.getAssets(Misc.armor) <= 0) {return false;}
			if (Mount.CAVALRY == mount && clan.getAssets(Misc.lobodonkey) <= 0) {return false;}
			if (range != null) { //if you dont care about range then we assume you dont care about edge either..
				if (edge == null) {
					if (Range.MISSILE == range && (clan.getAssets(Misc.bow) <= 0 && clan.getAssets(Misc.gun) <= 0)) {return false;}
					if (Range.MELEE == range && (clan.getAssets(Misc.sword) <= 0 && clan.getAssets(Misc.mace) <= 0 && clan.getAssets(Misc.xweapon) <= 0)) {return false;}
				}
				else if (edge == Edge.BLUNT && clan.getAssets(Misc.gun) <= 0) {return false;}
				else if (edge == Edge.SHARP && clan.getAssets(Misc.bow) <= 0) {return false;}
			}
			return true;
		}
		
	}
	
	public static final SoldierType DONKEY_KNIGHT = SoldierType.create().heavy().mounted().melee();
	public static final SoldierType CHARGERS = SoldierType.create().mounted().melee();
	public static final SoldierType MISSILE_CAVALRY = SoldierType.create().mounted().missile();
	public static final SoldierType CAVALRY = SoldierType.create().mounted();
	public static final SoldierType MISSILE = SoldierType.create().missile();
	public static final SoldierType PHALANX = SoldierType.create().heavy().foot().melee();
	public static final SoldierType FODDER = SoldierType.create().light().foot();
	public static final SoldierType FOOT = SoldierType.create().foot();
	public static final SoldierType ARCHERS = SoldierType.create().bow();
	public static final SoldierType GUNNERS = SoldierType.create().gun();
	

	public static enum Armor {LIGHT, HEAVY}
	public static enum Edge {BLUNT, SHARP}
	public static enum Range {MELEE, MISSILE}
	public static enum Mount {INFANTRY, CAVALRY}
	
	static class BattleStats {
		int archery, charge, armor, piercing, strength;
		
		private static void charge(BattleStats a, BattleStats b) {
			if (a.charge >= b.charge) {
				a.charge -= b.charge; b.charge = 0;
				if (a.charge > b.archery) {
					a.charge -= b.archery; b.archery = 0;
					b.strength -= a.charge; a.charge = 0;
				}
				else {b.archery -= a.charge; a.charge = 0;}
			}
			else {charge(b, a);}
		}
		private static void volley(BattleStats a, BattleStats b) {
			if (a.archery >= b.archery) {
				a.archery -= b.archery; b.archery = 0;
				b.strength -= a.archery; a.archery = 0;
			}
			else {volley(b, a);}
		}
		static boolean attackerWinsExchange(BattleStats attacker, BattleStats defender) {
			charge(attacker, defender);
			volley(attacker, defender);
			attacker.strength += Math.max(attacker.armor - defender.piercing, 0);
			defender.strength += Math.max(defender.armor - attacker.piercing, 0);
			return (attacker.strength > defender.strength);
		}
		
		void computeFriendly(Warrior warrior) {
			if (warrior.range == Range.MISSILE) {archery += warrior.getMarksmanship();}
			else if (warrior.mount == Mount.CAVALRY) {charge += 6;} //not for mounted archers
			if (warrior.edge == Edge.BLUNT) {piercing += warrior.getStrength();}
			if (warrior.armor == Armor.HEAVY) {armor += warrior.getStrength();}
			strength += ((warrior.range == Range.MELEE ? 2 : 1) + (warrior.edge == Edge.SHARP ? 2 : 1)) *
					(warrior.mount == Mount.CAVALRY ? 2 : 1) * warrior.getProwess();
		}
	}
	
}
