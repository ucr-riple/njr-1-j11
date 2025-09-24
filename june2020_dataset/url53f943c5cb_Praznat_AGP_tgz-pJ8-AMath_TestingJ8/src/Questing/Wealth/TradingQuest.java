package Questing.Wealth;

import java.util.*;

import Defs.*;
import Game.*;
import Markets.*;
import Questing.Quest;
import Sentiens.Clan;
import Shirage.Shire;


/**
 * trader initiates quest choosing a random route to go on and number of rounds to go on that route.
 * at movement in the route, trader chooses best good and places bid at Shire
 * which when hit will trigger a sell in the Shire with best seen value for good
 * @author alexanderbraylan
 *
 */
public class TradingQuest extends Quest implements GoodsAcquirable {
	
	private Shire[] route;
	private int plcInRoute = 0;
	private Trade favTrade = Job.TradeC; // TODO
	private int[] sellPlcs = new int[Misc.numGoods];
	private int numRounds;
	
	public TradingQuest(Clan P) {
		super(P);
		route = Me.myShire().getNeighborRoute(true, 2 + P.FB.getBeh(M_.WANDERLUST) / 4);
		for (int i = 0; i < sellPlcs.length; i++) sellPlcs[i] = Misc.E;
		numRounds = 3 + Me.FB.getBeh(M_.PATIENCE) / 5;
	}

	@Override
	public void pursue() {
		TradePlan.LEDGER.clear();
		Shire lastShire = route[plcInRoute < route.length ? plcInRoute : (route.length - 2 - plcInRoute % route.length)];
		plcInRoute++;
		int newshireplc = plcInRoute < route.length ? plcInRoute : (route.length - 2 - plcInRoute % route.length);
		Shire newShire = route[newshireplc];
		List<TradePlan> tradePlans = scoutShire(lastShire, newShire, favTrade);
		for (TradePlan tp : tradePlans) {
			int g = tp.g; int buyPx = tp.buyPx; int sellPx = tp.sellPx;
			if (sellPx > 0) {
				((MktO)lastShire.getMarket(g)).placeBid(Me, buyPx);
				if (sellPlcs[g] == Misc.E || sellPx >= ((MktO)route[sellPlcs[g]].getMarket(g)).riskySellPX(Me)) {
					sellPlcs[g] = newshireplc;
				}
			}
		}
		Me.setCurrentShire(newShire);
		if (plcInRoute == route.length * 2 - 2) {finishUp(); return;}
	}
	
	private void finishUp() {
		plcInRoute = 0;
		if(--numRounds == 0) success();
	}
	
	/** find best good, its cost at buyShire and value at sellShire */
	private List<TradePlan> scoutShire(Shire buyShire, Shire sellShire, Trade trade) {
//		int bestG = -1; double bestTrade = 0; double bestCost = 1; double bestValue = 0;
		for (int g : trade.getGoods()) {
			final int value = ((MktO)sellShire.getMarket(g)).riskySellPX(Me);
			int cost = buyShire.getMarket(g).buyablePX(Me);
			// offer to buy to make money at cost = between 1/3 and 2/3 of value)
			if (cost == MktO.NOASK) cost = 2 * value / (3 + Me.FB.getBeh(M_.BIDASKSPRD) / 5);
			// calc profit before you limit cost to money you own
			final double expProfit = Me.confuse((double)value - (double)cost);
			cost = Math.min(cost, Math.max(1, Me.getMillet()));
//			if (expProfit >= bestTrade) {bestG = g; bestTrade = expProfit; bestCost = cost; bestValue = value;}
			int riskMillet = 0; // TODO portion of current millet
			if (expProfit > riskMillet) {
				TradePlan.LEDGER.add(new TradePlan(g, (int)Math.round(value), (int)Math.round(cost)));
			}
		}
		return TradePlan.LEDGER;
//		return new int[] {bestG, (int)Math.round(bestCost), (int)Math.round(bestValue)};
	}

	@Override
	public String description() {return "Trading";}

	@Override
	public boolean alterG(MktO origin, int num) {
		int good = origin.getGood();
		int p = sellPlcs[good];
		if (p == Misc.E) return false;
		Shire sellShire = p < 0 ? Me.currentShire() : route[p];
		origin.removeBids(Me);
		for (int i = 0; i < num; i++) sellShire.getMarket(good).sellFair(Me);
		sellShire.getGraphingInfo("TRADER").alterValue(1);
		return true;
	}
	
	private static class TradePlan {
		private static List<TradePlan> LEDGER = new ArrayList<TradePlan>();
		private final int g, buyPx, sellPx;
		TradePlan(int g, int buyPx, int sellPx) {this.g = g; this.buyPx = buyPx; this.sellPx = sellPx;}
	}
}
