package darwInvest.evaluation;

import java.util.Set;

import darwInvest.environment.Settings;
import darwInvest.environment.StrategyParameters;
import darwin.population.Individual;

/**
 * The strategy executive executes a strategy on a set of data
 * points and sends the appropriate information to a balance
 * tracker for analysis
 * @author Kevin Dolan
 */
public class StrategyExecutive {

	private BalanceTracker balanceTracker;
	private long begin, end;
	private Individual individual;
	private Settings settings;
	
	/**
	 * Instantiate a new strategy executive
	 * @param balanceTracker the balance tracker to send the information to
	 * @param individual	 the individual being executed
	 * @param begin			 the beginning time of the test
	 * @param end			 the ending time of the test
	 * @param settings		 the settings object to use
	 */
	public StrategyExecutive(BalanceTracker balanceTracker, Individual individual, long begin, long end,
			Settings settings) {
		this.balanceTracker = balanceTracker;
		this.individual = individual;
		this.begin = begin;
		this.end = end;
		this.settings = settings;
	}
	
	/**
	 * Execute the strategy and send its results to the balance tracker
	 */
	public void execute() {
		Set<Long> times = settings.ticker.getTimesBetween(begin, end);
		StrategyParameters params = new StrategyParameters();
		params.cache = settings.cache;
		params.lookback = settings.lookback;
		params.ticker = settings.ticker;
		double buildup = 0;
		for(long time: times) {
			params.time = time;
			balanceTracker.notifyTime(time);
			
			//Set the price-- somewhere between the high and low
			double price = params.ticker.getPrice(time);
			balanceTracker.setPrice(price);
			
			//If we've died, no use to keep going
			if(!balanceTracker.isAlive())
				break;
			else {
				//Execute the strategy
				double order = (Double) individual.getTree(0).getValue(params);
				double orderPrice = order * price;
				
				//Limit the sentiment to the max
				if(orderPrice > settings.maxSentiment)
					orderPrice = settings.maxSentiment;
				if(orderPrice < - settings.maxSentiment)
					orderPrice = - settings.maxSentiment;
				
				//Add to the buildup, and limit to the max buildup
				buildup += order;
				double buildupPrice = buildup * price;
				if(buildupPrice > settings.maxSentimentBuildup)
					buildupPrice = settings.maxSentimentBuildup;
				if(buildupPrice < - settings.maxSentimentBuildup)
					buildupPrice = - settings.maxSentimentBuildup;
				
				//Notify of the sentiment
				balanceTracker.notifySentiment(order, buildup);
				
				//If we are past the threshold, execute the order
				if(Math.abs(buildupPrice) > settings.threshold) {
					if(buildup > 0)
						buildup -= balanceTracker.buy((int) buildup);
					else
						buildup += balanceTracker.sell((int) -buildup);
				}
			}
		}
	}
}
