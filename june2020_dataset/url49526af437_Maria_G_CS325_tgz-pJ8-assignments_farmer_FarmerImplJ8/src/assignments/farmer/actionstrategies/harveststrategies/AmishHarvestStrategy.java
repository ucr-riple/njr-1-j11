package assignments.farmer.actionstrategies.harveststrategies;

import assignments.farmer.actionstrategies.HarvestStrategy;

public class AmishHarvestStrategy implements HarvestStrategy{

	@Override
	public void harvestInTheSpring() {
		System.out.println("Wife and kids help out.");
		
	}

	@Override
	public void harvestInTheSummer() {
		System.out.println("Wife and kids help out in garden; neighbors help with oats and hay.");
		
	}

	@Override
	public void harvestInTheFall() {
		System.out.println("Wife and kids help out in garden; neighbors help with corn and hay.");
		
	}


}
