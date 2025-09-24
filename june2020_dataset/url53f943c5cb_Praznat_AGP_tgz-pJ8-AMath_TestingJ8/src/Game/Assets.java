package Game;

import Defs.Misc;
import Descriptions.XWeapon;
import Markets.*;
import Sentiens.Clan;

public class Assets implements Misc {
	
	public static int getRentGood(int asset) {
		switch (asset) {
		case land:   return rentland;
		case bovad: case donkey: case lobodonkey: return rentanimal;
		default: return -1;
		}
	}

	public static void gainXWeapon(Clan doer, short weapon) {
		//IF u like it better than ur current one
		equip(doer, weapon);
	}
	public static void loseXWeapon(Clan doer, short weapon) {
		unequip(doer, weapon);
	}
	public static void gain(Clan doer, int good) {
		gain(doer, good, 1);   // rent only if number of goods is 1
		// should be placing offer for good here (rather than after this method),
		// so that if it gets done you dont have to place the rent good
		int rg = getRentGood(good);
		if (rg != -1) {doer.myMkt(rg).sellFair(doer);}
	}
	public static void lose(Clan doer, int good) {
		lose(doer, good, 1);   // rent only if number of goods is 1
		int rg = getRentGood(good);
		if (rg != -1) {((RentMarket)doer.myMkt(rg)).removeRental(doer);}
	}
	public static void gain(Clan doer, int good, int N) {
		doer.incAssets(good, N);
	}
	public static void lose(Clan doer, int good, int N) {
		doer.decAssets(good, N);
	}
	private static void equip(Clan doer, short w) {
		doer.setXWeapon(w);
	}
	private static void unequip(Clan doer, short w) {
		doer.setXWeapon(XWeapon.NULL);
		//make sure this doesnt conflict with actual weapon. will have to remove one weapon description at some point
	}
	public static int FVmin(Clan doer, int good) {
		switch (good) {
		case land:   return MktO.annuity(getBestBidOrBestOffer(doer, rentland, true), doer);
		case bovad:   return MktO.annuity(getBestBidOrBestOffer(doer, rentanimal, true), doer);
		case donkey:   return MktO.annuity(getBestBidOrBestOffer(doer, rentanimal, true), doer);
		case lobodonkey:   return getBestBidOrBestOffer(doer, donkey, true);
		default: return 1;
		}
	}
	public static int FVmax(Clan doer, int good) {
		int result;
		switch (good) {
		case rentland:   result = MktO.interest(getBestBidOrBestOffer(doer,land,false), doer); break;
		case rentanimal:   result = MktO.interest(Math.max(getBestBidOrBestOffer(doer,donkey,false), 
				getBestBidOrBestOffer(doer,bovad,false)), doer); break;
		default: result = Integer.MAX_VALUE; break;
		}
		return Math.max(1, result);
	}
	private static int getBestBidOrBestOffer(Clan c, int g, boolean bidFirst) {
		int bb = c.myMkt(g).bestBid();
		int bo = Math.abs(c.myMkt(g).bestOffer());
		bo = bo >= 0 ? bo : MktO.NOASK;
		if (bidFirst) {return bb != MktO.NOBID && bb != MktO.NOASK ? bb : (bo != MktO.NOASK ? bo : 0);} //bb != MktO.NOASK for nobiddercanpay ... i know weird
		else {return bo != MktO.NOASK ? bo : (bb != MktO.NOBID ? bb : Integer.MAX_VALUE);}
	}
	public static double estimateNAV(Clan POV, Clan clan) {
		int sum = 0;
		for (int g = 0; g < numGoods; g++) {
			sum += clan.getAssets(g) * clan.myMkt(g).sellablePX(POV);
		}
		return sum;
	}
}
