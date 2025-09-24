package darwInvest.output;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import darwInvest.data.Ticker;
import darwin.output.Plot;
import darwin.output.ScatterPlot;

public class VolumeChart extends ScatterPlot {

	private static final long serialVersionUID = 4921734347081927307L;
	private Ticker ticker;
	private long start;
	private long end;
	private Plot volumes;
	
	/**
	 * Instantiate a new bar-chart
	 * @param ticker the ticker object
	 * @param start  the start time
	 * @param end    the end time
	 */
	public VolumeChart(Ticker ticker, long start, long end) {		
		setHeightProportion(1);
		setWidthProportion(1);
		setGridColor(new Color(200,200,200));
		setVerticalLines(21);
		setHorizontalLines(3);
		
		this.ticker = ticker;
		
		volumes = new Plot(new ArrayList<Point2D.Double>());
		volumes.setSize(0);
		volumes.setConnect(true);
		volumes.setConnectColor(new Color(50,50,50));
		addPlot(volumes);
		
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
		
		Set<Long> times = ticker.getTimesBetween(start, end);
		List<Point2D.Double> points = new ArrayList<Point2D.Double>();
		
		for(long time : times) {
			double vol = ticker.getData(time, 4);
			double prop = timeToProportion(time);
			points.add(new Point2D.Double(prop, vol));
			
		}
		volumes.setPoints(points);
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
	
}
