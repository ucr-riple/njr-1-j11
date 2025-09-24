package Game;

import Sentiens.Clan;

public interface Act {
	public void doit(Clan doer);

	public double estimateProfit(Clan pOV);

	public String getDesc();

	public int getSkill(Clan clan);
}
