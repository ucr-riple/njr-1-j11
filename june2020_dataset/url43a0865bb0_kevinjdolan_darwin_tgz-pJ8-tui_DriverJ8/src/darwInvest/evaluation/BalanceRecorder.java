package darwInvest.evaluation;

import java.util.TreeMap;

/**
 * The Balance Recorder keeps track of account information over
 * time for use by the GUI
 * @author Kevin Dolan
 */
public class BalanceRecorder extends BalanceTracker {

	private TreeMap<Long, Integer> orderHistory;
	private double maxOrder;
	private double maxPosition;
	private TreeMap<Long, Double> balanceHistory;
	private double minBalance, maxBalance;
	private TreeMap<Long, Double> cashHistory;
	private double maxCash;
	private TreeMap<Long, Double> sentimentHistory;
	private TreeMap<Long, Double> buildupHistory;
	private double minSentiment, maxSentiment;
	
	public BalanceRecorder(double balance, double commission, 
			double minCommission, double bankruptcy, double threshold) {
		super(balance, commission, minCommission, bankruptcy);
		
		orderHistory = new TreeMap<Long, Integer>();
		balanceHistory = new TreeMap<Long, Double>();
		cashHistory = new TreeMap<Long, Double>();
		sentimentHistory = new TreeMap<Long, Double>();
		buildupHistory = new TreeMap<Long, Double>();
		
		maxOrder = 0;
		maxPosition = 0;
		minBalance = balance;
		maxBalance = balance;
		maxCash = balance;
		minSentiment = -threshold;
		maxSentiment = threshold;
	}
	
	@Override
	public void notifySentiment(double current, double buildup) {
		super.notifySentiment(current, buildup);
		
		sentimentHistory.put(time, current);
		buildupHistory.put(time, buildup);
		
		if(current > maxSentiment)
			maxSentiment = current;
		if(current < minSentiment)
			minSentiment = current;
		if(buildup > maxSentiment)
			maxSentiment = buildup;
		if(buildup < minSentiment)
			minSentiment = buildup;
	}
	
	@Override
	public int buy(int shares) {
		int result = super.buy(shares);
		if(result != 0)
			orderHistory.put(time, result);
		double cash = account.getCashBalance();
		cashHistory.put(time, cash);
		if(result > maxOrder)
			maxOrder = result;
		if(account.getPosition() > maxPosition)
			maxPosition = account.getPosition();
		return result;
	}
	
	@Override
	public int sell(int shares) {
		int result = super.sell(shares);
		if(result != 0)
			orderHistory.put(time, -result);
		double cash = account.getCashBalance();
		cashHistory.put(time, cash);
		if(result > maxOrder)
			maxOrder = result;
		if(cash > maxCash)
			maxCash = cash;
		return result;
	}
	
	@Override
	public void setPrice(double price) {
		super.setPrice(price);
		double balance = getLiquidationValue();
		balanceHistory.put(time, balance);
		if(balance > maxBalance)
			maxBalance = balance;
		if(balance < minBalance)
			minBalance = balance;
	}

	/**
	 * @return the orderHistory
	 */
	public TreeMap<Long, Integer> getOrderHistory() {
		return orderHistory;
	}

	/**
	 * @return the balanceHistory
	 */
	public TreeMap<Long, Double> getBalanceHistory() {
		return balanceHistory;
	}

	/**
	 * @return the cashHistory
	 */
	public TreeMap<Long, Double> getCashHistory() {
		return cashHistory;
	}

	/**
	 * @return the sentimentHistory
	 */
	public TreeMap<Long, Double> getSentimentHistory() {
		return sentimentHistory;
	}

	/**
	 * @return the buildupHistory
	 */
	public TreeMap<Long, Double> getBuildupHistory() {
		return buildupHistory;
	}

	/**
	 * @return the maxOrder
	 */
	public double getMaxOrder() {
		return maxOrder;
	}

	/**
	 * @return the minBalance
	 */
	public double getMinBalance() {
		return minBalance;
	}
	
	/**
	 * @return the maxBalance
	 */
	public double getMaxBalance() {
		return maxBalance;
	}

	/**
	 * @return the maxCash
	 */
	public double getMaxCash() {
		return maxCash;
	}
	
	/**
	 * @return the minSentiment
	 */
	public double getMinSentiment() {
		return minSentiment;
	}

	/**
	 * @return the maxSentiment
	 */
	public double getMaxSentiment() {
		return maxSentiment;
	}
	
	/**
	 * @return the max position
	 */
	public double getMaxPosition() {
		return maxPosition;
	}

}
