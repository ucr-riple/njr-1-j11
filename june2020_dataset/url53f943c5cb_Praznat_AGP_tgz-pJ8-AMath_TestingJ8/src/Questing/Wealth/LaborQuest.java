package Questing.Wealth;

import AMath.Calc;
import Avatar.SubjectiveType;
import Defs.*;
import Descriptions.*;
import Game.*;
import Markets.*;
import Questing.Quest;
import Sentiens.Clan;

public class LaborQuest extends Quest implements GoodsAcquirable {
	public static final int WORKMEMORY = 30;
	private int[] workmemo = new int [WORKMEMORY]; //stock id
	private int[] workmemoX = new int [WORKMEMORY];//stock count
	private int stage = 0;
	private int turnsLeft;
	private Labor chosenAct;

	/** skips choose act stage (done immediately based on given job) */
	public LaborQuest(Clan P, Job j) {
		this(P); stage++; chooseAct(j);
	}
	public LaborQuest(Clan P) {
		super(P); setChosenAct((Labor) Job.NullAct); resetWM();
		turnsLeft = P.FB.getBeh(M_.PATIENCE) / 3 + 5;
	}
	@Override
	public String description() {return chosenAct + " stage " + stage;}
	
	@Override
	public void avatarPursue() {
		if (Me.getJob() instanceof Ministry) {Me.MB.finishQ(); return;}
		switch (stage) {
		case 0: avatarChooseAct(Me.getJob()); break; //avatarDoInputs called by avatarChooseAct
		case 1: avatarDoInputs(); break;
		case 2: doWork(); break;
		case 3: doOutputs(); break;
		default: break;
		}		
	}
	
	@Override
	public void pursue() {
		if (Me.getJob() instanceof Ministry) {Me.MB.finishQ(); return;}
		switch (stage) {
		case 0: chooseAct(Me.getJob());
		case 1: doInputs(); break;
		case 2: doWork(); break;
		case 3: doOutputs(); break;
		default: break;
		}		
	}
	
	public void resetWM() {
		for(int i = 0; i < workmemo.length; i++) {workmemo[i] = Misc.E; workmemoX[i] = 0;}
	}
	public void setWM(int g, int plc) {workmemo[plc] = g;   workmemoX[plc] = 0;}
	public boolean alterG(MktO origin, int n) {
		int g = origin.getGood();
		for(int i = 0; i < workmemo.length; i++) {
			if (getAbsWM(i) == g) {
				int newWMX = workmemoX[i] + n;
				workmemoX[i] = newWMX < 0 ? 0 : newWMX;
				return true;
			}
		}
		return false;
	}
	public void suspendG(int g) {
		for(int i = 0; i < workmemo.length; i++) {
			if (workmemo[i] == g) {workmemo[i] = -g;   i++;   return;}
		}
	}
	public int[] getWM() {return workmemo;}
	public int[] getWMX() {return workmemoX;}
	public int getAbsWM(int i) {return Math.abs(workmemo[i]);}
	public int getWMX(int i) {return workmemoX[i];}
	public Labor getChosenAct() {return chosenAct;}
	public int getStage() {return stage;}
	
