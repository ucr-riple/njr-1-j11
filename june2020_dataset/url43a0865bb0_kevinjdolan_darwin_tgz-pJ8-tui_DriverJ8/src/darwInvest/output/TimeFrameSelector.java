package darwInvest.output;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import darwInvest.environment.Settings;
import darwInvest.evaluation.BalanceTracker;
import darwInvest.evaluation.StrategyExecutive;
import darwin.output.Plot;
import darwin.output.ScatterPlot;
import darwin.population.Individual;

public class TimeFrameSelector extends ScatterPlot {

	private static final long serialVersionUID = 6410426510063167109L;
	private Settings settings;
	private long activeStart;
	
	/**
	 * Instantiate a new time-frame selector
	 * @param individual the individual being tested
	 * @param settings   the settings to use
	 * @param resolution the number of points to render
	 * @param selected   the selection listener
	 */
	public TimeFrameSelector(Individual individual, Settings settings, int resolution, 
			TimeFrameSelected selected) {
		this.settings = settings;
		
		//Set grid/plot parameters
		setHeightProportion(1);
		setWidthProportion(1);
		setGridColor(new Color(200,200,200));
		setVerticalLines(2);
		setHorizontalLines(6);
		
		//Collect raw data
		List<Point2D.Double> prices = new ArrayList<Point2D.Double>();
		List<Point2D.Double> volumes = new ArrayList<Point2D.Double>();
		
		for(int i = 0; i < resolution; i++) {
			double proportion = ((double) i) / (resolution - 1);
			long time = proportionToTime(proportion);
			
			double price = settings.ticker.getData(time, 0);
			price = 0.2 + 0.8 * settings.ticker.normalizeData(0, price);
			
			prices.add(new Point2D.Double(proportion, price));
			
			double volume = settings.ticker.getData(time, 4);
			volume = 0.2 * settings.ticker.normalizeData(4, volume);
			
			volumes.add(new Point2D.Double(proportion, volume));
		}
		
		//Calculate test performance
		List<Point2D.Double> wins = new ArrayList<Point2D.Double>();
		List<Point2D.Double> losses = new ArrayList<Point2D.Double>();
		List<Point2D.Double> neutrals = new ArrayList<Point2D.Double>();
		
		for(long t = settings.ticker.getFirstTime() + settings.lookback; 
			t < settings.ticker.getLastTime() - settings.testLength; 
			t += settings.testLength) {
			
			long start = t;
			long end = t + settings.testLength;
			
			BalanceTracker tracker = new BalanceTracker(settings.initialBalance, settings.commission, 
					settings.minCommission, settings.bankruptcy);
			StrategyExecutive executive = new StrategyExecutive(tracker, individual, start, end, settings);
			executive.execute();
			
			double gain = tracker.getLiquidationValue() - settings.initialBalance;
			
			double prop = timeToProportion(start);
			
			Point2D.Double point = new Point2D.Double(prop, 0.5);
			
			if(gain > 0)
				wins.add(point);
			else if(gain < 0)
				losses.add(point);
			else
				neutrals.add(point);
		}
		
		//Add all the plots in the right order
		Plot winPlot = new Plot(wins);
		winPlot.setSize(4);
		winPlot.setCircle(false);
		winPlot.setConnect(false);
		winPlot.setPointColor(new Color(0,255,0));
		addPlot(winPlot);
		
		Plot lossPlot = new Plot(losses);
		lossPlot.setSize(4);
		lossPlot.setCircle(false);
		lossPlot.setConnect(false);
		lossPlot.setPointColor(new Color(255,0,0));
		addPlot(lossPlot);
		
		Plot neutralPlot = new Plot(neutrals);
		neutralPlot.setSize(4);
		neutralPlot.setCircle(false);
		neutralPlot.setConnect(false);
		neutralPlot.setPointColor(new Color(0,0,0));
		addPlot(neutralPlot);
		
		Plot volPlot = new Plot(volumes);
		volPlot.setSize(0);
		volPlot.setConnect(true);
		volPlot.setConnectColor(new Color(50,50,50));
		addPlot(volPlot);
		
		Plot pricePlot = new Plot(prices);
		pricePlot.setSize(0);
		pricePlot.setConnect(true);
		pricePlot.setConnectColor(new Color(0,0,255));
		addPlot(pricePlot);
		
		activeStart = settings.testBeginning;
		
		this.addMouseListener(selected);
	}
	
	/**
	 * Convert a proportion to a time
	 * @param proportion the proportion to convert
	 * @return 			 the appropriate time
	 */
	public long proportionToTime(double proportion) {
		long firstTime = settings.ticker.getFirstTime();
		long lastTime = settings.ticker.getLastTime();
		long timeRange = lastTime - firstTime;
		long diff = (long) (proportion * timeRange);
		return firstTime + diff;
	}
	
	/**
	 * Convert a time to a proportion of test-period
	 * @param time the time to convert
	 * @return 	   the appropriate proportion
	 */
	public double timeToProportion(long time) {
		long firstTime = settings.ticker.getFirstTime();
		long lastTime = settings.ticker.getLastTime();
		long timeRange = lastTime - firstTime;
		long diff = time - firstTime;
		return (double) diff / timeRange;
	}
	
	/**
	 * Set the active time of this selector
	 */
	public void setActiveTime(long time) {
		activeStart = time;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//Draw the start and end of the active period
		g.setColor(new Color(0,0,0));
		int start = getXPixel(timeToProportion(activeStart));
		g.drawLine(start, 0, start, height);
		int end = getXPixel(timeToProportion(activeStart + settings.testLength));
		g.drawLine(end, 0, end, height);
		
		//Shade the inactive zones
		g.setColor(new Color(150,150,150));
		for(int i = 1; i < start; i += 4)
			g.drawLine(i, 0, i, height);
		for(int i = width - 2; i > end; i -= 4)
			g.drawLine(i, 0, i, height);
		
		//Draw the start and end of the test period
		g.setColor(new Color(255,0,0));
		start = getXPixel(timeToProportion(settings.testBeginning));
		g.drawLine(start, 0, start, height);
		end = getXPixel(timeToProportion(settings.testEnd));
		g.drawLine(end, 0, end, height);
	}
	
}
