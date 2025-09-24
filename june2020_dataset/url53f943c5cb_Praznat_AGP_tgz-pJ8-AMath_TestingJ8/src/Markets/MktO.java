package Markets;


import AMath.Calc;
import Defs.*;
import Descriptions.*;
import Game.*;
import Questing.*;
import Sentiens.Clan;
import Shirage.Shire;

public class MktO extends MktAbstract {
	//this is for LIQUID NAMES i.e. not ones that are kept in private inv
	protected static double expandSZ = 0.75;
	protected static double contractSZ = 0.25;
	protected static final int DEFAULTPX = 100;
	protected static final int STARTSZ = 10;
	public static final double[] RATES = {0.01,0.0125,0.015,0.02,0.03,0.05,0.075,0.1,0.15,0.2,0.25,0.3,0.35,0.4,0.45,0.5};
	public static final int NOASK = Integer.MAX_VALUE / 2;
	public static final int NOBID = 0;
	public static final int NOBIDDERCANPAY = -1;
	
	private static final int TOO_MANY_TRANSACTIONS_THRESH = 10;
	private static final int reportG = Misc.poop;
	
	protected String report = "";
	
	protected int LTAvg, STAvg, LastPX, MaxPX, MinPX, offerlen, bidlen, bb;
	protected Entry[] Bids, Offers;
	
	public MktO() {}
	public MktO(int ggg, Shire h) {
		g = ggg;
		Offers = new Entry[STARTSZ];
		Bids = new Entry[STARTSZ];
		for(int i = 0; i < STARTSZ; i++) {
			Offers[i] = new Entry();
			Bids[i] = new Entry();
		}
		LastPX = DEFAULTPX;
		LTAvg = DEFAULTPX;
		STAvg = DEFAULTPX;
		MaxPX = 0;   MinPX = Integer.MAX_VALUE / 2;
		home = h;
	}
	
	public static int annuity(int val, Clan doer) {
		if (val <= 0) {return val;}
		int result = Calc.roundy((double)val / RATES[doer.useBeh(M_.RISKPREMIUM)]);
		return result >= 0 ? result : val;
	}
	public static int interest(int val, Clan doer) {
		if (val == NOASK) {return val;}
		return Calc.roundy((double)val * RATES[doer.useBeh(M_.RISKPREMIUM)]);
	}
	
	public int lastPrice() {return (hasTradedEver ? LastPX : -1);}
	public int stAvg() {return (hasTradedEver ? STAvg : -1);}
	public int ltAvg() {return (hasTradedEver ? LTAvg : -1);}
	public int getBidSz() {return bidlen;}
	public int getAskSz() {return offerlen;}
	public int bestOffer() {
		if (offerlen == 0) {return NOASK;}
		else {return Offers[0].px;}
	}
	public int bestBid() {
		if (bidlen == 0) {return NOBID;}
		int plc = bestBidPlc();  if(plc == NOBIDDERCANPAY) {return NOASK;}
		else {return Bids[plc].px;}
	}
	public static String bidString(int b) {return b == NOASK || b == NOBID ? "" : b+"";}
	public static String offerString(int b) {return b == NOASK ? "" : b+"";}
	public int bestBidPlc() {
		for(int i = 0; i < bidlen; i++) {if (Bids[i].px <= Bids[i].trader.getMillet()) {return i;}}
		return NOBIDDERCANPAY;
	}
	
	protected void updateAvgs(int p) {
		LastPX = p;
		LTAvg = (int) Math.round(0.2 * p + 0.8 * LTAvg);
		STAvg = (int) Math.round(0.5 * p + 0.5 * STAvg);
		MaxPX = (p > MaxPX ? p : MaxPX);
		MinPX = (p < MinPX ? p : MinPX);
		periodVol++; // -1 -> to 0 on first trade ever... :S cheapskate
		hasTradedEver = true;
	}

