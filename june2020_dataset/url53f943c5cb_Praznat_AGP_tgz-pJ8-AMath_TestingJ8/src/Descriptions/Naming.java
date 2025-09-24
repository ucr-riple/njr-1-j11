package Descriptions;
import java.awt.Color;

import Defs.*;
import Game.*;
import Sentiens.Clan;

public class Naming implements Misc {
	
	public static String randShireName(int s) {
		String FP[] = new String[AGPmain.shireFPLen];
		String LP[] = new String[AGPmain.shireLPLen];

		FP[0] = "Gloom";
		FP[1] = "Dark";
		FP[2] = "Spore";
		FP[3] = "Sloth";
		FP[4] = "Tall";
		FP[5] = "Charm";
		FP[6] = "Mud";
		FP[7] = "Beam";
		FP[8] = "Ghoul";
		FP[9] = "Shadow";
		FP[10] = "Doom";
		FP[11] = "Scorn";
		FP[12] = "Glam";
		FP[13] = "Fear";
		FP[14] = "Fever";
		FP[15] = "Scalp";
		FP[16] = "Trunk";
		FP[17] = "Calm";
		FP[18] = "Flat";
		FP[19] = "Mule";
		FP[20] = "Storm";
		FP[21] = "Thorn";
		FP[22] = "Bleed";
		FP[23] = "Glare";
		FP[24] = "Hinter";
		FP[25] = "Silver";
		FP[26] = "Swine";
		FP[27] = "Mustard";
		FP[28] = "Zoo";
		FP[29] = "Wallow";
		FP[30] = "Rogue";
		FP[31] = "Crumb";
		FP[32] = "Never";
		FP[33] = "Morrow";
		FP[34] = "Nose";
		FP[35] = "Yester";
		FP[36] = "Righteous";
		FP[37] = "Low";
		FP[38] = "Random";
		FP[39] = "Stumble";
		FP[40] = "Jam";
		FP[41] = "Iron";
		FP[42] = "Spleen";
		
		LP[0] = "bank";
		LP[1] = "beach";
		LP[2] = "berg";
		LP[3] = "borough";
		LP[4] = "bridge";
		LP[5] = "brook";
		LP[6] = "burn";
		LP[7] = "bury";
		LP[8] = "bush";
		LP[9] = "by";
		LP[10] = "castle";
		LP[11] = "cliff";
		LP[12] = "cot";
		LP[13] = "court";
		LP[14] = "cove";
		LP[15] = "creek";
		LP[16] = "cross";
		LP[17] = "dale";
		LP[18] = "den";
		LP[19] = "field";
		LP[20] = "fold";
		LP[21] = "fork";
		LP[22] = "fort";
		LP[23] = "garden";
		LP[24] = "gate";
		LP[25] = "glade";
		LP[26] = "grave";
		LP[27] = "grove";
		LP[28] = "hall";
		LP[29] = "ham";
		LP[30] = "head";
		LP[31] = "hill";
		LP[32] = "hood";
		LP[33] = "lake";
		LP[34] = "land";
		LP[35] = "manor";
		LP[36] = "minster";
		LP[37] = "mound";
		LP[38] = "mont";
		LP[39] = "mouth";
		LP[40] = "pond";
		LP[41] = "pool";
		LP[42] = "port";
		LP[43] = "rock";
		LP[44] = "saltar";
		LP[45] = "shire";
		LP[46] = "shrine";
		LP[47] = "shul";
		LP[48] = "side";
		LP[49] = "spire";
		LP[50] = "stable";
		LP[51] = "stead";
		LP[52] = "stone";
		LP[53] = "tomb";
		LP[54] = "town";
		LP[55] = "top";
		LP[56] = "tower";
		LP[57] = "tree";
		LP[58] = "view";
		LP[59] = "ville";
		LP[60] = "peak";
		LP[61] = "stream";
		LP[62] = "river";
		LP[63] = "water";
		LP[64] = "well";
		LP[65] = "wick";
		LP[66] = "wood";
		LP[67] = "worth";
		LP[68] = "dam";
		LP[69] = "gard";
		LP[70] = "crust";
		LP[71] = "crater";
		LP[72] = "vale";
		//System.out.print(s  + " " + ((s / (AGPmain.shireLPLen-1)) % (AGPmain.shireFPLen-1)) + " " + (s % (AGPmain.shireLPLen-1)));
		s = AGPmain.SR[s];
		//System.out.print(" " + s  + " " + ((s / (AGPmain.shireLPLen-1)) % (AGPmain.shireFPLen-1)) + " " + (s % (AGPmain.shireLPLen-1)));
		int fp = ((s / (AGPmain.shireLPLen-1)) % (AGPmain.shireFPLen-1));
		int lp = (s % (AGPmain.shireLPLen-1));
		//System.out.println(" " + (FP[fp]+LP[lp]));
		return FP[fp]+LP[lp];
	}

