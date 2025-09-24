package main.generator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import main.implementations.OrderBuilderImpl;
import main.implementations.order.Order;
import main.interfaces.OrderBuilder;
import main.utils.TimeUtil;

public class TradeSignalGenerator {
	
	// Constants
	private static final int FIRST_ELEMENT = 0;
	private static final String ALGORITHMIC_BROKER_ID = "6969";
	private static final String ORDER_BID = "B";
	private static final String ORDER_ASK = "A";
	private static final int DEFAULT_LOOK_BACK_N = 1000;
	private static final double DEFAULT_THRESHOLD = 0.00000001; 
	
	private List<Order> bidList;
	private List<Order> askList;
	private List<Order> tradeList;

	public TradeSignalGenerator(List<Order> bidList, List<Order> askList, List<Order> tradeList) {
		this.bidList = bidList;
		this.askList = askList;
		this.tradeList = tradeList;
	}

	public List<Order> getBidList() {
		return bidList;
	}

	public void setBidList(List<Order> bidList) {
		this.bidList = bidList;
	}

	public List<Order> getAskList() {
		return askList;
	}

	public void setAskList(List<Order> askList) {
		this.askList = askList;
	}

	public List<Order> getTradeList() {
		return tradeList;
	}

	public void setTradeList(List<Order> tradeList) {
		this.tradeList = tradeList;
	}
	
	public List<Order> newRandomBid(String volume) { 
 
		// Set all required fields
		String instrument = bidList.get(FIRST_ELEMENT).getInstrument();
		Date date = bidList.get(FIRST_ELEMENT).getDateTime();
		Calendar dateSetter = Calendar.getInstance();
		dateSetter.setTime(date);
		dateSetter.set(Calendar.HOUR_OF_DAY, TimeUtil.generateHour());
		dateSetter.set(Calendar.MINUTE, TimeUtil.generateMinute());
		dateSetter.set(Calendar.MILLISECOND, TimeUtil.generateMillis());
		dateSetter.set(Calendar.SECOND, TimeUtil.generateSeconds());
		date = dateSetter.getTime();
		String buyerBrokerId = ALGORITHMIC_BROKER_ID;
		String bidOrAsk = ORDER_BID;
		
		double lowest = findLowestPrice(date);
		
		// Build the order.
		OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
										date,
										lowest,
										volume,
										bidOrAsk,
										buyerBrokerId);
		Order newOrder = orderBuilder.build();
		bidList.add(newOrder);	
		
		// Output the order.
		printOrder(newOrder);
		
		// Generate the required list for the matcher.
		List<Order> newOrderList = new ArrayList<Order>();
		newOrderList.add(newOrder);
		