	public int buyablePX(Clan buyer) {
		int px = bestOffer();
		return px <= buyer.getAssets(Misc.millet) ? px : NOASK;
	}
	public int sellablePX(Clan seller) {
		return estFairOffer(seller);
	}
	public int riskySellPX(Clan clan) {
		double r = bidlen == 0 ? (clan.FB.getBeh(M_.RISKPREMIUM)+1) : 1;
		return (int) Math.round(estFairOffer(clan) / r);
	}
	public void buyFair(Clan buyer) {
		int px = Math.min(estFairBid(buyer), buyer.getAssets(Misc.millet));
		addReport(buyer.getNomen() + " tries to buy " + Naming.goodName(g) + " at fair price of " + px);
		if (px == 0) return;
		placeBid(buyer, px);
	}
	public void sellFair(Clan seller) {
		int px = estFairOffer(seller);
		addReport(seller.getNomen() + " tries to sell " + Naming.goodName(g) + " at fair price of " + px);
		//if(px <= bestBid() && px > NOBID) {hitBid(seller);}
		if(px<=0) {
			System.out.println("SHWIIIIT");
			throw new IllegalArgumentException("cant do this price: " + estFairOffer(seller));
		}
		else {placeOffer(seller, px);}
	}
	public void sellFairAndRemoveBid(Clan seller) {
		removeBids(seller);
		addReport(seller.getNomen() + " finds " + Naming.goodName(g) + " UNNECESSARY");
		placeOffer(seller, estFairOffer(seller));
	}
	public void liftOffer(Clan buyer) {liftOffer(buyer, 0);}
	protected void liftOffer(Clan buyer, int plc) {
		if (Offers[plc].trader == buyer) {selfTransaction(buyer, plc, Entry.OFFERDIR); finish(); return;}
		addReport(buyer.getNomen() + " tries to lift offer for " + Naming.goodName(g));
		Clan seller = Offers[plc].trader;
		if (transaction(buyer, seller, Offers[plc].px)) {
			loseAsset(seller);   removeOffer(plc);   finish();
		} else {buyFair(buyer);}
	}
	public void hitBid(Clan seller) {
		if (bidlen == 0) {sellFair(seller);}
		else {hitBid(seller, bestBidPlc());}
	}
	protected void hitBid(Clan seller, int plc) {
		addReport(seller.getNomen() + " tries to hit bid for " + Naming.goodName(g));
		if (Bids[plc].trader == seller) {selfTransaction(seller, plc, Entry.BIDDIR); finish(); return;}
		int oldbidlen = bidlen;
		if (plc != -1 && transaction(Bids[plc].trader, seller, Bids[plc].px)) {
			if (bidlen != oldbidlen) {addReport("seriously wtf");}
			loseAsset(seller);
			if (bidlen == oldbidlen) {removeBid(plc);} //sometimes self-transaction causes bid to disappear beforehand
			finish();
		} else {sellFair(seller);}
	}
	protected void selfTransaction(Clan clan, int plc, int bidorask) {
		if (bidorask == Entry.BIDDIR) {removeBid(plc);}
		else if (bidorask == Entry.OFFERDIR) {removeOffer(plc);}
		addReport(clan.getNomen() + " takes own " + (bidorask == Entry.BIDDIR ? "bid" : "offer") + " of " + Naming.goodName(g) + " from market");
		alterWMG(clan, 1);
	}
	protected boolean transaction(Clan buyer, Clan seller, int price) {
		if(seller == null || buyer == null) {return false;}
		if (buyer.getMillet() < price) {
			addReport(buyer.getNomen() + " has not enough millet to buy " + Naming.goodName(g));
			return false;
		}
		seller.alterMillet(price);
		buyer.alterMillet(-price);
		buyer.addReport(GobLog.transaction(g, price, true, seller));
		seller.addReport(GobLog.transaction(g, price, false, buyer));
		addReport(buyer.getNomen() + " buys " + Naming.goodName(this.g) + " from " + seller.getNomen() + " for " + price);
		sendToInventory(buyer); //, price);
		updateAvgs(price);
//		Log.info(buyer.getNomen() + " buys " + Naming.goodName(this.g) + " from " + seller.getNomen() + " for " + price);
		return true;
	}
	protected void sendToInventory(Clan buyer) { //, int px) {
		gainAsset(buyer);
		alterWMG(buyer, 1);
	}
	protected void alterWMG(Clan buyer, int n) {
		QStack qs = buyer.MB.QuestStack; int sz = qs.size();
		if (sz > 0) {
			for (Quest q : qs) {
				if (accountForGood(q, n)) {return;}
			}
//			Quest q = qs.peek();
//			if (accountForGood(q, n)) {return;}
//			if (sz > 1) {
//				q = qs.peekUp();  //might as well check one quest up
//				if (accountForGood(q, n)) {return;}
//			}
		}
		sellFairAndRemoveBid(buyer);  //in case current (and previous) quest is not GoodsAcquirable quest
	}
	private boolean accountForGood(Quest q, int n) {
		if (GoodsAcquirable.class.isAssignableFrom(q.getClass())) {((GoodsAcquirable) q).alterG(this, n); return true;}
		else {return false;}
	}
	protected int fairPX(Clan doer, double flow) {
		double TechPX = (STAvg + LTAvg + 2 * ((LastPX - STAvg) * doer.useBeh(M_.STMOMENTUM) + 
				(LastPX - LTAvg) * doer.useBeh(M_.LTMOMENTUM)) / 15) / 2;
		//if(bidlen + offerlen == 0) {flow = TechPX;}  //first disable TechPX if there has been no volume
		int C = doer.useBeh(M_.CONFIDENCE);
		int T = doer.useBeh(M_.TECHNICAL) + C;  int F = doer.useBeh(M_.FLOW) + 15 - C;
		int PX = flow > 0 ? Calc.AtoBbyRatio(TechPX, flow, T, T+F) : Calc.roundy(TechPX);
		int min = Assets.FVmin(doer, g);   final int max = Assets.FVmax(doer, g);
//		addReport(doer.getNomen() + " estimates fair price for " + Naming.goodName(g) + " in following manner:");
//		addReport(PX + " = [(Flow=" + flow + ")*" + F + " + " + "(Tech=" + TechPX + ")*" + T + "] / " + (T+F));
//		addReport(min == 0 && max == NOASK ? "" : ", bounded between " + min + " and " + max);
		if (min < 0) {
			Calc.p("what the fio");
			min = Assets.FVmin(doer, g); 
			throw new IllegalArgumentException();
		}
		return Math.min(Math.max(PX, min), max);
	}
	protected void checkPriceLegality(Clan doer, int px) {
		if(px <= 0 || px == NOASK){
			String s = "Illegal entry placed by " + doer.getNomen();
			addReport(s);
			throw new IllegalStateException(s);
		}
	}
	protected int estFairOffer(Clan doer) {
		int bestoffer = (offerlen > 0 ? bestOffer() : offerFromNowhere(doer));
		int result;
		if (bestoffer == NOBID) {result = fairPX(doer, 0);}
		else {
			double FlowPX = addSpread(bestoffer, imbalance()*RATES[doer.useBeh(M_.BIDASKSPRD)]);
			result = fairPX(doer, FlowPX);
		}
		return result;
	}
	protected int estFairBid(Clan doer) {
		int bestbid = (bidlen > 0 ? bestBid() : bidFromNowhere(doer));
		if (bestbid == NOASK) {return fairPX(doer, 0);}
		double FlowPX = addSpread(bestbid, imbalance()*RATES[doer.useBeh(M_.BIDASKSPRD)]);
		return fairPX(doer, FlowPX);
	}
	//buy inputs at estFairOffer or market offer
	//sell outputs at estFairOffer
	//liquidate at estFairBid
	
