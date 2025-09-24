package test.runner;

import test.generator.TradeSignalGeneratorTest;

public class TradeSignalGeneratorTestRunner {
	public static void main(String[] args) {
		run();
	}
	
	public static void run() {
		TradeSignalGeneratorTest tradeSignalGeneratorTest = new TradeSignalGeneratorTest();
		
		// Trade signal generator test.
		tradeSignalGeneratorTest.testRandomAskSignal();
		tradeSignalGeneratorTest.testRandomBidSignal();
		tradeSignalGeneratorTest.testMeanReversionSignal();
		tradeSignalGeneratorTest.testMomentumSignal();
	}
}