	public static String randGoblinName() {
		
		return "Blount";
	}

	
	
	

	public static String randGoblinSanctityName(int[] p) {
		int numFP = 47; int numMP = 64; int numLP = 64;
		int[] N = {0, p[1], p[2]};
		String[] FP = new String[numFP];
//		String ot = " of the ";
		String[] MP = new String[numMP];
		String[] LP = new String[numLP];
		
		FP[0] = "Book";
		FP[1] = "Books";
		FP[2] = "Tome";
		FP[3] = "Tomes";
		FP[4] = "Scroll";
		FP[5] = "Scrolls";
		FP[6] = "Road";
		FP[7] = "Path";
		FP[8] = "Voice";
		FP[9] = "Word";
		FP[10] = "Image";
		FP[11] = "Vision";
		FP[12] = "Prophecy";
		FP[13] = "Philosophy";
		FP[14] = "Promise";
		FP[15] = "Covenant";
		FP[16] = "Song";
		FP[17] = "Poem";
		FP[18] = "Epic";
		FP[19] = "Way";
		FP[20] = "Message";
		FP[21] = "Messenger";
		FP[22] = "Son";
		FP[23] = "Sons";
		FP[24] = "Servant";
		FP[25] = "Daughter";
		FP[26] = "Daughters";
		FP[27] = "God";
		FP[28] = "Gods";
		FP[29] = "King";
		FP[30] = "Kings";
		FP[31] = "Queen";
		FP[32] = "Queens";
		FP[33] = "Emperor";
		FP[34] = "Emperors";
		FP[35] = "Empress";
		FP[36] = "Empresses";
		FP[37] = "Spirit";
		FP[38] = "Spirits";
		FP[39] = "Teachings";
		FP[40] = "Postulate";
		FP[41] = "Doctrine";
		FP[42] = "Theory";
		FP[43] = "Cult";
		FP[44] = "Banner";
		FP[45] = "Sign";
		FP[46] = "Emblem";

		
		MP[0] = "Holy";
		MP[1] = "People's";
		MP[2] = "Rising";
		MP[3] = "Fallen";
		MP[4] = "Dark";
		MP[5] = "Shadowy";
		MP[6] = "Glorious";
		MP[7] = "Magnificent";
		MP[8] = "Mighty";
		MP[9] = "Shining";
		MP[10] = "Unrecognizable";
		MP[11] = "Gallant";
		MP[12] = "Monstrous";
		MP[13] = "Demonic";
		MP[14] = "Unfathomable";
		MP[15] = "All-knowing";
		MP[16] = "Unrepenting";
		MP[17] = "Burning";
		MP[18] = "Frozen";
		MP[19] = "Intangible";
		MP[20] = "Notorious";
		MP[21] = "Immortal";
		MP[22] = "Agonizing";
		MP[23] = "Oppressed";
		MP[24] = "Ponderous";
		MP[25] = "Miserly";
		MP[26] = "Depraved";
		MP[27] = "Disturbing";
		MP[28] = "Golden";
		MP[29] = "Silver";
		MP[30] = "Bombastic";
		MP[31] = "Arbitrary";
		MP[32] = "Moronic";
		MP[33] = "Conspicuous";
		MP[34] = "Miraculous";
		MP[35] = "Contradictory";
		MP[36] = "Creeping";
		MP[37] = "Epic";
		MP[38] = "Eternal";
		MP[39] = "Celestial";
		MP[40] = "Bellowing";
		MP[41] = "Damp";
		MP[42] = "Ephemeral";
		MP[43] = "Dubious";
		MP[44] = "Unjust";
		MP[45] = "Dishonorable";
		MP[46] = "Majestic";
		MP[47] = "Sweaty";
		MP[48] = "Grotesque";
		MP[49] = "Rotting";
		MP[50] = "Sticky";
		MP[51] = "Fearsome";
		MP[52] = "Spastic";
		MP[53] = "Spongy";
		MP[54] = "Globulous";
		MP[55] = "Ominous";
		MP[56] = "Profane";
		MP[57] = "Twisted";
		MP[58] = "Dirty";
		MP[59] = "Infested";
		MP[60] = "Unholy";
		MP[61] = "Chosen";
		MP[62] = "Savory";
		MP[63] = "Profitable";
		
		LP[0] = "Savior";
		LP[1] = "Martyr";
		LP[2] = "Giant";
		LP[3] = "Orb";
		LP[4] = "Swords";
		LP[5] = "Spear";
		LP[6] = "Bovid";
		LP[7] = "Mace";
		LP[8] = "Hammer";
		LP[9] = "Mountains";
		LP[10] = "Sickle";
		LP[11] = "Ancestors";
		LP[12] = "Meat";
		LP[13] = "Finger";
		LP[14] = "Donkey";
		LP[15] = "Sloth";
		LP[16] = "Thundercloud";
		LP[17] = "Sun";
		LP[18] = "Kingdom";
		LP[19] = "Plague";
		LP[20] = "Booger";
		LP[21] = "Heavens";
		LP[22] = "Underworld";
		LP[23] = "Liver";
		LP[24] = "Mind";
		LP[25] = "Face";
		LP[26] = "Shield";
		LP[27] = "Arrow";
		LP[28] = "Mob";
		LP[29] = "Entity";
		LP[30] = "Yonder";
		LP[31] = "Mist";
		LP[32] = "Unknown";
		LP[33] = "Depths";
		LP[34] = "Wilderness";
		LP[35] = "Calling";
		LP[36] = "Covenant";
		LP[37] = "Epic";
		LP[38] = "Palace";
		LP[39] = "Gloom";
		LP[40] = "Doom";
		LP[41] = "Salvation";
		LP[42] = "Awesomeness";
		LP[43] = "Loogy";
		LP[44] = "Letter";
		LP[45] = "Grandeur";
		LP[46] = "Starvation";
		LP[47] = "Turbans";
		LP[48] = "Blunder";
		LP[49] = "Island";
		LP[50] = "Sea";
		LP[51] = "Desert";
		LP[52] = "Plains";
		LP[53] = "Jungle";
		LP[54] = "Caves";
		LP[55] = "Buffoon";
		LP[56] = "Icon";
		LP[57] = "Defenestration";
		LP[58] = "Toenail";
		LP[59] = "Glutton";
		LP[60] = "Sensation";
		LP[61] = "Affliction";
		LP[62] = "Master";
		LP[63] = "Fungus";

		return MP[N[1]]+" "+LP[N[2]];
		//return FP[N[0]]+ot+MP[N[1]]+" "+LP[N[2]];
	}
	
