package frame;

import java.awt.Graphics2D;
import java.util.ArrayList;

import frame.layout.Layout;

/**
 * Main wrapping container. Panels are allowed to contain layouts which deals
 * with respositioning and resizing the system.
 * 
 * @author bryanyang
 * 
 */
public class TBPanel implements TBComponent {

	private int xDim, xLoc, yDim, yLoc;
	private Layout myLayout;

	private ArrayList<TBComponent> myPanels;

	public TBPanel(int x, int y, int dx, int dy) {
		myPanels = new ArrayList<TBComponent>();
		xDim = dx;
		yDim = dy;
		xLoc = x;
		yLoc = y;
	}

	/**
	 * You should be able to add TBPanels or displays
	 * 
	 * @param s
	 */
	public void add(TBComponent t) {
		t.setLocation(t.getXLocation() + xLoc, t.getYLocation() + yLoc);
		myPanels.add(t);

	}

	/**
	 * If used before layout created than null pointer exception If used after
	 * layout created but there were stuff added already first add all of them
	 * to the layout specified here.
	 * 
	 * @param t
	 * @param layout
	 */
	public void add(TBComponent t, String layout) {
		add(t);
		try {
			if (myLayout.isEmpty()) {
				for (TBComponent temp : myPanels) {
					myLayout.add(temp, layout);
				}
			}
			myLayout.add(t, layout);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void remove(TBComponent t) {
		if (myPanels.contains(t))
			myPanels.remove(t);
	}

	public void render(Graphics2D g) {
		if (myLayout == null)
			;
		else
			myLayout.update();

		for (TBComponent t : myPanels) {
			t.render(g);
		}

	}

	/**
	 * Sets up layout for specific TBPanel
	 * 
	 * @param l
	 */
	public void setLayout(Layout l) {
		myLayout = l;
		myLayout.setLocation(xLoc, yLoc);
		myLayout.setSize(xDim, yDim);
		myLayout.initialize();
	}

	public void setLocation(int x, int y) {
		xLoc = x;
		yLoc = y;
		if (myLayout == null)
			return;
		myLayout.setLocation(x, y);
	}

	public int getHeight() {
		return yDim;
	}

	public int getWidth() {
		return xDim;
	}

	public int getXLocation() {
		return xLoc;
	}

	public int getYLocation() {
		return yLoc;
	}

	public void resize(int dx, int dy) {

		xDim = dx;
		yDim = dy;
		if (myLayout == null)
			return;
		myLayout.setSize(xDim, yDim);

	}

}
