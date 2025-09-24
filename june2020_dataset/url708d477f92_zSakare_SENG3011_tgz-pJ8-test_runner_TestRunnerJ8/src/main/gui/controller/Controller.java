package main.gui.controller;

import main.evaluator.TradeStrategyEvaluator;
import main.interfaces.Orderbook;
import main.parser.SircaCSVParser;
import main.utils.Strategy;

public class Controller {
	private Orderbook orderbook;
	private TradeStrategyEvaluator evaluator;
	private Strategy strategy;
	private String volume;
	private Integer lookbackPeriod;
	private Double threshold;
	private static final int PERCENTAGE_SCALE = 100;
	
	public void setOrderbook(String fileName) {
		evaluator = null;
		strategy = null;
		volume = null;
		lookbackPeriod = null;
		threshold = null;
		orderbook = SircaCSVParser.input(fileName);
	}

	public void setStrategy(String strategy) {
		if (("Random").equals(strategy)) {
			this.strategy = Strategy.RANDOM;
		} else if (("Momentum").equals(strategy)) {
			this.strategy = Strategy.MOMENTUM;
		} else if (("Mean Reversion").equals(strategy)) {
			this.strategy = Strategy.MEAN_REVERSION;
		}
	}

	public Strategy getStrategy() {
		return strategy;
	}
	
	public void runStrategy() {
		if (orderbook != null) {
			if(strategy != null && volume != null){
				evaluator = new TradeStrategyEvaluator(orderbook.runStrategy(strategy, volume, lookbackPeriod, threshold));
			}
		} else {
			System.err.println("Please load an orderbook prior to simulation.");
		}
	}
	
	public void setVolume(String volume) {
		this.volume = volume;
	}

	public void evaluate() {
		if (orderbook != null) {
			double evaluation = evaluator.calculateProfitLoss();
	
			evaluation = evaluation*PERCENTAGE_SCALE;
			
			// print return to 2 decimal places.
			System.out.printf("Percentage return: %.2f%%\n", evaluation);
		} else {
			System.err.println("Please load an orderbook prior to simulation.");
		}
	}

	public Integer getLookbackPeriod() {
		return lookbackPeriod;
	}

	public void setLookbackPeriod(String lookbackPeriod) throws NumberFormatException {
		if (lookbackPeriod.isEmpty()) {
			lookbackPeriod = null;
			System.out.println("Using default value 1000");
		} else {
			this.lookbackPeriod = Integer.parseInt(lookbackPeriod);
			System.out.println("Lookback period set to: " + this.lookbackPeriod);
		}
	}

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) throws NumberFormatException {
		if (threshold.isEmpty()) {
			this.threshold = null;
			System.out.println("Using default value 0.000001");
		} else {
			this.threshold = Double.parseDouble(threshold);
			System.out.println("Threshold set to: " + this.threshold);
		}
	}

	public TradeStrategyEvaluator getEvaluator() {
		return evaluator;
	}
}
