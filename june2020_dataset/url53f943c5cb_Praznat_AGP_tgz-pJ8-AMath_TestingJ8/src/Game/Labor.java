package Game;

import AMath.Calc;
import Defs.*;
import Descriptions.GobLog;
import Questing.ExpertiseQuests;
import Questing.Wealth.LaborQuest;
import Sentiens.Clan;

public class Labor implements Act, Misc {

	private static final int MAXACTS = 10; // max acts in a job

	private static int[] tmpOut = new int[1 + MAXACTS];
	private static int[] tmpIn = new int[1 + MAXACTS];

	private String desc;
	protected Logic input;
	protected Logic output;
	private int[] allPossibleInputs = {}; // one for each logic
	private int envc, envr, chance; // skill can point to prestige or quest!
	private Job njob;
	private P_ skill;

	public Labor(String n, Logic in, Logic out, P_ s, Job J, int denom) {
		desc = n;
		input = in;
		output = out;
		skill = s;
		njob = J;
		chance = denom;
	}

	public Labor(String n, Logic in, Logic out, P_ s, int ec, int er, Job J,
			int denom) {
		desc = n;
		input = in;
		output = out;
		skill = s;
		envc = ec;
		envr = er;
		njob = J;
		chance = denom;
	}

	public Labor(String n, Logic g, P_ s, Job J, int denom) {
		desc = n;
		input = g;
		output = g;
		skill = s;
		njob = J;
		chance = denom;
	}

	public int[] expOut(Clan doer) {
		// {fill txpP with probE * probS * expPrice, determined outGs, Es}
		int[] tmp = output.getBest(doer, Logic.SELL);
		int k = 0;
		while (tmp[k] != E) {
			tmpOut[k] = tmp[k++];
		}
		tmpOut[k] = E;
		return tmpOut;
	}

	public int[] expIn(Clan doer) {
		// {fill txpL with sum of expPrices, determined inGs, Es}
		int[] tmp = input.getTheBest(doer, Logic.BUY);
		int k = 0;
		while (tmp[k] != E) {
			tmpIn[k] = tmp[k++];
		}
		tmpIn[k] = E;
		return tmpIn;
	}
	
	public double estimateProfit(Clan doer) {
		// TODO env stuff, skill stuff
		return doer.confuse((double)this.expOut(doer)[0] - (double)this.expIn(doer)[0]);
	}

	/** fill WORKMEMO with allPossibleInputs */
	public void storeAllInputsInWM(LaborQuest lq) {
		for (int i = 0; i < allPossibleInputs.length; i++) {
			lq.setWM(allPossibleInputs[i], i);
		}
		lq.setWM(E, allPossibleInputs.length);
	}

	private int inAll(int g) { // can improve speed by branching search from mid
		int i = 0;
		for (; i < allPossibleInputs.length; i++) {
			if (allPossibleInputs[i] == g) {
				return i;
			}
		}
		return E;
	}

	public void addToAll(int g) {
		int i = inAll(g);
		if (i != E) {
			allPossibleInputs[i] = g;
		} else {
			int[] tmp = new int[allPossibleInputs.length + 1];
			System.arraycopy(allPossibleInputs, 0, tmp, 0,
					allPossibleInputs.length);
			tmp[allPossibleInputs.length] = g;
			allPossibleInputs = tmp;
		}
	}

	public static void socializeOrIntrospect(Clan doer) {
		if (Calc.pMem(doer.useBeh(M_.EXTROVERSION))) {
			socialize(doer);
		} else {
			introspect(doer);
		}
	}

	private static void socialize(Clan doer) {
		// discuss lastMeme
	}

	private static void introspect(Clan doer) {
		// randomize lastMeme
	}
	
	@Override
	public void doit(Clan doer) {
		ponderOrLearn(doer);
	}

	private void ponderOrLearn(Clan doer) {
		if (Calc.pMem(doer.useBeh(M_.MADNESS))) {
			ponder(doer);
		} else {
			ExpertiseQuests.practiceSkill(doer, skill);
		}
	}

	private void ponder(Clan doer) {
		int C = chance;
		Job J = njob;
		if (J == null) {
			C = (16 - doer.useBeh(M_.RESPENV)) * 500;
			J = Job.HUNTERGATHERER;
		}
		if (AGPmain.rand.nextInt(C) == 0) {
			doer.addReport(GobLog.discovery(J));
			doer.setAspiration(J);
		}
	}

	public P_ skill() {
		return skill;
	}

	@Override
	public int getSkill(Clan clan) {
		return (skill == null ? 0 : clan.FB.getPrs(skill));
	}

	@Override
	public String getDesc() {
		return desc;
	}
	@Override
	public String toString() {
		return desc;
	}

	public static Labor newCraft(String n, Logic in, Logic out, P_ s, Job J,
			int denom) {
		Labor newAct = new Labor(n, in, out, s, J, denom);
		in.addNodesToAll(newAct);
		return newAct;
	}

	public static Labor newReapC(String n, Logic in, Logic out, P_ s, int c,
			Job J, int denom) {
		Labor newAct = new Labor(n, in, out, s, c, E, J, denom);
		in.addNodesToAll(newAct);
		return newAct;
	}

	public static Labor newReapR(String n, Logic in, Logic out, P_ s, int r,
			Job J, int denom) {
		Labor newAct = new Labor(n, in, out, s, E, r, J, denom);
		in.addNodesToAll(newAct);
		return newAct;
	}

	public static Labor newTrade(String n, Logic g, Job J, int denom) {
		Labor newAct = new Labor(n, g, null, J, denom);
		return newAct;
	}

}