package assignments.farmer.actionstrategies.marketstrategies;

import assignments.farmer.actionstrategies.MarketStrategy;

public class AmishMarketStrategy implements MarketStrategy{

	@Override
	public void marketInTheSpring() {
		System.out.println("Jams, jellies, peas & lettuce to auction.");
		
	}

	@Override
	public void marketInTheSummer() {
		System.out.println("Peas, carrots, early beans, roma tomatoes to auction.");
		
	}

	@Override
	public void marketInTheFall() {
		System.out.println("Beans, squash, tomatoes to auction.");
		
	}

}
