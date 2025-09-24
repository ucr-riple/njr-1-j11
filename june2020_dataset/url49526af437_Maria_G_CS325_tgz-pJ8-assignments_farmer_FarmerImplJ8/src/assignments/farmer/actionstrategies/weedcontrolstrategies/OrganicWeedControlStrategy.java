package assignments.farmer.actionstrategies.weedcontrolstrategies;

import assignments.farmer.actionstrategies.WeedControlStrategy;

public class OrganicWeedControlStrategy implements WeedControlStrategy{

	@Override
	public void weedControlInTheSpring() {
		System.out.println("Employ lots of interns with hoes.");
		
	}

	@Override
	public void weedControlInTheSummer() {
		System.out.println("Employ lots of interns with hoes.");
		
	}

	@Override
	public void weedControlInTheFall() {
		System.out.println("No action.");
		
	}

}
