package test.implementations;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import main.implementations.OrderBuilderImpl;
import main.implementations.OrderbookImpl;
import main.implementations.order.AlgorithmicTrade;
import main.implementations.order.Order;
import main.interfaces.OrderBuilder;
import main.utils.Strategy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.helper.ListGenerator;

public class OrderbookImplTest {
	
	public void printList () { 
		
		for (Order order : ListGenerator.generateRandomBidList()) {
			System.out.println("price: " + order.getPrice());
		}
		
	}
	
	@Before
	public void setUp() throws Exception {
		
		System.out.println("Testing TradeStrategyEvaluator.");
	}

	@After
	public void tearDown() throws Exception {
		
		System.out.println("Testing Complete.");
	}

	/**
	 * Tests the Random Strategy by checking if the volume for the
	 * generated Bids and Asks orders are the same. 
	 * It also checks whether list provided after running the 
	 * strategy is not empty.
	 */
	@Test
	public void testRunRandomStrategy() {
		
		System.out.print("Testing Random Strategy.....");
		
		OrderbookImpl randomOrderBook = new OrderbookImpl(ListGenerator.generateRandomBidList(), 
				ListGenerator.generateRandomAskList(),
				null);
		Strategy strategy = Strategy.RANDOM;
		String randVolume = "999";
		
		List<AlgorithmicTrade> randomTradeList = randomOrderBook.runStrategy(strategy, randVolume, null, null);
		assertNotNull("Checking randomTradeList is not empty...", randomTradeList);
		
		for (AlgorithmicTrade algorithmicTrade : randomTradeList) {
			if (algorithmicTrade.getBidOrder().getBuyerBrokerId() == "6969") {
				// Checking volumes are the same (this has to be true)
				assert (algorithmicTrade.getBidOrder().getVolume() == 999);
			} else { 
				assert (algorithmicTrade.getAskOrder().getVolume() == 999);
			}
		}
		System.out.println("Test Passed");
	}
	
	/**
	 * Tests the Momentum Strategy for a Positive trend.
	 * It also checks whether list provided after running the 
	 * strategy is not empty.
	 * We also check to make sure that a Bid has been created to match
	 * the positive trend.
	 */
	@Test
	public void testPosMomentumStrategy() {
		
		System.out.print("Testing Positive Momentum Strategy.....");
		
		OrderbookImpl momentumOrderbook = new OrderbookImpl (ListGenerator.generateMomentumBidList(), 
				ListGenerator.generateAskMomentumList(), 
				ListGenerator.generatePositiveMomentumTradeList());
		Strategy strategy = Strategy.MOMENTUM;
		String randVolume = "999";
		
		List<AlgorithmicTrade> posMomentumTradeList = momentumOrderbook.runStrategy(strategy, randVolume, null, null);
		assertNotNull("Checking MomentumTradeList is not empty...", posMomentumTradeList);
		
		for (AlgorithmicTrade algorithmicTrade : posMomentumTradeList) {
			if (algorithmicTrade.getBidOrder().getBuyerBrokerId() == "6969") { 
				assert (algorithmicTrade.getBidOrder().getVolume() == 999);
			}
		}
		System.out.println("Test Passed");
	}
	
	/**
	 * Tests the Momentum Strategy for a Negative trend.
	 * It also checks whether list provided after running the 
	 * strategy is not empty.
	 * We also check to make sure that an Ask has been created to match
	 * the Negative trend.
	 */
	@Test
	public void testNegMomentumStrategy() {
		
		System.out.print("Testing Negative Momentum Strategy.....");
		
		OrderbookImpl momentumOrderbook = new OrderbookImpl (ListGenerator.generateMomentumBidList(), 
				ListGenerator.generateAskMomentumList(), 
				ListGenerator.generateNegativeMomentumTradeList());
		Strategy strategy = Strategy.MOMENTUM;
		String randVolume = "999";
		
		List<AlgorithmicTrade> negMomentumTradeList = momentumOrderbook.runStrategy(strategy, randVolume, null, null);
		assertNotNull("Checking MomentumTradeList is not empty...", negMomentumTradeList);
		
		for (AlgorithmicTrade algorithmicTrade : negMomentumTradeList) {
			if (algorithmicTrade.getBidOrder().getBuyerBrokerId() == "6969") { 
				assert (algorithmicTrade.getBidOrder().getVolume() == 999);
			}
		}
		System.out.println("Test Passed");
	}
	
