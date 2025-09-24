package Selecting_Algothrim;

import java.util.LinkedList;
import java.lang.Math;

import Trading_Engine.MyAskList;
import Trading_Engine.MyBidList;
import Trading_Engine.ResultData;

import java.sql.Time;

public class lecMSReverse implements lecMS {

	public lecMSReverse(){
		counting = 0;
		arrayIsFull = false;
		mode = BUY_MODE;
		average = 0;
		shareQuantity = 0;
		lastAverage = 0;
	}
	
	
	
	
	public void setThreShold( double number){
		this.epsilon = number;
	}
	
	public void setAmountToTrade(int number){
		this.shareQuantityToTrade = number;
	}
	
	
	public void addTrade( double trade){
		//once Counting reach 9 , wihch is SIZE_OF_ARRAY, we know arrayIsFull
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
			lastAverage = average;
			average = 0;
			for ( int i = 0 ; i < SIZE_OF_ARRAY ; i++ ){
				//System.out.println(i + " value: " + tenArray[i]);
				average += tenArray[i]; 
			}
			average = average/10;
			averageChange = (average - lastAverage)/ lastAverage;
		}
	}
	public LinkedList<signalObject> generateSignalList( LinkedList<orderObject> buyOrders, LinkedList<orderObject> sellOrders){
		
		int countingBuyList = 0;
		int countingSellList = 0;
		double tempPrice = 0;
		int tempAmount;
		LinkedList<signalObject> result = new LinkedList<signalObject>();
		boolean requestHappened = false;
		// share quantityLefit will be initialed here
		this.shareQuantityLeft = this.shareQuantityToTrade;

		
		if ( arrayIsFull == true){  // fill the list if only there arrayIsfull
			if ( mode == BUY_MODE){ // if we have to buy
				if (this.averageChange >  epsilon ){
					// we put buy sell here
					requestHappened = true;// for later checking to see if we need to swap
					
					while ( this.shareQuantityLeft > 0){
					
						if (buyOrders.get(0).getPrice() >= sellOrders.get(countingSellList).getPrice() ){
							tempPrice = buyOrders.get(0).getPrice() + this.epsilon; // what is wrong in here
						}else{// means buy has a buy has a lower price than sell, so we just need to give a price equal to sell price
							// we don't need to beat the buy price
							tempPrice = sellOrders.get(countingSellList).getPrice();
						}// 
						// we have price so far
						// now we have to choose for the amount
						if ( sellOrders.get(countingSellList).getQauntity() < this.shareQuantityLeft ){// if amount of orderObject is less than what we need 
							// we need to use that and other shares
							tempAmount = sellOrders.get(countingSellList).getQauntity();
							result.add( new signalObject(tempAmount, tempPrice, "buy"  ));// we make a buy signal
							this.shareQuantityLeft -= sellOrders.get(countingSellList).getQauntity(); // we decrease amount that we have to buy
							countingSellList++; // we have to check for the  other one
						}else{ // means that the amount we want to buy is less than what we other person wants to sell
							tempAmount = this.shareQuantityLeft;
							result.add(new signalObject(tempAmount, tempPrice, "buy"));
							this.shareQuantityLeft = 0;
							countingSellList++;
						}
					} // is is for while loop, means we do it till we put buy signal order into list till we buy amount we want
				}// there is no else,  if average wasn't high enough we won't trade
				// which means we are not adding anything to the signal Object list that we want to buy
				
			}else if ( mode == SELL_MODE){// if we have to Sell
				if ( this.averageChange <  (-epsilon)){// if average decrease than an ofshore
					requestHappened = true;
					// to change to reverse we have to change here
					
					while( this.shareQuantityLeft > 0){
						
						if ( sellOrders.get(0).getPrice() <=  buyOrders.get(countingBuyList).getPrice() ){
							tempPrice = sellOrders.get(0).getPrice() - this.epsilon; // we want our price to bitt he lowest price in the market 
							// since the person who wants to buy is higher than the lowest price. so it will happen if we don't do anything
						}else{
							tempPrice = buyOrders.get(countingBuyList).getPrice(); // see we will give a lower price in the market were no one did so 
							// we can sell our shares
						}
						// we difintily have a price now
						if( buyOrders.get(countingBuyList).getQauntity() < this.shareQuantityLeft){
							tempAmount = buyOrders.get(countingBuyList).getQauntity();
							result.add( new signalObject(tempAmount, tempPrice, "sell"));
							this.shareQuantityLeft -= buyOrders.get(countingBuyList).getQauntity();
							countingBuyList++;
			
						}else{
							tempAmount = this.shareQuantityLeft;
							result.add( new signalObject(tempAmount, tempPrice, "sell"));
							this.shareQuantityLeft = 0;
							countingBuyList++;
						}
					}
					
				}
				
					
				
			}
			
			
			
		}
		
		//
		//if ( requestHappened == true){// if any request happened
			//if ( mode == BUY_MODE){// swap mode for later transaction
				//mode = SELL_MODE;
	//		}else{
		//		mode = BUY_MODE;
			//}
//		}
		
		
		// after generating signal list, mode won't change mode will change ingetREceipt
		return result; // it can be empty or full .
	}
	
	/**
	 * we porpuse is to gather the couple of sells and buys list for 
	 * @param newReceiptList
	 */
	public void getReceiptList(LinkedList<Long> newReceiptList){
		//System.out.println("newReceiptList count: " + newReceiptList.size());
		//System.out.println(" mode: " + mode);
		if( !newReceiptList.isEmpty()){
		
			if ( mode == BUY_MODE){
				tempCR = new CoupleReciept();
				tempCR.setBuyList(newReceiptList);
				this.listOfAllReciept.add(tempCR);
				mode = SELL_MODE;
			}else if ( mode == SELL_MODE){
				//tempCR = new CoupleReciept();
				tempCR.setSellList(newReceiptList);
				this.listOfAllReciept.add(tempCR);
				mode = BUY_MODE;
			}
			else{
				System.out.println("There is a problem");
			}
		}

		//System.out.println("tempCR.getBuyList() count: " + tempCR.getBuyList().size());
	}
	
	// should be changed
	
	
	public LinkedList<CoupleReciept> getAllReciept(){
		return this.listOfAllReciept;
	}
	
	
	
	private double averageChange;
	private double lastAverage;
	private int shareQuantityToTrade;
	private int shareQuantityLeft;
	private int shareQuantity;
	private LinkedList<Integer> sellReceipt = new LinkedList<Integer>(); 
	private LinkedList<Integer> buyReceipt = new LinkedList<Integer>(); 
	private double average;
	private int mode;
	private double[] tenArray = new double[10];
	private  boolean recording;
	private int counting;
	private static final int SIZE_OF_ARRAY = 10;
	private boolean arrayIsFull;
	private static final int SELL_MODE = 1;
	private static final int BUY_MODE = -1;
	private  double epsilon = 0.001;
	private double epsilonPrice = 0.0002;
	private LinkedList<CoupleReciept> listOfAllReciept = new LinkedList<CoupleReciept>();

	// NEW ADDED PART    week 12
	long tempTime;// for calculation of time
	long tempTime2;
	Time tempAverageTime; 
	double outComePercentage;
	double tempDebit;
	double tempCredit;
	int tempNumberOfTrades;// this is for the reason of getting average time of trades
	LinkedList<resultObjectL> listOfResultsFromTrade = new LinkedList<resultObjectL>();
	//////////////////
	
	
	private CoupleReciept tempCR;

	
	
	
	/// NEW methode
	public void getSTrade( LinkedList< ResultData> listOfTrades){
		
		//ResultData tempRD;
		
		if ( mode == BUY_MODE ){// means these trades are because of our Buy
			tempTime  = 0;
			tempDebit = 0;
			tempNumberOfTrades = 0;
			for ( ResultData tempRD : listOfTrades){
				tempDebit += tempRD.getPrice();
				tempTime += tempRD.getTime().getTime();
			}
			tempNumberOfTrades = listOfTrades.size();
		}else if ( mode == SELL_MODE){
			tempCredit = 0;
			for ( ResultData tempRD : listOfTrades){
				tempCredit += tempRD.getPrice();
				tempTime += tempRD.getTime().getTime();
			}
			// when we sell couple gets complete
			tempNumberOfTrades += listOfTrades.size();
			tempTime2 = (long) tempTime/tempNumberOfTrades;
			tempAverageTime = new Time(tempTime2);
			outComePercentage = (tempCredit - tempDebit)/(tempCredit );
			listOfResultsFromTrade.add(new resultObjectL(  tempAverageTime, outComePercentage));
		}
		else{
			System.out.println("There is a problem on getSTrade Methode on LecMSMomentum");
		}
		
		
		
		
		
		
	}
	/**
	 * it's also for double checking
	 */
	public LinkedList< resultObjectL> getResultListFromStrategy(){
		return listOfResultsFromTrade;
	}
	
	
	
	public LinkedList<signalObject> generateSignalList(MyBidList myBidList,
			MyAskList myAskList) {

		int countingBuyList = 0;
		int countingSellList = 0;
		double tempPrice = 0;
		int tempAmount;
		LinkedList<signalObject> result = new LinkedList<signalObject>();
		boolean requestHappened = false;
		// share quantityLefit will be initialed here
		this.shareQuantityLeft = this.shareQuantityToTrade;

		
		if ( arrayIsFull == true){  // fill the list if only there arrayIsfull
			
			if ( mode == BUY_MODE){ // if we have to buy
				if (this.averageChange <  (-epsilon) ){
					// we put buy sell here
					requestHappened = true;// for later checking to see if we need to swap
					
					while ( this.shareQuantityLeft > 0){
						if (myBidList.get(0).getPrice() >= myAskList.get(countingSellList).getPrice() ){
							tempPrice = myBidList.get(0).getPrice() + this.epsilonPrice; // what is wrong in here
						}else{// means buy has a buy has a lower price than sell, so we just need to give a price equal to sell price
							// we don't need to beat the buy price
							tempPrice = myAskList.get(countingSellList).getPrice();
						}// 
						// we have price so far
						// now we have to choose for the amount
						if ( myAskList.get(countingSellList).getVol() < this.shareQuantityLeft ){// if amount of orderObject is less than what we need 
							// we need to use that and other shares
							tempAmount = myAskList.get(countingSellList).getVol();
							result.add( new signalObject(tempAmount, tempPrice, "buy"  ));// we make a buy signal
							this.shareQuantityLeft -= myAskList.get(countingSellList).getVol(); // we decrease amount that we have to buy
							countingSellList++; // we have to check for the  other one
						}else{ // means that the amount we want to buy is less than what we other person wants to sell
							tempAmount = this.shareQuantityLeft;
							result.add(new signalObject(tempAmount, tempPrice, "buy"));
							this.shareQuantityLeft = 0;
							countingSellList++;
						}
					} // is is for while loop, means we do it till we put buy signal order into list till we buy amount we want
				}// there is no else,  if average wasn't high enough we won't trade
				// which means we are not adding anything to the signal Object list that we want to buy
				
			}else if ( mode == SELL_MODE){// if we have to Sell
				//System.out.println("Mode is Sell");
				//System.out.println("this.averageChange :" + this.averageChange );
				if ( this.averageChange >  epsilon){// if average decrease than an ofshore
					System.out.println("it cames here");
					
					requestHappened = true;
					// to change to reverse we have to change here
					
					while( this.shareQuantityLeft > 0){
						
						if ( myAskList.get(0).getPrice() <=  myBidList.get(countingBuyList).getPrice() ){
							tempPrice = myAskList.get(0).getPrice() - this.epsilonPrice; // we want our price to bitt he lowest price in the market 
							// since the person who wants to buy is higher than the lowest price. so it will happen if we don't do anything
						}else{
							tempPrice = myBidList.get(countingBuyList).getPrice(); // see we will give a lower price in the market were no one did so 
							// we can sell our shares
						}
						// we difintily have a price now
						if( myBidList.get(countingBuyList).getVol() < this.shareQuantityLeft){
							tempAmount = myBidList.get(countingBuyList).getVol();
							result.add( new signalObject(tempAmount, tempPrice, "sell"));
							this.shareQuantityLeft -= myBidList.get(countingBuyList).getVol();
							countingBuyList++;
			
						}else{
							tempAmount = this.shareQuantityLeft;
							result.add( new signalObject(tempAmount, tempPrice, "sell"));
							this.shareQuantityLeft = 0;
							countingBuyList++;
						}
					}
					
				}
				
					
				
			}
			
			
			
		}
		
		//
		//if ( requestHappened == true){// if any request happened
			//if ( mode == BUY_MODE){// swap mode for later transaction
				//mode = SELL_MODE;
	//		}else{
		//		mode = BUY_MODE;
			//}
//		}
		
		
		// after generating signal list, mode won't change mode will change ingetREceipt
		return result; // it can be empty or full .
		
	}
	
	
	public int getReceiptLength() {
		return listOfAllReciept.size();
	}

}
