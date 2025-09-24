package assignments.farmer.seasonstates;

import assignments.farmer.SeasonState;
import assignments.farmer.actionstrategies.*;

public class SpringState implements SeasonState {

	@Override
	public void plow(PlowStrategy plowStrategy) {
		plowStrategy.plowInTheSpring();
	}

	@Override
	public void plant(PlantStrategy plantStrategy) {
		plantStrategy.plantInTheSpring();
	}

	@Override
	public void weedControl(WeedControlStrategy weedControlStrategy) {
		weedControlStrategy.weedControlInTheSpring();
	}

	@Override
	public void harvest(HarvestStrategy harvestStrategy) {
		harvestStrategy.harvestInTheSpring();
	}

	@Override
	public void market(MarketStrategy marketStrategy) {
		marketStrategy.marketInTheSpring();
	}


}