	protected int bidFromNowhere(Clan doer) {
		int fear = 15 - doer.useBeh(M_.PARANOIA);
		int bo = bestOffer();
		if (bo == NOASK) {return NOASK;}
		if(bo > MinPX) {return Calc.AtoBbyRatio(bo, MinPX, fear, 15);}
		else {return Calc.AtoBbyRatio(MinPX, bo, fear, 15);}
		//return Calc.roundy((Math.min(MinPX, bestOffer()) * risk + NOASK * (15-risk)) / 15);
	}
	protected int offerFromNowhere(Clan doer) {
		int fear = 15 - doer.useBeh(M_.PARANOIA);
		int bb = bestBid();
		if (bb == NOBID || bb == NOASK) {return NOBID;}
		if(bb < MaxPX) {return Calc.AtoBbyRatio(bb, MaxPX, fear, 15);}
		else return bb;
		//else {return Calc.AtoBbyRatio(MaxPX, bb, fear, 15);}
		//return Math.max(MaxPX, bestBid());
	}
	
	protected double imbalance() {
		//numbids vs numasks ... may be better to measure value than volume
		if(bidlen + offerlen == 0) {return 0;}
		else {return 2 * bidlen / (bidlen + offerlen) - 1;}
	}
	public void removeBids(Clan doer) {
		for(int i = 0; i < bidlen; i++) {
			if (Bids[i].trader == doer) {removeBid(i);}
		}
	}
	public void removeBids(int num) {
		for(int i = 0; i < bidlen; i++) {
			Bids[i].set(i + num < Bids.length ? Bids[i+num] : new Entry());
		}
		bidlenDown(num);
		addReport(num + " bids removed");
	}
	public void removeBid(int plc){
		for(int i = plc; i < bidlen; i++) {Bids[i].set(Bids[i+1]);}
		bidlenDown(1);
		addReport(Bids[plc] + " removed");
	}
	public void removeBid(Clan trader){
		int k = findBid(trader);
		if(k != -1) {removeBid(k);}
		else {Log.warning(trader.getNomen() + " ERROR removeBid(ENTRY NOT FOUND)");}
		finish();
	}
	public void removeOffers(int num) {
		for(int i = 0; i < offerlen; i++) {
			Offers[i].set(i + num < Offers.length ? Offers[i+num] : new Entry());
		}
		offerlenDown(num);
		addReport(num + " offers removed");
	}
	public void removeOffer(int plc){
		for(int i = plc; i < offerlen; i++) {Offers[i].set(Offers[i+1]);}
		offerlenDown(1);
		addReport(Offers[plc] + " removed");
	}

