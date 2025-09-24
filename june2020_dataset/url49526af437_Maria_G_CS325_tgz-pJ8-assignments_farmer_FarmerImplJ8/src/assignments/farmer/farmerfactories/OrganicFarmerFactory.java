package assignments.farmer.farmerfactories;

import assignments.farmer.Farmer;
import assignments.farmer.FarmerFactory;
import assignments.farmer.FarmerImpl;
import assignments.farmer.actionstrategies.*;
import assignments.farmer.actionstrategies.harveststrategies.OrganicHarvestStrategy;
import assignments.farmer.actionstrategies.marketstrategies.OrganicMarketStrategy;
import assignments.farmer.actionstrategies.plantstrategies.OrganicPlantStrategy;
import assignments.farmer.actionstrategies.plowstrategies.OrganicPlowStrategy;
import assignments.farmer.actionstrategies.weedcontrolstrategies.OrganicWeedControlStrategy;

public class OrganicFarmerFactory implements FarmerFactory {

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
		return new OrganicPlowStrategy();
	}

	@Override
	public PlantStrategy createPlantStrategy() {
		return new OrganicPlantStrategy();
	}

	@Override
	public WeedControlStrategy createWeedControlStrategy() {
		return new OrganicWeedControlStrategy();
	}

	@Override
	public HarvestStrategy createHarvestStrategy() {
		return new OrganicHarvestStrategy();
	}

	@Override
	public MarketStrategy createMarketStrategy() {
		return new OrganicMarketStrategy();
	}

}