	/**
	 * Tests the Mean Revision Strategy for a Positive trend.
	 * It also checks whether list provided after running the 
	 * strategy is not empty.
	 * We also check to make sure that an Ask has been created to match
	 * the positive trend.
	 */
	@Test
	public void testPosMeanRevStrategy() {
		
		System.out.print("Testing Positive Mean Revision Strategy.....");
		
		OrderbookImpl meanRevOrderbook = new OrderbookImpl (ListGenerator.generateMeanRevBidList(), 
				ListGenerator.generateMeanRevAskList(), 
				ListGenerator.generatePositiveMeanRevTradeList());
		Strategy strategy = Strategy.MEAN_REVERSION;
		String randVolume = "999";
		
		List<AlgorithmicTrade> posMeanRevTradeList = meanRevOrderbook.runStrategy(strategy, randVolume, null, null);
		assertNotNull("Checking MeanRevTradeList is not empty...", posMeanRevTradeList);
		
		for (AlgorithmicTrade algorithmicTrade : posMeanRevTradeList) {
			if (algorithmicTrade.getAskOrder().getSellerBrokerId() == "6969") { 
				assert (algorithmicTrade.getAskOrder().getVolume() == 999);
			}
		}
		System.out.println("Test Passed");
	}
	
	/**
	 * Tests the Mean Revision Strategy for a Negative trend.
	 * It also checks whether list provided after running the 
	 * strategy is not empty.
	 * We also check to make sure that a Bid has been created to match
	 * the negative trend.
	 */
	@Test
	public void testNegMeanRevStrategy() {
		
		System.out.print("Testing Negative Mean Revision Strategy.....");
		
		OrderbookImpl meanRevOrderbook = new OrderbookImpl (ListGenerator.generateMeanRevBidList(), 
				ListGenerator.generateMeanRevAskList(), 
				ListGenerator.generateNegativeMeanRevTradeList());
		Strategy strategy = Strategy.MEAN_REVERSION;
		String randVolume = "999";
		
		List<AlgorithmicTrade> negMeanRevTradeList = meanRevOrderbook.runStrategy(strategy, randVolume, null, null);
		assertNotNull("Checking MeanRevTradeList is not empty...", negMeanRevTradeList);
		
		for (AlgorithmicTrade algorithmicTrade : negMeanRevTradeList) {
			if (algorithmicTrade.getAskOrder().getBuyerBrokerId() == "6969") { 
				assert (algorithmicTrade.getBidOrder().getVolume() == 999);
			}
		}
		System.out.println("Test Passed");
	}

	@Test
	public void testTradeMatcher() {
		
		System.out.print("Testing trade matching mechanism......");
		
		OrderbookImpl matcherOrderbook = new OrderbookImpl(ListGenerator.generateRandomBidList(),
				ListGenerator.generateRandomAskList(),
				null);
		
		double priceToMatch = 1000.5;
		
		OrderBuilder orderMaker = new OrderBuilderImpl("DEF", 
				matcherOrderbook.getBidList().get(0).getDateTime(), 
				priceToMatch, 
				"50",
				"A",
				"6969");
		
		// Check an order matched.
		assert(matcherOrderbook.tradeMatcher(orderMaker.build()) != null);
		assert(matcherOrderbook.tradeMatcher(orderMaker.build()).getAskOrder().getPrice() == matcherOrderbook.tradeMatcher(orderMaker.build()).getBidOrder().getPrice());
		System.out.println("Test Passed.");
	}
	
