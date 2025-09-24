package darwInvest.evaluation;

/**
 * The Account class maintains the current state of an investment account
 * @author Kevin Dolan
 */
public class Account {

	private double balance;
	private double price;
	private int position;
	private double minCommission;
	private double commission;
	
	/**
	 * Instantiate a new account with a given balance
	 * @param balance 	 	the initial balance
	 * @param commission 	the percentage commission
	 * @param minCommission the minimum commission that can be charged
	 */
	public Account(double balance, double commission, double minCommission) {
		this.balance = balance;
		this.commission = commission;
		this.minCommission = minCommission;
		position = 0;
	}
	
	/**
	 * @return the current value of the account if it were immediately liquidated
	 */
	public double getLiquidationValue() {
		double salePrice = position * price;
		return balance + salePrice - getCommission(salePrice);
	}
	
	/**
	 * @return the current cash balance
	 */
	public double getCashBalance() {
		return balance;
	}
	
	/**
	 * Set the current price
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * Buy up to the requested number of shares
	 * @param shares the requested number of shares
	 * @return the number actually bought
	 */
	public int buy(int shares) {
		//Calculate the maximum number of shares that can be bought with the balance
		int maxShares = (int) (balance / price);
		double maxBaseCost = maxShares * price;
		while(maxShares != 0 && maxBaseCost + getCommission(maxBaseCost) > balance) {
			maxShares--;
			maxBaseCost = maxShares * price;
		}
		int number = Math.min(maxShares, shares);
		
		//If an order is present, then make it
		position += number;
		double cost = number * price;
		balance -= cost + getCommission(cost);
		return number;
	}
	
	/**
	 * Sell up to the requested number of shares
	 * @param shares the number of shares to sell
	 * @return the number actually sold
	 */
	public int sell(int shares) {
		int number = Math.min(shares, position);
		double salePrice = number * price;
		balance += salePrice - getCommission(salePrice);
		position -= number;
		return number;
	}
	
	/**
	 * @return the current position
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * Get the commission for a given price
	 * @param price the price to figure
	 * @return the billable commission
	 */
	private double getCommission(double price) {
		if(price == 0)
			return 0;
		return Math.max(minCommission, commission * price);
	}
}
