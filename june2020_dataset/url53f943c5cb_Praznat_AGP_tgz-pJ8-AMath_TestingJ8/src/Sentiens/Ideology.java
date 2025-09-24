package Sentiens;

import AMath.ArrayUtils;
import Defs.*;
import Descriptions.Naming;
import Game.AGPmain;
import Ideology.*;
import Sentiens.Law.Commandments;
import Sentiens.Law.PersonalCommandment;

public class Ideology implements Misc {

	private static final int NUMPRESTS = P_.length();
	private static final int NUMBEHS = M_.length();
	private static final int NUMVALS = Values.All.length;
	
	private static final Value[] tmpGarbage = new Value[NUMVALS];
	
	private byte[] condensed;
	private Value[] sancs = new Value[NUMVALS]; //list of sancs ordered from highest to lowest
	private int[] sancranks = new int[NUMVALS]; //rank of sancs in default sanc order
	private int creed;
	private Clan Me;
	public PersonalCommandment[] commandments;
	public Clan getEu() {return Me;}

	public Ideology(Clan i) {   
		Me = i;   initialize(defaultVars());   this.setPrs(P_.COMBAT.ordinal(), AGPmain.rand.nextInt(15));
		commandments = Commandments.INSTANCE.generatePersonalCommandments();
	}
	
	public Ideology() {
		initialize(defaultVars());
	}
	
	public void initialize(int[] in) {
		condensed = new byte[(in.length+1)/2];
		for (int i = 0; i < in.length; i++) {
			setVar(i, in[i]);
		}
		defaultSancs();
		setCreed(AGPmain.rand.nextInt());
		
	}
	public int numVars() {return condensed.length * 2;}
	
	public void setVar(int plc, int val) {
		val = Math.min(15, Math.max(0, val)); //remove to speedup
		boolean odd = (plc%2==1);
		int mask = 0xf << (odd? 4 : 0);
		val = val << (odd? 0 : 4);
		int tmp = condensed[plc/2] & mask;
		condensed[plc/2] = (byte) (tmp | val);
	}
	public void chgVar(int plc, int amt) {
		setVar(plc, getVar(plc) + amt);
	}
	public int getVar(int plc) {
		return getVar(plc, condensed);
	}
	public static int getVar(int plc, byte[] V) {
		int mask = 0xf << ((plc%2==1)? 0 : 4);
		return (V[plc/2] & mask) >> ((plc%2==1)? 0 : 4);
	}
	public int[] getValueRanks() {return sancranks;}
	
	public int getFac(F_ f) {return getVar(F(f));}
	public void setFac(F_ f, int val) {setVar(F(f), val);}
	public int getBeh(M_ m) {return getVar(B(m));}
	public int getBeh(int plc) {return getVar(B(plc));}
	public void setBeh(M_ m, int val) {setVar(B(m), val);}
	public void setBeh(int plc, int val) {setVar(B(plc), val);}
	public int getPrs(P_ p) {return getVar(P(p));}
	public int getPrs(int plc) {return getVar(P(plc));}
	public void setPrs(int plc, int val) {setVar(P(plc), val);}
	public void setPrs(P_ p, int val) {setVar(P(p), val);}

	public void upPrest(P_ plc) {
		final int p = P(plc);
		final int cur = getVar(p);
		setVar(p, (int) (4.7 + 0.75 * (double)cur));
	}
	public void downPrest(P_ plc) {
		final int p = P(plc);
		final int cur = getVar(p);
		setVar(p, (int) (0.2 + 0.75 * (double)cur));
	}
	
	public static int P(int plc) {return plc;}
	public static int P(P_ p) {return p.ordinal();}
	public static int unB(int x) {return x - NUMPRESTS;}
	public static int B(int plc) {return plc + NUMPRESTS;}
	public static int B(M_ m) {return m.ordinal() + NUMPRESTS;}
	public static int unP(int x) {return x;}
	private static final int FSTARTPLC = ((NUMPRESTS + NUMBEHS) / 2 + 1) * 2;
	public static int F(int plc) {return plc + FSTARTPLC;}
	public static int F(F_ f) {return f.ordinal() + FSTARTPLC;}
	public static int unF(int x) {return x - FSTARTPLC;}
	
