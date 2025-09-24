package Selecting_Algothrim;
import java.util.LinkedList;
import java.lang.Math;
import Trading_Engine.ResultData;
import Trading_Engine.MyAskList;
import Trading_Engine.MyBidList;


public interface lecMS {

	void setThreShold( double number);
	void setAmountToTrade( int number);
	void addTrade( double trade);
	LinkedList<signalObject> generateSignalList( MyBidList myBidList, MyAskList myAskList);
	void getReceiptList( LinkedList<Long> newReiptList);
	LinkedList<CoupleReciept> getAllReciept();
	int getReceiptLength();
	void getSTrade( LinkedList< ResultData> listOfTrades);
	LinkedList< resultObjectL> getResultListFromStrategy();
	
}