	public static int[] sanctColor(int p) {
		int num = 64;
		int N = p;
		Color[] FC = new Color[num];
		Color[] BC = new Color[num];
		
		final Color pblack = new Color(0,0,0);
		//final Color dblue = new Color(0,0,127);
		//final Color pcyan = new Color(0,255,255);
		final Color cerulean = new Color(0,127,255);
		//final Color glowindark = new Color(0,255,127);

		//final Color dred = new Color(127,0,0);
		final Color pgrey = new Color(127,127,127);
		final Color gobskin = new Color(127,0,255);
		final Color singmiel = new Color(127,255,0);
		//final Color lblue = new Color(127,127,255);
		
		final Color pred = new Color(255,0,0);
		//final Color lred = new Color(255,127,127);
		//final Color porange = new Color(255,127,0);
		final Color pwhite = new Color(255,255,255);
		final Color pyellow = new Color(255,255,0);

		final Color pbrown = new Color(127, 63, 31);
		
		int i = -1;
		
		i++; FC[i] = pred; BC[i] = pwhite;  //G		MP[0] = "Holy";
		i++; FC[i] = pyellow; BC[i] = pred;  //G		MP[1] = "People's";
		i++; FC[i] = pblack; BC[i] = cerulean;  //G		MP[2] = "Rising";
		i++; FC[i] = pred; BC[i] = pblack;  //G		MP[3] = "Fallen";
		i++; FC[i] = pgrey; BC[i] = pblack;  //G		MP[4] = "Dark";
		i++; FC[i] = gobskin; BC[i] = pblack;  //G		MP[5] = "Shadowy";
		i++; FC[i] = gobskin; BC[i] = pwhite;  //G		MP[6] = "Glorious";
		i++; FC[i] = cerulean; BC[i] = pred;  //G		MP[7] = "Magnificent";
		i++; FC[i] = pblack; BC[i] = gobskin;  //G		MP[8] = "Mighty";
		i++; FC[i] = singmiel; BC[i] = pyellow;  //G		MP[9] = "Shining";
		i++; FC[i] = pgrey; BC[i] = pbrown;  //G		MP[10] = "Unrecognizable";
		i++; FC[i] = pred; BC[i] = cerulean;  //G		MP[11] = "Gallant";
		i++; FC[i] = singmiel; BC[i] = pblack;  //G		MP[12] = "Monstrous";
		i++; FC[i] = pblack; BC[i] = pred;  //G		MP[13] = "Demonic";
		i++; FC[i] = pwhite; BC[i] = pblack;  //G		MP[14] = "Unfathomable";
		i++; FC[i] = pgrey; BC[i] = pyellow;  //G		MP[15] = "All-knowing";
		i++; FC[i] = pbrown; BC[i] = gobskin;  //G		MP[16] = "Unrepenting";
		i++; FC[i] = pred; BC[i] = pyellow;  //G		MP[17] = "Burning";
		i++; FC[i] = cerulean; BC[i] = pwhite;  //G		MP[18] = "Frozen";
		i++; FC[i] = pwhite; BC[i] = singmiel;  //G		MP[19] = "Intangible";
		i++; FC[i] = pbrown; BC[i] = pgrey;  //G		MP[20] = "Notorious";
		i++; FC[i] = pgrey; BC[i] = pwhite;  //G		MP[21] = "Immortal";
		i++; FC[i] = pred; BC[i] = pgrey;  //G		MP[22] = "Red";
		i++; FC[i] = cerulean; BC[i] = pgrey;  //G		MP[23] = "Blue";
		i++; FC[i] = pblack; BC[i] = pgrey;  //G		MP[24] = "Black";
		i++; FC[i] = pwhite; BC[i] = pgrey;  //G		MP[25] = "White";
		i++; FC[i] = gobskin; BC[i] = pgrey;  //G		MP[26] = "Depraved";
		i++; FC[i] = pyellow; BC[i] = pgrey;  //G		MP[27] = "Yellow";
		i++; FC[i] = pyellow; BC[i] = pwhite;  //G		MP[28] = "Golden";
		i++; FC[i] = pwhite; BC[i] = cerulean;  //G		MP[29] = "Silver";
		i++; FC[i] = pwhite; BC[i] = pred;  //G		MP[30] = "Bombastic";
		i++; FC[i] = gobskin; BC[i] = pred;  //G		MP[31] = "Arbitrary";
		i++; FC[i] = pred; BC[i] = singmiel;  //G		MP[32] = "Moronic";
		i++; FC[i] = pblack; BC[i] = pyellow;  //G		MP[33] = "Conspicious";
		i++; FC[i] = gobskin; BC[i] = cerulean;  //G		MP[34] = "Miraculous";
		i++; FC[i] = pbrown; BC[i] = pred;  //G		MP[35] = "Contradictory";
		i++; FC[i] = pbrown; BC[i] = pblack;  //G		MP[36] = "Creeping";
		i++; FC[i] = pblack; BC[i] = pwhite;  //G		MP[37] = "Epic";
		i++; FC[i] = pgrey; BC[i] = cerulean;  //G		MP[38] = "Eternal";
		i++; FC[i] = pyellow; BC[i] = cerulean;  //G		MP[39] = "Celestial";
		i++; FC[i] = pblack; BC[i] = pbrown;  //G		MP[40] = "Bellowing";
		i++; FC[i] = cerulean; BC[i] = singmiel;  //G		MP[41] = "Damp";
		i++; FC[i] = singmiel; BC[i] = pgrey;  //G		MP[42] = "Ephemeral";
		i++; FC[i] = pgrey; BC[i] = gobskin;  //G		MP[43] = "Dubious";
		i++; FC[i] = pred; BC[i] = gobskin;  //G		MP[44] = "Unjust";
		i++; FC[i] = pyellow; BC[i] = gobskin;  //G		MP[45] = "Dishonorable";
		i++; FC[i] = gobskin; BC[i] = pyellow;  //G		MP[46] = "Majestic";
		i++; FC[i] = cerulean; BC[i] = pbrown;  //G		MP[47] = "Sweaty";
		i++; FC[i] = gobskin; BC[i] = pbrown;  //G		MP[48] = "Grotesque";
		i++; FC[i] = singmiel; BC[i] = pbrown;  //G		MP[49] = "Rotting";
		i++; FC[i] = pbrown; BC[i] = pyellow;  //G		MP[50] = "Sticky";
		i++; FC[i] = pgrey; BC[i] = pred;  //G		MP[51] = "Fearsome";
		i++; FC[i] = pwhite; BC[i] = gobskin;  //G		MP[52] = "Spastic";
		i++; FC[i] = pyellow; BC[i] = singmiel;  //G		MP[53] = "Spongey";
		i++; FC[i] = pbrown; BC[i] = cerulean;  //G		MP[54] = "Globulous";
		i++; FC[i] = cerulean; BC[i] = pblack;  //G		MP[55] = "Ominous";
		i++; FC[i] = singmiel; BC[i] = gobskin;  //G		MP[56] = "Profane";
		i++; FC[i] = cerulean; BC[i] = pyellow;  //G		MP[57] = "Twisted";
		i++; FC[i] = pwhite; BC[i] = pbrown;   //G		MP[58] = "Dirty";
		i++; FC[i] = singmiel; BC[i] = pwhite;  //G		MP[59] = "Infested";
		i++; FC[i] = gobskin; BC[i] = singmiel;  //G		MP[60] = "Unholy";
		i++; FC[i] = pblack; BC[i] = singmiel;  //G		MP[61] = "Chosen";
		i++; FC[i] = pyellow; BC[i] = pbrown;  //G		MP[62] = "Savory";
		i++; FC[i] = pyellow; BC[i] = singmiel;    //G		MP[63] = "Profitable";
		
		//i++; FC[i] = pbrown; BC[i] = pwhite;  
		//i++; FC[i] = pred; BC[i] = pbrown;  
		//i++; FC[i] = pgrey; BC[i] = singmiel;  
		//i++; FC[i] = singmiel; BC[i] = pred;    
		//i++; FC[i] = pyellow; BC[i] = pblack;
		//i++; FC[i] = pwhite; BC[i] = pyellow; 
		//i++; FC[i] = singmiel; BC[i] = cerulean;
		//i++; FC[i] = cerulean; BC[i] = gobskin;  
		return new int[] {FC[N].getRGB(), BC[N].getRGB()};
	}

