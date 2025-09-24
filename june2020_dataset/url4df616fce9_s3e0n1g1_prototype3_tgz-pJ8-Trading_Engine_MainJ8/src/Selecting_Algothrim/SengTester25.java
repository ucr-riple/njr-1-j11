package Selecting_Algothrim;

import java.util.LinkedList;
import java.util.Random;

public class SengTester25 {
	/**
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		
		LinkedList<TradeObject> tradeListPast = new LinkedList<TradeObject>();
		int currentTime = 20; // this is a varaible which is from time varable in trade to show us current time
		TradeObject tempTrade;
		signalObject tempSignal;
		// --------- this part is just for making a fake trading list, means we assum this exist;
		int SID  = 1;
		int BID = 100;
		double price = 11.4;
		int quantity = 3;
		int time = 20; 		// time here is int. u should make ur own if u think it's not enough
		Random ran = new Random();// just to make a random quantity
		
		LinkedList<orderObject> buyOrderList = new LinkedList<orderObject>();
		LinkedList<orderObject> sellOrderList = new LinkedList<orderObject>();
		LinkedList<Integer> orderID = new LinkedList<Integer>();
		LinkedList<signalObject> signalList;
		LinkedList<resultObjectL> listOfResult;   // resultObjectL, be carefull is has L at the end
		
		resultObjectL  tempResult;
		
		// THIS IS just for making some trades to put into our strategy
		for ( int i = 1 ; i <= 100 ; i++){	
			tradeListPast.add( new TradeObject( SID, BID, price , quantity, time )); // TradeObject( SID, BID, price, quantity, time);
			SID++;
			BID++;
			quantity = ran.nextInt(25) + 1; // just to have randomation in quantity
			time++;
			price = ran.nextDouble();
		}
		// reseting these IDS
		SID = 200;
		BID = 300;
		
		// this is for having some fake Orders for buy and sell these actually should be prority queue and from Sirca
		for ( int i = 1 ; i <= 50 ; i++){ // orderObject( ID, quantity, price)
			buyOrderList.add(new orderObject(BID, quantity, price));
			BID++;
			quantity = ran.nextInt(25) + 1; // just to have randomation in quantity
			price =- 0.005;
		}
		for ( int i = 1 ; i <= 50 ; i++){
			sellOrderList.add(new orderObject(SID, quantity, price));
			SID++;
			quantity = ran.nextInt(25) + 1;
			price += 0.01;
		}
		// so far we just generate fake Orderlist and Trade List to use
		
		LinkedList<TradeObject> DynamicListOfTrade = new LinkedList<TradeObject>();
		
												// they each element has percentage and 
		lecMS strategy = new lecMS();
		
		
		// this means user requested that 100 shares happens each buy or sell
		strategy.setAmountToTrade(100); 
		
		while (currentTime < 90 ){// end of trades
			
			
			//System.out.println("we are passing here");
			tempTrade = tradeListPast.poll();
			strategy.addTrade(tempTrade.getPrice());
				
			signalList = strategy.generateSignalList(buyOrderList, sellOrderList);
			orderID = new LinkedList<Integer>();
			while( !signalList.isEmpty()){
				// here is where the signal is converted into the OrderObject with Given ID
				tempSignal = signalList.poll();
				if ( tempSignal.getType().equalsIgnoreCase("sell")){
					DynamicListOfTrade.add( new TradeObject(SID++, BID++, tempSignal.getPrice(),tempSignal.getQuantity() , time++));
					orderID.add(SID);
				}else{
					DynamicListOfTrade.add( new TradeObject(SID++, BID++, tempSignal.getPrice(),tempSignal.getQuantity() , time++));
					orderID.add(BID);
				}
				
				
			}
			// here we assum the signalObjects in the list are converted into OrderObject.			
			strategy.getReceiptList(orderID);// this is a list of integer where we have 
			
			currentTime = tempTrade.getTime(); // time passes in day
			
		}
		
		// at the end of day, Trading engine generated enough trades and now we wants to evaluate it
		// DynamicListOfTrade here is the final trade List
		
		EvaluatorLec evaluator = new EvaluatorLec( strategy, DynamicListOfTrade);
		evaluator.run();
		
		ResultGenerator resultGenerator = new ResultGenerator( evaluator);
		listOfResult = resultGenerator.getResultList(); // here u have all the result with average time and percentage if we get or not 
		// now we can do anything we want with List of Results in here
		// this is a use
		while( !listOfResult.isEmpty()){
			tempResult = listOfResult.poll();
			System.out.println("At time: " + tempResult.getTime() + " We get this " + tempResult.getPercentage() + " benefit" );
			
			
		}


	}
*/
}