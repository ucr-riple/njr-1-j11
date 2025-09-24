package main.evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.implementations.order.AlgorithmicTrade;
import main.implementations.order.Order;

/**
 * TradeStrategyEvaluator.java
 * 
 * This class will look at the list of algorithmic trade and give some sort of payoff
 * (profit or loss) from using the trading strategy.
 * 
 **/
public class TradeStrategyEvaluator {
	
	private List<AlgorithmicTrade> tradeList;
	private Map<AlgorithmicTrade, AlgorithmicTrade> tradePair;
	
	/**
	 * Constructor accepts list of algorithmic trades to evaluate.
	 * @param tradeList The list of algorithmic trades of TradeList type. 
	 * */
	public TradeStrategyEvaluator(List<AlgorithmicTrade> tradeList) {
		this.tradeList = tradeList;
		tradePair = new HashMap<AlgorithmicTrade, AlgorithmicTrade>();
		setTradePairs();
	}
	
	/**
	 * Gets the profit/loss value for the algorithmic trades within a trade list
	 * 
	 * @return the percentage gained. 
	 * */
	public double calculateProfitLoss() {
		double profit = 0;
		double purchasePrice = 0;
		
		List<AlgorithmicTrade> keys = new ArrayList<AlgorithmicTrade>();
		keys.addAll(tradePair.keySet());
		
		for (AlgorithmicTrade bids : keys) {
			AlgorithmicTrade ask = tradePair.get(bids);
			purchasePrice += bids.evaluateTrade();
			profit += ask.evaluateTrade();
		}
		
		// print the prices to 2 decimal places.
		System.out.printf("Total sold: $%.2f\nTotal bought: $%.2f\n", profit, purchasePrice);
		// Calculate percentage gained/lost
		double percent = (profit - purchasePrice)/purchasePrice;
		return percent;
	}
	
	public void setTradePairs() { 
		
		// gets the size of the tradeList for the while loop
		int tradeListSize = tradeList.size();
		// initialize the counter
		int i = 0;
		// while the tradeList is not empty and the counter is less than the size
		while (tradeList != null && i < tradeListSize - 1) { 
			// because in the way we add to the tradeList
			AlgorithmicTrade bidTrade = tradeList.get(i);		// we know that the first one is a bid
			printOrder(bidTrade);
			AlgorithmicTrade askTrade = tradeList.get(i + 1);	// and the next will always be the ask
			printOrder(askTrade);
			tradePair.put(bidTrade, askTrade);					// add the two values to the mapping
			i += 2;			
		}
	}
	
	public Map<AlgorithmicTrade, AlgorithmicTrade> getTradePair() {
		return this.tradePair;
	}
	
	private void printOrder(AlgorithmicTrade newOrder) {
		
		List<AlgorithmicTrade> keys = new ArrayList<AlgorithmicTrade>();
		keys.addAll(tradePair.keySet());
		
	
		System.out.println("DateTime: " + newOrder.getBidOrder().getDateTime() + " Price: " + newOrder.getBidOrder().getPrice());
		
		
	}
	
//	public ArrayList<AlgorithmicTrade> keysToArray() { 
//		
//		List<AlgorithmicTrade> keys = new ArrayList<AlgorithmicTrade>();
//		keys.addAll(tradePair.keySet());
//		
//		AlgorithmicTrade array[keys.size()] = keys.toArray(); 
//	}
}
