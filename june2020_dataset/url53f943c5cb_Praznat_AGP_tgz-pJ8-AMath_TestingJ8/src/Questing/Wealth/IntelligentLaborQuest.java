package Questing.Wealth;

import java.util.*;

import AMath.Calc;
import Avatar.SubjectiveType;
import Defs.G_;
import Descriptions.Naming;
import Game.*;
import Markets.*;
import Questing.*;
import Questing.Quest.PatronedQuest;
import Sentiens.Clan;
import Sentiens.Stress.StressorFactory;

public class IntelligentLaborQuest extends PatronedQuest implements GoodsAcquirable {

	private TradeMemory memory;
	
	public IntelligentLaborQuest(Clan P, Clan patron) {
		super(P, patron);
	}
	
	public IntelligentLaborQuest(Clan P, Labor l) {
		super(P, P);
		memory = new TradeMemory(l, P);
	}

	@Override
	public void pursue() {
		if (!checkOk()) return;
		
		if (memory == null) {chooseAct(); return;}
		if (!memory.noMoreInputsNeeded()) {doInputs(); return;}
		doOutputs();
	}

	@Override
	public void avatarPursue() {
		if (!checkOk()) return;
		
		if (memory == null) {avatarChooseAct(); return;}
		if (!memory.noMoreInputsNeeded()) {avatarDoInputs(); return;}
		doOutputs();
	}
	
	private boolean checkOk() {
		final Job j = Me.getJob();
		if (memory == null && (j instanceof Ministry || j == Job.TRADER)) {Me.MB.finishQ(); return false;}
		return true;
	}
	
	private void chooseAct() {
		final Act[] actSet = Me.getJobActs();
		Labor curAct;   double bestPL = 0;//Integer.MIN_VALUE / 2;
		for (int i = 0; i < actSet.length; i++) {
			curAct = (Labor) actSet[i];
			TradeMemory tm = new TradeMemory(curAct, Me);
			double pl = tm.estimateProfit(Me);
			final int expTime = 1;
			pl /= expTime;
			if (pl > bestPL) {bestPL = pl; memory = tm;}
		}
		if (memory != null) {doInputs();}
		else {failure(StressorFactory.createJobStressor(Me.getJob()));}
	}
	
	private void avatarChooseAct() {
		avatarConsole().showChoices("Choose labor", Me, Me.getJobActs(), SubjectiveType.ACT_PROFIT_ORDER, new Calc.Listener() {
			@Override
			public void call(Object arg) {
				Quest q = Me.MB.QuestStack.peek();
				if (q instanceof IntelligentLaborQuest) {
					((IntelligentLaborQuest) q).memory = new TradeMemory((Labor) arg, Me);
				}
				avatarDoInputs();
			}
		});
	}
	
	private void doInputs() {
		// TODO liquidate if run out of time while buying inputs
		memory.placeBuys(Me);
	}


	/** remove preexisting bids then place them again */
	private void avatarDoInputs() {
		for (G_ g_ : memory.getBuySlots().keySet()) {
			((MktO)Me.myMkt(g_.ordinal())).removeBids(Me);
		}
		avatarDoInputs(0);
	}
	private void avatarDoInputs(final int i) {
		// TODO liquidate if run out of time while buying inputs
		
		// for each input good, ask price to buy at (while also showing other market info)
		final Map<G_, TradeInventorySlot> buySlots = memory.getBuySlots();
		final G_[] gs = buySlots.keySet().toArray(new G_[0]);
		if (i >= gs.length) {
			return;
		}
		
		final int g = gs[i].ordinal();
		final MktO mkt = (MktO)Me.myMkt(g);
		avatarConsole().showQuery("Choose bid price for " + Naming.goodName(g),
				mkt.buyablePX(Me) + "", new Calc.Listener() {
			@Override
			public void call(Object arg) {
				int px = Integer.parseInt(arg.toString());
				mkt.placeBid(Me, px);
				avatarDoInputs(i + 1);
			}
		});
	}
	
	private void doOutputs() {
		memory.placeSells(Me);
		success();
	}
	
	@Override
	public boolean alterG(MktO origin, int num) {
		if (memory == null) return false;
		int g = origin.getGood();
		boolean wasNeeded = memory.addToInventory(g, num);
		return wasNeeded;
	}

	@Override
	public String description() {
		return "Work";
	}

}