	public void gainAsset(Clan gainer) {
		if(g < numAssets) {
			addReport(gainer.getNomen() + " gains " + Naming.goodName(g));
//			Log.info(gainer.getNomen() + " gains " + Naming.goodName(g));
			Assets.gain(gainer, g);
		}
	}
	public void loseAsset(Clan loser) {
		if(g < numAssets) {
			addReport(loser.getNomen() + " loses " + Naming.goodName(g));
//			Log.info(loser.getNomen() + " loses " + Naming.goodName(g, false, false));
			Assets.lose(loser, g);
		}
	}
	protected int findBid(Clan doer) {
		for(int i = 0; i < bidlen; i++) {
			if(Bids[i].trader == doer) {return i;}
		}  return -1;
	}
	protected int findOffer(Clan doer) {
		for(int i = 0; i < offerlen; i++) {
			if(Offers[i].trader == doer) {return i;}
		}  return -1;
	}

	public void placeBid(final Clan doer, final int px){
		if(px >= bestOffer()) {liftOffer(doer); return;} // redundant!!
		checkPriceLegality(doer, px);
		doer.addReport(GobLog.limitOrder(g, px, true));
		addReport(doer.getNomen() + " places bid for " + Naming.goodName(g) + " at " + px);
		if(px >= bestOffer()) {liftOffer(doer); return;}
		int k = findPlcInV(px, Bids, bidlen, Entry.BIDDIR);
		bidlenUp();
		for(int i = bidlen; i > k; i--) {Bids[i].set(Bids[i-1]);}
		Bids[k].set(px, doer);
		finish();
//		Log.info(doer.getNomen() + " places bid for " + Naming.goodName(this.g, false, false) + " at " + px);
	}
	public int placeOffer(final Clan doer, final int px){
		checkPriceLegality(doer, px);
		doer.addReport(GobLog.limitOrder(g, px, false));
		addReport(doer.getNomen() + " places offer for " + Naming.goodName(g) + " at " + px);
		int bbp = bestBidPlc();
		if(bidlen > 0 && bbp != NOBIDDERCANPAY && px <= Bids[bbp].px) {hitBid(doer); return NOASK;}
		int k = findPlcInV(px, Offers, offerlen, Entry.OFFERDIR);
		offerlenUp();
		for(int i = offerlen; i > k; i--) {Offers[i].set(Offers[i-1]);}
		Offers[k].set(px, doer);
		finish();
		return k;
	}
	protected final void bidlenDown(int n) {
		if (bidlen < n) {
			System.out.println("ERROR TRYING TO MAKE NEGATIVE BIDLEN");
			throw new IllegalArgumentException(this + "");
		}
		bidlen -= n;
	}
	protected final void bidlenUp() {
		if (bidlen++ > Bids.length*expandSZ) {
			Entry[] tmp = new Entry[2*bidlen];
			System.arraycopy(Bids,0,tmp,0,Bids.length);
			for(int i = Bids.length; i < tmp.length; i++) {tmp[i] = new Entry();}
			Bids = tmp;
		}
	}
	protected final void offerlenDown(int n) {
		if (offerlen < n) {
			System.out.println("ERROR TRYING TO MAKE NEGATIVE OFFERLEN");
			throw new IllegalArgumentException();
		}
		offerlen -= n;
	}
	protected final void offerlenUp() {
		if (offerlen++ > Offers.length*expandSZ) {
			Entry[] tmp = new Entry[2*offerlen];
			System.arraycopy(Offers,0,tmp,0,Offers.length);
			for(int i = Offers.length; i < tmp.length; i++) {tmp[i] = new Entry();}
			Offers = tmp;
		}
	}
	public Entry alterEntry(Entry entry, int dir, int newPx) {
		Entry[] V = dir == Entry.BIDDIR ? Bids : (dir == Entry.OFFERDIR ? Offers : null);
		for (int plc = 0; plc < V.length; plc++) {
			if (entry == V[plc]) {
				int newplc = chgEntry(plc, newPx, V, dir);
				return V[newplc];
			}
		}
		return null;
	}
	protected int vchg(int plc, int px, Entry[] V, int dir) {
		int newplc = findPlcInV(px, V, (dir>0?offerlen:bidlen), dir);
		Clan oldie = V[plc].trader;
		if(newplc < plc) {
			for(int i = plc; i > newplc; i--) {V[i].set(V[i-1]);}
			V[newplc].set(px, oldie);
		}
		else if (newplc > plc) {
			for(int i = plc; i < newplc; i++) {V[i].set(V[i+1]);}
			V[newplc-1].set(px, oldie);
		}
		return newplc;
	}
	protected int chgEntry(int plc, int px, Entry[] bidorask, int dir) {
		Entry e = bidorask[plc];
		int newplc = vchg(plc, px, bidorask, dir);
		e.px = px;
		return newplc;
	}
	public void chgBid(int plc, int px) {chgEntry(plc, px, Bids, Entry.BIDDIR);}
	public void chgOffer(int plc, int px) {chgEntry(plc, px, Offers, Entry.OFFERDIR);}
	/** traders move to recalculated fair prices */
	protected void auction() {
		for (int i = 0; i < bidlen; i++) {if(Bids[i].trader!=null){chgBid(i, estFairBid(Bids[i].trader));}}
		for (int i = 0; i < offerlen; i++) {if(Offers[i].trader!=null){chgOffer(i, estFairOffer(Offers[i].trader));}}
	}
	public void clearMarket() {
		   //dont forget to clear bids&offers from same trader (who isnt market maker)
			//also should probably handle unwanted bids here
		auction();
//		addReport("Clearing " + Naming.goodName(g) + " market " + RET + this.printOneLineStatus());
//		finish();
		Entry bid, ask; int i = 0;
		while (true) {
			if (i >= offerlen || i >= bidlen) {return;} // TODO probably shouldnt be return?? unit test!
			bid = Bids[i]; ask = Offers[i];
			if (bid.px >= ask.px) {
				if (bid.trader == ask.trader) {selfTransaction(bid.trader, 0, Entry.AUCTION);}
				transaction(bid.trader, ask.trader, (bid.px + ask.px) / 2);
				i++;
			}
			else {break;}
		}
		// remove up to i
		removeBids(i);   removeOffers(i);

		//finish day stuff
		if (getPeriodVol() > TOO_MANY_TRANSACTIONS_THRESH) {
			System.out.println("TOO MANY MARKET REPORTS... INF LOOP?   " + report);
		}
	}
	protected int addSpread(int px, double s) {int result = (int) Math.round((double)px * (1+s)); return (result > 0 ? result : px);}

