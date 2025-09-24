package frame.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import frame.TBComponent;

/**
 * Layout superclass
 * 
 * @author bryanyang
 * 
 */
public abstract class Layout implements java.io.Serializable {

	private HashMap<String, ArrayList<TBComponent>> map;
	private int xLoc, yLoc, xDim, yDim;
	
	

	public Layout() {
		map = new HashMap<String, ArrayList<TBComponent>>();
	}

	/**
	 * Adds a TBComponent to a specific state
	 * After it adds the TB Component, it calls
	 * update for the particular implementation of layout
	 * which goes through and resets locations.
	 * 
	 * @param t
	 * @param s
	 */
	public void add(TBComponent t, String s) {
		if (map.keySet().contains(s)) {
			
			map.get(s).add(t);
		} else {
			ArrayList<TBComponent> list = new ArrayList<TBComponent>();
			list.add(t);
			map.put(s, list);
		}
	}

	/**
	 * Remove a TBComponent from a state if it is found
	 * This is currently NOT done recursively so you have to be
	 * at the right level.
	 * 
	 * After it removes the TB component, it calls update for
	 * the particular implementation of layout which goes through 
	 * and resets locations
	 * 
	 * @param t
	 */
	public void remove(TBComponent t) {
		for (String s : map.keySet()) {
			ArrayList<TBComponent> temp = map.get(s);
			if (temp.contains(t)) {
				temp.remove(t);
			}
		}

	}
	
	/**
	 * Helper method for TBComponent
	 * @return
	 */
	public boolean isEmpty(){
		return map.isEmpty();
	}
	
	/**
	 * Resets location for this particular layout
	 * Should really think about tying this to a specific
	 * TBPanel as they are the only things that are 
	 * allowed to have layouts
	 * 
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y){
		xLoc = x;
		yLoc = y;
		update();
	}
	
	public void setLoc(int x, int y)
	{
		xLoc = x;
		yLoc = y;
	}
	/**
	 * Not really used as of now, will be used once resizing
	 * is implemented
	 * @param dx
	 * @param dy
	 */
	public void setSize(int dx, int dy){
		xDim = dx;
		yDim = dy;
		update();
	}
	
	public void setDim(int dx, int dy){
		xDim = dx;
		yDim = dy;
	}
	
	public Map getMap(){
		return map;
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

	/**
	 * Extensions all have to implement this
	 */
	public abstract void update();
	
	/**
	 * Initializes containers according to desired layout
	 */
	public abstract void initialize();
	
	/**
	 * Updates containers
	 */
	public abstract void updateContainers();

}
