package Trading_Engine;

import java.util.LinkedList;

public class myTrade {
	private int length;
	private LinkedList<Double> price;
	
	public myTrade(){
		length = 0;
		price = new LinkedList<Double>();
	}
	
	public void addPice (Double d){
		price.add(d);
		length++;
	}
	public int getLength (){
		return length;
	}

	public LinkedList<Double> getAllPrice() {
		return price;
	}
}
