package utility;

import java.util.ArrayList;
import java.util.List;

public class PositionVector {

	private List<Integer> elements;
	
	/**
	 * Create a zero vector if position is -1, else return an indicator vector set to 1 at position
	 * @param pos
	 * @param size
	 */
	public PositionVector(int pos, int size) {
		if (pos > size) {
			System.err.println("Invalid position vector requested");
		}
		elements = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			elements.add(0);
		}
		if (pos != -1) {
			// set element at pos to 1 and all others to 0
			elements.set(pos, 1);
		} 
	}
	
	public PositionVector(List<Integer> elements) {
		this.elements = elements;
	}
	
	public int get(int i) {
		return elements.get(i);
	}
	
	public List<Integer> getEle() {
		return elements;
	}

	public PositionVector add (PositionVector x) {
		List<Integer> result = new ArrayList<Integer>();
		if (x.size() != this.size()) {
			System.err.println("Fatal: Adding two unequal vectors");
		} else {
			for (int i = 0; i < x.size(); i++) {
				result.add(this.get(i) + x.get(i));
			}
		}
		return new PositionVector(result);
	}
	
	public int size() {
		return elements.size();
	}
	
}
