package assignments.farmer.actionstrategies.harveststrategies;

import assignments.farmer.actionstrategies.HarvestStrategy;

public class ConventionalHarvestStrategy implements HarvestStrategy{

	@Override
	public void harvestInTheSpring() {
		System.out.println("Nothing to harvest.");
		
	}

	@Override
	public void harvestInTheSummer() {
		System.out.println("Nothing to harvest.");
		
	}

	@Override
	public void harvestInTheFall() {
		System.out.println("Hire combine.");
		
	}

}
