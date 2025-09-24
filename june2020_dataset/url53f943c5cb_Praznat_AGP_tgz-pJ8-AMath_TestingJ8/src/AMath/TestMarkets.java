package AMath;

import Defs.*;
import Descriptions.*;
import Descriptions.GobLog.Reportable;
import Game.*;
import Markets.*;
import Questing.*;
import Questing.Wealth.LaborQuest;
import Sentiens.Clan;
import Shirage.Shire;

public class TestMarkets extends Testing {
	
	private static Clan a;
	private static Clan b;
	private static Shire s;
	public static void workInputManagement() {
		reset();
		Clan guy = testRealm.getClan(0);
		Clan other = testRealm.getClan(1);
		guy.addReport(GobLog.transaction(1, 9, true, other));
		other.addReport(GobLog.transaction(1, 9, false, guy));
		for (Reportable R : guy.getLog()) {
			System.out.println(R);
		}
		for (Reportable R : other.getLog()) {
			System.out.println(R);
		}
		for (Reportable R : guy.getLog()) {
			System.out.println(R);
		}
		for (Reportable R : other.getLog()) {
			System.out.println(R);
		}
		//FAAIL!!!!! on date()
	}
	public static void normalMarketFunctions() {
		resetMarketFunctions();
		Clan.DMC = 0;
		final int aStartMillet = a.getMillet();
		final int bStartMillet = b.getMillet();
		normalMarketFunctions(s, a, b);
		affirm(a.getCumulativeIncome() == a.getMillet() - aStartMillet);
		affirm(b.getCumulativeIncome() == b.getMillet() - bStartMillet);
		normalMarketFunctions(s, a, b);
		s.newMarketDay();
		normalMarketFunctions(s, a, b);
		testPlaceBidForInputChangeQuestGetFilled(a, b);
		testPlaceBidForInputChangeQuestGetFilled(a, a);
	}
	private static void resetMarketFunctions() {
		reset();
		s = testRealm.getClan(0).myShire();
		a = s.getCensus(0);
		b = s.getCensus(1);
	}
	public static void normalMarketFunctions(Shire shire, Clan a, Clan b) {
		produceOne(setupClanForWork(shire, a, Job.Settle), 0, 0, Misc.land, 1);
		produceOne(setupClanForWork(shire, a, Job.Settle), 0, 0, Misc.land, 1);
		produceOne(setupClanForWork(shire, a, Job.Farm), Misc.rentland, 1, Misc.millet, 3);
		produceOne(setupClanForWork(shire, b, Job.Farm), Misc.rentland, 1, Misc.millet, 3);
		produceOne(setupClanForWork(shire, b, Job.HerdD), 0, 0, Misc.donkey, 1);
//		int donkeyBestOffer = shire.getMarket(Defs.donkey).bestOffer();
//		produceOne(setupClanForWork(shire, b, Job.HerdD), 0, 0, Defs.donkey, 1);
//		affirm(shire.getMarket(Defs.donkey).bestOffer() < donkeyBestOffer); //not necessarily..think fair value
		produceOne(setupClanForWork(shire, b, Job.DungCollecting), Misc.rentanimal, 1, Misc.poop, 1);
		produceOne(setupClanForWork(shire, b, Job.HerdD), 0, 0, Misc.donkey, 1);
		((MktO)shire.getMarket(Misc.donkey)).chgOffer(0, 10);
		produceOne(setupClanForWork(shire, a, Job.Butcher), Misc.donkey, 1, Misc.meat, 3);
	}
	/**
	 * this is when a clan collects the inputs to produce a good so that they are in work memory,
	 * then another clan buys some of those inputs before the final good is produced, must test
	 * to make sure that the original clan that collected the inputs no longer has them in work memory
	 */
	public static void testSellG() {
		
	}
	
	public static void testPlaceBidForInputChangeQuestGetFilled(Clan doer1, Clan doer2) {
		resetMarketFunctions();
		MktO timberMarket = ((MktO)doer1.myShire().getMarket(Misc.timber));
		doer1.alterMillet(1000000);
		((MktO)doer1.myShire().getMarket(Misc.timber)).placeBid(doer1, 10000);
		doer1.MB.QuestStack.clear();
		setupClanForWork(doer2.myShire(), doer2, Job.Lumberjacking);
		doPursueUntilProduced(doer2, true);
		affirm(timberMarket.getAskSz() > 0);
	}
	
