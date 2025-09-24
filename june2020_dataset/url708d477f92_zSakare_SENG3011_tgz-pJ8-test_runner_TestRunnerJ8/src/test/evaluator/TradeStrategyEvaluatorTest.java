package test.evaluator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.evaluator.TradeStrategyEvaluator;
import main.implementations.OrderBuilderImpl;
import main.implementations.order.AlgorithmicTrade;
import main.implementations.order.Order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.helper.ListGenerator;

public class TradeStrategyEvaluatorTest {
	
	private static final String INSTRUMENT = "DEF";
	private static final Date DATE = new Date();
	private static final double PRICE = 100.0; 
	private static final String VOLUME = "50";
	private static final String ORDER_BID = "B";
	private static final String ORDER_ASK = "A";
	
	private static final String RANDOM_BROKER_ID = "6969";
	private static final String buyerBrokerID = RANDOM_BROKER_ID;
	private static final String sellerBrokerID = RANDOM_BROKER_ID;
	private static final String brokerID = RANDOM_BROKER_ID;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("Testing TradeStrategyEvalutor");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Testing complete.");
	}

	@Test
	public void testCalculateProfitLoss() {
		System.out.print("Testing if evaluator reads in a trade list correctly......");
		TradeStrategyEvaluator evaluator = new TradeStrategyEvaluator(
				testTradeList(ListGenerator.generateRandomAskList(), 
						ListGenerator.generateRandomBidList()));
		
		assert (evaluator.calculateProfitLoss() != 0);
		System.out.println("Test Passed!");
	}

	@Test
	public void testCalculateBreakEven() {
		System.out.print("Testing if profit breaks even......");
		TradeStrategyEvaluator evaluator = new TradeStrategyEvaluator(testEvenTradeList());
		
		assert (evaluator.calculateProfitLoss() == 0);
		System.out.println("Test Passed!");
	}
	
	@Test
	public void testCalculatePositive() {
		System.out.print("Testing if profit calculation is correct with 1 buy and 2 sells......");
		TradeStrategyEvaluator evaluator = new TradeStrategyEvaluator(testPositiveTradeList());
		
		// Should break even, trades should only occur in pairs to accommodate mean reversion and momentum
		assert (evaluator.calculateProfitLoss() == 0.0);
		System.out.println("Test Passed!");
	}
	
	@Test
	public void testCalculateNegative() {
		System.out.print("Testing if profit calculation is correct with 2 buy and 1 sells......");
		TradeStrategyEvaluator evaluator = new TradeStrategyEvaluator(testNegativeTradeList());
		
		// Should break even, trades should only occur in pairs to accommodate mean reversion and momentum
		assert (evaluator.calculateProfitLoss() == 0.0);
		System.out.println("Test Passed!");
	}
	
	private List<AlgorithmicTrade> testNegativeTradeList() {
		List<AlgorithmicTrade> tradeList = new ArrayList<AlgorithmicTrade>();
		
		tradeList.add(new AlgorithmicTrade((new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_BID, buyerBrokerID)).build(), 
				(new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_ASK, "NO")).build()));
		tradeList.add(new AlgorithmicTrade((new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_BID, buyerBrokerID)).build(), 
				(new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_ASK, "NO")).build()));
		tradeList.add(new AlgorithmicTrade((new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_BID, "NO")).build(), 
				(new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_ASK, sellerBrokerID)).build()));
		
		return tradeList;
	}

	private List<AlgorithmicTrade> testPositiveTradeList() {
		List<AlgorithmicTrade> tradeList = new ArrayList<AlgorithmicTrade>();
		
		tradeList.add(new AlgorithmicTrade((new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_BID, buyerBrokerID)).build(), 
				(new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_ASK, "NO")).build()));
		tradeList.add(new AlgorithmicTrade((new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_BID, "NO")).build(), 
				(new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_ASK, sellerBrokerID)).build()));
		tradeList.add(new AlgorithmicTrade((new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_BID, "NO")).build(), 
				(new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_ASK, sellerBrokerID)).build()));
		
		return tradeList;
	}

	private List<AlgorithmicTrade> testEvenTradeList() {
		List<AlgorithmicTrade> tradeList = new ArrayList<AlgorithmicTrade>();
		
		tradeList.add(new AlgorithmicTrade((new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_BID, "NO")).build(), 
				(new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_ASK, sellerBrokerID)).build()));
		tradeList.add(new AlgorithmicTrade((new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_BID, buyerBrokerID)).build(), 
				(new OrderBuilderImpl(INSTRUMENT, DATE, PRICE, VOLUME, ORDER_ASK, "NO")).build()));
		
		return tradeList;
	}

	private List<AlgorithmicTrade> testTradeList(List<Order> bidList, List<Order> askList) {
		List<AlgorithmicTrade> tradeList = new ArrayList<AlgorithmicTrade>();
		
		for (Order bid : bidList) {
			for (Order ask : askList) {
				if (ask.getPrice() == bid.getPrice()) {
					tradeList.add(new AlgorithmicTrade(bid, ask));
				}
			}
		}
		
		return tradeList;
	}

}
