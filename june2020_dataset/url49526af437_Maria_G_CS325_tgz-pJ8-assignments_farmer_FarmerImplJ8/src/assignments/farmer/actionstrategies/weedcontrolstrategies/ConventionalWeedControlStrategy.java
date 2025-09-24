package assignments.farmer.actionstrategies.weedcontrolstrategies;

import assignments.farmer.actionstrategies.WeedControlStrategy;

public class ConventionalWeedControlStrategy implements WeedControlStrategy{

	@Override
	public void weedControlInTheSpring() {
		System.out.println("No action.");
		
	}

	@Override
	public void weedControlInTheSummer() {
		System.out.println("Spray.");
		
	}

	@Override
	public void weedControlInTheFall() {
		System.out.println("No action.");
		
	}

}
