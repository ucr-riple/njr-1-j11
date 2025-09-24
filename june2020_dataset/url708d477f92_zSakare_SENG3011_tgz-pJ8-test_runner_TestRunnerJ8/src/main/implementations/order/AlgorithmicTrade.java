package main.implementations.order;

/**
 * A wrapper class for two matched orders. 
 */
public class AlgorithmicTrade {
	
	private Order bidOrder;
	private Order askOrder;
	
	public AlgorithmicTrade(Order bidOrder, Order askOrder) {
		this.bidOrder = bidOrder;
		this.askOrder = askOrder;
	}
	
	public Order getBidOrder() {
		return bidOrder;
	}
	
	public Order getAskOrder() {
		return askOrder;
	}
	
	/**
	 * Calculate the value of the trade
	 * 
	 * @return value - as dollars with type double.
	 */
	public double evaluateTrade() {
		// Find out which one has the lower volume.
		int lowerVolume;
		
		if (bidOrder.getVolume() < askOrder.getVolume()) {
			lowerVolume = bidOrder.getVolume();
		} else {
			lowerVolume = askOrder.getVolume();
		}
		
		return bidOrder.getPrice()*lowerVolume;
	}
	
	public boolean equals(Object o) {
		boolean equals = false;
		
		if (o != null) {
			AlgorithmicTrade trade = (AlgorithmicTrade) o;
			if (this.getBidOrder().equals(trade.getBidOrder()) && this.getAskOrder().equals(trade.getAskOrder())) {
				equals = true;
			}
		}
		
		return equals;
	}
	
	public int hashCode() {
		return (int) (this.bidOrder.getDateTime().getTime() + this.bidOrder.getPrice() + this.askOrder.getDateTime().getTime() + this.askOrder.getPrice());
	}
}
