package imageCompression;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import darwin.population.Individual;
import darwin.population.Node;

public class FitnessAnalyzer extends darwin.problemObject.FitnessAnalyzer {

	private static final int NUM_POINTS = 50;
	
	@Override
	public String getFitnessName(int which) {
		return "Color Difference";
	}
	
	@Override
	public double[] assessFitness(Individual individual, Object environment) {
		BufferedImage image = (BufferedImage) environment;
		double difference = 0;;
		int width = image.getWidth();
		int height = image.getHeight();
		for(int i = 0; i < NUM_POINTS; i++) {
			double xRand = Math.random();
			double yRand = Math.random();
			int x = (int) (xRand * width);
			int y = (int) (yRand * height);
			
			int c = image.getRGB(x,y);
			int red = (c & 0x00ff0000) >> 16;
			int green = (c & 0x0000ff00) >> 8;
			int blue = c & 0x000000ff;
			
			double r = red / 255.;
			double g = green / 255.;
			double b = blue / 255.;
			
			Node redNode = individual.getTree(0);
			Node greenNode = individual.getTree(1);
			Node blueNode = individual.getTree(2);
			
			Point2D.Double point = new Point2D.Double(xRand, yRand);
			double redValue = (Double) redNode.getValue(point);
			double greenValue = (Double) greenNode.getValue(point);
			double blueValue = (Double) blueNode.getValue(point);
			redValue -= Math.floor(redValue);
			greenValue -= Math.floor(greenValue);
			blueValue -= Math.floor(blueValue);
			
			double redDiff = redValue - r;
			double greenDiff = greenValue - g;
			double blueDiff = blueValue - b;
			
			difference += redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
		}
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