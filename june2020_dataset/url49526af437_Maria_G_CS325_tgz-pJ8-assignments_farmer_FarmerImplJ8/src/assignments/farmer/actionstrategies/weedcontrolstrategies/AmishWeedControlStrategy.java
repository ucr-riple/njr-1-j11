package assignments.farmer.actionstrategies.weedcontrolstrategies;

import assignments.farmer.actionstrategies.WeedControlStrategy;

public class AmishWeedControlStrategy implements WeedControlStrategy{

	@Override
	public void weedControlInTheSpring() {
		System.out.println("Walking cultivator in garden.");
	}

	@Override
	public void weedControlInTheSummer() {
		System.out.println("Walking cultivator in garden, two-row, horse-drawn cultivator in fields.");
	}

	@Override
	public void weedControlInTheFall() {
		System.out.println("No action.");		
	}

}
