package Defs;

public interface Misc {
	public static final String RET = System.getProperty("line.separator");
	public static final int E = Integer.MIN_VALUE;
	
//	public static final int numSancs = 21;

	public static final boolean FEMALE = true;
	public static final boolean MALE = false;
	
	//sancs
	public static final int DE = 0; 
	public static final int RX = 1;
	public static final int HL = 2; 
	public static final int NS = 3; 
	public static final int AG = 4;
	public static final int SZ = 5;
	public static final int WL = 6; 
	public static final int SH = 7; 
	public static final int SA = 8; 
	public static final int WP = 9; 
	public static final int MP = 10; 
	public static final int HS = 11; 
	public static final int FH = 12;
	public static final int BM = 13; 
	public static final int RT = 14;
	public static final int JB = 15; 
	public static final int SC = 16; 
	public static final int LH = 17; 
	public static final int EY = 18; 
	public static final int SS = 19; 
	public static final int TY = 20; 
	public static final int POWER = 0; 
	public static final int PLEASURE = POWER+1; 
	public static final int HONOR = PLEASURE+1; 
	public static final int WISDOM = HONOR+1; 

//	public static final int numPrests = TY+1;
	
	

	
	//prestiges
//	public static final int SANCP = 0;
//	public static final int STRENGTH = SANCP+1;
////	public static final int HUNTING = 2;
////	public static final int GATHERING = 3;
////	public static final int FARMING = 4;
////	public static final int HERDING = 5;
//	public static final int MARKSMANSHIP = STRENGTH+1;
//	public static final int MASONRY = MARKSMANSHIP+1;
//	public static final int ARTISTRY = MASONRY+1;
//	public static final int CARPENTRY = ARTISTRY+1;
//	public static final int SMITHING = CARPENTRY+1;
//	public static final int LOBOTOMY = SMITHING+1;
//	public static final int TRADEP = LOBOTOMY+1;
//	public static final int INVESTP = TRADEP+1;
//	public static final int ARITHMETICP = INVESTP+1;
//	public static final int STRATEGYP = ARITHMETICP+1;
//	public static final int TACTICSP = STRATEGYP+1;
//	public static final int MARTIALP = TACTICSP+1;
//	public static final int ARMORYP = MARTIALP+1;
//	public static final int WPREDICTION = ARMORYP+1;
//	public static final int MPREDICTION = WPREDICTION+1;
//	public static final int PREACHP = MPREDICTION+1;
//	public static final int RSPCP = PREACHP+1;
//	public static final int TYRRP = RSPCP+1;
//	public static final int CONFP = TYRRP+1;
//	public static final int SEXP = CONFP+1;
//	public static final int HEALP = SEXP+1;
	
