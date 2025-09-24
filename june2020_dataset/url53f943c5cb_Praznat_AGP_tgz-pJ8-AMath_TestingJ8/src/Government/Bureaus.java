package Government;

import Descriptions.*;
import Descriptions.NameVars.NameVar;

public class Bureaus {

	
	public static abstract class Bureau {
		public abstract NameVar topTitle(); 

	}
	public static final Bureau MILITARY = new Bureau() {
		public NameVar topTitle() {return NameVars.MINISTER_MILITARY;}
		//decides defense (and offense) strategy
		//recruits soldiers
		//fights wars
		//acts as police and guard as well
		//spreads military memes
	};
	public static final Bureau RELIGION = new Bureau() {
		public NameVar topTitle() {return NameVars.MINISTER_RELIGION;}
		//decides religious philosophy
		//recruits priests
		//conducts rituals
		//influences values
		//spreads religious memes
	};
	public static final Bureau ECONOMY = new Bureau() {
		public NameVar topTitle() {return NameVars.MINISTER_ECONOMY;}

		//spreads economic memes
	};
	public static final Bureau INTERIOR = new Bureau() {
		public NameVar topTitle() {return NameVars.MINISTER_INTERIOR;}
		//decides law defining who can be citizen (law-breakers forfeit citizenship)
		//recruits new citizens
		//assigns court and mayoral positions
		//persecutes non-citizens (enforces law)
	};
}
