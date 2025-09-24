package assignments.farmer.farmerfactories;

import assignments.farmer.Farmer;
import assignments.farmer.FarmerFactory;
import assignments.farmer.FarmerImpl;
import assignments.farmer.actionstrategies.HarvestStrategy;
import assignments.farmer.actionstrategies.MarketStrategy;
import assignments.farmer.actionstrategies.PlantStrategy;
import assignments.farmer.actionstrategies.PlowStrategy;
import assignments.farmer.actionstrategies.WeedControlStrategy;
import assignments.farmer.actionstrategies.harveststrategies.AmishHarvestStrategy;
import assignments.farmer.actionstrategies.marketstrategies.AmishMarketStrategy;
import assignments.farmer.actionstrategies.plantstrategies.AmishPlantStrategy;
import assignments.farmer.actionstrategies.plowstrategies.AmishPlowStrategy;
import assignments.farmer.actionstrategies.weedcontrolstrategies.AmishWeedControlStrategy;


public class AmishFarmerFactory implements FarmerFactory {

	@Override
	public Farmer create(String str) {
		FarmerImpl f = new FarmerImpl();
		f.setStrategies(createPlowStrategy(),
						createPlantStrategy(), 
						createWeedControlStrategy(), 
						createHarvestStrategy(), 
						createMarketStrategy());
		return f;
	}

	@Override
	public PlowStrategy createPlowStrategy() {
		return new AmishPlowStrategy();
	}

	@Override
	public PlantStrategy createPlantStrategy() {
		return new AmishPlantStrategy();
	}

	@Override
	public WeedControlStrategy createWeedControlStrategy() {
		return new AmishWeedControlStrategy();
	}

	@Override
	public HarvestStrategy createHarvestStrategy() {
		return new AmishHarvestStrategy();
	}

	@Override
	public MarketStrategy createMarketStrategy() {
		return new AmishMarketStrategy();
	}

}
