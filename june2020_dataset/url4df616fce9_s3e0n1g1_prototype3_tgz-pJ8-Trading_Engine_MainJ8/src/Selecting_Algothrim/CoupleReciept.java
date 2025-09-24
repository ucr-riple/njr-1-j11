package Selecting_Algothrim;


import java.sql.Time;
import java.util.LinkedList;
public class CoupleReciept {
	
	/**
	 * A class to hold the recipt of each couple of buy and sell
	 */
	public CoupleReciept(){
		buyList = new LinkedList<Long>();
		sellList = new LinkedList<Long>();
		debit = 0; // bedehkar
		credit = 0; // talabkar
		time = new Time(0);
	}
	
	/**
	 * TO set the buy and sell List
	 * @param aBuyList
	 */
	public void setBuyList(LinkedList<Long> aBuyList ){
		this.buyList = aBuyList;
	}
	
	public void setSellList( LinkedList<Long> aSellList){
		this.sellList = aSellList;
	}
	
	public void addToCredit( double aPrice){
		this.credit += aPrice;
	}
	
	public void addToDebit(double aPrice){
		this.debit += aPrice;
	}
	
	public void addToTime( Time aTime){
		long tmp = this.time.getTime();
		this.time = new Time(tmp + aTime.getTime());
	}
	
	public LinkedList<Long> getBuyList(){
		return buyList;
	}
	
	public LinkedList<Long> geSellList(){
		return sellList;
	}
	
	
	public Time getTime(){
		return time;
	}
	
	public double getCredit(){
		return credit;
	}
	
	public double getDebit(){
		return debit;
	}
	Time time;
	double credit;
	double debit;
	LinkedList<Long> buyList;
	LinkedList<Long> sellList;

}
