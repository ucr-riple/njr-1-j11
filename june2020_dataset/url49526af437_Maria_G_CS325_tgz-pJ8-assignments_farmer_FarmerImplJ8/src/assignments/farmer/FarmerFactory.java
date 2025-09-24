package assignments.farmer;

import assignments.farmer.actionstrategies.*;

public interface FarmerFactory {

	public Farmer create(String str);
	
	public PlowStrategy createPlowStrategy();
	public PlantStrategy createPlantStrategy();
	public WeedControlStrategy createWeedControlStrategy();
	public HarvestStrategy createHarvestStrategy();
	public MarketStrategy createMarketStrategy();
	
}
