package Deprecated;
import java.util.LinkedList;

public class GSTester {

	/**
	 * THis is just for testing MomentumStrategy You can delete it if u want 
	 * @param args
	 */
	public static void main(String[] args) {
		
		LinkedList<Double> trades = new LinkedList<Double>();
		double result;
		trades.add(2.00);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.43);
		trades.add(2.43);
		trades.add(2.43);
		trades.add(2.43);
		trades.add(2.43);
		trades.add(2.43);
		trades.add(2.43);
		trades.add(2.43);
		trades.add(2.43);
		trades.add(2.43);
		trades.add(2.43);
		trades.add(2.43);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(2.21);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.34);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);
		trades.add(1.1);

		
		MomentumStrategy ms = new MomentumStrategy();
		ms.runStrategy(trades);
		result = ms.evaluteTheStrategy();
		
		System.out.println("Here is the result: " + result);
	}

}
