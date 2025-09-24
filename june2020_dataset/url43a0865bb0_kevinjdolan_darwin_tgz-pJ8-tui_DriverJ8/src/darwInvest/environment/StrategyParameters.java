package darwInvest.environment;

import java.util.List;
import java.util.TreeMap;

import darwInvest.data.Ticker;

/**
 * The DarwInvestParameters object maintains the parameters
 * for evaluation of a purchase order at a given time
 * @author Kevin Dolan
 */
public class StrategyParameters {

	/**
	 * The ticker object we are accessing
	 */
	public Ticker ticker;
	
	/**
	 * The current time we are accessing
	 */
	public long time;
	
	/**
	 * The amount of time we can look back
	 */
	public long lookback;
	
	/**
	 * Find the full time value for a given request value
	 * @param value the request parameter
	 * @return		the appropriate time value	
	 */
	public long getTime(double value) {
		double restricted = restrict(value);
		long look = (long) (restricted * lookback);
		return time - look;
	}
	
	/**
	 * Restrict an incoming value to [0,1]
	 * @param value the value to be restricted
	 * @return		a number [0,1]
	 */
	public double restrict(double value) {
		value = Math.abs(value);
		if(value <= 1)
			return value;
		return restrict(value / 10); 
	}
	
	/**
	 * The cache of news scores
	 */
	public TreeMap<Long,List<Double>> cache;

	public StrategyParameters clone() {
		StrategyParameters cloned = new StrategyParameters();
		cloned.ticker = ticker;
		cloned.time = time;
		cloned.lookback = lookback;
		cloned.cache = cache;
		return cloned;
	}
	
}
