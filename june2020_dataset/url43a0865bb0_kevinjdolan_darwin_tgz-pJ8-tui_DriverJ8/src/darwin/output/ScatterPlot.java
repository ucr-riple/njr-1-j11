package darwin.output;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class ScatterPlot extends Grid{

	private static final long serialVersionUID = 3160055151478549230L;
	
	private ArrayList<Plot> plots;

	public ScatterPlot() {
		plots = new ArrayList<Plot>();
	}
	
	/**
	 * Add a plot to the list of plots
	 * @param p the plot
	 */
	public void addPlot(Plot p) {
		plots.add(p);
	}
	
	/**
	 * @returns the number of plots
	 */
	public int getNumPlots() {
		return plots.size();
	}
	
	/**
	 * Return one plot
	 * @param index the index to access
	 * @returns		the plot at index
	 */
	public Plot getPlot(int index) {
		return plots.get(index);
	}
	
	/**
	 * Process the range of values
	 */
	protected void processRange() {
		minX = Double.MAX_VALUE;
		minY = Double.MAX_VALUE;
		maxX = Double.MIN_VALUE;
		maxY = Double.MIN_VALUE;
		
		for(Plot plot : plots) {
			for(Point2D.Double point : plot.getPoints()) {
				double x = point.x;
				double y = point.y;
				minX = Math.min(x, minX);
				maxX = Math.max(x, maxX);
				minY = Math.min(y, minY);
				maxY = Math.max(y, maxY);
			}
		}
		
		xRange = maxX - minX;
		yRange = maxY - minY;
	}
	
	public void paint(Graphics g) {
		processRange();
		super.paint(g);
		
		//Plot the points
		for(Plot plot : plots) {
			g.setColor(plot.getPointColor());
			List<Point2D.Double> points = plot.getPoints();
			int[] x = null;
			int[] y = null;
			int size = plot.getSize();
			x = new int[points.size()];
			y = new int[points.size()];

			//Collect the points
			for(int i = 0; i < points.size(); i++) {
				Point2D.Double pair = points.get(i);
				double wx = pair.x;
				double wy = pair.y;
				int px = getXPixel(wx);
				int py = getYPixel(wy);
							
				x[i] = px;
				y[i] = py;
			}
			
			//Draw the connect line
			g.setColor(plot.getConnectColor());
			if(plot.isConnect())
				g.drawPolyline(x, y, x.length);
			
			//Draw the points
			g.setColor(plot.getPointColor());
			for(int i = 0; i < x.length; i++) {
				int px = x[i];
				int py = y[i];
				
				if(plot.isCircle()) {
					if(plot.isFill())
						g.fillOval(px - size / 2, py - size / 2, size, size);
					else
						g.drawOval(px - size / 2, py - size / 2, size, size);
				}
				else {
					if(plot.isFill())
						g.fillRect(px - size / 2, py - size / 2, size, size);
					else
						g.drawRect(px - size / 2, py - size / 2, size, size);
				}
			}
		}
	}
	
}
