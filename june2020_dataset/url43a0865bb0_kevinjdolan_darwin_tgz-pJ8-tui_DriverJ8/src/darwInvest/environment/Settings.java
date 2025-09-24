package darwInvest.environment;

import java.util.List;
import java.util.TreeMap;

import darwInvest.data.Ticker;

/**
 * The Settings class contains all the settings of an environment
 * of a DarwInvest run
 * @author Kevin Dolan
 */
public class Settings {

	/**
	 * The ticker this environment is being trained on
	 */
	public Ticker ticker;
	
	/**
	 * The length of the test period
	 */
	public long testLength;
	
	/**
	 * The lookback period, in ms
	 */
	public long lookback;
	
	/**
	 * The number of trials to test on
	 */
	public int numTrials;
	
	/**
	 * Initial balance for the trials
	 */
	public double initialBalance;
	
	/**
	 * Commission rate
	 */
	public double commission;
	
	/**
	 * Minimum billable commission
	 */
	public double minCommission;
	
	/**
	 * The minimum number of shares that can trigger
	 * a buy or sell signal (in equivalent dollars)
	 */
	public double threshold;
	
	/**
	 * The maximum sentiment that can ever be received
	 * (in equivalent dollars)
	 */
	public double maxSentiment;
	
	/**
	 * The maximum a sentiment that can ever be built up
	 * (in equivalent dollars)
	 */
	public double maxSentimentBuildup;
	
	/**
	 * The minimum balance an account must have to remain alive
	 */
	public double bankruptcy;
	
	/**
	 * Beginning of the testable time-period
	 */
	public long testBeginning;
	
	/**
	 * End of the testable time-period
	 */
	public long testEnd;
	
	/**
	 * The maximum proportion evaluations that can be positive or negative
	 */
	public double cutoffSentimentProportion;
	
	/**
	 * The maximum proportion of time a strategy can spend in or out of a position
	 */
	public double cutoffTimeProportion;
	
	/**
	 * The cache of news scores
	 */
	public TreeMap<Long,List<Double>> cache;
	
	/**
	 * @return an identical settings object
	 */
	public Settings clone() {
		Settings result = new Settings();
		
		result.ticker = ticker;
		result.testLength = testLength;
		result.lookback = lookback;
		result.numTrials = numTrials;
		result.initialBalance = initialBalance;
		result.commission = commission;
		result.minCommission = minCommission;
		result.threshold = threshold;
		result.maxSentiment = maxSentiment;
		result.maxSentimentBuildup = maxSentimentBuildup;
		result.bankruptcy = bankruptcy;
		result.testBeginning = testBeginning;
		result.testEnd = testEnd;
		result.cutoffSentimentProportion = cutoffSentimentProportion;
		result.cutoffTimeProportion = cutoffTimeProportion;
		result.cache = cache;
		
		return result;
	}
}
