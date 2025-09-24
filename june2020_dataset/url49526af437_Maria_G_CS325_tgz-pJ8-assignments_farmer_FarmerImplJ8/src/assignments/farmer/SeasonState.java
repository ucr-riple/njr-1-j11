package assignments.farmer;

import assignments.farmer.actionstrategies.*;

public interface SeasonState {
	public void plow(PlowStrategy plowStrategy);
	public void plant(PlantStrategy plantStrategy);
	public void weedControl(WeedControlStrategy weedControlStrategy);
	public void harvest(HarvestStrategy harvestStrategy);
	public void market(MarketStrategy marketStrategy);
}
