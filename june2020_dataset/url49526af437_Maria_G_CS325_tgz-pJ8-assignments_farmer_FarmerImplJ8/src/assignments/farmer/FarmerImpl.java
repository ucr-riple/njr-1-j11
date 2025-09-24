package assignments.farmer;

import assignments.farmer.actionstrategies.*;
import assignments.farmer.farmerfactories.*;
import assignments.farmer.seasonstates.*;

public class FarmerImpl implements Farmer {

	private SeasonState season = new SpringState();
	private PlowStrategy plowStrategy;
	private PlantStrategy plantStrategy;
	private WeedControlStrategy weedControlStrategy;
	private HarvestStrategy harvestStrategy;
	private MarketStrategy marketStrategy;
		
	
	@Override
	public void plow() {
		season.plow(plowStrategy);		
	}

	@Override
	public void plant() {
		season.plant(plantStrategy);
	}

	@Override
	public void weedControl() {
		season.weedControl(weedControlStrategy);		
	}

	@Override
	public void harvest() {
		season.harvest(harvestStrategy);
	}

	@Override
	public void market() {
		season.market(marketStrategy);
	}
	
	public void setSeasonState(SeasonState seasonState){
		this.season = seasonState;
	}
	
	public void setStrategies(PlowStrategy plowStrategy, PlantStrategy plantStrategy, WeedControlStrategy weedControlStrategy, HarvestStrategy harvestStrategy, MarketStrategy marketStrategy){
		this.plowStrategy = plowStrategy;
		this.plantStrategy = plantStrategy;
		this.weedControlStrategy = weedControlStrategy;
		this.harvestStrategy = harvestStrategy;
		this.marketStrategy = marketStrategy;
	}
	
	public static void main(String[] args){
		
		Farmer[] farmers = { new AmishFarmerFactory().create("Amish"),
					 		 new OrganicFarmerFactory().create("Organic"),
					 		 new ConventionalFarmerFactory().create("Conv") };
		
		SeasonState[] seasons = { new SummerState(),
								  new FallState(),
								  new SpringState()} ;
				
		for (int i = 0; i < 2; i++){
			for (int j = 0; j < 3; j++){
				for (int k = 0; k < 3; k++){
					farmers[j].plow();
					farmers[j].plant();
					farmers[j].weedControl();
					farmers[j].harvest();
					farmers[j].market();
					
					farmers[j].setSeasonState(seasons[k]);
					
				}
			}
		}
		
	}

}
