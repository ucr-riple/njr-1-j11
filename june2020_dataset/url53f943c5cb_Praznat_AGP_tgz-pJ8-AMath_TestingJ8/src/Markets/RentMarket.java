package Markets;

import Defs.M_;
import Descriptions.Naming;
import Sentiens.Clan;
import Shirage.Shire;


public class RentMarket extends MktO {
	private int bestplc;
	
	public RentMarket(int ggg, Shire h) {super(ggg, h);} 
	
	private void refresh() {
		for (int i = 0; i < offerlen; i++) {Offers[i].makeUnrented();} //reclaim
	}
	
	public int bestOffer() {
		if (!anyUnrentedLeft()) {return NOASK;}
		else {return Offers[bestplc].px;}
	}
	
	@Override
	protected int estFairOffer(Clan doer) {
		int bestoffer = (anyUnrentedLeft() ? bestOffer() : offerFromNowhere(doer));
		double FlowPX = addSpread(bestoffer, imbalance()*RATES[doer.useBeh(M_.BIDASKSPRD)]);
		return fairPX(doer, FlowPX);
	}
	@Override
	protected void hitBid(Clan seller, int plc) {
		Entry bid = Bids[plc];
		int k = findPlcInV(bid.px, Offers, offerlen, Entry.OFFERDIR);
		offerlenUp();
		for(int i = offerlen; i > k; i--) {Offers[i].set(Offers[i-1]);}
		Offers[k].set(bid.px, seller);
		Offers[k].makeRented();
//		if (k == bestplc) {findNextUnused();}
		if (bid.trader == seller) {selfTransaction(seller, plc, Entry.BIDDIR); finish(); return;}
		addReport(seller.getNomen() + " tries to hit bid for " + Naming.goodName(g));
		int oldbidlen = bidlen;
		Clan tmpbuyer = bid.trader;
		if (plc != -1 && transaction(bid.trader, seller, bid.px)) {
			if (oldbidlen != bidlen) {
				addReport("transaction fucked up, buyer = " + tmpbuyer + ", seller = " + seller);
			}
			removeBid(plc);  //no loss of asset
		} else {sellFair(seller);}
	}
	
	public int placeOffer(Clan doer, int px){
		 //must recalculate "best", not sure if working
		int k = super.placeOffer(doer, px);
		bestplc = Math.min(bestplc, k);
		if (k == NOASK) {bestplc++;} // hit bid case
		finish();
		return k;
	}
	
	@Override
	public void sellFairAndRemoveBid(Clan seller) {}
	
	@Override
	public void chgOffer(int plc, int v) {
		int oldplc = plc;
		int newplc = chgEntry(plc, v, Offers, Entry.OFFERDIR);
		if(oldplc < bestplc && bestplc <= newplc) {bestplc--;}
		else if(oldplc > bestplc && bestplc >= newplc) {bestplc++;}
	}

	private boolean anyUnrentedLeft() {
		return bestplc < offerlen;
	}
	@Override
	public void removeOffers(int num) { //only used in clear market
		int i; for (i = 0; i < num; i++) {
			Offers[i].makeRented();
		}
		bestplc = i;
	}
	@Override
	public void removeOffer(int plc){
		Offers[plc].makeRented();
		if(plc == bestplc) {findNextUnused();}
		else if (plc < bestplc) {bestplc--;}
	}
	/** actually remove from market due to loss of asset */
	public void removeRental(Clan trader) {
		int k = findOffer(trader);
		if(k != -1) {
			super.removeOffer(k);
			if(k == bestplc) {findNextUnused();}
			else if (k < bestplc) {bestplc--;}
		}
		else {
			Log.warning(trader.getNomen() + " ERROR removeOffer(ENTRY NOT FOUND)");
		}
		finish();
	}
	@Override
	public void liftOffer(Clan buyer) {
		if (!isBestPlaceCorrect()) {
			finish();
			addReport("Serious problem with " + buyer.getNomen() + " trying to lift " + Naming.goodName(g));
			addReport(this.printOneLineStatus());
		}
		if (!anyUnrentedLeft()) {buyFair(buyer); return;}
		Entry offer = Offers[bestplc];
		if (offer.trader == buyer) {selfTransaction(buyer, bestplc, Entry.OFFERDIR); finish(); return;}
		addReport(buyer.getNomen() + " tries to lift offer for " + Naming.goodName(g));
		if (transaction(buyer, offer.trader, offer.px)) {
			offer.makeRented(); //designate as used
			findNextUnused();
		} else {buyFair(buyer);}
	}
	
	@Override
	protected void selfTransaction(Clan clan, int plc, int bidorask) {
		if (bidorask == Entry.BIDDIR) {removeBid(plc);}
		else if (bidorask == Entry.OFFERDIR) {Offers[plc].makeRented(); findNextUnused();}
		else {throw new IllegalArgumentException();}
		addReport(clan.getNomen() + " takes own " + (bidorask == Entry.BIDDIR ? "bid" : "offer") + " of " + Naming.goodName(g) + " from market");
		alterWMG(clan, 1);
	}
	
	/**
	protected boolean transaction(Clan buyer, Clan seller, int price) {
		if(seller == null || buyer == null) {return false;}
		seller.alterMillet(price);
		if (!buyer.alterMillet(-price)) {seller.alterMillet(-price); return false;}
		sendToInventory(buyer); //    OK for rent market
		Log.info(buyer.getNomen() + " buys " + Naming.goodName(this.g, false, false) + " from " + seller.getNomen() + " for " + price);
		return true;
	}
	**/
	private int solveBestPlc() {
		int best = 0;
		while (best < offerlen) {
			if (Offers[best].isRented()) {best++;}
			else {break;}
		}
		return best;
	}
	private void findNextUnused() {
		while (bestplc < offerlen) {
			if (Offers[bestplc].isRented()) {bestplc++;}
			else {return;}
		}
	}
	@Override
	public void clearMarket() {
		if (true) return;
		//rent offers dont remove them
		refresh();
		auction();
		// TODO must needs do clearing stuff here (likely source of problems)
		bestplc = 0;
	}
	
	@Override
	public String toString() {return bestplc + super.toString();}
	
	@Override
	public String printOneLineStatus() {
		return bestplc + "bst, " + super.printOneLineStatus();
	}
	
	/** for use in testing */
	public int numberUnrentedRemaining() {
		int n = 0; for (int i = 0; i < offerlen; i++) {if (!Offers[i].isRented()) {n++;}}
		return n;
	}
	public boolean isBestPlaceCorrect() {return bestplc == solveBestPlc();}
	
}
