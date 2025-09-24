package Descriptions;

import AMath.Calc;
import Defs.*;
import Game.Job;
import Ideology.*;
import Sentiens.*;

public class GobName {
	
	private static final String[] SYL1 = {
		"A",
		"Aha",
		"Ba",
		"Bee",
		"Bla",
		"Blee",
		"Blo",
		"Blou",
		"Bloo",
		"Blu",
		"Bo",
		"Booba",
		"Bu",
		"Bwa",
		"Bwee",
		"Cha",
		"Chee",
		"Cho",
		"Da",
		"Dee",
		"Doo",
		"Du",
		"Dwa",
		"Dwee",
		"Er",
		"Ehe",
		"Fla",
		"Flee",
		"Flo",
		"Flou",
		"Fna",
		"Fnee",
		"Fnoo",
		"Fwa",
		"Fwee",
		"Gla",
		"Glee",
		"Glo",
		"Glu",
		"Ghee",
		"Gho",
		"Gree",
		"Gwa",
		"Gwee",
		"Hi",
		"Ho",
		"Ja",
		"Jo",
		"Kli",
		"Kra",
		"Kree",
		"Ma",
		"Mee",
		"Mo",
		"Na",
		"Nee",
		"No",
		"O",
		"Oho",
		"Pra",
		"Proo",
		"Pee",
		"Poko",
		"Poo",
		"Plee",
		"Ploo",
		"Qua",
		"Quee",
		"Ree",
		"Roo",
		"Ru",
		"Sa",
		"Shee",
		"Soo",
		"Sma",
		"Smee",
		"Smo",
		"Spa",
		"Thar",
		"Tho",
		"Too",
		"Twa",
		"Twee",
		"Vee",
		"Wa",
		"Wee",
		"Ya",
		"Yee",
		"Yo",
		"Zar",
		"Zaza",
		"Zna",
		"Znee",
		"Zno"
	};

	private static final BiName[] SYL2 = {
		new BiName("bar"),
		new BiName("bax"),
		new BiName("ber"),
		new BiName("ble"),
		new BiName("bo"),
		new BiName("bonk"),
		new BiName("bor"),
		new BiName("bod"),
		new BiName("cka"),
		new BiName("ckle"),
		new BiName("kn", "ckoo"),
		new BiName("cky"),
		new BiName("dle"),
		new BiName("dd"),
		new BiName("do"),
		new BiName("dor"),
		new BiName("duh"),
		new BiName("dwan"),
		new BiName("ga"),
		new BiName("gar"),
		new BiName("gle"),
		new BiName("gump"),
		new BiName("ham"),
		new BiName("ho"),
		new BiName("honk"),
		new BiName("ja"),
		new BiName("jack"),
		new BiName("kan"),
		new BiName("ll"),
		new BiName("lm"),
		new BiName("mm"),
		new BiName("mak"),
		new BiName("mnak"),
		new BiName("mo"),
		new BiName("mok"),
		new BiName("mor"),
		new BiName("mple"),
		new BiName("nn"),
		new BiName("nad"),
		new BiName("nax"),
		new BiName("njarm"),
		new BiName("nk"),
		new BiName("pan"),
		new BiName("pat"),
		new BiName("per"),
		new BiName("ppy"),
		new BiName("qu", "quod"),
		new BiName("snax"),
		new BiName("spat"),
		new BiName("th"),
		new BiName("tmo"),
		new BiName("ton"),
		new BiName("twax"),
		new BiName("twod"),
		new BiName("ty"),
		new BiName("nt"),
		new BiName("vap"),
		new BiName("x"),
		new BiName("xum"),
		new BiName("xy"),
		new BiName("zle"),
		new BiName("znat"),
		new BiName("znax"),
		new BiName("zz"),
		new BiName("r", "re"),
		new BiName("t", "tch"),
		new BiName("the", "theus"),
		new BiName("li" , "dlus"),
		new BiName("f" , "fus"),
		new BiName("ck" , "cus"),
		new BiName("rc" , "rcus"),
		new BiName("xi" , "xus"),
		new BiName("n" , "nus"),
		new BiName("m" ,"mus"),
		new BiName("lb" , "lbus"),
		new BiName("t" , "tus")
	};
	
	public static String firstName(byte fn, byte ln, boolean gender) {
		int[] N = {Calc.squeezeByte(fn, 0, SYL1.length),Calc.squeezeByte(ln, 0, SYL2.length)};

		String p1 = SYL1[N[0]], p2 = SYL2[N[1]].get(gender);
		
		String suffix = "";
		if (gender == Misc.FEMALE) {
			switch ((fn * ln) % 3) {
			case 0: suffix += "a"; break;
			case 1: suffix += "i"; break;
			case 2: suffix += "et"; break;
			case -1: suffix += "el"; break;
			case -2: suffix += "ra"; break;
			default: break;
			}
		}
		
		return p1 + p2 + suffix;
	}
	
	private static class BiName {
		private final String n1, n2;
		BiName(String s) {n1=s; n2=s;}
		BiName(String s1, String s2) {n1=s1; n2=s2;}
		protected String get(boolean gender) {return gender == Misc.FEMALE ? n1 : n2;}
	}
	
	public static String fullName(Clan goblin) {return fullName(goblin, goblin.getFirstName(), 0);}
	private static String fullName(Clan goblin, String N, int i) {
		if (i >= 5) {return N;}
		Value V = goblin.FB.getValue(i);
		if (V == Values.ALLEGIANCE || V == Values.WEALTH || V == Values.MIGHT || V == Values.INFLUENCE || V == Values.BEAUTY) {
			String title = (goblin.myOrder() == null ? "" : goblin.myOrder().getTitle(goblin));
			if (title != "") {
				N = title + " " + N;
				if (goblin.getJob() == Job.HUNTERGATHERER) {return N + " the Barbarian";}
				P_ bestP = highestPrest(goblin);
				if (bestP == null || goblin.FB.getPrs(bestP) != 15) {return N;}
				switch (bestP) {
				case TYRRP: return N + " the " + tyrantWord(goblin.getID(), goblin.FB.getBeh(M_.BLOODLUST) > 11);
				case STRENGTH: return N + " the Mighty";
				case RSPCP: return N + " the Great";
				default: return N;
				}
			}
			else {return fullName(goblin, N, i+1);}
		}
		
		return fullName(goblin, N, i+1);
	}
	
	private static String tyrantWord(int i, boolean bloody) {
		switch (i % 6) {
		case 0: return (bloody ? "Merciless" : "Conqueror");
		case 1: return (bloody ? "Cruel" : "Unstoppable");
		case 2: return (bloody ? "Tyrant" : "Mountain");
		case 3: return (bloody ? "Terrible" : "Mighty");
		case 4: return (bloody ? "Wicked" : "Conqueror");
		default: return (bloody ? "Bloodthirsty" : "Vanquisher");
		}
	}
	private static P_ highestPrest(Clan goblin) {
		P_[] candidates = new P_[] {P_.TYRRP, P_.STRENGTH, P_.RSPCP};
		P_ highest = null;   int max = 10;   int b;
		for (P_ p : candidates) {b = goblin.FB.getPrs(p); if (goblin.FB.getPrs(p) > max) {max = b; highest = p;}}
		return highest;
	}
}