	public byte[] copyFs() {
		byte[] result = new byte[condensed.length - FSTARTPLC/2];
		System.arraycopy(condensed, FSTARTPLC/2, result, 0, result.length);
		return result;
	}
	

	private void defaultSancs() {
		//sancs = new int[] {SZ, SS, TY, WL, HS, DE, HL, NS, AG, SC, EY, SA, BM, WP, MP, RX, FH, SH, RT, JB, LH};
		sancs = new Value[NUMVALS];
		int s = 0;   for (Value v : Values.All) {
			sancranks[v.ordinal()] = s;
			sancs[s++] = v;
		}
//		s = 0;   for (Value v : Values.All) {
//			for(int j = 0; j < NUMVALS; j++) {if (sancs[j] == v) {sancranks[s] = j;}}
//		}
		Values.All = ArrayUtils.shuffle(Value.class, Values.All); //reshuffle (this line just for testing)
	}
	public int[] defaultVars() {
		int[] V = new int[NUMBEHS + NUMPRESTS + M_.values().length];  //slow?
		for (int i = V.length - 1; i >= NUMPRESTS; i--) {V[i] = AGPmain.rand.nextInt(15);} //includes M_ and F_
		for (int i = NUMPRESTS - 1; i >= 0; i--) {V[i] = 0;} //AGPmain.rand.nextInt(15);}
		return V;
	}
	public int[] facelessVars() {  //FOR THE FACELESS MASSES!
		//retain only the face memes needed to calculate beauty
		//upgrade to faced only when met for first time by Avatar
		int[] V = new int[NUMPRESTS + M_.values().length];  //slow?
		for (int i = V.length - 1; i >= NUMPRESTS; i--) {V[i] = AGPmain.rand.nextInt(15);}
		for (int i = NUMPRESTS - 1; i >= 0; i--) {V[i] = 0;} //AGPmain.rand.nextInt(15);}
		return V;
	}
//	private void randomizeSancs() {
//		for (int i = 0; i < NUMVALS; i++) {
//			int s = AGPmain.rand.nextInt(NUMVALS);
//			sancs[i] = Values.All[s]; sancranks[s] = i; //sancvals[i] = 0;
//		}
//	}
	public boolean upSanc(Value s) {return upSanc(s.ordinal());}
	public boolean upSanc(int s) {
		int plc = sancranks[s];
		if (plc > 0) {
			sancranks[s] = plc - 1;   sancranks[sancs[plc-1].ordinal()] = plc;
			Value tmp = sancs[plc-1]; sancs[plc-1] = sancs[plc]; sancs[plc] = tmp;
			return true;
		}	else {return false;} //already top
	}
	public boolean downSanc(Value s) {return downSanc(s.ordinal());}
	public boolean downSanc(int s) {
		int plc = sancranks[s];
		if (plc < NUMVALS-1) {
			sancranks[s] = plc + 1;   sancranks[sancs[plc+1].ordinal()] = plc;
			Value tmp = sancs[plc+1]; sancs[plc+1] = sancs[plc]; sancs[plc] = tmp;
			return true;
		}	else {return false;} //already bottom
	}
	
