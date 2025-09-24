package Test;

import java.util.LinkedList;

import java.sql.Time;

import Selecting_Algothrim.*;
import Trading_Engine.*;

import org.junit.Assert;
import org.junit.Test;
import java.util.Random;


public class TestStrategyAndEval {

	
	
	@Test
	public void testSV(){
		
		lecMS strategy = new lecMSMomentum();
		LinkedList<ResultData> tradeListPast = new LinkedList<ResultData>();
		LinkedList<Long> orderID = new LinkedList<Long>();
		LinkedList<signalObject> signalList;

		signalObject tempSignalObject; 
		ResultData tempRD;
		LinkedList<resultObjectL> resultList; 
		
		long bitID = 1;
		long askID = -1;
		Random ran = new Random();
		double price = 10.11;
		double tempPrice;
		int tempVol;
		long timeVal = 1;
		Time tempTime = new Time(timeVal);
		MyBidList bidList = new MyBidList();
		MyAskList askList = new MyAskList();

		bidList.add(100, 4, 5, tempTime);
		askList.add(200, 5, 3, tempTime);


		// creating trading
		strategy.setThreShold(0);
		strategy.setAmountToTrade(1);

		for ( int i = 0 ; i < 9 ; i++ ){// there is less than 10
			tempPrice = price + (ran.nextDouble()*4) - 2;
			tempVol = ran.nextInt(30)+1;

			tradeListPast.add(new ResultData( bitID, askID, tempPrice, tempVol, tempTime));
			bitID++;
			askID--;
			timeVal++;
			tempTime = new Time(timeVal);
		}

		for ( ResultData RD : tradeListPast){
			strategy.addTrade(RD.getPrice());
			signalList = strategy.generateSignalList(bidList, askList); 
			Assert.assertEquals("Error in getting signal from less than 10",0, signalList.size());			
		}


		strategy.addTrade(20);
		signalList = strategy.generateSignalList(bidList, askList);
		if ( signalList.isEmpty()){
			strategy.addTrade(23);
			signalList = strategy.generateSignalList(bidList, askList);
		}
		long tempcheckingPrice = (long) 5;
		//if(signalList.isEmpty()){
			//System.out.println("signalList1 is empty");
		//}else{
			Assert.assertEquals("there was a problem with generating Buy signal", tempcheckingPrice, signalList.get(0).getPrice(), 0.001);
			orderID.add(1L);
			
			
			LinkedList <ResultData> listOfTrades = new LinkedList<ResultData>();
			while( !signalList.isEmpty()){
				tempSignalObject = signalList.poll();
				tempRD = new ResultData( 1L,20L, tempSignalObject.getPrice(), tempSignalObject.getQuantity(), new Time(2) ); // this is How I convert signal Order to tradeObject(resultData)
				// you should do how u do for the top part
				listOfTrades.add(tempRD);
			}
			// this part should be before getReceipt part
			strategy.getSTrade(listOfTrades);
			// be careful it should be before this one
			strategy.getReceiptList(orderID);
		//}
			
			
			strategy.addTrade(1);
			signalList = strategy.generateSignalList(bidList, askList);
			tempcheckingPrice = (long ) 4;
			orderID = new LinkedList<Long>();
			listOfTrades = new LinkedList<ResultData>();
			// empting stuff
			
			while ( !signalList.isEmpty()){
				tempSignalObject = signalList.poll();
				tempRD = new ResultData( 2L,21L, tempSignalObject.getPrice(), tempSignalObject.getQuantity(), new Time(4) ); // this is How I convert signal Order to tradeObject(resultData)
				listOfTrades.add(tempRD);
			}
			orderID.add(21L);
			
			strategy.getSTrade(listOfTrades);
			// this is last
			strategy.getReceiptList(orderID);
			
			
			
			EvaluatorLec ev = new EvaluatorLec( strategy, tradeListPast /*Consider that this should be a full list of trades but here i just use something   */);
			resultList = ev.run();
			Assert.assertEquals("Error in resultList size",1, resultList.size());	// testing size		
			Assert.assertEquals("Error on time of the result", new Time(3), resultList.get(0).getTime());
			double tempLong = ( double) ((4.0 - 5.0)/4.0);
			double tempResultPercentage = resultList.get(0).getPercentage();
			Assert.assertEquals("Error On the percentage of of the get", tempLong, tempResultPercentage, 0.01); 
			
		
	}
}
