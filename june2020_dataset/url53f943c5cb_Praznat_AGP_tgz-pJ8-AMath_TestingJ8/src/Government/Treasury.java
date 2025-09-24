package Government;

import java.util.Collection;

import Descriptions.GobLog;
import Game.AGPmain;
import Sentiens.Clan;
import Sentiens.Stress.StressorFactory;

public class Treasury {
	private final Collection<Clan> taxBase;
	
	private int money = 0;
	private int dateLastPaid = 0;
	
	public Treasury(Collection<Clan> taxBase) {
		this.taxBase = taxBase;
	}
	
	public void takePercent(Clan taker, double percent) {
		if (percent > 1 || percent <= 0) throw new IllegalStateException("illegal percent to take");
		int amt = (int) Math.round(money * percent);
		money -= amt;
		taker.alterMillet(amt);
		taker.addReport(GobLog.tookFromTreasury(amt));
	}
	public void collectPProfitFromAll(double percent, Clan collector) {
		if (percent <= 0) throw new IllegalStateException("illegal tax multiplier");
		final int today = AGPmain.TheRealm.getDay();
		for (Clan c : taxBase) {
			if (c.isSomeBossOf(collector)) continue;
			int daysOfIncome = today - dateLastPaid;
			int owedAmt = Math.min((int) Math.round(c.getAvgIncome() * daysOfIncome * percent), c.getMillet());
			collectTaxes(c, owedAmt, collector);
		}
		dateLastPaid = today;
	}
	public void collectPWealthFromAll(double percent, Clan collector) {
		if (percent > 1 || percent <= 0) throw new IllegalStateException("illegal percent tax");
		for (Clan c : taxBase) {
			int owedAmt = (int) Math.round(c.getMillet() * percent);
			collectTaxes(c, owedAmt, collector);
		}
	}
	
	private void collectTaxes(Clan payer, int amt, Clan collector) {
		if (amt <= 0) return;
		payer.alterMillet(-amt);
		money += amt;
		Order collectOrder = collector.myOrder();
		payer.addReport(GobLog.paidTax(collector, amt));
		if (collectOrder != null && collectOrder != payer.myOrder()) {
			payer.AB.add(StressorFactory.createPayTaxesStressor(collector));
		}
	}
}
