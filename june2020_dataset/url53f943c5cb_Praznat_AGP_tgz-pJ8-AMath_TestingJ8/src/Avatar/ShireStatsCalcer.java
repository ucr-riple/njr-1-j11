package Avatar;

import Defs.G_;
import Game.*;
import Markets.*;
import Sentiens.Clan;
import Shirage.Shire;

public class ShireStatsCalcer {

	public static final String PRODUCTIVITY = "PRODUCTIVITY";
	public static final String POPULATION = "POPULATION";
	public static final String LAST_PX = "LAST_PX";
	public static final String NUM_ASSETS = "NUM_ASSETS";
	
	public static void calcProductivity() {
		for (Shire s : AGPmain.TheRealm.getShires()) {
			double result = 0;
			for (Clan c : s.getCensus()) {result += c.getAvgIncome();}
			s.getGraphingInfo(PRODUCTIVITY).setValue(result);
		}
	}
	public static void calcPopulation() {
		for (Shire s : AGPmain.TheRealm.getShires()) {
			s.getGraphingInfo(POPULATION).setValue(s.getPopsize());
		}
	}

	public static void calcMarket(String goodName) {
		int g = G_.valueOf(goodName).ordinal();
		for (Shire s : AGPmain.TheRealm.getShires()) {
			int px = s.getMarket(g).lastPrice();
			if (px == MktO.NOASK || px == MktO.NOBID || px == MktO.NOBIDDERCANPAY) {px = 0;}
			s.getGraphingInfo(LAST_PX).setValue(px);
		}
	}

	public static void calcWealth(String goodName) {
		int g = G_.valueOf(goodName).ordinal();
		for (Shire s : AGPmain.TheRealm.getShires()) {
			double result = 0;
			for (Clan c : s.getCensus()) {result += c.getAssets(g);}
			s.getGraphingInfo(NUM_ASSETS).setValue(result);
		}
	}
	
	protected static Clan avatar() {
		return AGPmain.mainGUI.AC.getAvatar();
	}
	
}