	@Test
	public void testTradeMatcherFails() {
		
		System.out.print("Testing trade matching mechanism with no match......");
		
		OrderbookImpl matcherOrderbook = new OrderbookImpl(ListGenerator.generateRandomBidList(),
				ListGenerator.generateRandomAskList(),
				null);
		
		double priceToMatch = 923.1;
		
		OrderBuilder orderMaker = new OrderBuilderImpl("DEF", 
				matcherOrderbook.getBidList().get(0).getDateTime(), 
				priceToMatch, 
				"50",
				"A",
				"6969");
		
		// Check an order matched.
		assert(matcherOrderbook.tradeMatcher(orderMaker.build()) == null);
		System.out.println("Test Passed.");
	}
	
	@Test
	public void testTradeMatcherManyTrades() {
		
		System.out.print("Testing trade matching with many orders......");
		
		OrderbookImpl matcherOrderbook = new OrderbookImpl(ListGenerator.generateRandomBidList(),
				ListGenerator.generateRandomAskList(),
				null);
		
		List<Order> ordersToMatch = new ArrayList<Order>();
		
		double priceToMatch = 1000.5;
		
		OrderBuilder orderMaker = new OrderBuilderImpl("DEF", 
				matcherOrderbook.getBidList().get(0).getDateTime(), 
				priceToMatch, 
				"50",
				"A",
				"6969");
		ordersToMatch.add(orderMaker.build());
		
		priceToMatch = 1001;
		
		orderMaker = new OrderBuilderImpl("DEF", 
				matcherOrderbook.getBidList().get(0).getDateTime(), 
				priceToMatch, 
				"50",
				"A",
				"6969");
		ordersToMatch.add(orderMaker.build());
		
		priceToMatch = 1001.5;
		
		orderMaker = new OrderBuilderImpl("DEF", 
				matcherOrderbook.getBidList().get(0).getDateTime(), 
				priceToMatch, 
				"50",
				"A",
				"6969");
		ordersToMatch.add(orderMaker.build());
		
		// Check an order matched.
		assert(matcherOrderbook.matchTrades(ordersToMatch) != null);
		assert(matcherOrderbook.matchTrades(ordersToMatch).size() == 3);
		System.out.println("Test Passed.");
	}
	
	@Test
	public void testTradeMatcherManyTradesWithSomeNonMatches() {
		
		System.out.print("Testing trade matching with many orders and some failures......");
		
		OrderbookImpl matcherOrderbook = new OrderbookImpl(ListGenerator.generateRandomBidList(),
				ListGenerator.generateRandomAskList(),
				null);
		
		List<Order> ordersToMatch = new ArrayList<Order>();
		
		double priceToMatch = 1000.5;
		
		OrderBuilder orderMaker = new OrderBuilderImpl("DEF", 
				matcherOrderbook.getBidList().get(0).getDateTime(), 
				priceToMatch, 
				"50",
				"A",
				"6969");
		ordersToMatch.add(orderMaker.build());
		
		priceToMatch = 1001.12;
		
		orderMaker = new OrderBuilderImpl("DEF", 
				matcherOrderbook.getBidList().get(0).getDateTime(), 
				priceToMatch, 
				"50",
				"A",
				"6969");
		ordersToMatch.add(orderMaker.build());
		
		priceToMatch = 1001.5;
		
		orderMaker = new OrderBuilderImpl("DEF", 
				matcherOrderbook.getBidList().get(0).getDateTime(), 
				priceToMatch, 
				"50",
				"A",
				"6969");
		ordersToMatch.add(orderMaker.build());
		
		priceToMatch = 1123.12;
		
		orderMaker = new OrderBuilderImpl("DEF", 
				matcherOrderbook.getBidList().get(0).getDateTime(), 
				priceToMatch, 
				"50",
				"B",
				"6969");
		ordersToMatch.add(orderMaker.build());
		
		// Check an order matched.
		assert(matcherOrderbook.matchTrades(ordersToMatch) != null);
		assert(matcherOrderbook.matchTrades(ordersToMatch).size() == 2);
		System.out.println("Test Passed.");
	}
}
