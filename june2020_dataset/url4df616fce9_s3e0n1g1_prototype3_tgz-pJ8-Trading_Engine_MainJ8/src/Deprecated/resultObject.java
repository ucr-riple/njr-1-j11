package Deprecated;

public class resultObject {

	public resultObject ( int NST, int NBT, int NSB, int NSS, double bene ){
		NumberOfSellTrades = NST;
		NumberOfBuyTrades = NBT;
		NumberOfShareBought = NSB;
		NumberOfShareSold = NSS;
		benefit = bene;
	}
	
	public int getNST(){
		return NumberOfSellTrades;
	}
	public int getNBT(){
		return NumberOfBuyTrades;
	}
	public int getNSB(){
		return NumberOfShareBought;
	}
	public int getNSS(){
		return NumberOfShareSold;
	}
	public double getNBenefit(){
		return benefit;
	}
	
	private int NumberOfSellTrades;
	private int NumberOfBuyTrades;
	private int NumberOfShareBought;
	private int NumberOfShareSold;
	private double benefit;
}