	//	/** for cases like [Praznat@42, null, null, Praznat@42, null, null] dunno why it would even happen, TODO maybe case where bids and he cant pay so later ones get taken? */
//	public void clearAllNulls(Entry[] entries) {
//		for (int i = entries.length - 2; i >= 0; i--) {
//			if (entries[i] == null && entries[i+1] != null) {
//				for (int j = i; j < entries.length - 1; j++) entries[j] = entries[j+1];
//				entries[entries.length - 1] = null;
//				bidlenDown(1);
//			}
//		}
//	}
	
	public String[][] getBaikai() {
		String[][] B = new String[1+offerlen+bidlen][3];
		B[0][0] = "trader";B[0][1] = "px";B[0][2] = "trader";
		int k = 1;
		for (int i = offerlen-1; i>=0; i--) {
			B[k][0] = (Offers[i].trader!=null ? ""+ Offers[i].trader.getNomen() : "");
			B[k][1] = (Offers[i].trader!=null ?  ""+ Offers[i].px : "");
			B[k++][2] = "";
		}
		for (int i = 0; i<bidlen; i++) {
			B[k][0] = "";
			B[k][1] = (Bids[i].trader!=null ? ""+ Bids[i].px : "");
			B[k++][2] = (Bids[i].trader!=null ? ""+ Bids[i].trader.getNomen() : "");
		}
		return B;
	}
	public void printBaikai() {
		System.out.println(Naming.goodName(g) + " BAIKAI");
		System.out.println("Offers: "+offerlen);
		System.out.println("Bids: "+bidlen);
		Calc.printArray(getBaikai());
	}
	
