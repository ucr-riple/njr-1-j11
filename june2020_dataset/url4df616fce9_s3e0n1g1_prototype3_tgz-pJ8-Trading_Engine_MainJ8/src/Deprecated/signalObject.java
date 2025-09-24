package Deprecated;

public class signalObject {

	
	public signalObject( int quan, double pri, String ty ){
		quantity = quan;
		price = pri;
		type = ty;
	}
	
	
	public int getQuantity(){
		return quantity;
	}
	public double getPrice(){
		return price;
	}
	
	public String getType(){
		return type;
	}
	private String type; // can be "sell" or "buy" or nothing
	private int quantity;
	private double price;
}
