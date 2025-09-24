package glass.drawing;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The shape set contains a set of several shapes and can
 * reason about them
 * @author Kevin Dolan
 */
public class ShapeSet {

	private List<Shape> shapeList;
	private Color background;
	
	/**
	 * Construct a new shape set
	 * @param background the background color
	 */
	public ShapeSet(Color background) {
		shapeList = new ArrayList<Shape>();
		this.background = background;
	}
	
	/**
	 * Add a shap to the set of shapes
	 * @param shape the shape to add
	 */
	public void addShape(Shape shape) {
		shapeList.add(shape);
	}
	
	/**
	 * Determine the color of a given point
	 * @param point the point to check, with values [0,1]
	 */
	public Color getPointColor(Point2D.Double point) {
		return getPointColorAtN(point, 1);
	}
	
	/**
	 * Determine the color of a point at and below the n'th shape
	 * @param point the point to consider
	 * @param n		the number of shapes added
	 * @return		the color below this point
	 */
	private Color getPointColorAtN(Point2D.Double point, int n) {
		//If we're at the bottom, the backgound color
		if(n > shapeList.size())
			return background;
		
		//Get the current shape
		Shape active = shapeList.get(shapeList.size() - n);
		
		//If we have the point, process the shape
		if(active.containsPoint(point)) {
			double opacity = active.getOpacity();
			//If it's solid, we're done
			if(opacity == 1)
				return active.getColor();
			
			//Otherwise, we need to average it
			int myRed = active.getColor().getRed();
			int myGreen = active.getColor().getGreen();
			int myBlue = active.getColor().getBlue();
			
			Color below = getPointColorAtN(point, n+1);
			int yourRed = below.getRed();
			int yourGreen = below.getGreen();
			int yourBlue = below.getBlue();
			
			int red = (int) (opacity * myRed + (1 - opacity) * yourRed);
			int green = (int) (opacity * myGreen + (1 - opacity) * yourGreen);
			int blue = (int) (opacity * myBlue + (1 - opacity) * yourBlue);
			
			return new Color(red, green, blue);
		}
		
		//Otherwise, just keep going
		return getPointColorAtN(point, n+1);
	}
}
