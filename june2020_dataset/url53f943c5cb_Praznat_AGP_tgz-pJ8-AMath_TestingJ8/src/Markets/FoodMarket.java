package Markets;

import Defs.Misc;
import Descriptions.Naming;
import Sentiens.Clan;
import Shirage.Shire;

public class FoodMarket extends MktAbstract {
	public static final int MILLETVAL = 500;
	public static final int MEATVAL = 1500;  //may not use
	private int val;

	public FoodMarket(int ggg, Shire h) {
		g = ggg;
		home = h;
		switch (ggg) {
		case Misc.millet: val = MILLETVAL; break;
		case Misc.meat: val = MEATVAL; break;
		default: val = 0; break;
		}
	} 
	
	public void gainAsset(Clan me) {} //nothing happens (called in Quest DoOutputs)
	private void sellFood(Clan seller) {
		seller.alterMillet(val);
	}
	
	public void sellFair(Clan seller) {
		sellFood(seller);
	}
	public int sellablePX(Clan seller) {
		return val;
	}

	public void hitBid(Clan seller) {sellFood(seller);}
	protected void hitBid(Clan seller, int plc) {sellFood(seller);}
	
	
	
	private void error() {Log.warning("Attempting illegal action in Food Market for " + Naming.goodName(g, true, true));}
	
	public int lastPrice() {return val;}
	public int stAvg() {return val;}
	public int ltAvg() {return val;}
	public int getBidSz() {return Integer.MAX_VALUE;}
	public int getAskSz() {return 0;}
	public int bestOffer() {return val;}
	public int bestBid() {return val;}
	public int buyablePX(Clan c) {error();return val;}
	public void buyFair(Clan buyer) {error();}
	public void liftOffer(Clan buyer) {error();}
	public void removeBid(int plc) {error();}
	public void removeOffer(int plc) {error();}
	public void removeOffer(Clan c) {error();}
	public void chgOffer(int plc, int v) {error();}
	public void chgBid(int plc, int v) {error();}
	protected void auction() {error();}
	public void clearMarket() {} // do nothing
	public void loseAsset(Clan me) {error();}
}
