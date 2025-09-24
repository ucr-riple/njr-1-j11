package main.implementations;

import java.util.ArrayList;
import java.util.List;

import main.generator.TradeSignalGenerator;
import main.implementations.order.AlgorithmicTrade;
import main.implementations.order.Order;
import main.interfaces.Orderbook;
import main.utils.Strategy;

/**
 * A list implementation of an orderbook. Contains all orders separated by ask, bid and trades. 
 */
public class OrderbookImpl implements Orderbook {

	private List<Order> bidList;
	private List<Order> askList;
	private List<Order> tradeList;

	public OrderbookImpl(List<Order> bidList, List<Order> askList, List<Order> tradeList) {
		this.bidList = bidList;
		this.askList = askList;
		this.tradeList = tradeList;
	}


	@Override
	public void setBidList(List<Order> bidList) {
		
		this.bidList = bidList;
	}

	@Override
	public void setAskList(List<Order> askList) {
		
		this.askList = askList;
	}

	@Override
	public void setTradeList(List<Order> tradeList) {
		
		this.tradeList = tradeList;
	}
	
	@Override
	public List<Order> getBidList() {
		
		return bidList;
	}

	@Override
	public List<Order> getAskList() {

		return askList;
	}

	@Override
	public List<Order> getTradeList() {
		return tradeList;	
	}

	public List<AlgorithmicTrade> runStrategy(Strategy strategy, String volume, Integer lookBackPeriod, Double threshold) {
		
		List<AlgorithmicTrade> tradeList = new ArrayList<AlgorithmicTrade>();
		TradeSignalGenerator signalGenerator = new TradeSignalGenerator(bidList, askList, this.tradeList);
		if (Strategy.RANDOM == strategy) {
			tradeList.addAll(matchTrades(signalGenerator.newRandomBid(volume)));
			tradeList.addAll(matchTrades(signalGenerator.newRandomAsk(volume)));
		} else if (Strategy.MOMENTUM == strategy) {
			tradeList.addAll(matchTrades(signalGenerator.newMomentumOrders(volume, lookBackPeriod, threshold)));
		} else if (Strategy.MEAN_REVERSION == strategy) {
			tradeList.addAll(matchTrades(signalGenerator.newMeanReversionOrders(volume, lookBackPeriod, threshold)));
		}
		
		return tradeList;
	}

	public AlgorithmicTrade tradeMatcher (Order orderToCompare) { 
		
		Order matchedOrder = null;
		AlgorithmicTrade matchedTrade = null;
		
		if (orderToCompare.isBid()) { 
			for (Order ask : askList) { 
				if (orderToCompare.getPrice() == ask.getPrice()) {
					matchedOrder = ask; 
					matchedTrade = new AlgorithmicTrade(orderToCompare, matchedOrder);
				}
			}
		} else { 
			for (Order bid : bidList) { 
				if (orderToCompare.getPrice () == bid.getPrice()) { 
					matchedOrder = bid;
					matchedTrade = new AlgorithmicTrade(matchedOrder, orderToCompare);
				}
			}
		}
		
		return matchedTrade;
	}
	
	public List<AlgorithmicTrade> matchTrades(List<Order> newOrders) {
		
		// trade list.
		List<AlgorithmicTrade> tradeList = new ArrayList<AlgorithmicTrade>();
		
		// Match all new orders.
		for (Order order : newOrders) {
			if (tradeMatcher(order) != null) {
				tradeList.add(tradeMatcher(order));
			}
		}
		
		return tradeList;
	}
}