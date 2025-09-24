package assignments.farmer.actionstrategies.plowstrategies;

import assignments.farmer.actionstrategies.PlowStrategy;

public class ConventionalPlowStrategy implements PlowStrategy {

	@Override
	public void plowInTheSpring() {
		System.out.println("Using no-till; no plowing.");

	}

	@Override
	public void plowInTheSummer() {
		System.out.println("No action.");
		
	}

	@Override
	public void plowInTheFall() {
		System.out.println("No action.");
		
	}

}
