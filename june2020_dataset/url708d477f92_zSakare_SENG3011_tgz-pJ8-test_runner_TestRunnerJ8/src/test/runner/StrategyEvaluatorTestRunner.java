package test.runner;

import test.evaluator.TradeStrategyEvaluatorTest;

public class StrategyEvaluatorTestRunner {
	public static void main(String[] args) {
		run();
	}
	
	public static void run() {
		TradeStrategyEvaluatorTest strategyEvaluatorTest = new TradeStrategyEvaluatorTest();
		
		// Trade strategy evaluator.
		strategyEvaluatorTest.testCalculateBreakEven();
		strategyEvaluatorTest.testCalculateNegative();
		strategyEvaluatorTest.testCalculatePositive();
		strategyEvaluatorTest.testCalculateProfitLoss();
	}
}
