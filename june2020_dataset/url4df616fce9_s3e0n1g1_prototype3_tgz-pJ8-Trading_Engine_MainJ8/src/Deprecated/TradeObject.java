package Deprecated;

public class TradeObject {
	
	public TradeObject( int SID, int BID, Double pr, int quan){
		sellID = SID;
		buyID = BID;
		quantity = quan;
		price = pr;
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
	private Double price;
	private int quantity;
	private int buyID;
	private int sellID;

}
