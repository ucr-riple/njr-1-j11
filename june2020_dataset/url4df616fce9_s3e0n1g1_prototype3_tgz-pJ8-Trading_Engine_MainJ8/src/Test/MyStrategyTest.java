package Test;

import java.util.LinkedList;

import java.sql.Time;

import Selecting_Algothrim.*;
import Trading_Engine.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Random;

public class MyStrategyTest {


	@Test
	public void testStrategy(){
		lecMS strategy = new lecMSMomentum();
		LinkedList<ResultData> tradeListPast = new LinkedList<ResultData>();
		LinkedList<ResultData> newTradeList = new LinkedList<ResultData>();
		LinkedList<Long> orderID = new LinkedList<Long>();
		LinkedList<signalObject> signalList;

		long bitID = 1;
		long askID = -1;
		Random ran = new Random();
		double price = 10.11;
		double tempPrice;
		int tempVol;
		long timeVal = 1;
		Time tempTime = new Time(timeVal);
		int tempSize;
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
		Assert.assertEquals("there was a problem with generating Buy signal", tempcheckingPrice, signalList.get(0).getPrice(), 0.001);
		orderID.add(1L);
		strategy.getReceiptList(orderID);
		strategy.addTrade(1);
		signalList = strategy.generateSignalList(bidList, askList);
		tempcheckingPrice = (long ) 4;
		Assert.assertEquals("There is a problem with generating sell Signal", tempcheckingPrice, signalList.get(0).getPrice(), 0.001);



	}
}
