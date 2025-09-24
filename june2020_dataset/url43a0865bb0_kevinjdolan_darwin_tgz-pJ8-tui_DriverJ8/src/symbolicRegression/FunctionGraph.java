package symbolicRegression;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.List;
import symbolicRegression.environment.XYValues;
import darwin.output.Plot;
import darwin.output.ScatterPlot;
import darwin.population.Individual;
import darwin.population.Node;

public class FunctionGraph extends ScatterPlot {

	private static final long serialVersionUID = -5582067023772844263L;

	private List<Point2D.Double> points;
	private Individual individual;
	
	public FunctionGraph(Individual individual, XYValues xy) {
		this.individual = individual;
		points = xy.getValues();
		Plot plot = new Plot(points);
		plot.setSize(6);
		addPlot(plot);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Node rpb = individual.getTree(0);
		
		//Draw the error lines
		g.setColor(new Color(255, 0, 0));
		for(Point2D.Double pos : points) {
			double result = (Double) rpb.getValue(pos.x);
			
			if(result > maxY)
				result = maxY;
			else if(result < minY)
				result = minY;
			
			int x = getXPixel(pos.x);
			int y1 = getYPixel(pos.y);
			int y2 = getYPixel(result);
			
			g.drawLine(x, y1, x, y2);
		}
		
		//Plot the function
		g.setColor(new Color(0, 255, 0));
		int[] xs = new int[right - left];
		int[] ys = new int[right - left];
		for(int i = left; i < right; i++) {
			double x = getXCoord(i);
			double y = (Double) rpb.getValue(x);
			
			if(y > maxY)
				y = maxY;
			else if(y < minY)
				y = minY;
			
			int j = getYPixel(y);
			
			xs[i - left] = i;
			ys[i - left] = j;
		}
		g.drawPolyline(xs, ys, xs.length);
	}
	
}
