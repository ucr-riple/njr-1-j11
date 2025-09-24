package Questing.Wealth;

import AMath.Calc;
import Defs.M_;
import Descriptions.GobLog;
import Game.*;
import Government.Treasury;
import Questing.*;
import Questing.Quest.PatronedQuest;
import Sentiens.Clan;
import Sentiens.Stress.StressorFactory;

public class BuildWealthQuest extends PatronedQuest {
	public static PatronedQuestFactory getMinistryFactory() {return new PatronedQuestFactory(BuildWealthQuest.class) {public Quest createFor(Clan c, Clan p) {return new BuildWealthQuest(c, p);}};}
	
	private final int startCumInc, goalCumInc;
	public BuildWealthQuest(Clan clan, Clan patron) {
		super(clan, patron);
		startCumInc = clan.getCumulativeIncome();
		goalCumInc = (int) Math.min(Integer.MAX_VALUE / 2, clan.getNetAssetValue(clan)); // try to double NAV
	} //default is to double NAV
	@Override
	public void pursue() {
		final int accumulated = Me.getCumulativeIncome() - startCumInc;
		final int riskTolerance = goalCumInc * (Me.FB.getBeh(M_.PARANOIA) + 5) / 50; // 10~40% of goal
		if (accumulated >= goalCumInc) {Me.addReport(GobLog.accumulated(accumulated));   success(Me.myShire(), Me.getJob());   return;}
		if (accumulated <= -riskTolerance && Calc.pPercent(80-4*Me.FB.getBeh(M_.PATIENCE))) { // 20-80% chance of give up
			// TODO blame shire or blame trading behs or blame job? if library has behs XOR wealthyshire knowledge then use what it has
			Me.addReport(GobLog.accumulated(accumulated));
			failure(StressorFactory.createWealthStressor(Me)); return;
		}
		
		collectTaxes();
		
		final Job j = Me.getJob();
		if (j instanceof Ministry) {
			// ministry job
			((Ministry) j).getService().doit(Me);
		}
		else if (j == Job.TRADER) {
			Me.MB.newQ(new TradingQuest(Me));
		}
		else {Me.MB.newQ(new IntelligentLaborQuest(Me, patron));}
	}
	public String description() {return "Build Wealth" + (patron != Me ? " for " + patron.getNomen() : "");}
	
	//TODO make quest
	private void collectTaxes() {
		boolean isPatron = Me == patron;
		if (isPatron && Me != Me.getBoss() && Me.getGovernedShire() == null) return; // cant collect taxes for yourself if youre a minion and not a governor
		
		double myCut = 0; 
		if (patron.getGovernedShire() != null) {
			myCut = isPatron ? 0 : calculateMyCut();
			collectTaxes(patron.getGovernedShire().getTreasury(), calculateShireTax(), myCut, isPatron);
		}
		if (patron.myOrder() != null) {
			if (myCut == 0) myCut = isPatron ? 0 : calculateMyCut();
			collectTaxes(patron.myOrder().getTreasury(), calculateOrderTax(), myCut, isPatron);
		}
	}
	private void collectTaxes(Treasury treasury, double tax, double myCut, boolean isPatron) {
		treasury.collectPProfitFromAll(tax, patron);
		if (!isPatron) treasury.takePercent(patron, myCut);
		treasury.takePercent(patron, 1);
	}
	private double calculateShireTax() {
		return 0.3;
	}
	private double calculateOrderTax() {
		return 0.2;
	}
	private double calculateMyCut() {
		return 0.05;
	}
}