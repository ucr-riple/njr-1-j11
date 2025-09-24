package Selecting_Algothrim;


/**
 * an Object which consist of 2 field , type and price. type can be "buy" or it can be "sell"
 * types are in lower case.
 * the object is for to hold our generated Signals to future evaluations
 * @author chitizadeh
 *
 */
public class GeneratedSignal {

	/**
	 * 
	 * @param Atype it should be lower case ,   sell    or    buy
	 * @param Aprice the price that it is considered
	 */
	public GeneratedSignal (String Atype, double Aprice){
		type = Atype;
		price= Aprice;	
	}
	
	/**
	 * just normal get to get the private field
	 * @return the type which is can be sell or buy
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * just normal get to get the private field
	 * @return price or order which is consider as double here. 
	 */
	public double getPrice(){
		return price;
	}
	private String type;
	private double price;
	
}