		return newOrderList;
	}

	public List<Order> newRandomAsk(String volume) { 

		// Set all required fields
		String instrument = askList.get(FIRST_ELEMENT).getInstrument();
		Date date = askList.get(FIRST_ELEMENT).getDateTime();
		Calendar dateSetter = Calendar.getInstance();
		dateSetter.setTime(date);
		dateSetter.set(Calendar.HOUR_OF_DAY, TimeUtil.generateHour());
		dateSetter.set(Calendar.MINUTE, TimeUtil.generateMinute());
		dateSetter.set(Calendar.MILLISECOND, TimeUtil.generateMillis());
		dateSetter.set(Calendar.SECOND, TimeUtil.generateSeconds());
		date = dateSetter.getTime();
		String sellerBrokerId = ALGORITHMIC_BROKER_ID;
		String bidOrAsk = ORDER_ASK;
		
		double highest = findHighestPrice(date);
		
		OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
										date,
										highest,
										volume,
										bidOrAsk,
										sellerBrokerId);
		
		Order newOrder = orderBuilder.build();
		askList.add(newOrder);	
		
		printOrder(newOrder);
		
		// Generate the required list for the matcher.
		List<Order> newOrderList = new ArrayList<Order>();
		newOrderList.add(newOrder);
		
		return newOrderList;
	}


	private double findHighestPrice(Date date) {
		List<Order> nullBids = new ArrayList<Order>();
		// Find the highest price just before the newly generated time.
		double highest = bidList.get(FIRST_ELEMENT).getPrice();
		for (Order bid : bidList) {
			// Check if our dates are after the bid list dates.
			try {
				if (date.after(bid.getDateTime())) { 
					if (highest < bid.getPrice()) { 
						highest = bid.getPrice();
					}
				}
			} catch (NullPointerException e) {
				// Bid order invalid, mark for cleanup
				nullBids.add(bid);
			}
		}
		
		// Clean up invalid bids
		bidList.removeAll(nullBids);
		return highest;
	}
	
	private double findLowestPrice(Date date) {
		List<Order> nullAsks = new ArrayList<Order>();
		// Find the lowest price just before the newly generated time.
		double lowest = askList.get(FIRST_ELEMENT).getPrice();
		for (Order ask : askList) {
			try {
				// Check if the our date is after the ask list dates.
				if (date.after(ask.getDateTime())) { 
					if (lowest > ask.getPrice()) { 
						lowest = ask.getPrice();
					}
				}
			} catch (NullPointerException e) {
				// Mark ask orders for clean up.
				nullAsks.add(ask);
			}
		}
		
		// Clean up null orders.
		askList.removeAll(nullAsks);
		return lowest;
	}
	
	public List<Order> newMomentumOrders(String volume, Integer lookBackPeriod, Double threshold) {
		
		List<Order> newOrders = new ArrayList<Order>();
		
		// Grab all the required order fields
		String instrument = askList.get(FIRST_ELEMENT).getInstrument();
		
		// If user does not supply anything, use default values.
		if (lookBackPeriod == null) {
			lookBackPeriod = DEFAULT_LOOK_BACK_N;
		}
		if (threshold == null) {
			threshold = DEFAULT_THRESHOLD;
		}
		
		int maxIterations = tradeList.size()/lookBackPeriod;
		boolean paired = false;
		for (int j = 0; j < maxIterations; j++) {
			// Look back on N number of trades to work out the momentum of the market.
			double tradeReturn = 0.0;
			Order finalTrade = null;
			int shiftAcross = j*lookBackPeriod;
			for (int i = shiftAcross; i < lookBackPeriod+shiftAcross && i < tradeList.size()-1; i++) {
				Order previousTrade = tradeList.get(i); 
				Order tradeToCompare = tradeList.get(i+1);
				double calculatedReturn = ((tradeToCompare.getPrice() - previousTrade.getPrice())/previousTrade.getPrice());
				tradeReturn += calculatedReturn;
				finalTrade = tradeToCompare;
				
				// Calculate the current moving average.
				double movingAverage = tradeReturn/i;
				// Check if the moving average breaks the threshold.
				if (movingAverage >= threshold && !paired) {
					// Average broke positive threshold, indicates we should buy (stock getting better)
					OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
													finalTrade.getDateTime(),
													findBidPriceAtTime(finalTrade.getDateTime()),
													volume,
													ORDER_BID,
													ALGORITHMIC_BROKER_ID);
					
					Order newOrder = orderBuilder.build();
					if (newOrder != null) {
						printOrder(newOrder);
						newOrders.add(newOrder);
						paired = true;
					}
				} else if (movingAverage <= -threshold && paired) {
					// Average broke negative threshold, indicates we should sell (stock getting worse)
					OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
													finalTrade.getDateTime(),
													findAskPriceAtTime(finalTrade.getDateTime()),
													volume,
													ORDER_ASK,
													ALGORITHMIC_BROKER_ID);
					
					Order newOrder = orderBuilder.build();
					if (newOrder != null) {
						printOrder(newOrder);
						newOrders.add(newOrder);
						paired = false;
					}
				}
			}
		}
		
		// Match any new orders.
		if (newOrders.size() % 2 != 0) {
			newOrders.remove(newOrders.size()-1);
		}
		return newOrders;
	}
	
	public List<Order> newMeanReversionOrders(String volume, Integer lookBackPeriod, Double threshold) {
		
		List<Order> newOrders = new ArrayList<Order>();
		
		// Grab all the required order fields
		String instrument = askList.get(FIRST_ELEMENT).getInstrument();
		
		// If user does not supply anything, use default values.
		if (lookBackPeriod == null) {
			lookBackPeriod = DEFAULT_LOOK_BACK_N;
		}
		if (threshold == null) {
			threshold = DEFAULT_THRESHOLD;
		}
		
		int maxIterations = tradeList.size()/lookBackPeriod;
		boolean paired = false;
		for (int j = 0; j < maxIterations; j++) {
			// Look back on N number of trades to work out the momentum of the market.
			double tradeReturn = 0.0;
			Order finalTrade = null;
			int shiftAcross = j*lookBackPeriod;
			for (int i = shiftAcross; i < lookBackPeriod+shiftAcross && i < tradeList.size()-1; i++) {
				Order previousTrade = tradeList.get(i); 
				Order tradeToCompare = tradeList.get(i+1);
				double calculatedReturn = ((tradeToCompare.getPrice() - previousTrade.getPrice())/previousTrade.getPrice());
				tradeReturn += calculatedReturn;
				finalTrade = tradeToCompare;
				
				// Calculate the current moving average.
				double movingAverage = tradeReturn/i;
				// Check if the moving average breaks the threshold.
				if (movingAverage >= threshold && paired) {
					// Average broke positive threshold, indicates we should buy (stock getting better)
					OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
													finalTrade.getDateTime(),
													findAskPriceAtTime(finalTrade.getDateTime()),
													volume,
													ORDER_ASK,
													ALGORITHMIC_BROKER_ID);
					
					Order newOrder = orderBuilder.build();
					if (newOrder != null) {
						printOrder(newOrder);
						newOrders.add(newOrder);
						paired = false;
					}
				} else if (movingAverage <= -threshold && !paired) {
					// Average broke negative threshold, indicates we should sell (stock getting worse)
					OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
													finalTrade.getDateTime(),
													findBidPriceAtTime(finalTrade.getDateTime()),
													volume,
													ORDER_BID,
													ALGORITHMIC_BROKER_ID);
					
					Order newOrder = orderBuilder.build();
					if (newOrder != null) {
						printOrder(newOrder);
						newOrders.add(newOrder);
						paired = true;
					}
				}
			}
		}
		
		// Match any new orders.
		if (newOrders.size() % 2 != 0) {
			newOrders.remove(newOrders.size()-1);
		}
		return newOrders;
	}

	// ---------- PRIVATE HELPER METHODS -----------
	/**
	 * Helper function to determine price for ask orders at a given point (used for market orders)
	 * 
	 * @param dateTime - time to look up until.
	 * @return the price closest to a given point of time.
	 */
	private double findAskPriceAtTime(Date dateTime) {
		
		double price = 0.0;
		Order previousPrice = askList.get(FIRST_ELEMENT);
		
		for (Order ask : askList) {
			if (ask.getDateTime().getTime() > dateTime.getTime()) {
				price = previousPrice.getPrice();
				break;
			} else {
				previousPrice = ask;
			}
		}
		
		return price;
	}

	/**
	 * Helper function to determine price for bid orders at a given point (used for market orders)
	 * 
	 * @param dateTime - time to look up until.
	 * @return the price closest to a given point of time.
	 */
	private double findBidPriceAtTime(Date dateTime) {
		
		double price = 0.0;
		Order previousPrice = askList.get(FIRST_ELEMENT);
		
		for (Order bid : bidList) {
			if (bid.getDateTime().getTime() > dateTime.getTime()) {
				price = previousPrice.getPrice();
				break;
			} else {
				previousPrice = bid;
			}
		}
		
		return price;
	}
	
	private void printOrder(Order newOrder) {
		String orderType = "";
		if (newOrder.isBid()) {
			orderType = "Bid";
		} else {
			orderType = "Ask";
		}
		
		System.out.println(orderType + " order generated at " + newOrder.getDateTime() + ". Price: " + newOrder.getPrice() + " Quantity: " + newOrder.getVolume());
	}
}