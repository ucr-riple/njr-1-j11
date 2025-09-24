package test.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.implementations.OrderBuilderImpl;
import main.implementations.order.Order;
import main.interfaces.OrderBuilder;

public class ListGenerator {

	private static final String INSTRUMENT = "DEF";
	private static final Date DATE = new Date();
	private static final double PRICE = 1000.0; 
	private static final String VOLUME = "50";
	private static final String ORDER_BID = "B";
	private static final String ORDER_ASK = "A";
	
	private static final String RANDOM_BROKER_ID = "123";
	private static final String buyerBrokerID = RANDOM_BROKER_ID;
	private static final String sellerBrokerID = RANDOM_BROKER_ID;
	private static final String brokerID = RANDOM_BROKER_ID;
	
	private static final int MAX_ORDERS = 1000;
	
	public static List<Order> generateRandomBidList() { 
		
		List<Order> randomBidList = new ArrayList<Order>();
		
		int numTimes = 0;
		String instrument = INSTRUMENT;
		Date date = DATE;
		double price = PRICE;
		String volume = VOLUME;
		long randTime = date.getTime();
		
		while (numTimes < MAX_ORDERS) { 
			OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
					date,
					price,
					volume,
					ORDER_BID,
					buyerBrokerID);
			Order newOrder = orderBuilder.build();
			randomBidList.add(newOrder);
			price += 0.5;
			randTime += 5000;
			date.setTime(randTime);
			numTimes++;
		}
		
