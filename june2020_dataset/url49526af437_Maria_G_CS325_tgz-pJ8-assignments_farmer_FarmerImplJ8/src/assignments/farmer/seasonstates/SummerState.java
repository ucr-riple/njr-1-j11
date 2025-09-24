package assignments.farmer.seasonstates;

import assignments.farmer.SeasonState;
import assignments.farmer.actionstrategies.*;

public class SummerState implements SeasonState {

	@Override
	public void plow(PlowStrategy plowStrategy) {
		plowStrategy.plowInTheSummer();
	}

	@Override
	public void plant(PlantStrategy plantStrategy) {
		plantStrategy.plantInTheSummer();
	}

	@Override
	public void weedControl(WeedControlStrategy weedControlStrategy) {
		weedControlStrategy.weedControlInTheSummer();
	}

	@Override
	public void harvest(HarvestStrategy harvestStrategy) {
		harvestStrategy.harvestInTheSummer();
	}

	@Override
	public void market(MarketStrategy marketStrategy) {
		marketStrategy.marketInTheSummer();
	}

}
