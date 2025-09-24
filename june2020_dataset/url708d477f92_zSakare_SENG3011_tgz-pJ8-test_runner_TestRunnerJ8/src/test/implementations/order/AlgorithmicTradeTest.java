package test.implementations.order;

import java.util.Date;

import main.implementations.OrderBuilderImpl;
import main.implementations.order.AlgorithmicTrade;
import main.interfaces.OrderBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AlgorithmicTradeTest {

	@Before
	public void setUp() throws Exception {
		System.out.println("Testing algorithmic trade class.");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Test complete.");
	}

	@Test
	public void testEvaluateTradeLowerAskVolume() {
		System.out.print("Testing with lower ask volume......");
		OrderBuilder buyBuilder = new OrderBuilderImpl("TST", new Date(), 10.0, "500", "b", "6969");
		OrderBuilder askBuilder = new OrderBuilderImpl("TST", new Date(), 10.0, "300", "a", "6969");
		
		AlgorithmicTrade trade = new AlgorithmicTrade(buyBuilder.build(), askBuilder.build());
		
		assert(trade.evaluateTrade() == 3000);
		System.out.println("Test passed!");
	}
	
	@Test
	public void testEvaluateTradeLowerBidVolume() {
		System.out.print("Testing with lower bid volume......");
		OrderBuilder buyBuilder = new OrderBuilderImpl("TST", new Date(), 10.0, "100", "b", "6969");
		OrderBuilder askBuilder = new OrderBuilderImpl("TST", new Date(), 10.0, "600", "a", "6969");
		
		AlgorithmicTrade trade = new AlgorithmicTrade(buyBuilder.build(), askBuilder.build());
		
		assert(trade.evaluateTrade() == 1000);
		System.out.println("Test Passed!");
	}
	
	@Test
	public void testEvaluateTradeSameVolume() {
		System.out.print("Testing with same volume......");
		OrderBuilder buyBuilder = new OrderBuilderImpl("TST", new Date(), 10.0, "250", "b", "6969");
		OrderBuilder askBuilder = new OrderBuilderImpl("TST", new Date(), 10.0, "250", "a", "6969");
		
		AlgorithmicTrade trade = new AlgorithmicTrade(buyBuilder.build(), askBuilder.build());
		
		assert(trade.evaluateTrade() == 2500);
		System.out.println("Test Passed!");
	}

}
