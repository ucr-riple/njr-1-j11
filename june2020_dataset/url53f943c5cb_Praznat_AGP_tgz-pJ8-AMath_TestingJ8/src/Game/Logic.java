package Game;

import Defs.Misc;
import Questing.*;
import Questing.Wealth.LaborQuest;
import Sentiens.Clan;

public abstract class Logic {
	protected static final int BUY = 0;
	protected static final int SELL = 1;
	protected static final int E = Misc.E;
	protected static final int[] tmpInventory = new int[Misc.numGoods];
	
	
	protected Logic[] subLogics;
	protected int[] X = new int[LaborQuest.WORKMEMORY]; //one for each logic
	
	public abstract Logic replica();
	public static Logic[] replicate(Logic[] others) {
		Logic[] result = new Logic[others.length];
		for (int i= 0; i < result.length; i++) {result[i] = others[i].replica();}
		return result;
	}
	
	protected boolean sufficient(Clan doer) {return false;} //dont ask from here, use workmemo
	
	/** returns array of best value/cost in place [0] and each good in every subsequent place,
	 * with repeats for plural goods, ending with E */
	public int[] getBestGs(Clan doer, int type) {
		return getBest(doer, type);
	}
	public int[] getTheBest(Clan doer, int type) {
		if (type == BUY) {setupTempInventory(doer);}
		return getBest(doer, type);
	}
	protected abstract int[] getBest(Clan doer, int type);
	protected void setupTempInventory(Clan doer) {
		for (int i = 0; i < tmpInventory.length; i++) {tmpInventory[i] = 0;}
		QStack qs = doer.MB.QuestStack;
		if (qs.isEmpty()) {return;}
		Quest q = qs.peek();
		if (!(q instanceof LaborQuest)) {return;}
		LaborQuest lq = (LaborQuest) q;
		int[] wm = lq.getWM();   int[]wmx = lq.getWMX();
		for (int i = 1; i < wm.length; i++) {
			int g = Math.abs(wm[i]);
			if(g == E) {return;}
			tmpInventory[g] = wmx[i];
		}
	}

	public void addNodesToAll(Labor a) {
		if (subLogics != null) {
			for(int i = 0; i < subLogics.length; i++) {subLogics[i].addNodesToAll(a);}
		}
	}

	
	protected boolean better(int p1, int type) {
		if(X[0] < 0) {return true;}
		if(type == BUY) {return p1<X[0];}
		else if (type == SELL) {return p1>X[0];}
		else return false;
	}
	
	public static Logic[] L(int... inputs) {
		Logic[] output = new Logic[inputs.length];
		for (int i = 0; i < output.length; i++) {output[i] = new Node(inputs[i]);}
		return output;
	}
	protected boolean searchTmp(int g) {
		if (tmpInventory[g] > 0) {tmpInventory[g]--; return true;} else {return false;}
	}
	
	protected void deleteFromX(int plc) {
		for (int i = plc; i < X.length; i++) {
			int next = X[i+1];
			X[i] = next;
			if (next == E) {break;}
		}
	}
	public Logic[] getSubLogics() {return subLogics;}
	
}

class And extends Logic {
	public And(Logic... subs) {subLogics = subs;}
	public And(int... subs) {subLogics = L(subs);}
	
	protected boolean sufficient(Clan doer) { //WRONG! must maintain global count
		for (int i = 0; i < subLogics.length; i++) {
			if(subLogics[i].sufficient(doer) == false) {return false;}
		}   return true;
	}
	@Override
	protected int[] getBest(Clan doer, int type) {
		X[0] = 0;   int p = 1;   int k;
		for (int i = 0; i < subLogics.length; i++) {
			int[] tmp = subLogics[i].getBest(doer, type);
			X[0] = (int) Math.min((long)X[0] + tmp[0], Integer.MAX_VALUE);  //add cost from sublogic i
			k = 1;   while (tmp[k] != E) {X[p++] = tmp[k++];}
		}      X[p] = E;
		return X;
	}
	@Override
	public Logic replica() {return new And(replicate(subLogics));}

}

class Or extends Logic {
	public Or(Logic... subs) {subLogics = subs;}
	public Or(int... subs) {subLogics = L(subs);}
	
	protected boolean sufficient(Clan doer) { //TODO WRONG! must maintain global count
		for (int i = 0; i < subLogics.length; i++) {
			if(subLogics[i].sufficient(doer) == true) {return true;}
		}   return false;
	}

	@Override
	protected int[] getBest(Clan doer, int type) {
		X[0] = E;   int k = 0;
		for (int i = 0; i < subLogics.length; i++) {
			int[] tmp = subLogics[i].getBest(doer, type);
			if (better(tmp[0], type)) {
				X[0] = tmp[0];   k = 1;   while (tmp[k] != E) {X[k] = tmp[k++];}
			}
		}   X[k] = E;
		return X;
	}

	@Override
	public Logic replica() {return new Or(replicate(subLogics));}

}

class Mult extends Logic {
	int mult;
	public Mult(int m, Logic subs) {mult = m; subLogics = new Logic[] {subs};}
	public Mult(int m, int subs) {mult = m; subLogics = L(subs);}
	
	@Override
	protected int[] getBest(Clan doer, int type) {
		int p = 1;   int k;
		int[] tmp = subLogics[0].getBest(doer, type);
		X[0] = (int) Math.min(mult * (long) tmp[0], Integer.MAX_VALUE);
		for (int i = 0; i < mult; i++) {
			k = 1;   while (tmp[k] != E) {X[p++] = tmp[k++];}
		}      X[p] = E;
		return X;
	}
	
	@Override
	public Logic replica() {return new Mult(mult, replicate(subLogics)[0]);}
	
}

class Node extends Logic {
	int node;
	public Node(int input) {node = input;}
	private int Cost(Clan doer) {
		if (tmpInventory[node] > 0) {return 0;}
		return doer.myMkt(node).buyablePX(doer);
	}
	private int Value(Clan doer) {return doer.myMkt(node).sellablePX(doer);}
	@Override
	protected int[] getBest(Clan doer, int type) {
		switch (type) {
		case BUY: X[0] = Cost(doer); break;
		case SELL: X[0] = Value(doer); break;
		default: break;
		}
		X[1] = node;//searchTmp(node) ? E : node;
		X[2] = E;   return X;
	}
	@Override
	public void addNodesToAll(Labor a) {a.addToAll(node);}

	@Override
	public Logic replica() {return new Node(node);}

}

class Nada extends Logic {
	//just leave empty, inherited from Logic
	@Override
	protected int[] getBest(Clan doer, int type) {X[0] = 0; X[1] = E; return X;}
	@Override
	public Logic replica() {return new Nada();}
}