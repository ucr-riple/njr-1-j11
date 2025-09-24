package assignments.farmer.seasonstates;

import assignments.farmer.SeasonState;
import assignments.farmer.actionstrategies.*;

public class FallState implements SeasonState {

	@Override
	public void plow(PlowStrategy plowStrategy) {
		plowStrategy.plowInTheFall();
	}

	@Override
	public void plant(PlantStrategy plantStrategy) {
		plantStrategy.plantInTheFall();
	}

	@Override
	public void weedControl(WeedControlStrategy weedControlStrategy) {
		weedControlStrategy.weedControlInTheFall();
	}

	@Override
	public void harvest(HarvestStrategy harvestStrategy) {
		harvestStrategy.harvestInTheFall();
	}

	@Override
	public void market(MarketStrategy marketStrategy) {
		marketStrategy.marketInTheFall();
	}
	
}
