package frame.layout.container;

import java.util.ArrayList;

import frame.TBComponent;

/**
 * Layoutcontainer helps layouts organize their TBComponents
 * @author bryanyang
 *
 */
public abstract class LayoutContainer implements java.io.Serializable {

	private int xLoc, yLoc, xDim, yDim;
	private ArrayList<TBComponent> myComponents;

	public LayoutContainer(int x, int y, int dx, int dy) {
		xLoc = x;
		yLoc = y;
		xDim = dx;
		yDim = dy;
		myComponents = new ArrayList<TBComponent>();
	}

	public void add(TBComponent t) {
		myComponents.add(t);
		update();
	}

	public void addAll(ArrayList<TBComponent> list) {
		myComponents.clear();
		if (list.isEmpty())
			return;
		myComponents.addAll(list);
		update();
	}

	public void remove(TBComponent t) {
		myComponents.remove(t);
		update();
	}

	public void setLocation(int x, int y) {
		xLoc = x;
		yLoc = y;
		update();
	}
	
	public void setLoc(int x, int y){
		xLoc = x;
		yLoc = y;
	}
	
	
	public void setSize(int x, int y) {
		xDim = x;
		yDim = y;
		update();
	}
	
	public void setDim(int x, int y){
		xDim = x;
		yDim = y;
	}
	
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
	public int getXDim(){
		return xDim;
	}
	
	public int getYDim(){
		return yDim;
	}
	
	public ArrayList<TBComponent> getComponents(){
		return myComponents;
	}

	/**
	 * Dictates how TBComponents are updated and organized
	 */
	public abstract void update();
}