	public Value randomValueInPriority() {
		final int i = AGPmain.rand.nextInt(17);
		if (i == 16) {return sancs[AGPmain.rand.nextInt(sancs.length)];} //rando factor
		int v = FSM[getBeh(M_.STRICTNESS)][i];
		return sancs[v];
	}
	public Value randomValueInPriorityOtherThan(Value not) {
		int v = FSM[getBeh(M_.STRICTNESS)][AGPmain.rand.nextInt(16)];
		boolean hitNot = false;
		for (int i = 0; i < sancs.length - 1; i++) {
			if (sancs[i] == not) {hitNot = true;}
			tmpGarbage[i] = sancs[i + (hitNot ? 1 : 0)];
		}
		return tmpGarbage[v];
	}
	/** get value in ith priority */
	public Value getValue(int i) {return sancs[i];}
	/** get values ordered by priority */
	public Value[] getValues() {return sancs;}
	public int sumWeightOfValues(Value... V) {
		int w = 0;
		int[] wgts = WGTMAP[getBeh(M_.STRICTNESS)];
		for (Value v : V) {w += wgts[sancranks[v.ordinal()]];}
		return w;
	}
	public int weightOfValue(Value V) {
		return WGTMAP[getBeh(M_.STRICTNESS)][sancranks[V.ordinal()]];
	}
	public Value[] valuesInPriority() {
		int[] fsm = FSM[getBeh(M_.STRICTNESS)];
		Value[] result = new Value[fsm.length];
		for (int i = 0; i < fsm.length; i++) {result[i] = sancs[fsm[i]];}
		return result;
	}
	public Value valueInPriority(int i) {
		return sancs[FSM[getBeh(M_.STRICTNESS)][i]];
	}
	public Value strongerOf(Value A, Value B) {
		for (Value v : sancs) {if (v == A) return A; else if (v == B) return B;} return null;
	}
	public int compareValues(Value A, Value B) {
		for (Value v : sancs) {if (v == A) return -1; else if (v == B) return 1;} return 0;
	}

	/** 1 if eu > ele */
	public int compareRespect(Clan other) {
		int k;   int ihi;
		for(int i = getEu().useBeh(M_.PATIENCE) / 3; i >= 0; i--) {
			k = AGPmain.rand.nextInt(NUMVALS);
			ihi = (int) Math.signum(valueInPriority(k).compare(getEu(), getEu(), other));
			switch (ihi) {
				case -1: case 1: return ihi;
				default: break;
			}
		}
		return 0;
	}
	/** cognitive dissonance */
	public void reflect(Value sanc, Clan other) {
		int euvsele = (int) Math.signum(sanc.compare(getEu(), getEu(), other));
		switch (euvsele) {
		case -1: downSanc(sanc);   break;
		case 0: break;
		case 1: upSanc(sanc);   break;
		}
	}
	public void reflect(Clan other) {
		reflect(ArrayUtils.randomIndexOf(Values.All), other);
	}
//	public void reflectOLD(Clan other) {
//		int euvsele = 0; boolean lostonce = false; boolean tiedonce = false;
//		int skip = 1 + getEu().useBeh(M_.MADNESS);
//		int k = 0;
//		for(int i = getEu().useBeh(M_.PATIENCE); i >= 0; i--) {
//			euvsele = SancInPriority(k).compare(getEu(), other, getEu());
//			if (euvsele < 0) {lostonce = true;}
//			else if (euvsele == 0) {tiedonce = true;}
//			if (((euvsele >= 0) && (lostonce)) || ((euvsele > 0) && (tiedonce))) {
//				upSanc(sancs[k]);   return;
//			}
//			k += AGPmain.rand.nextInt(skip) + 1;
//			if (k >= numSancs) {return;}
//		}
//	}
	public static final int[][] FSM = {
		{0,0,1,1,2,2,3,3,4,4,5,5,6,7,8,9},
		{0,0,0,1,1,2,2,3,3,4,4,5,5,6,7,8},
		{0,0,0,1,1,2,2,3,3,4,4,5,6,7,8,9},
		{0,0,0,1,1,1,2,2,3,3,4,4,5,6,7,8},
		{0,0,0,1,1,1,2,2,3,3,4,4,5,5,6,7},
		{0,0,0,1,1,1,2,2,2,3,3,4,4,5,5,6},
		{0,0,0,1,1,1,2,2,2,3,3,3,4,4,5,6},
		{0,0,0,0,1,1,1,1,2,2,3,3,4,4,5,5},
		{0,0,0,0,1,1,1,1,2,2,2,3,3,3,4,4},
		{0,0,0,0,0,1,1,1,1,2,2,2,3,3,4,4},
		{0,0,0,0,0,0,1,1,1,1,2,2,2,3,3,4},
		{0,0,0,0,0,0,0,1,1,1,1,2,2,2,3,3},
		{0,0,0,0,0,0,0,0,1,1,1,1,2,2,2,3},
		{0,0,0,0,0,0,0,0,0,1,1,1,1,2,2,2},
		{0,0,0,0,0,0,0,0,0,0,1,1,1,1,2,2},
		{0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1}
	};
	public static final int[][] WGTMAP = calcWeightMap(FSM);
	private static int[][] calcWeightMap(int[][] fsm) { // only used once at startup
		int[][] result = new int[16][NUMVALS];
		for (int i = 0; i < 16; i++) {
			int[] f = fsm[i];
			for (int j = 0; j < f.length; j++) {
				result[i][f[j]]++;
			}
		}
		return result;
	}
	public int getSancPct(Value sanc) {
		return (int) Math.round(100.0 * weightOfValue(sanc) / FSM[0].length);
//		int count = 0;   int L = FSM[0].length;
//		for (int i = 0; i < L; i++) {
//			if (valueInPriority(i) == sanc) {count++;}
//		}
//		return (int) Math.round(100 * (double)count / L);
	}
	public int getSancPcts(Value[] Sncs, double[] Pcts) {
		int c = 1;   int n = 1;   int L = FSM[0].length;
		for (int i = 1; i < L; i++) {
			if (valueInPriority(i) != valueInPriority(i-1)) {
				Sncs[c - 1] = valueInPriority(i-1);
				Pcts[c - 1] = 100*(double) n / L;
				c++;   n = 1;
			} else {n++;}
		}
		Sncs[c-1] = valueInPriority(L-1);
		Pcts[c-1] = 100*(double) n / L;
		return c;
	}

