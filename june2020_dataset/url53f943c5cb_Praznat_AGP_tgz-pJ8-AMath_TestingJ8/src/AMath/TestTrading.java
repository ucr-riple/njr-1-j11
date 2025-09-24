package AMath;

import Defs.*;
import Descriptions.GobLog.Reportable;
import Game.*;
import Questing.Wealth.TradingQuest;
import Sentiens.Clan;
import Shirage.Shire;

public class TestTrading extends Testing {
	private static Clan a;
	private static Clan b;
	private static Shire s1;
	private static Shire s2;
	
	private static void resetTrading() {
		testRealm = TestRealm.makeTestRealm(2, 1, 100);
		testRealm.doCensus();
		AGPmain.setRealm(testRealm);
		s1 = testRealm.getShires()[0];
		s2 = testRealm.getShires()[1];
		a = s1.getCensus(0);
		b = s1.getCensus(1);
		a.setActive(true);
		b.setActive(true);
		a.setJob(Job.TRADER);
		a.FB.setBeh(M_.WANDERLUST, 0);
		a.FB.setBeh(M_.PATIENCE, 0);
		a.FB.setBeh(M_.RISKPREMIUM, 15); //TODO hmmm try varying?
	}
	
	public static void doTradingTests() {
		System.out.println("TRADING TESTS");
//		testNoMarkets();
		resetTrading();
		testSimple(s1, s2);
		resetTrading();
		testSimple(s2, s1);
		System.out.println("TRADING TESTS OK");
		
		// TODO test bid 100 in s1, bid 500 in s2, trader should put a bid slightly above 100 in s1 to sell at 500 in s2
	}
	
	private static void testNoMarkets() {
		resetTrading();
		a.MB.newQ(new TradingQuest(a));
		pursueUntilDone(a);
		for (int g = 0; g < Misc.numGoods; g++) affirm(s1.getMarket(g).getPeriodVol() == 0);
		for (int g = 0; g < Misc.numGoods; g++) affirm(s2.getMarket(g).getPeriodVol() == 0);
		//TODO someone comes and hits bids, make sure offers are then placed
	}
	
	private static void testSimple(final Shire s1, final Shire s2) {
		int startMillet = a.getMillet();
		s2.getMarket(18).buyFair(b); // b places bid at s2
		a.MB.newQ(new TradingQuest(a));
//		pursueUntilDone(a); // a does trading run
		doPursueUntil(a, false, new Calc.BooleanCheck() { // a does trading run until placing new bid at s1
			@Override
			public boolean check() {
				return s1.getMarket(18).getBidSz() > 0;
			}
		});
		affirm(s1.getMarket(18).sellablePX(b) > 0); // non-zero bid at s1
		affirm(s1.getMarket(18).sellablePX(b) < s2.getMarket(18).sellablePX(b)); //s1 bid smaller than s2 bid
		s1.getMarket(18).hitBid(b);
		affirm(s2.getMarket(18).getAskSz() > 0 && s2.getMarket(18).sellablePX(b) <= s2.getMarket(18).buyablePX(b));
		for (Reportable r : a.getGoblog().getBook()) System.out.println(r);
	}
}
