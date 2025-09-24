package Deprecated;
import java.util.LinkedList;// I'm not sure if it's a good choice any other idea ?
import java.util.Arrays;



/**
 * How to use : first construct MomentumStrategy object then, runStrategy( LinkedList ) by passing it a LinkedList
 * @author chitizadeh
 *
 */
public class MomentumStrategy {
	
	public MomentumStrategy(){
		genSigList = new LinkedList<GeneratedSignal>();
		threshold = 0; 			// for future calculations
	}
	
	/**
	 * check a small list here 10 double of trades to get their return 2 by 2 
	 * then return sell or buy signal 
	 * @return -1: sell     ,  0: nothing,   1: buy
	 */
	private int generatOrderSignal( double[] splittedTradingList){
		int result = 0;
		double returnSum = 0;
		double returnAve;
		//System.out.println(splittedTradingList.length);
			// check here
		for ( int i = 0 ; i < (splittedTradingList.length - 1 ); i++){ // putting all the returns ( diff/prevouse trade) into returnSum
			returnSum += (splittedTradingList[i+1] - splittedTradingList[i])/splittedTradingList[i];
			
		}
		returnAve = returnSum / splittedTradingList.length;
		// considering ThreShold
		if ( (returnAve - threshold) > 0){
			result = BUY;
		}
		else if ( ( returnAve + threshold) < 0){
			result = SELL;
		}
		
		return result;
	}
	
	public  LinkedList<Double> runStrategy( LinkedList<Double> oldTradingList){
		LinkedList<Double> newTradingList = new LinkedList<Double>();
		double temp;
		double[ ] tempList = new double[LOOK_BACK_PERIOD];
		int theSignal = 0;
		int numberOfShareWeHave = 0; 		// this is for that we can not sell something we don't have
		double marketPrice;
		// this loop will empty the oldTradingList till we can not split it anymore;
		while ( oldTradingList.size() >= LOOK_BACK_PERIOD){
			
			for ( int i = 0 ; i < LOOK_BACK_PERIOD && oldTradingList.size() >= LOOK_BACK_PERIOD; i++){
				temp = oldTradingList.poll();
				newTradingList.add(new Double(temp));
				tempList[i] = temp;
			}
			marketPrice = oldTradingList.peek(); // peek the latest market price
			theSignal = generatOrderSignal(tempList);
			if ( theSignal == BUY){
				genSigList.add( new GeneratedSignal ( "buy", marketPrice));
				numberOfShareWeHave ++;
				newTradingList.add(marketPrice);
			}
			else if( theSignal == SELL && numberOfShareWeHave > 0) {
				genSigList.add( new GeneratedSignal ( "sell", marketPrice));
				numberOfShareWeHave --;
				newTradingList.add(marketPrice);
			}
			
		}
				
		return newTradingList;
	}
	
	/**
	 * You can get the number of buys that u have done in a running strategy
	 * @contract YOU NEED TO Construct the MoemntumStrategy and run it. then use this methode
	 * @return number of buys we have done
	 */
	public int getNumberOfBuy(){
		int count = 0;
		for ( GeneratedSignal gs : genSigList){
			if ( gs.getType().equals("buy")){
				count++;
			}
		}
		return count;
	}
	
	
	/**
	 * You can get the number of buys that u have done in a running strategy
	 * @contract YOU NEED TO Construct the MoemntumStrategy and run it. then use this methode
	 * @return number of sells we have done
	 */
	public int getNumberOfSell(){
		int count = 0;
		for ( GeneratedSignal gs : genSigList){
			if ( gs.getType().equals("sell")){
				count++;
			}
		}
		return count;
	}
	
	
	/**
	 * it ignores the shares that is left in our hound and we couldn't sell
	 * @return the amount that we gain or lost , it can be negetive or positive
	 */
	public double evaluteTheStrategy(){
		double sellSum = 0;
		double buySum = 0;
		int count = 0;
		int NumberOfSell = this.getNumberOfSell(); // this is to count buying only shares that we could sell 
		
		for ( GeneratedSignal gs : genSigList){
			if ( gs.getType().equals("sell")){
				sellSum += gs.getPrice();
			}
			else if ( gs.getType().equals("buy") && count < NumberOfSell ){
				buySum += gs.getPrice();
				count++;
				
			}
		}
		
		return (sellSum - buySum);
	}
	
	double threshold;			// at this stage we consider it as zero
	double[] newTradingList;
	private LinkedList<GeneratedSignal> genSigList; // list of generated order signals from our machine which can be buy or sell
	private final static  int LOOK_BACK_PERIOD = 10;  // number that our machine consider averaging before generating signal
	private final static int BUY = 1;
	private final static int SELL = -1;
}
