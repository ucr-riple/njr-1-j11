package Descriptions;

import AMath.Calc;
import Defs.Misc;
import Game.AGPmain;

public class XWeapon {

	/**
	 * 
	 * 0123456789ABCDEF
	 * tkkvvnnnnnrr....
	 * 
	 */
	public static final short NULL = (16 << 6); //"Pacifier of Souls"
	
	public static short craftNewWeapon(int wtype, int skill) {
		short result = (short) (AGPmain.rand.nextInt() << 6);
		if (wtype == Misc.sword) {result = Calc.setBitPart(result, 0, 1, 1);}
		else if (wtype == Misc.mace) {result = Calc.setBitPart(result, 0, 1, 0);}
		int rarety = 0;
		final int denom = 11;
		for (int i = 0; i < 5; i++) {
			rarety += (AGPmain.rand.nextInt(skill + 1) > denom ? 1 : 0);
			//if (AGPmain.rand.nextInt(skill) > denom) {rarety++;} else {break;}
		}
		if (rarety < 2) {return NULL;} //failed to make famous weapon
		result += ((rarety-2) << 4);
		return result;
	}
	
	public static enum weaponType {
		MACHETE, BROADSWORD, RAPIER, AXE,
		MACE, HAMMER, FLAIL, CLUB
	}
	
	public static String weaponName(short W) {
		if (W == NULL) {return "none";}
		return skillLevel(W) + weaponType(W).name() + " '" + weaponName1(W) + " of " + weaponName2(W) + "'";
	}
	private static String skillLevel(short W) {
		int s = Calc.bitPart(W, 10, 2);
		switch(s) {
		case 0: return "Fine ";
		case 1: return "Famous ";
		case 2: return "Epic ";
		case 3: return "Epicest of Epic ";
		default: return "";
		}
	}
	private static weaponType weaponType(short W) {
		int c = Calc.bitPart(W, 0, 1);
		int k = Calc.bitPart(W, 1, 2);
		switch (c) {
		case 0:
			switch (k) {
			case 0: return weaponType.CLUB;
			case 1: return weaponType.HAMMER;
			case 2: return weaponType.FLAIL;
			case 3: return weaponType.MACE;
			}
		case 1: 
			switch (k) {
			case 0: return weaponType.MACHETE;
			case 1: return weaponType.BROADSWORD;
			case 2: return weaponType.RAPIER;
			case 3: return weaponType.AXE;
			}
		default: return null;
		}
	}
	private static String weaponName1(short W) {
		weaponType type = weaponType(W);
		int v = Calc.bitPart(W, 3, 2);
		if (type == weaponType.MACHETE) {
			switch (v) {
			case 0: return "Reducer";
			case 1: return "Hacker";
			case 2: return "Defiler";
			case 3: return "Tormentor";
			}
		}
		if (type == weaponType.BROADSWORD) {
			switch (v) {
			case 0: return "Challenger";
			case 1: return "Punisher";
			case 2: return "Condemner";
			case 3: return "Conqueror";
			}
		}
		if (type == weaponType.RAPIER) {
			switch (v) {
			case 0: return "Torturer";
			case 1: return "Impaler";
			case 2: return "Ravager";
			case 3: return "Devourer";
			}
		}
		if (type == weaponType.AXE) {
			switch (v) {
			case 0: return "Upsetter";
			case 1: return "Cleaver";
			case 2: return "Dismemberer";
			case 3: return "Decimator";
			}
		}
		if (type == weaponType.MACE) {
			switch (v) {
			case 0: return "Scourge";
			case 1: return "Banisher";
			case 2: return "Mutilator";
			case 3: return "Annihilator";
			}
		}
		if (type == weaponType.HAMMER) {
			switch (v) {
			case 0: return "Purger";
			case 1: return "Impounder";
			case 2: return "Demolisher";
			case 3: return "Destroyer";
			}
		}
		if (type == weaponType.FLAIL) {
			switch (v) {
			case 0: return "Distorter";
			case 1: return "Thrasher";
			case 2: return "Horrifier";
			case 3: return "Nightmare";
			}
		}
		if (type == weaponType.CLUB) {
			switch (v) {
			case 0: return "Pacifier";
			case 1: return "Corrupter";
			case 2: return "Bastardizer";
			case 3: return "Consumer";
			}
		}
		return "";
	}
	private static String weaponName2(short W) {
		int v = Calc.bitPart(W, 5, 5);
		switch (v) {
		case 0: return "Insects";
		case 1: return "Donkeys";
		case 2: return "Sloths";
		case 3: return "the Weak";
		case 4: return "Cowards";
		case 5: return "the Unworthy";
		case 6: return "the Innocent";
		case 7: return "the Unsightly";
		case 8: return "the Insane";
		case 9: return "the Masses";
		case 10: return "the Untrue";
		case 11: return "Heathens";
		case 12: return "Enemies";
		case 13: return "Beasts";
		case 14: return "Generals";
		case 15: return "the Mighty";
		case 16: return "Souls";
		case 17: return "Kings";
		case 18: return "Truth";
		case 19: return "the Wilderness";
		case 20: return "Empires";
		case 21: return "Darkness";
		case 22: return "Realms";
		case 23: return "the Infernal";
		case 24: return "Demons";
		case 25: return "Evil";
		case 26: return "Worlds";
		case 27: return "the Heavens";
		case 28: return "Creation";
		case 29: return "All Matter";
		case 30: return "Gods";
		case 31: return "the Unknowable";
		}
		return "";
	}
	
	
}
