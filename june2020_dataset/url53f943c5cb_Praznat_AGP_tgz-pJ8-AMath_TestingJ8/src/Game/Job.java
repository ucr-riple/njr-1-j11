package Game;

import Defs.*;
import Ideology.Value;
import Questing.*;
import Questing.Quest.PatronedQuestFactory;
import Questing.Knowledge.KnowledgeQuests;
import Questing.Might.MightQuests;
import Questing.Wealth.BuildWealthQuest;
import Sentiens.Clan;
import Sentiens.Stress.Blameable;
import Shirage.Shire;

public class Job implements Misc, Blameable {
	protected Act[] acts;
	protected String desc;
	
	public Job(String N, Act... A) {desc = N; acts = A;}

	public Act[] getActs() {return acts;}

	private static Logic AND(Logic... L) {return new And(L);}
	private static Logic AND(int... L) {return new And(Logic.L(L));}
	private static Logic OR(Logic... L) {return new Or(L);}
	private static Logic OR(int... L) {return new Or(Logic.L(L));}
	private static Logic M(int n, int L) {
		int[] gs = new int[n]; for (int i = 0; i < gs.length; i++) {gs[i] = L;}
		return AND(gs);
	}
	private static Logic M(int n, Logic L) {
		Logic[] ls = new Logic[n]; for (int i = 0; i < ls.length; i++) {ls[i] = L.replica();}
		return AND(ls);
	}
//	private static Logic M(int n, int L) {return new Mult(n, L);}
	private static Logic I(int i) {return new Node(i);}
	private static Logic N() {return new Nada();}
	
	public final static Act NullAct = new Labor("Nothing",							N(),						N(),			null,								null,		-1);
	
	public final static Labor Lobotomize = Labor.newCraft("Lobotomize Donkey",		I(donkey),					I(lobodonkey),	P_.LOBOTOMY,						null,		-1);
	public static final Job LOBOTOMIST = new Job("Lobotomist", Lobotomize);
	
	public final static Labor ConstructS = Labor.newCraft("Build Construction",		I(stone),					I(sconstr),		P_.MASONRY,							null,		-1);
	public final static Labor Build = Labor.newCraft("Construct Building",			OR(wconstr, sconstr),		I(constr),		P_.STRENGTH,						null,		-1);
	public static final Job MASON = new Job("Stonemason", ConstructS, Build);
	
	public final static Trade TradeA = Trade.newTrade("Trade Animals",				bovad, donkey, lobodonkey, poop);
	public final static Trade TradeW = Trade.newTrade("Trade Arms",					sword, mace, bow, gun, armor);
	public final static Trade TradeC = Trade.newTrade("Trade Commodities",			timber, stone, iron, silver, wconstr, sconstr);
	public static final Job TRADER = new Job("Merchant", TradeA, TradeC, TradeW);
	
	public final static Labor MakeJewel = Labor.newCraft("Silvercrafting",			AND(silver, timber),		I(jewelry),		P_.ARTISTRY,						null,		-1);
	public final static Labor ForgeArmor = Labor.newCraft("Smith Armor",			AND(M(3, iron), I(timber)),	I(armor),		P_.SMITHING,						null,		-1);
	public final static Labor ForgeWeapon = Labor.newCraft("Smith Weapon",			AND(iron, timber),			OR(sword, mace),P_.SMITHING,						null,		-1);
	public final static Labor ForgeGun = Labor.newCraft("Smith Arquebus",			AND(I(iron), I(poop), M(2, timber)),	I(gun),			P_.SMITHING,						null,		-1);
	public static final Job SMITHY = new Job("Blacksmith", ForgeWeapon, ForgeArmor, ForgeGun, MakeJewel);
	
	public final static Labor MakeBow = Labor.newCraft("Craft Crossbow",			I(timber),					I(bow),			P_.CARPENTRY,						SMITHY,		500);
	public final static Labor ConstructW = Labor.newCraft("Build Construction",		I(timber),					I(wconstr),		P_.CARPENTRY,						null,		-1);
	public static final Job CARPENTER = new Job("Carpenter", MakeBow, ConstructW, Build);
	