	//mems
//	public static final int BIDASKSPRD = 1;
//	public static final int STMOMENTUM = BIDASKSPRD+1; // 0-7 reversion 8-15 momentum
//	public static final int LTMOMENTUM = STMOMENTUM+1; // 0-7 reversion 8-15 momentum
//	public static final int DISCRATE = LTMOMENTUM+1;
//	public static final int MARGIN = DISCRATE+1;
//	public static final int INVORTRD = MARGIN+1;
//	public static final int TECHNICAL = INVORTRD+1;
//	public static final int FLOW = TECHNICAL+1;
//
//	public static final int CONST1 = FLOW+1;
//	public static final int CONST2 = CONST1+1;
//	public static final int CONST3 = CONST2+1;
//	public static final int CONST4 = CONST3+1;
//	public static final int CONST5 = CONST4+1;
//	public static final int CONST6 = CONST5+1;
//	
//	public static final int ARM = CONST6+1;
//	public static final int MIS = ARM+1;
//	public static final int PRC = MIS+1;
//	public static final int CAV = PRC+1;
//	public static final int PYRAMIDALITY = CAV+1;
//	public static final int LEADERSHIP = PYRAMIDALITY+1;
//	public static final int MERITOCRACITY = LEADERSHIP+1;
//	
//
//
//	public static final int INDIVIDUALITY = MERITOCRACITY+1;
//	public static final int EMPIRICISM = INDIVIDUALITY+1;
//	public static final int JOVIALITY = EMPIRICISM+1;
//	public static final int TEMPER = JOVIALITY+1;   //used to decide when to explode in amygdala
//	public static final int ROMANTICNESS = TEMPER+1;
//	public static final int PROFANITY = ROMANTICNESS+1;
//	public static final int INTROSPECTION = PROFANITY+1;
//	public static final int RESPENV = INTROSPECTION+1;
//	public static final int BLOODLUST = RESPENV+1;
//	public static final int MADNESS = BLOODLUST+1; //innovation, creativity, randomness in behavior
//	public static final int HUMILITY = MADNESS+1;
//	public static final int PATIENCE = HUMILITY+1;
//	public static final int PARANOIA = PATIENCE+1;
//	public static final int STRICTNESS = PARANOIA+1;
//	public static final int SUPERST = STRICTNESS+1; // add/subtract this amount to secular prestige amt if ELE sanc prest >/< EU's
//	public static final int CONFIDENCE = SUPERST+1;
//	public static final int AGGR = CONFIDENCE+1;
//	public static final int MIERTE = AGGR+1;
//	public static final int WORKETHIC = MIERTE+1;
//	public static final int OCD = WORKETHIC+1; //neatness, like short vs long hair...
//	public static final int PROMISCUITY = OCD+1;
//	
//	//SANC MEMES
//	//Wealth&Power
//	public static final int S_LAND = PROMISCUITY+1;
//	public static final int S_ANIMALS = S_LAND+1;
//	public static final int S_VASSALS = S_ANIMALS+1;
//	public static final int S_POPULARITY = S_VASSALS+1;
//	public static final int S_WEAPONS = S_POPULARITY+1;
//	//S_TOT_N_MINIONS
//	//S_DIRECT_VASSAL_WEALTH
//	public static final int S_COMBAT = S_WEAPONS+1;
//	public static final int S_STRENGTH = S_COMBAT+1;
//	//Pleasure&Beauty
//	public static final int S_SILVER = S_STRENGTH+1;
//	public static final int S_MEAT = S_SILVER+1;
//	public static final int S_NOSELEN = S_MEAT+1;
//	public static final int S_EYESIZE = S_NOSELEN+1;
//	public static final int S_JAWWIDTH = S_EYESIZE+1;
//	public static final int S_HAIRLEN = S_JAWWIDTH+1;
//	//Honor&Glory
//	public static final int S_PATRIOTISM = S_HAIRLEN+1; //includes #fights/victories for homeland
//	public static final int S_LOYALTY = S_PATRIOTISM+1; //includes #fights/victories for king
//	public static final int S_ZEAL = S_LOYALTY+1;
//	public static final int S_PROMISCUITY = S_ZEAL+1;
//	public static final int S_GREED = S_PROMISCUITY+1;
//	public static final int S_BLOODLUST = S_GREED+1;
//	public static final int S_MONUMENTS = S_BLOODLUST+1;
//	//Knowledge&Wisdom
//	public static final int S_KNOWLEDGE = S_MONUMENTS+1;
//	public static final int S_ASACRIFICE = S_KNOWLEDGE+1;
//	public static final int S_HSACRIFICE = S_ASACRIFICE+1;
//	public static final int S_HEALING = S_HSACRIFICE+1;
//	public static final int S_SORCERY = S_HEALING+1;
//	public static final int S_STRATEGY = S_SORCERY+1;
//	public static final int S_TACTICS = S_STRATEGY+1;
//	public static final int S_AGE = S_TACTICS+1;
//	//All the above
//	public static final int S_ART = S_AGE+1;
//	
//
//	public static final int NOSERX = S_ART+1;    //beauty calc
//	public static final int NOSERY = NOSERX+1;
//	public static final int NOSELX = NOSERY+1;   //beauty calc
//	public static final int NOSELY = NOSELX+1;
//	public static final int NOSELW = NOSELY+1;
//	public static final int NOSEMW = NOSELW+1;
//	public static final int NOSERW = NOSEMW+1;
//	public static final int MOUTHBX = NOSERW+1;
//	public static final int MOUTHBY = MOUTHBX+1;
//	public static final int MOUTHP = MOUTHBY+1;
//	public static final int MOUTHC = MOUTHP+1;
//	public static final int MOUTHLH = MOUTHC+1;
//	public static final int MOUTHLW = MOUTHLH+1;
//	public static final int MOUTHJH = MOUTHLW+1;
//	public static final int MOUTHJW = MOUTHJH+1;    //beauty calc
//	public static final int EYERP = MOUTHJW+1; 
//	public static final int EYELP = EYERP+1;
//	public static final int EYERW = EYELP+1; 
//	public static final int EYELW = EYERW+1;     //beauty calc (LEFT AND RIGHT ARE POV OF GOBLIN!)
//	public static final int EYEHGT = EYELW+1;
//	public static final int EYESPRD = EYEHGT+1;
//	public static final int EARH = EYESPRD+1; 
//	public static final int EARW = EARH+1;
//	public static final int EART = EARW+1;
//	public static final int EARD = EART+1;
//	public static final int SKINR = EARD+1;
//	public static final int SKING = SKINR+1;
//	public static final int SKINB = SKING+1;
//	public static final int HAIRL = SKINB+1;    //beauty calc
//	public static final int HAIRC = HAIRL+1;
//	public static final int HAIRW = HAIRC+1;
//	public static final int HAIRR = HAIRW+1; 
//	public static final int HAIRG = HAIRR+1;
//	public static final int HAIRB = HAIRG+1;
//	public static final int HAIRX = HAIRB+1;
//	public static final int HAIRS = HAIRX+1;
	

//	public static final int numMems = HAIRS+1;
	
	
	//Quests
	public static final int BREED = 0;
	public static final int FINDMATE = 1;
	public static final int BUILDWEALTH = 2; //leads to work or steal
	public static final int COMPETE4MATE = 3;
	public static final int IDLE = 4;
	public static final int HUMANSAC = 5;
	public static final int ANIMALSAC = 6;
	public static final int HEALSICK = 7;
	public static final int FEEDPOOR = 8;
	public static final int CONQUER = 9;
	public static final int RAISEARMY = 10;
	public static final int ATTACK = 11;
	public static final int RECRUIT = 12;
	public static final int FINDALLY = 13;
	public static final int FINDMINION = 14;
	public static final int PREACH = 15;
	public static final int WORK = 16;
	public static final int DREAMJOB = 17;
	public static final int PDJOB = 18;
	public static final int FINDWEAKLING = 19;
	public static final int TERRORIZE = 20;
	public static final int TRADE = 21;
	public static final int FINDNEWJOB = 22;
	public static final int BUILDPOPULARITY = 23;
	

