package Deprecated;

public class orderObject {
	
	public orderObject(int aQuantity, double aPrice){
		qauntity = aQuantity;
		price = aPrice;
	}
	
	public int getQauntity(){
		return qauntity;
	}
	public double getPrice(){
		return price;
	}
	
	private int qauntity;
	private double price;
}
