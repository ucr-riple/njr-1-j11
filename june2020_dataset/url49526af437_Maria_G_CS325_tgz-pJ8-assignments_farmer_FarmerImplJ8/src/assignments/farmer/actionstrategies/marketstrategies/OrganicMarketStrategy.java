package assignments.farmer.actionstrategies.marketstrategies;

import assignments.farmer.actionstrategies.MarketStrategy;

public class OrganicMarketStrategy implements MarketStrategy{

	@Override
	public void marketInTheSpring() {
		System.out.println("Fall garlic, peas & lettuce to farmer's market.");
		
	}

	@Override
	public void marketInTheSummer() {
		System.out.println("Peas, carrots, early beans, roma tomatoes to farmer's market.");
		
	}

	@Override
	public void marketInTheFall() {
		System.out.println("Beans, squash, tomatoes to farmer's market; big harvest party on farm.");
		
	}

}
