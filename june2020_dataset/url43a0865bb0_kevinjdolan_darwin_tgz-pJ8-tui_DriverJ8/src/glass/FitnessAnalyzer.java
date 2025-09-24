package glass;

import glass.drawing.ShapeSet;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import darwin.population.Individual;
public class FitnessAnalyzer extends darwin.problemObject.FitnessAnalyzer {

	//private static final int NUM_POINTS = 1000;
	
	@Override
	public String getFitnessName(int which) {
		return "Color Difference";
	}
	
	@Override
	public double[] assessFitness(Individual individual, Object environment) {
		//Build the shape-set
		ShapeSet shapes = new ShapeSet(new Color(255,255,255));
		individual.getTree(0).getValue(shapes);
		
		BufferedImage image = (BufferedImage) environment;
		double difference = 0;;
		int width = image.getWidth();
		int height = image.getHeight();
		
		/*for(int i = 0; i < NUM_POINTS; i++) {
			double xRand = Math.random();
			double yRand = Math.random();
			int x = (int) (xRand * width);
			int y = (int) (yRand * height);
		 */
		
		for(double x = 0; x < width; x++) {
			for(double y = 0; y < height; y++) {
				int c = image.getRGB((int)x,(int)y);
				int red = (c & 0x00ff0000) >> 16;
				int green = (c & 0x0000ff00) >> 8;
				int blue = c & 0x000000ff;
				
				Point2D.Double point = new Point2D.Double(x/(width-1), y/(height-1));
				Color color = shapes.getPointColor(point);
				
				double redDiff = red - color.getRed();
				double greenDiff = green - color.getGreen();
				double blueDiff = blue - color.getBlue();
				
				difference += redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
			}
		}
			//}
		return new double[] {difference};
	}

	@Override
	public int getNumberMetrics() {
		return 1;
	}

	@Override
	public FitnessAnalyzer clone() {
		return new FitnessAnalyzer();
	}

	@Override
	public double standardize(int fitnessMetric, double fitness) {
		return fitness;
	}

}