	public static void produceOne(Clan doer, int inG, int inN, int outG, int outN) {
		final Labor labor = (Labor) doer.getJobActs()[0];
		final int[] expIn = labor.expIn(doer);
		for(int i = 1; expIn[i] != Misc.E; i++) {inG = expIn[i];}
		final int[] expOut = labor.expOut(doer);
		for(int i = 1; expOut[i] != Misc.E; i++) {outG = expOut[i];}
		if(expOut[0] <= expIn[0]) {inN = 0; outN = 0;}
		final Shire shire = doer.myShire();
		final int rentInG = Assets.getRentGood(inG);
		final int rentOutG = Assets.getRentGood(outG);
		final int prevOutAskSz = shire.getMarket(outG).getAskSz();
		final int prevInRentSz = rentInG >= 0 ? shire.getMarket(rentInG).getAskSz() : 0;
		final int prevOutRentSz = rentOutG >= 0 ? shire.getMarket(rentOutG).getAskSz() : 0;
		final MktAbstract inMarket = shire.getMarket(inG);
		final boolean inputIsRental = inMarket instanceof RentMarket;
		final int prevInBidSz = inMarket.getBidSz();
		final int prevInAskSz = inputIsRental ? ((RentMarket)inMarket).numberUnrentedRemaining() : inMarket.getAskSz();
		final int prevMyAsksForInG = ((MktO)inMarket).findNumberOf(doer, Entry.OFFERDIR);
//		doPursueUntilInputsObtained(doer, true);
//		affirm(inMarket.getAskSz() == prevInAskSz);
//		affirm(((MktO)inMarket).findNumberOf(doer, Entry.OFFERDIR) == prevMyAsksForInG + inG);
		doPursueUntilConsumed(doer, true);
		if (inputIsRental) {affirm(((RentMarket)inMarket).isBestPlaceCorrect());}
		if (inN > 0) {
			affirm(inMarket.getBidSz() == prevInBidSz);
			if (inputIsRental) {
				affirm(((RentMarket)inMarket).numberUnrentedRemaining() == prevInAskSz - inN);
			}	else {
				affirm(inMarket.getAskSz() == prevInAskSz - inN);
				affirm(((MktO)inMarket).findNumberOf(doer, Entry.OFFERDIR) == prevMyAsksForInG);
			}
		}
		final int prevDoerOutN = outG < Misc.numAssets ? doer.getAssets(outG) : 0;
		doPursueUntilProduced(doer, true);
		//food is consumed so this will definitely need to change... meat is already being consumed during learn process
		if (outG < Misc.numAssets && outG!=Misc.meat) affirm(doer.getAssets(outG) == prevDoerOutN + outN * (outG==Misc.millet ? FoodMarket.MILLETVAL: 1));
		
		if (!(shire.getMarket(outG) instanceof FoodMarket)) {affirm(shire.getMarket(outG).getAskSz() == prevOutAskSz + outN);}
		if (rentInG >= 0) {
			final RentMarket rMarket = (RentMarket) shire.getMarket(rentInG);
			affirm(rMarket.getAskSz() == prevInRentSz - inN);
			affirm(rMarket.bestOffer() <= shire.getMarket(inG).bestOffer());
			affirm(rMarket.isBestPlaceCorrect());
			rMarket.printBaikai();
		}
		if (rentOutG >= 0) {
			final RentMarket rMarket = (RentMarket) shire.getMarket(rentOutG);
			affirm(rMarket.getAskSz() == prevOutRentSz + outN);
			affirm(rMarket.bestOffer() <= shire.getMarket(outG).bestOffer());
			affirm(rMarket.isBestPlaceCorrect());
			rMarket.printBaikai();
		}
		
	}
	
	public static void testLogics() {
		reset();
		Clan clan = testRealm.getClan(0);
		Shire shire = clan.myShire();
		((MktO)shire.getMarket(Misc.donkey)).placeOffer(clan, 10);
		((MktO)shire.getMarket(Misc.bovad)).placeOffer(clan, 5);
		int[] expIn = ((Labor)Job.Butcher).expIn(clan);
		affirm(expIn[0] == 5 && expIn[1] == Misc.bovad && expIn[2] == Misc.E);

		reset();
		clan = testRealm.getClan(0);
		shire = clan.myShire();
		clan = setupClanForWork(shire, clan, Job.Butcher);
		clan.MB.newQ(new LaborQuest(clan));
		((MktO)shire.getMarket(Misc.bovad)).placeOffer(clan, 5);
		((MktO)shire.getMarket(Misc.donkey)).placeOffer(clan, 10);
		clan.pursue();
		clan.pursue();
		clan.MB.QuestStack.peek();
		((MktO)shire.getMarket(Misc.donkey)).liftOffer(clan);
		expIn = ((Labor)Job.Butcher).expIn(clan);
		affirm(expIn[0] == 0 && expIn[1] == Misc.donkey && expIn[2] == Misc.E);
	}
	
	private static Clan setupClanForWork(Shire s, Clan clan, Act act) {
		clan.setJob(new Job(act.getDesc() + " Pro", act));
		clan.MB.newQ(new LaborQuest(clan));
		setClanMemMax(clan, P_.ARITHMETIC);
		setClanMemMax(clan, M_.PATIENCE);
		setClanMemMin(clan, M_.MADNESS);
		return clan;
	}

	private static void doPursueUntilDoInputs(final Clan clan, boolean report) {
		doPursueUntil(clan, report, new Calc.BooleanCheck() {
			@Override
			public boolean check() {return ((LaborQuest)clan.MB.QuestStack.peek()).getStage() == 1;}
		});
	}
	private static void doPursueUntilConsumed(final Clan clan, boolean report) {
		doPursueUntil(clan, report, new Calc.BooleanCheck() {
			@Override
			public boolean check() {
				QStack qs = clan.MB.QuestStack;
				if (!qs.isEmpty()) {
					Quest lq = qs.peek();
					if (lq instanceof LaborQuest && ((LaborQuest)lq).getStage() != 2) {return false;}
				}
				return true; // gave up old labor quest
			}
		});
	}
	private static void doPursueUntilProduced(final Clan clan, boolean report) {
		doPursueUntil(clan, report, new Calc.BooleanCheck() {
			@Override
			public boolean check() {return clan.MB.QuestStack.isEmpty() || !(clan.MB.QuestStack.peek() instanceof LaborQuest);}
		});
	}
	private static void doPursueUntilNumGs(Clan clan, int num, int g, boolean report) {
		for (int i = 0; i < num*50; i++) {
			if (report) {Calc.p(clan + " " + clan.MB);}
			if (clan.getAssets(g) >= num) {return;}
			clan.pursue();
		}
		throw new IllegalStateException("doPursueUntilNumGs failed");
	}
}