	public String getDeusName() {
		int [] deus = getDeusV();
		return Naming.randGoblinSanctityName(deus);
	}
	public int[] getDeusV() {
		int [] deus = new int[3];
		deus[2] = (byte) (creed & 63);
		deus[1] = (byte) ((creed % 63) & 63);
		deus[0] = 0;
		return deus;
	}
	public int getDeusInt() {
		return ((creed % 63) & 63) * 100 + (creed & 63);
	}

	public int getCreed() {
		return creed;
	}

	public void setCreed(int creed) {
		this.creed = creed;
	}

	/** direction 1 means glorify, -1 means denounce */
	private static void preach(Clan listener, Value v, int direction) {
		if (direction == 1) {listener.FB.upSanc(v);}
		else if (direction == -1) {listener.FB.downSanc(v);}
		else {throw new IllegalStateException("Cant preach that way");}
	}
	private static boolean preach(Clan listener, Ideology ideology) {
		final Value[] ideologyVals = ideology.getValues();
		final Value[] listenerVals = listener.FB.getValues();
		final int[] ideologyValRanks = ideology.getValueRanks();
		final int[] listenerValRanks = listener.FB.getValueRanks();
		int maxdiff = 0; int bestI = -1;
		for (int i = 0; i < ideologyValRanks.length; i++) {
			final int diff = Math.abs(ideologyValRanks[i] - listenerValRanks[i]);
			if (diff > maxdiff) {maxdiff = diff; bestI = i;}
		}
		if (bestI < 0) {return false;}
		final Value valToPreach = ideologyVals[ideologyValRanks[bestI]];
		if (valToPreach != listenerVals[listenerValRanks[bestI]]) {throw new IllegalStateException("Shitty sanc arrays");}
		final int direction = - (int) Math.signum(ideologyValRanks[bestI] - listenerValRanks[bestI]);
		preach(listener, valToPreach, direction);
		return true;
	}
	public static boolean attemptPreach(Clan preacher, Clan listener, Ideology ideology) {
		if (listener.FB.compareRespect(preacher) > 0) {return preach(listener, ideology);}
		else {return false;}
	}
	
}

