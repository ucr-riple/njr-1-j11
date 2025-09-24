package Test;

import java.sql.Time;

import org.junit.Assert;
import org.junit.Test;

import Trading_Engine.MyAskList;
import Trading_Engine.MyBidList;

public class MyListTest {
	@Test
	public void testMyBidListSimple() {
		MyBidList myBidList = new MyBidList();
		
		myBidList.add(30, 40, 100, Time.valueOf("12:00:00"));
		myBidList.add(32, 4, 100, Time.valueOf("12:30:00"));
		myBidList.add(34, 52, 100, Time.valueOf("12:50:00"));
		myBidList.add(36, 46, 100, Time.valueOf("12:51:00"));
		myBidList.add(38, 47, 100, Time.valueOf("12:55:00"));
		Assert.assertEquals("Error in getLength()!", 5, myBidList.getLength());
		Assert.assertEquals("Error in insert!",52.0, myBidList.get(0).getPrice(),0.01);
		myBidList.add(30, 33, 100, Time.valueOf("12:00:00"));
		myBidList.add(32, 62, 100, Time.valueOf("12:30:00"));
		myBidList.add(34, 12, 100, Time.valueOf("12:50:00"));
		myBidList.add(36, 9, 100, Time.valueOf("12:51:00"));
		myBidList.add(38, 49, 100, Time.valueOf("12:55:00"));
		myBidList.add(38, 73, 100, Time.valueOf("12:55:00"));
		Assert.assertEquals("Error in getLength()!", 11, myBidList.getLength());
		Assert.assertEquals("Error in insert!",73.0, myBidList.get(0).getPrice(),0.01);
		//myBidList.printAll();
	}
	
	@Test
	public void testMyBidListSameValue() {
		MyBidList myBidList = new MyBidList();
		
		myBidList.add(30, 40, 100, Time.valueOf("12:00:00"));
		myBidList.add(32, 40, 100, Time.valueOf("12:30:00"));
		myBidList.add(34, 40, 100, Time.valueOf("12:50:00"));
		myBidList.add(36, 40, 100, Time.valueOf("12:51:00"));
		myBidList.add(38, 40, 100, Time.valueOf("12:55:00"));
		Assert.assertEquals("Error in insert!",Time.valueOf("12:00:00"), myBidList.get(0).getTime());
		myBidList.add(30, 30, 100, Time.valueOf("12:56:00"));
		myBidList.add(32, 42, 100, Time.valueOf("12:57:00"));
		myBidList.add(34, 40, 100, Time.valueOf("12:58:00"));
		myBidList.add(36, 45, 100, Time.valueOf("12:59:00"));
		myBidList.add(38, 40, 100, Time.valueOf("13:00:00"));
		Assert.assertEquals("Error in insert!",Time.valueOf("12:00:00"), myBidList.get(2).getTime());
		Assert.assertEquals("Error in insert!",Time.valueOf("13:00:00"), myBidList.get(8).getTime());
		//myBidList.printAll();
	}
	
	@Test
	public void testMyAskListSimple() {
		MyAskList myAskList = new MyAskList();
		
		myAskList.add(30, 40, 100, Time.valueOf("12:00:00"));
		myAskList.add(32, 4, 100, Time.valueOf("12:30:00"));
		myAskList.add(34, 52, 100, Time.valueOf("12:50:00"));
		myAskList.add(36, 46, 100, Time.valueOf("12:51:00"));
		myAskList.add(38, 47, 100, Time.valueOf("12:55:00"));
		Assert.assertEquals("Error in getLength()!", 5, myAskList.getLength());
		Assert.assertEquals("Error in insert!",4.0, myAskList.get(0).getPrice(),0.01);
		myAskList.add(30, 33, 100, Time.valueOf("12:00:00"));
		myAskList.add(32, 62, 100, Time.valueOf("12:30:00"));
		myAskList.add(34, 12, 100, Time.valueOf("12:50:00"));
		myAskList.add(36, 3, 100, Time.valueOf("12:51:00"));
		myAskList.add(38, 49, 100, Time.valueOf("12:55:00"));
		myAskList.add(38, 73, 100, Time.valueOf("12:55:00"));
		Assert.assertEquals("Error in getLength()!", 11, myAskList.getLength());
		Assert.assertEquals("Error in insert!",3.0, myAskList.get(0).getPrice(),0.01);
		//myAskList.printAll();
	}
	
	@Test
	public void testMyAskListSameValue() {
		MyAskList myAskList = new MyAskList();
		
		myAskList.add(30, 40, 100, Time.valueOf("12:00:00"));
		myAskList.add(32, 40, 100, Time.valueOf("12:30:00"));
		myAskList.add(34, 40, 100, Time.valueOf("12:50:00"));
		myAskList.add(36, 40, 100, Time.valueOf("12:51:00"));
		myAskList.add(38, 40, 100, Time.valueOf("12:55:00"));
		Assert.assertEquals("Error in insert!",Time.valueOf("12:00:00"), myAskList.get(0).getTime());
		myAskList.add(30, 30, 100, Time.valueOf("12:56:00"));
		myAskList.add(32, 42, 100, Time.valueOf("12:57:00"));
		myAskList.add(34, 40, 100, Time.valueOf("12:58:00"));
		myAskList.add(36, 45, 100, Time.valueOf("12:59:00"));
		myAskList.add(38, 40, 100, Time.valueOf("13:00:00"));
		Assert.assertEquals("Error in insert!",Time.valueOf("12:00:00"), myAskList.get(1).getTime());
		Assert.assertEquals("Error in insert!",Time.valueOf("13:00:00"), myAskList.get(7).getTime());
		//myAskList.printAll();
	}
	
	/*
	float tmpBidPrice1;
	float tmpBidPrice2;
	for(int i = 1; i < myBidList.getLength();i++){
		tmpBidPrice1 = myBidList.get(i-1).getPrice();
		tmpBidPrice2 = myBidList.get(i).getPrice();
		if(tmpBidPrice1 >= tmpBidPrice2){
			
		}else{
			System.out.println("------------error in insertion Sort BidList " + tmpBidPrice1 + " >= " + tmpBidPrice2);
			//myBidList.printAll();
		}
	}
	float tmpAskPrice1;
	float tmpAskPrice2;
	for(int i = 1; i < myAskList.getLength();i++){
		tmpAskPrice1 = myAskList.get(i-1).getPrice();
		tmpAskPrice2 = myAskList.get(i).getPrice();
		if(tmpAskPrice1 <= tmpAskPrice2){
			
		}else{
			System.out.println("------------error in insertion Sort AskList " + tmpAskPrice1 + " <= " + tmpAskPrice2);
			//myAskList.printAll();
		}
	}
	
	*/
}
