package darwInvest.evaluation;

/**
 * The BalanceTracker tracks the balance of an account over time
 * and records important information
 * @author Kevin Dolan
 */
public class BalanceTracker {

	protected Account account;
	protected double minBalance, maxBalance;
	protected double bankruptcy;
	protected int numBuys, numSells;
	protected boolean alive;
	protected long lastTime, timeIn;
	protected long time;
	protected int evaluationCount, positiveCount;
	
	/**
	 * Istantiate a new balance tracker with an initial balance
	 * @param balance 		the initial balance to begin with
	 * @param commission 	the percentage cut for the broker
	 * @param minCommission the minimum billable commission
	 * @param bankruptcy	the minimum value one must maintain to survive
	 */
	public BalanceTracker(double balance, double commission, double minCommission, double bankruptcy) {
		account = new Account(balance, commission, minCommission);
		minBalance = balance;
		maxBalance = balance;
		this.bankruptcy = bankruptcy;
		alive = true;
		lastTime = -1;
		timeIn = 0;
		evaluationCount = 0;
		positiveCount = 0;
	}
	
	/**
	 * @return the current liquidation value of the account
	 */
	public double getLiquidationValue() {
		return account.getLiquidationValue();
	}
	
	/**
	 * @return the number of shares bought
	 */
	public int getNumBuys() {
		return numBuys;
	}
	
	/**
	 * @return the number of shares sold
	 */
	public int getNumSells() {
		return numSells;
	}
	
	/**
	 * @return the minimum historical balance
	 */
	public double getMinBalance() {
		return minBalance;
	}
	
	/**
	 * @return the maximum historical balance
	 */
	public double getMaxBalance() {
		return maxBalance;
	}
	
	/**
	 * Make a purchase
	 * @param shares the requested number of shares
	 * @return 		 the number actually bought
	 */
	public int buy(int shares) {
		int amount = account.buy(shares);
		numBuys += amount;
		return amount;
	}
	
	/**
	 * Make a sale
	 * @param shares the requested number of shares
	 * @return 		 the number actually sold
	 */
	public int sell(int shares) {
		int amount = account.sell(shares);
		numSells += amount;
		return amount;
	}
	
	/**
	 * Set the current price of the stock
	 * @param price the current price
	 */
	public void setPrice(double price) {
		account.setPrice(price);
		double value = account.getLiquidationValue();
		minBalance = Math.min(minBalance, value);
		maxBalance = Math.max(maxBalance, value);
		if(value < bankruptcy)
			alive = false;
	}
	
	/**
	 * Notify the balance tracker of what time it is
	 * @param time the time value of now
	 */
	public void notifyTime(long time) {
		this.time = time;
		if(lastTime != -1 && account.getPosition() > 0)
			timeIn += time - lastTime;
		lastTime = time;
	}
	
	/**
	 * Notify the balance tracker of the current sentiment
	 * @param current the current sentiment value returned by the individual
	 * @param buildup the aggregate buildup value
	 */
	public void notifySentiment(double current, double buildup) {
		evaluationCount++;
		if(current > 0)
			positiveCount++;
	}
	
	/**
	 * @return whether or not this is currently alive
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * @return the total time in the position
	 */
	public long getTimeIn() {
		return timeIn;
	}
	
	/**
	 * @return the proportion of positive sentiment evaluations
	 */
	public double getPositiveSentimentProportion() {
		return ((double) positiveCount) / evaluationCount; 
	}
}