	public static String prestName(P_ p) {
		switch(p) {
//			case HUNTING : return "Tracking";
//			case GATHERING : return "Foraging";
//			case FARMING : return "Farming";
			case STRENGTH : return "Strength";
//			case HERDING : return "Herding";
			case MARKSMANSHIP : return "Marksmanship";
			case MASONRY : return "Masonry";
			case ARTISTRY : return "Artistry";
			case PROSE : return "Prose";
			case CARPENTRY: return "Carpentry";
			case SMITHING : return "Blacksmithing";
			case LOBOTOMY : return "Lobotomy";
			case TRADEP : return "Trading";
			case INVESTP : return "Investing";
			case ARITHMETIC : return "Arithmetic";
			case STRATEGYP : return "Strategy";
			case TACTICSP : return "Tactics";
			case COMBAT : return "Fighting Ability";
			case BATTLEP : return "Military Success";
			case DANCE : return "Ritual Dance";
			case ARMORYP : return "Arms Lore";
			case WPREDICTION : return "Divination";
			case MPREDICTION : return "Sorcery";
			case CONFP : return "Self-Confidence";
			case RSPCP : return "Popularity";
			case TYRRP : return "Tyranny";
			case SEXP : return "Sexual Prowess";
			case PREACHP : return "Evangelism";
			case HEALP : return "Medicine";
			default : return "";
		}
	}