	public void liquidateWM() {resetWM();}  // TODO and sell all to market
	public void setChosenAct(Labor a) {
		//liquidate if it's a new act
		//do nothing if act is same
		if (chosenAct == null || !chosenAct.equals(a)) {
			liquidateWM();
			chosenAct = a;
		} //new WORKMEMO
		if (chosenAct != Job.NullAct) {chosenAct.storeAllInputsInWM(this);}
	}
	private Labor compareTrades(Job j) {
		final Act[] actSet = j.getActs();
		Labor curAct;   Labor bestAct = (Labor) Job.NullAct;   int bestPL = 0;//Integer.MIN_VALUE / 2;
		for(int i = 0; i < actSet.length; i++) {
			curAct = (Labor) actSet[i];
			final double expOut = curAct.expOut(Me)[0];
			final double expIn = curAct.expIn(Me)[0];
			double PL = expOut - expIn; //cuz of weird shit with MAX_INTEGER
			PL = Me.confuse(PL);
//			System.out.println(Me + " " + PL + "=" + expOut + "-" + expIn + (expIn < expOut));
			final int expTime = 1;
			PL /= expTime;
			if (PL > bestPL) {bestPL = (int)Math.round(PL); bestAct = curAct;}
		}
//		System.out.println(bestPL);
		return bestAct;
	}
	private void avatarChooseAct(Job j) {
		avatarConsole().showChoices("Choose labor", Me, j.getActs(), SubjectiveType.ACT_PROFIT_ORDER, new Calc.Listener() {
			@Override
			public void call(Object arg) {
				Quest q = Me.MB.QuestStack.peek();
				if (q instanceof LaborQuest) {((LaborQuest) q).setChosenAct((Labor) arg);}
				stage++;
				avatarDoInputs();
			}
		});
	}
	private void chooseAct(Job j) {
		setChosenAct(compareTrades(j));
		if (chosenAct == Job.NullAct) {
			failure(Me.myShire()); //LaborQuest fails Shire, BuildWealth fails Shire and Job
		}
		//fill WORKMEMO with every possible g in A:
		stage++;
	}
	private void avatarDoInputs() {
		doInputs();
	}
	private void doInputs() {
		if (turnsLeft <= 0) { // untested
			int i = -1;   while (workmemo[++i] != Misc.E) { //liquidate
				MktO mkt = (MktO)Me.myMkt(getAbsWM(i));
				mkt.removeBids(Me); // TODO no idea if this works.. pls test
				for (int k = workmemoX[i]; k > 0; k--) {mkt.sellFair(Me);} //sell leftovers
			}
			failure(Me.myShire()); return;
		}
		turnsLeft--;
		//

		// PROBLEM WITH MULTIPLE "OR" INPUTS (SEE BUTCHER) ?
		
		//buy cheap input, but then in next round of doInputs, offer on recently bought cheap input is higher
		//than alternative, so it doesnt go in totalNeeded..
		int[] totalNeeded = chosenAct.expIn(Me); //first number zero for expProfit
		int[] stillNeeded = Calc.copyArray(totalNeeded);
		int j;   int i = -1;   while (workmemo[++i] != Misc.E) {
			int N = workmemoX[i];
			j = 0;   while (stillNeeded[++j] != Misc.E) { //goes through WM setting to E all nec inputs already owned
				if(workmemo[i] == -stillNeeded[j]) {totalNeeded[j] = -Math.abs(totalNeeded[j]);} //mark as dont buy if already in market
				if(N>0 && stillNeeded[j]==getAbsWM(i)) {stillNeeded[j] = 0;   N--;}  //0 should not be a good
			}
		}
		boolean go = true;   j = 0;   while (stillNeeded[++j] != Misc.E) {if(stillNeeded[j]!=0) {go=false; break;}}
		if (go) {   //in case all nec inputs owned
			i = -1;   while (workmemo[++i] != Misc.E) {
				int wmg = getAbsWM(i);
				MktAbstract mkt = Me.myMkt(wmg);
				j = 0;   while (totalNeeded[++j] != Misc.E) {  //consume WM goods used in input:
					if(workmemoX[i] == 0) {break;}
					//is this right?? setting in[j] to E even though the while loop stops at E?
					//set in[j] to 0 to correct this problem... see if it works
					if(Math.abs(totalNeeded[j]) == wmg) {totalNeeded[j] = 0;   workmemoX[i]--;   mkt.loseAsset(Me);   Me.addReport(GobLog.consume(wmg));}
				}
				for (int k = workmemoX[i]; k > 0; k--) {mkt.sellFair(Me);} //sell leftovers
			}
			stage++;   //move on
		}
		else {i = 0; while (totalNeeded[++i] != Misc.E) {if(totalNeeded[i] >= 0) {
			Me.myMkt(totalNeeded[i]).liftOffer(Me);   suspendG(totalNeeded[i]);
		}}} //dont lift in case of - (see above)
	}
	private void doWork() {
		chosenAct.doit(Me);
		stage++;
	}
	private void doOutputs() {
		int[] out = chosenAct.expOut(Me);
		int i = 1; for (; out[i] != Misc.E; i++) {
			int g = out[i];
			if (g == Misc.sword || g == Misc.mace) {
				short x = XWeapon.craftNewWeapon(g, Me.FB.getPrs(P_.SMITHING));
				if (x != XWeapon.NULL) {
					g = Misc.xweapon;
					Me.addReport(GobLog.produce(x));
					((XWeaponMarket) Me.myMkt(Misc.xweapon)).setUpTmpXP(x);
				}
			}
			if (g != Misc.xweapon) {Me.addReport(GobLog.produce(g));}
			Me.myMkt(g).gainAsset(Me);
			Me.myMkt(g).sellFair(Me);
		}
		stage = 0;
		success();
	}

};