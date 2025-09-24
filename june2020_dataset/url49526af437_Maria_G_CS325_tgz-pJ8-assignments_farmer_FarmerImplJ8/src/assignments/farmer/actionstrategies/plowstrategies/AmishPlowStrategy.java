package assignments.farmer.actionstrategies.plowstrategies;

import assignments.farmer.actionstrategies.PlowStrategy;

public class AmishPlowStrategy implements PlowStrategy {

	@Override
	public void plowInTheSpring() {
		System.out.println("Plow corn fields.");
		
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
