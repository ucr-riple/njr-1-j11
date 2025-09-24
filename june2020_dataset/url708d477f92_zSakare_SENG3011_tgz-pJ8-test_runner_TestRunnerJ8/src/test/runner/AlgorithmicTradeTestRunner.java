package test.runner;

import test.implementations.order.AlgorithmicTradeTest;

public class AlgorithmicTradeTestRunner {
	public static void main(String[] args) {
		run();
	}
	
	public static void run() {
		AlgorithmicTradeTest algorithmicTradeTest = new AlgorithmicTradeTest();
		
		// Algorithmic Trade
		algorithmicTradeTest.testEvaluateTradeLowerAskVolume();
		algorithmicTradeTest.testEvaluateTradeLowerBidVolume();
		algorithmicTradeTest.testEvaluateTradeSameVolume();
	}
}
