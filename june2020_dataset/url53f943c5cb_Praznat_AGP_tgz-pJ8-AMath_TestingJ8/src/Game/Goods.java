package Game;
public class Goods {
	
	//value
	//change in wealth is accompanied by change in num items
	//dW : dN(G1) + dN(G2) + dN(G3)... where N(x) is num of x
	//so each evaluation changes value of item by change in num of item divided by change in wealth
	//but W = 

	public static int numGoods = 200;
	public static int[] NGOODS = {20, 100, 200}; //number of goods each rank can have

	//public static int milletvalue = 100;
	//public static int bloodvalue = 100;
	//public static int meatvalue = 300;
	
	//basic goods
	public static final int millet = 0; //MUST BE 0
	public static final int land = 1; //MUST BE 1
	
	public static final int meat = 2;

	public static final int workers = 3;

	public static final int chisel = 4;
	public static final int scythe = 5;
	public static final int pick = 6;
	public static final int axe = 7;
	public static final int machete = 8;
	public static final int hammer = 9;
	
	public static final int singmiel1 = 88;
	
	//high end goods
	public static final int jewelry = 10;
	public static final int encrustedskulls = 89;
	
	//commodity goods
	public static final int stone = 11;
	public static final int iron = 12;
	public static final int silver = 13;
	public static final int lumber = 14;
	//animals go here
	public static final int blood = 19;
	public static final int excrement = 20;
	public static final int skulls = 21;
	public static final int gunpowder = 22;
	public static final int yakhair = 23;
	public static final int beasthide = 24;
	public static final int leather = 25;
	public static final int constructSt = 26;
	public static final int constructW = 27;
	public static final int constructI = 28;
	public static final int constructSi = 29;
	public static final int brains = 30;
	
	//animals
	public static final int bovads = 15;
	public static final int donkeys = 16;
	public static final int lobodonkeys = 17;
	public static final int horses = 18; //hunted by goblins
	
	//weapons and armor
	public static final int stonecuirass = 31;
	public static final int stonetophelm = 32;
	public static final int ironchestplate = 33;
	public static final int irontophelm = 34;
	public static final int ironsurroundhelm = 35;
	public static final int ironleggings = 36;
	public static final int ironsleaves = 37;
	public static final int ironboots = 38;
	public static final int irongloves = 39;
	public static final int beasthideshirt = 40;
	public static final int beasthidecap = 41;
	public static final int beasthidepants = 42;
	public static final int beasthideboots = 43;
	public static final int beasthidegloves = 44;
	
	public static final int triangleshield = 61;
	public static final int hoplonshield = 62;
	
	public static final int dagger = 71;
	public static final int staff = 72;
	public static final int pike = 73;
	public static final int dory = 74;
	public static final int broadsword = 75;
	public static final int mace = 76;
	
	public static final int arquebus = 80;
	public static final int culverin = 81;
	public static final int crossbow = 82;
	

	public static final int thugs = 101; // machete, leather armor
	public static final int swordsmen = 102;
	public static final int macemen = 103;
	public static final int hammerers = 104;
	public static final int hoplites = 105;
	public static final int crossbowmen = 106;
	public static final int arquebusiers = 107;
	public static final int mtedcrossbowmen = 108;
	public static final int mtedculveriners = 109;
	public static final int donkeyknights = 110;
	

	public static String gName(int n) {
		switch (n) {
			case meat: return "blobs of meat";
			case jewelry: return "pieces of jewelry";
			case encrustedskulls: return "silver-encrusted skulls";
			case singmiel1: return "singmiel";
			case stone: return "blocks of stone";
			case iron: return "iron ore";
			case silver: return "silver ore";
			case bovads: return "tame bovads";
			case donkeys: return "tame donkeys";
			case lobodonkeys: return "lobotomized donkeys";
			case lumber: return "stacks of lumber";
			case beasthide: return "boxes of beasthide";
			case excrement: return "piles of dung";
			case stonecuirass: return "stone cuirasses";
			case stonetophelm: return "stone top helmet";
			case ironchestplate: return "iron chest plate";
			case irontophelm: return "iron top helmet";
			case ironsurroundhelm: return "iron surround helmet";
			case ironleggings: return "iron leggings";
			case ironsleaves: return "iron sleaves";
			case ironboots: return "iron boots";
			case irongloves: return "iron gloves";
			default: return "???";
		}
	}
	

}