	public static final int TARGET = 1;
	public static final int JOB = 1;
	public static final int TIMELEFT = 2;
	public static final int STAGE = 2;
	public static final int PREST = 3;
	public static final int COURTSLEFT = 3;
	public static final int POV = 4;
	public static final int PATRON = 4;

	
	

	//Goods
	public static int numAssets = 15; //fixed tracked owned goods
	public static int numGoods = 25; //all goods
	//basic goods
	//NO ZERO GOOD!! negatives used in WM
	public static final int millet = 1;
	public static final int meat = 2;
	public static final int land = 3;
	public static final int bovad = 4;
	public static final int donkey = 5;
	public static final int lobodonkey = 6;
			//no horses
	public static final int jewelry = 7;
	public static final int armor = 8;
	public static final int sword = 9;
	public static final int mace = 10;
	public static final int bow = 11;
	public static final int gun = 12;
	public static final int xweapon = 13;
	public static final int captive = 14; //has own market type, each one points to Legacy info in case freed as regular character

	//non-assets:
	public static final int rentland = 15;
	public static final int rentanimal = 16;
	public static final int timber = 17;
	public static final int stone = 18;
	public static final int iron = 19;
	public static final int silver = 20;
	public static final int poop = 21;
	public static final int constr = 22;
	public static final int wconstr = 23;
	public static final int sconstr = 24;
	
}
