package frame.layout.container;

import frame.TBComponent;

/**
 * Vertical layout container extends layoutcontainer 
 * Organizes all components vertically
 * 
 * @author bryanyang
 *
 */
public class VerticalLayoutContainer extends LayoutContainer {

	private int SPACING = 5;

	public VerticalLayoutContainer(int x, int y, int dx, int dy) {
		super(x, y, dx, dy);
	}

	/**
	 * Updates location of TBcomponents
	 */
	public void update() {

		/** Calculates vertical sum **/
		int verticalSum = calculateVerticalSum();

		/** If components end up being larger vertically **/
		double scalingFactor;
		while (verticalSum > getYDim()) {
			scalingFactor = ((double) getYDim()) / ((double) verticalSum);
			resizeComponents(scalingFactor);
			verticalSum = calculateVerticalSum();
		}

		/** If components end up being larger horizontally **/

		for (TBComponent temp : super.getComponents()) {
			if (temp.getWidth() > getXDim()) {
				double factor = ((double) getXDim()) / ((double) temp.getWidth());
				int dx = (int) (temp.getWidth() * factor);
				int dy = (int) (temp.getHeight() * factor);
				temp.resize(dx, dy);
			}
		}

		int startY = (getYDim() - verticalSum) / 2;
		int x;

		/** Goes through and sets necessary location **/
		for (TBComponent temp : super.getComponents()) {
			x = (getXDim() - temp.getWidth()) / 2;
			temp.setLocation(x + super.getXLoc(), startY + super.getYLoc());
			startY += temp.getHeight() + SPACING;
		}

	}

	/**
	 * Private helper classes for layout container
	 * @param factor
	 */
	private void resizeComponents(double factor) {
		for (TBComponent temp : super.getComponents()) {
			int dx = (int) (temp.getWidth() * factor);
			int dy = (int) (temp.getHeight() * factor);

			temp.resize(dx, dy);

		}
	}

	/**
	 * Calculates vertical sum to see if resizing necessary
	 * @return
	 */
	private int calculateVerticalSum() {
		int verticalSum = 0;

		/** Calculates sum of vertical elements **/
		for (TBComponent temp : super.getComponents()) {
			verticalSum += temp.getHeight();
		}
		verticalSum += (super.getComponents().size()) * SPACING;
		return verticalSum;
	}

}
