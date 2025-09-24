package Markets;

import Defs.Misc;
import Descriptions.Naming;
import Descriptions.XWeapon;
import Game.*;
import Sentiens.Clan;
import Shirage.Shire;

public class XWeaponMarket extends MktO {
	
	private short tmpXW;

	public XWeaponMarket(Shire h) {
		g = Misc.xweapon;   //this is where its overridden
		Offers = new EntryB[STARTSZ];   //this is where its overridden
		Bids = new EntryB[STARTSZ];   //this is where its overridden
		for(int i = 0; i < STARTSZ; i++) {
			Offers[i] = new EntryB();   //this is where its overridden
			Bids[i] = new EntryB();   //this is where its overridden
		}
		LastPX = DEFAULTPX;
		LTAvg = DEFAULTPX;
		STAvg = DEFAULTPX;
		MaxPX = 0;   MinPX = Integer.MAX_VALUE;
		home = h;
	}
	
	

	@Override
	public void placeBid(Clan doer, int px){
		if(px<0){px = 1/0;}
		report += doer.getNomen() + " places bid for " + Naming.goodName(g) + " at " + px + RET;
		if(px >= bestOffer()) {liftOffer(doer); return;}
		int k = findPlcInV(px, Bids, bidlen, -1);
		bidlenUp();
		for(int i = bidlen; i > k; i--) {Bids[i].set(Bids[i-1]);}
		((EntryB)Bids[k]).set(px, doer, tmpXW);   //this is where its overridden
		Log.info(doer.getNomen() + " places bid for " + Naming.goodName(this.g, false, false) + " at " + px);
	}
	@Override
	public int placeOffer(Clan doer, int px){
		if(px<0){px = 1/0;}
		report += doer.getNomen() + " places offer for " + Naming.goodName(g) + " at " + px + RET;
		int bbp = bestBidPlc();
		if(bbp > -1 && px <= Bids[bbp].px) {hitBid(doer); return Integer.MAX_VALUE;}
		int k = findPlcInV(px, Offers, offerlen, 1);
		offerlenUp();
		for(int i = offerlen; i > k; i--) {Offers[i].set(Offers[i-1]);}
		((EntryB)Offers[k]).set(px, doer, tmpXW);   //this is where its overridden
		Log.info(doer.getNomen() + " places offer for " + Naming.goodName(this.g, false, false) + " at " + px);
		return k;
	}
	@Override
	protected int vchg(int plc, int px, Entry[] V, int dir) {
		int newplc = findPlcInV(px, V, (dir>0?offerlen:bidlen), dir);
		Clan oldie = V[plc].trader;
		if(newplc < plc) {
			for(int i = plc; i > newplc; i--) {V[i].set(V[i-1]);}
			((EntryB)V[newplc]).set(px, oldie, tmpXW);   //this is where its overridden
		}
		else if (newplc > plc) {
			for(int i = plc; i < newplc; i++) {V[i].set(V[i+1]);}
			((EntryB)V[newplc-1]).set(px, oldie, tmpXW);   //this is where its overridden
		}
		return newplc;
	}
	
	@Override
	public void gainAsset(Clan gainer) {
		if(g < numAssets) {
			report += gainer.getNomen() + " gains " + XWeapon.weaponName(tmpXW) + RET;
			Assets.gainXWeapon(gainer, tmpXW);
		}
	}
	@Override
	public void loseAsset(Clan loser) {
		if(g < numAssets) {
			report += loser.getNomen() + " loses " + XWeapon.weaponName(tmpXW) + RET;
			Assets.loseXWeapon(loser, tmpXW);
		}
	}
	
	/**
	 * probably should use before every time you use this kind of market
	 */
	public void setUpTmpXP(short x) {tmpXW = x;}
	
	@Override
	public void finish() {
		super.finish();
		tmpXW = XWeapon.NULL;
	}
}
