package assignments.farmer.actionstrategies.marketstrategies;

import assignments.farmer.actionstrategies.MarketStrategy;

public class ConventionalMarketStrategy implements MarketStrategy{

	@Override
	public void marketInTheSpring() {
		System.out.println("Nothing to market.");
		
	}

	@Override
	public void marketInTheSummer() {
		System.out.println("Nothing to market.");
		
	}

	@Override
	public void marketInTheFall() {
		System.out.println("Feed corn to elevator.");
		
	}

}
