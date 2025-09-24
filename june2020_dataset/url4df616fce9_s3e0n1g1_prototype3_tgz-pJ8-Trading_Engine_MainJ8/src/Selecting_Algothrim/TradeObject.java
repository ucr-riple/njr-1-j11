package Selecting_Algothrim;


public class TradeObject {
	
	public TradeObject( int SID, int BID, Double pr, int quan, int ti){
		sellID = SID;
		buyID = BID;
		quantity = quan;
		price = pr;
		time = ti;
	}
	

	public int getSellID(){
		return sellID;
	}
	
	public int getBuyID(){
		return buyID;
	}
	public int getQuantity(){
		return quantity;
	}
	public Double getPrice(){
		return price;
	}
	public int getTime(){
		return time;
	}
	private Double price;
	private int quantity;
	private int buyID;
	private int sellID;
	private int time;
}
