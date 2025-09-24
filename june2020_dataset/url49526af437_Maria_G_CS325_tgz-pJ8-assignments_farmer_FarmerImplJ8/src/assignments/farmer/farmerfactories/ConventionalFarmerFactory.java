package assignments.farmer.farmerfactories;

import assignments.farmer.Farmer;
import assignments.farmer.FarmerFactory;
import assignments.farmer.FarmerImpl;
import assignments.farmer.actionstrategies.HarvestStrategy;
import assignments.farmer.actionstrategies.MarketStrategy;
import assignments.farmer.actionstrategies.PlantStrategy;
import assignments.farmer.actionstrategies.PlowStrategy;
import assignments.farmer.actionstrategies.WeedControlStrategy;
import assignments.farmer.actionstrategies.harveststrategies.ConventionalHarvestStrategy;
import assignments.farmer.actionstrategies.marketstrategies.ConventionalMarketStrategy;
import assignments.farmer.actionstrategies.plantstrategies.ConventionalPlantStrategy;
import assignments.farmer.actionstrategies.plowstrategies.ConventionalPlowStrategy;
import assignments.farmer.actionstrategies.weedcontrolstrategies.ConventionalWeedControlStrategy;


public class ConventionalFarmerFactory implements FarmerFactory {

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
		return new ConventionalPlowStrategy();
	}

	@Override
	public PlantStrategy createPlantStrategy() {
		return new ConventionalPlantStrategy();
	}

	@Override
	public WeedControlStrategy createWeedControlStrategy() {
		return new ConventionalWeedControlStrategy();
	}

	@Override
	public HarvestStrategy createHarvestStrategy() {
		return new ConventionalHarvestStrategy();
	}

	@Override
	public MarketStrategy createMarketStrategy() {
		return new ConventionalMarketStrategy();
	}

}