	public final static Labor Quarry = Labor.newReapR("Quarry Stone",				N(),						I(stone),		P_.STRENGTH,	Shire.STONES,		MASON,		500);
	public final static Labor MineIron = Labor.newReapR("Iron Mining",				N(),						I(iron),		P_.STRENGTH,	Shire.IORE,			null,		-1);
	public final static Labor MineSilver = Labor.newReapR("Silver Mining",			N(),						I(silver),		P_.STRENGTH,	Shire.SORE,			null,		-1);
	public final static Act Lumberjacking = Labor.newReapR("Fell Trees",			N(),						I(timber),		P_.STRENGTH,	Shire.TREES,		CARPENTER,	500);
	public static final Job MINER = new Job("Miner", Quarry, MineIron, MineSilver, Lumberjacking);
	
	public final static Labor Settle = Labor.newReapR("Settle Land",				N(),						I(land),		null,			Shire.WILDERNESS,	MINER,		100);
	public final static Labor Farm = Labor.newReapC("Minimal Farming",				I(rentland),				M(3, millet),	null,			Shire.FERTILITY,	MINER,		100);
	public final static Labor FarmF = Labor.newReapC("Fertilized Farming",			AND(rentland, poop),		M(3, millet),	null,			-1,					MINER,		100);
	public final static Labor FarmA = Labor.newReapC("Assisted Farming",			AND(M(2, rentland),
																					I(rentanimal)),				M(6, millet),	null,			Shire.FERTILITY,	MINER,		100);
	public final static Labor FarmAF = Labor.newReapC("Complete Farming",			AND(M(2, rentland),
																					AND(rentanimal, poop)),		M(6, millet),	null,			-1,					MINER,		100);
	public static final Job FARMER = new Job("Farmer", Settle, Farm, FarmF, FarmA, FarmAF);
	
	public final static Labor HerdB = Labor.newReapR("Bovad Herding",				N(),						I(bovad),		null,			Shire.WBOVADS,		TRADER,		100);
	public final static Labor HerdD = Labor.newReapR("Donkey Herding",				N(),						I(donkey),		null,	 		Shire.WDONKEYS,		TRADER,		100);
	public final static Labor Butcher = Labor.newCraft("Butcher Animal",			OR(I(bovad), I(donkey),
																					I(lobodonkey)),				M(3, meat),		null,								LOBOTOMIST,	1000);
	public final static Labor DungCollecting = Labor.newCraft("Collect Dung",		I(rentanimal),				I(poop),		null,								null,		-1);
	public static final Job HERDER = new Job("Herder", HerdB, HerdD, DungCollecting, Butcher);
	
	public final static Labor GatherMillet = Labor.newReapC("Gather Edibles",		N(),						I(millet),		null,			Shire.FERTILITY,	FARMER,		1000); //vegetation
	public final static Labor HuntSloth = Labor.newReapC("Hunt Sloth",				N(),						I(meat),		P_.MARKSMANSHIP,Shire.WILDERNESS,	HERDER,		1000);
	public static final Job HUNTERGATHERER = new Job("Savage", GatherMillet, HuntSloth);
	
	public static final Ministry JUDGE = new Ministry(CreedQuests.getMinistryFactory());
	public static final Ministry NOBLE = new Ministry(AllegianceQuests.getMinistryFactory());
	public static final Ministry HISTORIAN = new Ministry(LegacyQuests.getMinistryFactory());
	public static final Ministry PHILOSOPHER = new Ministry(KnowledgeQuests.getMinistryFactory());
	public static final Ministry TUTOR = new Ministry(ExpertiseQuests.getMinistryFactory());
	public static final Ministry SORCEROR = new Ministry(FaithQuests.getMinistryFactory());
	public static final Ministry VIZIER = new Ministry(InfluenceQuests.getMinistryFactory());
	public static final Ministry GENERAL = new Ministry(MightQuests.getMinistryFactory());
	public static final Ministry TREASURER = new Ministry(BuildWealthQuest.getMinistryFactory());
	public static final Ministry COURTESAN = new Ministry(RomanceQuests.getMinistryFactory());
	public static final Ministry APOTHECARY = new Ministry(Quest.DefaultQuest.getMinistryFactory());//TODO
	public static final Ministry ARCHITECT = new Ministry(SplendorQuests.getMinistryFactory());
	
	public static final Ministry[] ALL_MINISTRIES = {
		JUDGE, NOBLE, HISTORIAN, PHILOSOPHER, TUTOR, SORCEROR, VIZIER, GENERAL, TREASURER, COURTESAN, APOTHECARY, ARCHITECT
	};
	
	public static PatronedQuestFactory getPatronQuestFactoryForValue(Value v) {
		return v.getMinistry().getService().questFactory;
	}

	public String getDesc() {return desc;}
	public String getDesc(Clan c) {return desc;}
	@Override
	public String toString() {return getDesc();}
}