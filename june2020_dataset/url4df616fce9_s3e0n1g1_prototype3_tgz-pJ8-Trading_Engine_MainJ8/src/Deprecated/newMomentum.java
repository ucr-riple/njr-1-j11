package Deprecated;
import java.util.LinkedList;
import java.lang.Math;

public class newMomentum {

	public newMomentum ( ){
		counting = 0;
		arrayIsFull = false;
		mode = BUY_MODE;
		average = 0;
		shareQuantity = 0;
		lastAverage = 0;
	}
	
	public double getAverage () {
		return average;
	}
	
	public void addTrade( double trade){
		//once Counting reach 9 , wihch is SIZE_OF_ARRAY, we know arrayIsFull
		lastAverage = average;
		if (( arrayIsFull == false) && counting == SIZE_OF_ARRAY) { // just to check if should it take average or not
			arrayIsFull = true;
		}
		// this makes counting equals to zero, so it works like a round array
		if ( counting == SIZE_OF_ARRAY ) { // to see if counting reachs 9
			counting = 0;
		}
		// insert the trade inside the tenArray
		tenArray[counting] = trade;
		counting++;
		// take Average only when arrayIsFull
		if ( arrayIsFull){
			for ( int i = 0 ; i <= SIZE_OF_ARRAY ; i++ ){
				average += tenArray[i]; 
			}
			average = average/10;
		}
	}
	
	public signalObject generateOrderSignal ( orderObject lastSale, orderObject lastBuy){
		signalObject result = new signalObject( -1, -1, "nothing");
		if ( mode == BUY_MODE && arrayIsFull ){ // we want to buy !
			if ((average - lastAverage) >epsilon){
				result = new signalObject( lastSale.getQauntity(), Math.max(lastSale.getPrice(), lastBuy.getPrice()) + 0.001, "buy"); // to be sure ours is happening
				mode = SELL_MODE;		
			}
		}
		else if ( mode == SELL_MODE && arrayIsFull){
			if ( (average - lastAverage) <  (- epsilon)){ // we want to sell
				result = new signalObject( lastBuy.getQauntity(), Math.min(lastSale.getPrice(), lastBuy.getPrice()) - 0.001, "sell");
				mode = BUY_MODE;		
			}
		}
		return result;
	}
	
	
	public void getreceiptNumber( int receiptNumber /*, int quantityOfShare*/){   // this is gonna be used for the evaluating machine
		if ( mode == BUY_MODE){ // I sold my shares;
			sellReceipt.add(receiptNumber);
			//shareQuantity -= quantityOfShare;
		}
		else if ( mode == SELL_MODE){ // I bought some share
			buyReceipt.add(receiptNumber);
			//shareQuantity += quantityOfShare;
		}
	}
	
	/**
	 * to check any share left or any  share owed
	 * @return
	 */
	public boolean isShareQuantityZero(){
		boolean result;
		if ( shareQuantity == 0){
			result = true;
		}
		else{
			result = false;
		}
		return result;
	}
	
	
	public signalObject finishShare( orderObject lastSell, orderObject lastBuy){
		signalObject result = new signalObject( 0, -1 , "nothing"); // stupid java
		
		if ( shareQuantity > 0){
			// means we have to sell our shares
			if ( shareQuantity < lastBuy.getQauntity()){// means we have less share to sell than requested
				result = new signalObject( shareQuantity, Math.max(lastSell.getPrice(), lastBuy.getPrice()) + 0.001, "buy" );
				shareQuantity = 0;
			}else{ // means we last request to buy has less than what we want to sell
				result = new signalObject( lastBuy.getQauntity(), Math.max(lastSell.getPrice(), lastBuy.getPrice()) + 0.001, "buy");
				shareQuantity -= lastBuy.getQauntity();
			}
		}else if( shareQuantity < 0){
			// means we have to buy some shares 
			if( shareQuantity < lastSell.getQauntity()){ // if we have less thing than actual person wants to sell
				result = new signalObject( shareQuantity, Math.min(lastSell.getPrice(), lastBuy.getPrice()) - 0.001, "sell");
				shareQuantity = 0;
			}else{
				result = new signalObject( lastSell.getQauntity(), Math.min(lastSell.getPrice(), lastBuy.getPrice()) - 0.001, "sell");
				shareQuantity -= lastSell.getQauntity();
			}
		}
		return result;
		
	}
	

	public double[] gettenArray(){
		return tenArray;
	}


	/**
	 * these two are for the evaluation so we ( our machine ) know which trades belong to us
	 * @return
	 */
	public LinkedList<Integer> getSellReceipt(){
		return sellReceipt;
	}
	public LinkedList<Integer> getBuyReceipt(){
		//return sellReceipt;
		return buyReceipt;
	}
	
	private int shareQuantity;
	private LinkedList<Integer> sellReceipt = new LinkedList<Integer>(); 
	private LinkedList<Integer> buyReceipt = new LinkedList<Integer>(); 
	private double average;
	private int mode;
	private double[] tenArray = new double[10];
	private  boolean recording;
	private int counting;
	private static final int SIZE_OF_ARRAY = 9;
	private boolean arrayIsFull;
	private static final int SELL_MODE = 1;
	private static final int BUY_MODE = -1;
	private static final double epsilon = 0.0001;
	private double lastAverage;
	
}
