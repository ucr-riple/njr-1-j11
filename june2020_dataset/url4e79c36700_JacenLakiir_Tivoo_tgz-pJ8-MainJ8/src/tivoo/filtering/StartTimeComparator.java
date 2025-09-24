package tivoo.filtering;

import java.util.Comparator;

import tivoo.Event;

public class StartTimeComparator implements Comparator<Event>{
	private final int coeff;
	
	public StartTimeComparator(boolean reversed){
		this.coeff = reversed ? -1 : 1;
	}
	public int compare(Event arg0, Event arg1) {
		return coeff*arg0.getStartTime().compareTo(arg1.getStartTime());
	}

}