	public static String goodName(int p) {
		return goodName(p, false, false);
	}
	public static String goodName(int p, boolean plural, boolean abbr) {
		String s = (plural ? "s" : "");
		if(p==jewelry) s = (plural ? "ies" : "y");
		else if(p==gun) s = (plural ? "es" : "");
		switch(p) {
			case millet : return (abbr ? "Millet" : "clump" + s + " of millet");
			case land : return (abbr ? "Land" : "plot" + s + " of land");
			case meat : return (abbr ? "Meat" : "glob" + s + " of meat");
			case bovad : return (abbr ? "Bovids" : "bovid" + s);
			case donkey : return (abbr ? "Donkeys" : "donkey" + s);
			case lobodonkey : return (abbr ? "L.Donkeys" : "lobotomized donkey" + s);
			case jewelry : return (abbr ? "Jewelry" : "silver accessor" + s);
			case armor : return (abbr ? "Armor" : "suit" + s + " of armor");
			case sword : return (abbr ? "Blades" : "blade" + s);
			case mace : return (abbr ? "Blunts" : "blunt" + s);
			case xweapon : return (abbr ? "Weapon" : "weapon" + s);
			case bow : return (abbr ? "Crossbows" : "crossbow" + s);
			case gun : return (abbr ? "Arquebuses" : "arquebus" + s);
			case captive : return (abbr ? "Captives" : "captive" + s);
			case rentland : return (abbr ? "(R)Land" : "rented plot" + s + " of land");
			case rentanimal : return (abbr ? "(R)Animals" : "rented animal" + s);
			case timber : return (abbr ? "Lumber" : "stack" + s + " of lumber");
			case stone : return (abbr ? "Stone" : "large rock" + s);
			case iron : return (abbr ? "Iron Ore" : "chunk" + s + " of iron ore");
			case silver : return (abbr ? "Silver Ore" : "chunk" + s + " of silver ore");
			case poop : return (abbr ? "Fertilizer" : "pile" + s + " of excrement");
			case constr : return (abbr ? "Construction" : "ton" + s + " of construction");
			case wconstr : return (abbr ? "Wooden Blocks" : "block" + s + " of wooden material");
			case sconstr : return (abbr ? "Stone Blocks" : "block" + s + " of stone material");
			default : return "";
		}
	}
	
	public static String possessive(Clan sub) {return (sub.getGender() == FEMALE ? "her" : "his");}
	

	
	
}
