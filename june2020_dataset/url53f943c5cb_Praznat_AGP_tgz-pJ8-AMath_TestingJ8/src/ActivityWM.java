//package Game;
//
//import Sentiens.Clan;
//
//public class ActivityWM implements Defs {
//	private static final int MAXTRADES = 10;
//
//	private static int[] tmpP = new int[1 + MAXTRADES];
//	private static int[] tmpL = new int[1 + MAXTRADES];
//	//FX cost can be simplified as follows:
//	//before: (outputs o1, o2, o3) (intputs i1, i2) (fx k1, k2)
//	//after: (outputs o1, o2, o3, k1, k2) (inputs i1, i2, k1, k2)
//	
//	//into Defs:
//	
//	//pre-existing:
//
//	public static final ActivityWM A1 = new ActivityWM(new int[] {0}, new int[] {0});
//	public static final ActivityWM A2 = new ActivityWM(new int[] {}, new int[] {});
//	public static final ActivityWM A3 = new ActivityWM(new int[] {}, new int[] {});
//	public static final ActivityWM NullA = new ActivityWM(new int[] {}, new int[] {});
//
//	public static final ActivityWM[] J1 = new ActivityWM[] {A1};
//	public static final ActivityWM[] J2 = new ActivityWM[] {A1, A2, A3};
//	
//	private int[] input, output; //actually will be Logic
//	
//	public ActivityWM(int[] in, int[] out) {
//		input = in; output = out;
//	}
//	
//	public int[] expP() {
//		//{fill txpP with probE * probS * expPrice, determined outGs, -1s}
//		//for(int i = tmpP.length-1; i >= 0; i--) { tmpP[i] = -1;}
//		System.arraycopy(output, 0, tmpP, 1, output.length);
//		tmpP[output.length + 1] = -1;
//		tmpP[0] = 100;
//		return tmpP;
//	}
//	public int[] expL() {
//		//{fill txpL with sum of expPrices, determined inGs, -1s}
//		//for(int i = tmpL.length-1; i >= 0; i--) { tmpL[i] = -1;}
//		System.arraycopy(input, 0, tmpL, 1, input.length);
//		tmpL[input.length + 1] = -1;
//		tmpL[0] = 50;
//		return tmpL;
//	}
//
//
//
//	//REITs
//	//not in Reap Act but separate Act (b/c bestBid():annuity)
//	//num shares data kept in market instance
//	//place in market found by search
//	//OK to lose data from Q, can always reload from market instance
//
//
//	
//}
//
//
//
//
//
//
//
//
//
//
//class MktN implements Defs {
//	//this is for LIQUID NAMES i.e. not ones that are kept in private inv
//	public static double[] RATES = {0.01,0.0125,0.015,0.02,0.03,0.05,0.075,0.1,0.15,0.2,0.25,0.3,0.35,0.4,0.45,0.5};
//	
//	protected static double expandSZ = 0.75;
//	protected static double contractSZ = 0.25;
//	protected static final int DEFAULTPX = 100;
//	
//	protected int g, LTAvg, STAvg, LastPX, offerlen, bidlen, bb;
//	protected int[] offers;
//	protected Clan[] offerers;
//	protected int[] bids;
//	protected Clan[] bidders;
//	
//	public MktN(int ggg) {
//		g = ggg;
//		offers = new int[] {};
//		offerers = new Clan[] {};
//		bids = new int[] {};
//		bidders = new Clan[] {};
//		LastPX = DEFAULTPX;
//		LTAvg = DEFAULTPX;
//		STAvg = DEFAULTPX;
//	}
//	
//	public int lastPrice() {return LastPX;}
//	public int bestOffer() {return offers[0];}
//	public int bestBid() {return bids[bestBidPlc()];}
//	public int bestBidPlc() {
//		for(int i = 0; i < bidlen; i++) {if (bids[i] < bidders[i].getMillet()) {return i;}}
//		return -1;
//	}
//	
//	public void updateAvgs(int p) {
//		LTAvg = (int) Math.round(0.2 * p + 0.8 * LTAvg);
//		STAvg = (int) Math.round(0.5 * p + 0.5 * STAvg);
//		LastPX = p;
//	}
//
//
//
//	public void buy(Clan buyer) {
//		int px = estFairBid(buyer);
//		if(px >= bestOffer()) {liftOffer(buyer);}
//		else {placeBid(buyer, px);}
//	}
//	public void sell(Clan seller) {
//		int px = estFairOffer(seller);
//		if(px <= bestBid()) {hitBid(seller);}
//		else {placeOffer(seller, px);}
//	}
//	
//	public void liftOffer(Clan buyer) {liftOffer(buyer, 0);}
//	protected void liftOffer(Clan buyer, int plc) {
//		transaction(buyer, offerers[plc], offers[plc], plc);
//		removeOffer(plc);
//	}
//	public void hitBid(Clan seller) {hitBid(seller, bestBidPlc());}
//	protected void hitBid(Clan seller, int plc) {
//		transaction(bidders[plc], seller, bids[plc], plc);
//		removeBid(plc);
//	}
//	protected void transaction(Clan buyer, Clan seller, int price, int plc) {
//		seller.alterMillet(price);
//		//if (!buyer.alterMillet(-price)) {seller.alterMillet(-price); return;}
//		sendToInventory(buyer, price);
//		updateAvgs(price);
//	}
//	protected void sendToInventory(Clan buyer, int px) {
//		placeOffer(buyer, addSpread(px, buyer.useBeh(BIDASKSPRD)));
//	}
//	protected int fairPX(Clan doer, double flow) {
//		double TechPX = STAvg + LTAvg + 2 * ((LastPX - STAvg) * doer.useBeh(STMOMENTUM) + 
//				(LastPX - LTAvg) * doer.useBeh(LTMOMENTUM)) / 15;
//		int T = doer.useBeh(TECHNICAL);  int F = doer.useBeh(FLOW);
//		int PX = (int)Math.round((T * TechPX + F * flow) / (T + F));
//		return (PX <= doer.getMillet() ? PX : doer.getMillet());
//	}
//	public int estFairOffer(Clan doer) {
//		double FlowPX = subtractSpread(bestOffer(), imbalance()*RATES[doer.useBeh(BIDASKSPRD)]);
//		return fairPX(doer, FlowPX);
//	}
//	public int estFairBid(Clan doer) {
//		double FlowPX = addSpread(bestBid(), imbalance()*RATES[doer.useBeh(BIDASKSPRD)]);
//		return fairPX(doer, FlowPX);
//	}
//
//	protected double flowEst(Clan doer) {
//		if (bidlen * offerlen != 0) {
//			double R = imbalance();
//			return (bestOffer() * (2*R+1) - bestBid() * (2*R-1)) / 2;
//		}
//		if(bidlen > offerlen) {return addSpread(bestBid(), doer.useBeh(BIDASKSPRD));}
//		if(bidlen < offerlen) {return subtractSpread(bestOffer(), doer.useBeh(BIDASKSPRD));}
//		return LastPX;
//	}
//	protected double imbalance() {
//		//numbids vs numasks ... may be better to measure value than volume
//		if(bidlen + offerlen == 0) {return 0;}
//		else {return 2 * bidlen / (bidlen + offerlen) - 1;}
//	}
//	
//	public void removeBid(int plc){
//		System.arraycopy(bids,plc+1,bids,plc,bidlen-plc);
//		System.arraycopy(bidders,plc+1,bidders,plc,bidlen-plc);
//		bidlenDown();
//	}
//	public void removeOffer(int plc){
//		System.arraycopy(offers,plc+1,offers,plc,offerlen-plc);
//		System.arraycopy(offerers,plc+1,offerers,plc,offerlen-plc);
//		offerlenDown();
//	}
//	
//	protected void placeBid(Clan doer, int px){
//		if(px<0){px = 1/0;}
//		if(px >= bestOffer()) {liftOffer(doer); return;}
//		int k = findPlcInV(px, bids, bidlen);
//		System.arraycopy(bids,k,bids,k+1,bidlen-k);
//		System.arraycopy(bidders,k,bidders,k+1,bidlen-k);
//		bids[k] = px;  bidders[k] = doer;
//		bidlenUp();
//	}
//	protected void placeOffer(Clan doer, int px){
//		if(px<0){px = 1/0;}
//		int bbp = bestBidPlc();
//		if(px <= bids[bbp]) {hitBid(doer); return;}
//		int k = findPlcInV(px, offers, offerlen);
//		System.arraycopy(offers,k,offers,k+1,offerlen-k);
//		System.arraycopy(offerers,k,offerers,k+1,offerlen-k);
//		offers[k] = px;  offerers[k] = doer;
//		offerlenUp();
//	}
//	protected void bidlenDown() {bidlen--;}
//	protected void bidlenUp() {
//		if (bidlen++ > bids.length*expandSZ) {
//			int[] tmp = new int[2*bidlen];Clan[] tmpC = new Clan[2*bidlen];
//			System.arraycopy(bids,0,tmp,0,bids.length);
//			System.arraycopy(bidders,0,tmpC,0,bidders.length);
//			bids = tmp; bidders = tmpC;
//		}
//	}
//	protected void offerlenDown() {bidlen--;}
//	protected void offerlenUp() {
//		if (offerlen++ > offers.length*expandSZ) {
//			int[] tmp = new int[2*offerlen];Clan[] tmpC = new Clan[2*offerlen];
//			System.arraycopy(offers,0,tmp,0,offers.length);
//			System.arraycopy(offerers,0,tmpC,0,offerers.length);
//			offers = tmp; offerers = tmpC;
//		}
//	}
//	protected static int vchg(int plc, int px, int[] V, Clan[] CV) {
//		int newplc = findPlcInV(px, V, V.length);
//		Clan oldie = CV[plc];
//		if(newplc < plc) {
//			System.arraycopy(V,newplc,V,newplc+1,plc-newplc);   V[newplc] = px;
//			System.arraycopy(CV,newplc,CV,newplc+1,plc-newplc);   CV[newplc] = oldie;
//		}
//		else {System.arraycopy(V,plc+1,V,plc,newplc-plc);   V[newplc-1] = px;
//			System.arraycopy(CV,plc+1,CV,plc,newplc-plc);   CV[newplc-1] = oldie;}
//		return newplc;
//	}
//	protected int chgOffer(int plc, int px) {
//		return vchg(plc, px, offers, offerers);
//	}
//	protected int chgBid(int plc, int px) {
//		return vchg(plc, px, bids, bidders);
//	}
//	public void auction() {
//		for (int i = 0; i < bidlen; i++) {chgBid(i, estFairBid(bidders[i]));}
//		for (int i = 0; i < offerlen; i++) {chgOffer(i, estFairOffer(offerers[i]));}
//		//clear mkt
//	}
//
//	protected int addSpread(int px, double s) {return (int) Math.round((double)px * (s + 100)) / 100;}
//	protected int subtractSpread(int px, double s) {return (int) Math.round((double)px * (-s + 100)) / 100;}
//	
//	
//	protected static int findPlcInV(int x, int[] V, int Vlen) {
//		if(Vlen==0){return 0;}
//		int dir = (int)Math.signum(V[Vlen-1] - V[0]);
//		int lo = 0; int hi = Vlen - 1;
//		int mid; int midpx;  int cur = -1;
//		while (true) {
//			midpx = V[mid =(lo+hi)/2];
//			if(cur==mid) {break;} else {cur = mid;}
//			if (dir*x < dir*midpx) {hi = cur;}
//			else if (dir*x > dir*midpx) {lo = cur;}
//		}
//		while (dir*V[cur] <= dir*x) {cur++; if(cur==0||cur==Vlen){break;}}
//		return cur;
//	}
//}
//
//
//
//
//
//
//
//
//
//class ReitMkt extends MktN {
//	private int best; //first unused offer
//
//	
//	public ReitMkt() {super(108);} //or whatever # reit is
//	
//	public void refresh() {
//		for (int i = 0; i < offers.length; i++) {offers[i] = Math.abs(offers[i]);} //reclaim
//		best = 0;
//	}
//	public int bestOffer() {return offers[best];}
//	
//	
//	
//	public int chgOffer(int plc, int v) {
//		int oldplc = plc;
//		int newplc = super.chgOffer(plc, v);
//		if(oldplc < best && best <= newplc) {best--;}
//		else if(oldplc > best && best >= newplc) {best++;}
//		return v;
//	}
//	
//	public void liftOffer(Clan buyer) {
//		if (best < offers.length) { //otherwise all used
//			transaction(buyer, offerers[best], offers[best], best);
//			offers[best] = -offers[best]; //designate as used
//			findNextUnused();
//		}
//	}
//	private void findNextUnused() {
//		while (best < offers.length) {
//			if (offers[best] < 0) {best++;}
//			else {return;}
//		}
//	}
//}
//
//
//
//
//
//
//
//
//
//
//class Q {
//	private Clan eu;
//	private static final int MEMORY = 30; // >= 2*MAXTRADES
//	private ActivityWM chosenAct = ActivityWM.NullA;
//	
//	private int STAGE = 0; //proper Q variable
//	
//	public Q(Clan c) {
//		eu = c;
//		resetWM();
//	}
//
//	//WORKMEMO should include ALL possible g for A
//	private int[] WORKMEMO = new int [MEMORY]; //stock id
//	private int[] WORKMEMOX = new int [MEMORY];//stock count
//	//keeps track of stocks owned
//	//only for inputs
//	//outputs released in market and handled there
//	
//
//	public void resetWM() {
//		for(int i = 0; i < WORKMEMO.length; i++) {WORKMEMO[i] = -1;   WORKMEMOX[i] = 0;}
//	}
//	public void setWM(int g, int plc) {
//		WORKMEMO[plc] = g;   WORKMEMOX[plc] = 0;
//	}
//	public void getG(int g) {
//		for(int i = 0; i < WORKMEMO.length; i++) {
//			if (WORKMEMO[i] == g) {WORKMEMOX[i]++;   return;}
//		}
//	}
//	public void liquidateWM() {
//		resetWM();  //and sell all to market
//	}
//	
//	public void setChosenAct(ActivityWM a) {
//		if (!chosenAct.equals(a)) {liquidateWM();} //new WORKMEMO
//		chosenAct = a;
//	}
//	
//	//MAKE NUM REITS OWNED SANC
//	//REMOVE SETLREIT FROM AG ACT
//	private static ActivityWM compareTrades(Clan doer, ActivityWM[] actSet) {
//		ActivityWM curAct;   ActivityWM bestAct = null;   int bestPL = 0;
//		for(int i = 0; i < actSet.length; i++) {
//			curAct = actSet[i];
//			int PL = curAct.expP()[0] - curAct.expL()[0]; //fills static fields
//			if (PL > bestPL) {bestPL = PL; bestAct = curAct;}
//		}
//		return bestAct;
//	}
//
//	
//	public void DOTRADE() {
//		
//		switch (STAGE) {
//			case 0: ChooseAct();
//			case 1: DoInputs(); break;
//			case 2: DoWork(); break;
//			case 3: DoOutputs(); break;
//			default: break;
//		}
//		
//
//		
//		
//		if (true) {}
//		
//		//if TIMELEFT==0, hit bids on sells, remove bids on buys
//		
//	}
//	public void ChooseAct() {
//		//setChosenAct(compareTrades(eu, eu.getJobM()));
//		//fill WORKMEMO with every possible g in A:
//		for (int i = 0; i < WORKMEMO.length; i++) {
//			WORKMEMO[i] = -1; WORKMEMOX[i] = 0;
//		}   WORKMEMO[0] = 0;   WORKMEMOX[0] = 0;
//		STAGE++;
//	}
//	public void DoInputs() {
//		int i = -1;
//		while (WORKMEMO[++i] != -1) {eu.myMkt(WORKMEMO[i]).liftOffer(eu);}
//		System.out.println("Clan"+eu.getID());
//		i = -1;   while (WORKMEMO[++i] != -1) {System.out.print(WORKMEMOX[i]+"   ");}
//		System.out.println();
//		STAGE++;
//	}
//	public void DoWork() {
//		STAGE++;
//	}
//	public void DoOutputs() {
//		
//		STAGE++;
//	}
//	public void Finish() {
//		resetWM();
//		chosenAct = ActivityWM.NullA;
//	}
//	
//	
//	
//	
//	
//	
//	public static final int[][] FSM = {
//		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//		{0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1},
//		{0,0,0,0,0,0,0,0,0,0,1,1,1,1,2,2},
//		{0,0,0,0,0,0,0,0,1,1,1,1,2,2,2,2},
//		{0,0,0,0,0,0,0,1,1,1,1,2,2,2,3,3},
//		{0,0,0,0,0,0,1,1,1,1,2,2,2,3,3,4},
//		{0,0,0,0,0,1,1,1,1,2,2,2,3,3,4,4},
//		{0,0,0,0,1,1,1,1,2,2,2,3,3,3,4,4},
//		{0,0,0,0,1,1,1,1,2,2,3,3,4,4,5,5},
//		{0,0,0,1,1,1,2,2,2,3,3,3,4,4,5,6},
//		{0,0,0,1,1,1,2,2,2,3,3,4,4,5,5,6},
//		{0,0,0,1,1,1,2,2,3,3,4,4,5,5,6,7},
//		{0,0,0,1,1,1,2,2,3,3,4,4,5,6,7,8},
//		{0,0,0,1,1,2,2,3,3,4,4,5,5,6,7,8},
//		{0,0,0,1,1,2,2,3,3,4,4,5,6,7,8,9},
//		{0,0,1,1,2,2,3,3,4,4,5,5,6,7,8,9},
//	};
//	
//}