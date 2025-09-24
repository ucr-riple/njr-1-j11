package test.runner;

import test.implementations.OrderbookImplTest;

public class OrderbookTestRunner {
	public static void main(String[] args) {
		run();
	}

	public static void run() {
		OrderbookImplTest orderbookImplTest = new OrderbookImplTest();
		
		// Orderbook impl test.
		orderbookImplTest.testNegMeanRevStrategy();
		orderbookImplTest.testNegMomentumStrategy();
		orderbookImplTest.testPosMeanRevStrategy();
		orderbookImplTest.testPosMomentumStrategy();
		orderbookImplTest.testRunRandomStrategy();
		orderbookImplTest.testTradeMatcher();
		orderbookImplTest.testTradeMatcherFails();
		orderbookImplTest.testTradeMatcherManyTrades();
		orderbookImplTest.testTradeMatcherManyTradesWithSomeNonMatches();
	}
}
