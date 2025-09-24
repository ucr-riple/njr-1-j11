package assignments.farmer.actionstrategies.plowstrategies;

import assignments.farmer.actionstrategies.PlowStrategy;

public class OrganicPlowStrategy implements PlowStrategy {

	@Override
	public void plowInTheSpring() {
		System.out.println("Plow under green manure.");

	}

	@Override
	public void plowInTheSummer() {
		System.out.println("Plow fallow fields to prepare for fall cover crop.");
		
	}

	@Override
	public void plowInTheFall() {
		System.out.println("No action.");
		
	}

}