		return randomBidList;
	}
	
	public static List<Order> generateMomentumBidList() { 
		
		List<Order> bidMomentumList = new ArrayList<Order>();
		
		int numTimes = 0;
		String instrument = INSTRUMENT;
		Date date = DATE;
		double price = PRICE;
		String volume = VOLUME;
		Long randTime = date.getTime();
		
		while (numTimes < MAX_ORDERS) { 
			OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
					date,
					price,
					volume,
					ORDER_ASK,
					sellerBrokerID);
			Order newOrder = orderBuilder.build();
			bidMomentumList.add(newOrder);
			price += 0.4;
			randTime += 4000;
			date.setTime(randTime);
			numTimes++;
		}
		
		return bidMomentumList;
	}
	
	public static List<Order> generateMeanRevBidList() { 
		
		List<Order> bidMeanRevList = new ArrayList<Order>();
		
		int numTimes = 0;
		String instrument = INSTRUMENT;
		Date date = DATE;
		double price = PRICE;
		String volume = VOLUME;
		Long randTime = date.getTime();
		
		while (numTimes < MAX_ORDERS) { 
			OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
					date,
					price,
					volume,
					ORDER_ASK,
					sellerBrokerID);
			Order newOrder = orderBuilder.build();
			bidMeanRevList.add(newOrder);
			price -= 0.4;
			randTime += 4000;
			date.setTime(randTime);
			numTimes++;
		}
		
		return bidMeanRevList;
	}
	
	public static List<Order> generateRandomAskList() { 
		
		List<Order> randomAskList = new ArrayList<Order>();
		
		int numTimes = 0;
		String instrument = INSTRUMENT;
		Date date = DATE;
		double price = PRICE;
		String volume = VOLUME;
		Long randTime = date.getTime();
		
		while (numTimes < MAX_ORDERS) { 
			OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
					date,
					price,
					volume,
					ORDER_ASK,
					sellerBrokerID);
			Order newOrder = orderBuilder.build();
			randomAskList.add(newOrder);
			price -= 0.4;
			randTime += 4000;
			date.setTime(randTime);
			numTimes++;
		}
		
		return randomAskList;
	}
	
	public static List<Order> generateAskMomentumList() { 
		
		List<Order> askMomentumList = new ArrayList<Order>();
		
		int numTimes = 0;
		String instrument = INSTRUMENT;
		Date date = DATE;
		double price = PRICE;
		String volume = VOLUME;
		Long randTime = date.getTime();
		
		while (numTimes < MAX_ORDERS) { 
			OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
					date,
					price,
					volume,
					ORDER_ASK,
					sellerBrokerID);
			Order newOrder = orderBuilder.build();
			askMomentumList.add(newOrder);
			price -= 0.4;
			randTime += 4000;
			date.setTime(randTime);
			numTimes++;
		}
		
		return askMomentumList;
	}
	
	public static List<Order> generateMeanRevAskList() { 
		
		List<Order> askMeanRevList = new ArrayList<Order>();
		
		int numTimes = 0;
		String instrument = INSTRUMENT;
		Date date = DATE;
		double price = PRICE;
		String volume = VOLUME;
		Long randTime = date.getTime();
		
		while (numTimes < MAX_ORDERS) { 
			OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
					date,
					price,
					volume,
					ORDER_ASK,
					sellerBrokerID);
			Order newOrder = orderBuilder.build();
			askMeanRevList.add(newOrder);
			price += 0.4;
			randTime += 4000;
			date.setTime(randTime);
			numTimes++;
		}
		
		return askMeanRevList;
	}
	
	public static List<Order> generatePositiveMomentumTradeList() { 
		
		List<Order> momentumTradeList = new ArrayList<Order>();
		
		int numTimes = 0;
		String instrument = INSTRUMENT;
		Date date = DATE;
		double price = PRICE;
		String volume = VOLUME;
		Long randTime = date.getTime();
		
		while (numTimes < MAX_ORDERS) { 
			OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
					date,
					price,
					volume,
					brokerID);
			Order newOrder = orderBuilder.build();
			momentumTradeList.add(newOrder);
			price += 0.4;
			randTime += 4000;
			date.setTime(randTime);
			numTimes++;
		}
		
		return momentumTradeList;
	}
	
	public static List<Order> generateNegativeMomentumTradeList() { 
		
		List<Order> momentumTradeList = new ArrayList<Order>();
		
		int numTimes = 0;
		String instrument = INSTRUMENT;
		Date date = DATE;
		double price = PRICE;
		String volume = VOLUME;
		Long randTime = date.getTime();
		
		while (numTimes < MAX_ORDERS) { 
			OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
					date,
					price,
					volume,
					brokerID);
			Order newOrder = orderBuilder.build();
			momentumTradeList.add(newOrder);
			price -= 0.4;
			randTime += 4000;
			date.setTime(randTime);
			numTimes++;
		}
		
		return momentumTradeList;
	}
	
	public static List<Order> generatePositiveMeanRevTradeList() { 
		
		List<Order> meanRevTradeList = new ArrayList<Order>();
		
		int numTimes = 0;
		String instrument = INSTRUMENT;
		Date date = DATE;
		double price = PRICE;
		String volume = VOLUME;
		Long randTime = date.getTime();
		
		while (numTimes < MAX_ORDERS) { 
			OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
					date,
					price,
					volume,
					brokerID);
			Order newOrder = orderBuilder.build();
			meanRevTradeList.add(newOrder);
			price -= 0.4;
			randTime += 4000;
			date.setTime(randTime);
			numTimes++;
		}
		
		return meanRevTradeList;
	}
	
	public static List<Order> generateNegativeMeanRevTradeList() { 
		
		List<Order> momentumTradeList = new ArrayList<Order>();
		
		int numTimes = 0;
		String instrument = INSTRUMENT;
		Date date = DATE;
		double price = PRICE;
		String volume = VOLUME;
		Long randTime = date.getTime();
		
		while (numTimes < MAX_ORDERS) { 
			OrderBuilder orderBuilder = new OrderBuilderImpl(instrument,
					date,
					price,
					volume,
					brokerID);
			Order newOrder = orderBuilder.build();
			momentumTradeList.add(newOrder);
			price += 0.4;
			randTime += 4000;
			date.setTime(randTime);
			numTimes++;
		}
		
		return momentumTradeList;
	}
}
