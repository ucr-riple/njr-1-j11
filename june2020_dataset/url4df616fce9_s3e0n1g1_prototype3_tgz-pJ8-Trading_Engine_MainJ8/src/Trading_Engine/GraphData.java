package Trading_Engine;

import java.sql.Time;

public class GraphData {
	private double Price;
	private Time time;
	
	public GraphData(double tmpPrice, Time tmpTime){
		Price = tmpPrice;
		time = tmpTime;
	}
	
	public double getPrice() {
		return Price;
	}

	public Time getTime() {
		return time;
	}
}
