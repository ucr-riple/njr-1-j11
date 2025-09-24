package Selecting_Algothrim;


public class orderObject {
	
	public orderObject( int aNumber, int aQuantity, int aPrice){
	
		id = aNumber;
		qauntity = aQuantity;
		price = aPrice;
	}
	
	
	public int getId(){
		return id;
	}
	public int getQauntity(){
		return qauntity;
	}
	public int getPrice(){
		return price;
	}
	
	private int id;
	private int qauntity;
	private int price;
}
