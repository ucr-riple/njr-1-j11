package assignments.farmer.actionstrategies.harveststrategies;

import assignments.farmer.actionstrategies.HarvestStrategy;

public class OrganicHarvestStrategy implements HarvestStrategy{

	@Override
	public void harvestInTheSpring() {
		System.out.println("Employ lots of interns and volunteers.");
		
	}

	@Override
	public void harvestInTheSummer() {
		System.out.println("Employ lots of interns and volunteers.");
		
	}

	@Override
	public void harvestInTheFall() {
		System.out.println("Employ lots of interns and volunteers; u-pick for squash.");
		
	}

}
