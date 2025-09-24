package darwInvest.output;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;

import darwInvest.data.Ticker;
import darwin.output.Grid;

public class BarChart extends Grid {

	private static final long serialVersionUID = 4921734347081927307L;
	private Ticker ticker;
	private long start;
	private long end;
	
	/**
	 * Instantiate a new bar-chart
	 * @param ticker the ticker object
	 * @param start  the start time
	 * @param end    the end time
	 */
	public BarChart(Ticker ticker, long start, long end) {		
		setHeightProportion(1);
		setWidthProportion(1);
		setGridColor(new Color(200,200,200));
		setVerticalLines(21);
		setHorizontalLines(5);
		
		this.ticker = ticker;
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
		
		//Calculate the ranges
		minX = Double.MAX_VALUE;
		maxX = Double.MIN_VALUE;
		minY = Double.MAX_VALUE;
		maxY = Double.MIN_VALUE;
		
		Set<Long> times = ticker.getTimesBetween(start, end);
		
		for(long time : times) {
			double high = ticker.getData(time, 1);
			high = ticker.normalizeData(0, high);
			double low = ticker.getData(time, 2);
			low = ticker.normalizeData(0, low);
			
			double prop = timeToProportion(time);
			minX = Math.min(prop, minX);
			maxX = Math.max(prop, maxX);
			minY = Math.min(low, minY);
			maxY = Math.max(high, maxY);
		}
		xRange = maxX - minX;
		yRange = maxY - minY;
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
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Set<Long> times = ticker.getTimesBetween(start, end);
		
		g.setColor(new Color(0,0,0));
		for(long time : times) {
			double open = ticker.getData(time, 0);
			open = ticker.normalizeData(0, open);
			double high = ticker.getData(time, 1);
			high = ticker.normalizeData(0, high);
			double low = ticker.getData(time, 2);
			low = ticker.normalizeData(0, low);
			double close = ticker.getData(time, 3);
			close = ticker.normalizeData(0, close);
			
			double prop = timeToProportion(time);
			int x = getXPixel(prop);
			int y0 = getYPixel(open);
			int y1 = getYPixel(high);
			int y2 = getYPixel(low);
			int y3 = getYPixel(close);
			g.drawLine(x, y1, x, y2);
			g.drawLine(x - 3, y0, x, y0);
			g.drawLine(x, y3, x + 3, y3);
		}
	}
	
}