	protected static int findPlcInV(int x, Entry[] V, int Vlen, int dir) {
		if(Vlen==0){return 0;}
		// dir+ for asks, dir- for bids
		//int dir = (int)Math.signum(V[Vlen-1].px - V[0].px);
		int lo = 0; int hi = Vlen - 1;
		int mid; int midpx;  int cur = -1;
		while (true) {
			if ((lo+hi)/2 < 0) {
				Calc.p("this sucks");
				throw new IllegalArgumentException();
			}
			midpx = V[mid =(lo+hi)/2].getPX();
			if(cur==mid) {break;} else {cur = mid;}
			if (dir*x < dir*midpx) {hi = cur;}
			else if (dir*x > dir*midpx) {lo = cur;}
		}
		while (dir*V[cur].getPX() <= dir*x) {cur++; if(cur==0||cur==Vlen){break;}}
		return cur;
	}
	
	/**
	 * use when string of market actions is finished to get complete detailed briefing
	 **/
	public void finish() {
		report += this + RET;
		for (int i = 0; i < bidlen; i++) {report += Bids[i] + ", ";} report += RET;
		for (int i = 0; i < offerlen; i++) {report += Offers[i] + ", ";} report += RET;
		if (AGPmain.mainGUI != null
				&& home == AGPmain.mainGUI.SM.getShire()
				&& (reportG < 0 || g == reportG)) {Log.info(report + RET);}
		report = "";
	}
	
	protected void addReport(String s) {
		if (AGPmain.mainGUI != null
				&& home == AGPmain.mainGUI.SM.getShire()
				&& (reportG < 0 || g == reportG)){
//				) {Calc.p(s);}
			report += s + RET;
		}
	}
	
	@Override
	public String getReport() {
		return report;
	}
	
	@Override
	public String toString() {
		return Naming.goodName(g) + " b/a: " + bidString(bestBid()) + "/" + offerString(bestOffer());
	}
	
	public String printOneLineStatus() {
		String s = "";
		for (int i = bidlen - 1; i >= 0; i--) {s += Bids[i]+" ";} s+="/";
		for (int i = 0; i < offerlen; i++) {s += " "+Offers[i];}
		return s;
	}
	
	
	/** for testing only */
	public int findNumberOf(Clan trader, int type) {
		Entry[] entries = null; int max = 0;
		if (type == Entry.BIDDIR) {entries = Bids; max = bidlen;}
		else if (type == Entry.OFFERDIR) {entries = Offers; max = offerlen;}
		else {throw new IllegalStateException("pick bid or offer");}
		int n = 0;
		for (int i = 0; i < max; i++) {if (entries[i].trader == trader) {n++;}}
		return n;
	}

}

