package main.interfaces;

import java.util.List;

import main.implementations.order.AlgorithmicTrade;
import main.implementations.order.Order;
import main.utils.Strategy;

/**
 * Orderbook interface which handles order generating and trade generating. 
 */
public interface Orderbook {

	/**
	 * Set the existing bid list in the orderbook to provided list of orders.
	 * This function should additionally remove all the existing bid order mappings in the orderbook
	 * and recreate the matching orders in the order book.
	 * @param bidList - the bid list to replace the current bid list with.
	 */
	public void setBidList(List<Order> bidList);

	/**
	 * Set the existing ask list in the orderbook to provided list of orders.
	 * This function should additionally remove all the existing ask order mappings in the orderbook
	 * and recreate the matching orders in the order book.
	 * 
	 * @param askList - the ask list to replace the current ask list with.
	 */
	public void setAskList(List<Order> askList);

	/**
	 * Sets the trade list to the given trade list.
	 * 
	 * @param tradeList - the trades to populate the orderbook with.
	 */
	public void setTradeList(List<Order> tradeList);

	/**
	 * Returns the current bid list.
	 * 
	 * @return the bid list.
	 */
	public List<Order> getBidList();
	
	/**
	 * Returns the current ask list.
	 * 
	 * @return the ask list.
	 */
	public List<Order> getAskList();

	/**
	 * Returns the trade list of trades in the orderbook.
	 * 
	 * @return the trade list.
	 */
	public List<Order> getTradeList();
	
	/**
	 * Generate new orders based on strategy provided. 
	 * 
	 * @param strategy - the strategy to use.
	 * @param volume - the amount to sell or buy
	 * @param lookBackPeriod - the number of trades to look back
	 * @param threshold - the threshold for momentum and mean reversion to trigger.
	 * @return a list of trades to run.
	 */
	public List<AlgorithmicTrade> runStrategy(Strategy strategy, String volume, Integer lookBackPeriod, Double threshold);

}
