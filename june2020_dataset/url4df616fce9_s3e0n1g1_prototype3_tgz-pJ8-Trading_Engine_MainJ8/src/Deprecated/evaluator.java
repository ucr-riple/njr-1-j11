package Deprecated;
import java.util.LinkedList;


public class evaluator {

	
	public evaluator( int[] BuyR, int[] SellR){
		BuyRef = BuyR;
		SellRef = SellR;
		buyCount = 0; 			// just to stop counting over and over
		sellCount = 0;
		debit = 0;
		credit = 0;
		shareQuantityBought = 0;
		shareQuantitySold = 0;
		
	}
	
	public resultObject start( LinkedList<TradeObject> trades){
		
		TradeObject tempTrade;
		while ( !trades.isEmpty()  && ( buyCount < BuyRef.length) && (sellCount < SellRef.length)){
			tempTrade = trades.poll();
			
			
			if ( tempTrade.getBuyID() == BuyRef[buyCount]){
				this.shareQuantityBought += tempTrade.getQuantity();
				debit += tempTrade.getPrice();
				buyCount++;
			}
			else if ( tempTrade.getSellID() == SellRef[sellCount]){
				this.shareQuantitySold += tempTrade.getQuantity();
				credit -= tempTrade.getPrice();
				sellCount++;
			}
		}
		// when it's Done
		
		
		return (new resultObject ( sellCount, buyCount, shareQuantityBought,shareQuantitySold, credit - debit  ));
		
		
		
	}
	
	
	private int credit;
	private int debit;
	private int shareQuantityBought;
	private int shareQuantitySold;
	private int buyCount;
	private int sellCount;
	private int [] BuyRef;
	private int [] SellRef;
}
