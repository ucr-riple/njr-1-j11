package darwInvest.output;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map.Entry;
import darwInvest.environment.Settings;
import darwInvest.evaluation.BalanceRecorder;
import darwInvest.evaluation.StrategyExecutive;
import darwin.output.Plot;
import darwin.output.ScatterPlot;
import darwin.population.Individual;

public class Performance extends ScatterPlot {

	private static final long serialVersionUID = 4921734347081927307L;
	private long start;
	private long end;
	private Plot cash, balance, sentiment, buildup, zero, threshold;
	private Plot inverseThreshold, position, buy, sell;
	private Individual individual;
	private Settings settings;
	
	/**
	 * Instantiate a new bar-chart
	 * @param individual the individual being tested
	 * @param settings	 the settings being applied
	 * @param ticker	 the ticker object
	 * @param start		 the start time
	 * @param end		 the end time
	 */
	public Performance(Individual individual, Settings settings, long start, long end) {		
		setHeightProportion(1);
		setWidthProportion(1);
		setGridColor(new Color(200,200,200));
		setVerticalLines(21);
		setHorizontalLines(11);
		
		this.individual = individual;
		this.settings = settings;
		
		cash = new Plot(new ArrayList<Point2D.Double>());
		cash.setSize(0);
		cash.setConnect(true);
		cash.setConnectColor(new Color(0,192,0));
		addPlot(cash);
		
		balance = new Plot(new ArrayList<Point2D.Double>());
		balance.setSize(0);
		balance.setConnect(true);
		balance.setConnectColor(new Color(0,0,255));
		addPlot(balance);
		
		buildup = new Plot(new ArrayList<Point2D.Double>());
		buildup.setSize(0);
		buildup.setConnect(true);
		buildup.setConnectColor(new Color(255,0,255));
		addPlot(buildup);
		
		sentiment = new Plot(new ArrayList<Point2D.Double>());
		sentiment.setSize(4);
		sentiment.setFill(false);
		sentiment.setConnect(false);
		sentiment.setCircle(true);
		sentiment.setPointColor(new Color(0,0,0));
		addPlot(sentiment);
		
		zero = new Plot(new ArrayList<Point2D.Double>());
		zero.setSize(0);
		zero.setConnect(true);
		zero.setConnectColor(new Color(100,100,100));
		addPlot(zero);
		
		threshold = new Plot(new ArrayList<Point2D.Double>());
		threshold.setSize(0);
		threshold.setConnect(true);
		threshold.setConnectColor(new Color(0,255,0));
		addPlot(threshold);
		
		inverseThreshold = new Plot(new ArrayList<Point2D.Double>());
		inverseThreshold.setSize(0);
		inverseThreshold.setConnect(true);
		inverseThreshold.setConnectColor(new Color(255,0,0));
		addPlot(inverseThreshold);
	
		position = new Plot(new ArrayList<Point2D.Double>());
		position.setSize(0);
		position.setConnect(true);
		position.setConnectColor(new Color(0,180,0));
		addPlot(position);
		
		buy = new Plot(new ArrayList<Point2D.Double>());
		buy.setSize(5);
		buy.setFill(true);
		buy.setConnect(false);
		buy.setCircle(false);
		buy.setPointColor(new Color(0,255,0));
		addPlot(buy);
		
		sell = new Plot(new ArrayList<Point2D.Double>());
		sell.setSize(5);
		sell.setFill(true);
		sell.setConnect(false);
		sell.setCircle(false);
		sell.setPointColor(new Color(255,0,0));
		addPlot(sell);
		
		setRange(start, end);
	}
	
