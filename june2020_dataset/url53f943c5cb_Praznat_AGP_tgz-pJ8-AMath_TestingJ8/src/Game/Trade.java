package Game;

import Sentiens.Clan;
import Shirage.Shire;

public class Trade implements Act {
	private final int[] goods;
	private final String desc;
	public Trade(String desc, int[] goods) {
		this.desc = desc; this.goods = goods;
	}
	public static Trade newTrade(String desc, int... goods) {return new Trade(desc, goods);}

	@Override
	public void doit(Clan doer) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * this is for avatar comparator... i think
	 */
	public double estimateProfit(Clan doer, Shire buyShire) {
		return 0;
	}

	@Override
	public double estimateProfit(Clan pOV) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDesc() {return desc;}

	@Override
	public int getSkill(Clan clan) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int[] getGoods() {return goods;}

}
