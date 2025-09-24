package Markets;

import java.util.logging.Logger;

import Defs.Misc;
import Game.AGPmain;
import Sentiens.Clan;
import Shirage.Shire;

public abstract class MktAbstract implements Misc {
	
	protected static final int VOL_DISPLAY_PERIOD = 30; // report 30-day volume cuz 1 day is too small
	
	protected int g, periodVol; // <0 means hasnt had a single trade ever
	boolean hasTradedEver;
	protected Shire home;
	
	public int getGood() {return g;}
	public abstract int lastPrice();
	public abstract int stAvg();
	public abstract int ltAvg();
	public abstract int getBidSz();
	public abstract int getAskSz();
	public abstract int bestOffer();
	public abstract int bestBid();
	public abstract int sellablePX(Clan c);  //used in Logic
	public abstract int buyablePX(Clan c);  //used in Logic
	public abstract void buyFair(Clan buyer);
	public abstract void sellFair(Clan seller);
	public abstract void liftOffer(Clan buyer);
	public abstract void hitBid(Clan seller);
	public abstract void removeBid(int plc);
	public abstract void removeOffer(int plc);
	public abstract void chgOffer(int plc, int v);
	public abstract void chgBid(int plc, int v);
	protected abstract void auction();
	protected abstract void clearMarket();
	public String getReport() {return "";}
	
	public void newDay() {
		clearMarket();
		if (AGPmain.TheRealm.getDay() % VOL_DISPLAY_PERIOD == 0) {
			periodVol = 0;
		}
	}
	
	protected final Logger Log = Logger.getLogger(this.getClass().getName());

	public abstract void loseAsset(Clan me);
	public abstract void gainAsset(Clan me);
	public int getPeriodVol() {return periodVol;}
	
	
}