	/**
	 * Set the time range, and recalculate the range
	 * @param start the starting time 
	 * @param end   the ending time
	 */
	public void setRange(long start, long end) {
		this.start = start;
		this.end = end;

		BalanceRecorder record = new BalanceRecorder(settings.initialBalance, settings.commission, 
				settings.minCommission, settings.bankruptcy, settings.threshold);
		
		StrategyExecutive executive = new StrategyExecutive(record, individual, start, end, settings);
		executive.execute();

		double early = timeToProportion(record.getBalanceHistory().firstKey());
		double late = timeToProportion(record.getBalanceHistory().lastKey());
		
		/*//CASH HISTORY - GREEN
		double scale = record.getMaxCash();
		ArrayList<Point2D.Double> cash = new ArrayList<Point2D.Double>();
		cash.add(new Point2D.Double(early, 0.8 + 0.2 * settings.initialBalance / scale));
		Set<Entry<Long, Double>> cashHistory = record.getCashHistory().entrySet();
		for(Entry<Long, Double> entry : cashHistory) {
			double time = timeToProportion(entry.getKey());
			double value = 0.6 + 0.2 * entry.getValue() / scale;
			cash.add(new Point2D.Double(time, value));
		}
		this.cash.setPoints(cash);*/
		
		//BALANCE HISTORY - BLUE
		double maxScale = record.getMaxBalance();
		double minScale = record.getMinBalance();
		double scale = maxScale - minScale;
		ArrayList<Point2D.Double> balance = new ArrayList<Point2D.Double>();
		Set<Entry<Long, Double>> balanceHistory = record.getBalanceHistory().entrySet();
		for(Entry<Long, Double> entry : balanceHistory) {
			double time = timeToProportion(entry.getKey());
			double value;
			if(scale != 0)
				value = 0.7 + 0.3 * ((entry.getValue() - minScale) / scale);
			else
				value = 0.85;
			balance.add(new Point2D.Double(time, value));
		}
		this.balance.setPoints(balance);
		
		//SENTIMENT RECOMENDATIONS - BLACK
		maxScale = Math.max(settings.threshold, record.getMaxSentiment());
		minScale = record.getMinSentiment();
		scale = maxScale - minScale;
		ArrayList<Point2D.Double> sentiment = new ArrayList<Point2D.Double>();
		Set<Entry<Long, Double>> sentimentHistory = record.getSentimentHistory().entrySet();
		for(Entry<Long, Double> entry : sentimentHistory) {
			double time = timeToProportion(entry.getKey());
			double value = 0.4 * (entry.getValue() - minScale) / scale;
			sentiment.add(new Point2D.Double(time, value));
		}
		this.sentiment.setPoints(sentiment);
		
		//SENTIMENT BUILDUP - PURPLE
		ArrayList<Point2D.Double> buildup = new ArrayList<Point2D.Double>();
		Set<Entry<Long, Double>> buildupHistory = record.getBuildupHistory().entrySet();
		for(Entry<Long, Double> entry : buildupHistory) {
			double time = timeToProportion(entry.getKey());
			double value = 0.4 * (entry.getValue() - minScale) / scale;
			buildup.add(new Point2D.Double(time, value));
		}
		this.buildup.setPoints(buildup);
		
		//ZERO LINE FOR SENTIMENT - GREY
		ArrayList<Point2D.Double> zero = new ArrayList<Point2D.Double>();
		double value = 0.4 * - minScale / scale;
		zero.add(new Point2D.Double(early, value));
		zero.add(new Point2D.Double(late, value));
		this.zero.setPoints(zero);
		
		//BUY THRESHOLD - GREEN
		ArrayList<Point2D.Double> threshold = new ArrayList<Point2D.Double>();
		value = 0.4 * (settings.threshold - minScale) / scale;
		threshold.add(new Point2D.Double(early, value));
		threshold.add(new Point2D.Double(late, value));
		this.threshold.setPoints(threshold);
		
		//SELL THRESHOLD - RED
		ArrayList<Point2D.Double> inverseThreshold = new ArrayList<Point2D.Double>();
		value = 0.4 * (-settings.threshold - minScale) / scale;
		inverseThreshold.add(new Point2D.Double(early, value));
		inverseThreshold.add(new Point2D.Double(late, value));
		this.inverseThreshold.setPoints(inverseThreshold);
		
		//ORDER HISTORY - GREEN = BUY, RED = SELL, POSITION = DARK GREEN
		scale = record.getMaxOrder();
		double scale2 = record.getMaxPosition();
		ArrayList<Point2D.Double> buy = new ArrayList<Point2D.Double>();
		ArrayList<Point2D.Double> sell = new ArrayList<Point2D.Double>();
		ArrayList<Point2D.Double> position = new ArrayList<Point2D.Double>();
		position.add(new Point2D.Double(early,0.4));
		Set<Entry<Long, Integer>> orderHistory = record.getOrderHistory().entrySet();
		int pos = 0;
		for(Entry<Long, Integer> entry : orderHistory) {
			double time = timeToProportion(entry.getKey());
			value = entry.getValue();
			position.add(new Point2D.Double(time, 0.4 + 0.29 * pos / scale2));
			pos += value;
			position.add(new Point2D.Double(time, 0.4 + 0.29 * pos / scale2));
			if(value > 0)
				buy.add(new Point2D.Double(time, 0.4 + 0.29 * value / scale));
			else
				sell.add(new Point2D.Double(time, 0.4 - 0.29 * value / scale));
		}
		position.add(new Point2D.Double(late, 0.4 + 0.29 * pos / scale2));
		this.position.setPoints(position);
		this.buy.setPoints(buy);
		this.sell.setPoints(sell);
	}
	
	/**
	 * Convert a time to a proportion of test-period
	 * @param time the time to convert
	 * @return 	   the appropriate proportion
	 */
	public double timeToProportion(long time) {
		long timeRange = end - start;
		long diff = time - start;
		return (double) diff / timeRange;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(0,0,0));
		
		for(int i = 0; i < width; i+= 10) {
			int y0 = (int) (0.3 * height) - 1;
			int y1 = (int) (0.6 * height);
			g.drawLine(i, y0, i+5, y0);
			g.drawLine(i, y1, i+5, y1);
		}
	}
	
}
