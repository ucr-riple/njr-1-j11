package Trading_Engine;

import java.sql.Time;

public class ResultData {
	private long BidID;
	private long AskID;
	private double Price;
	private int Vol;
	private Time time;
	
	public ResultData(long tmpBidID, long tmpAskID, double tmpPrice, int tmpVol, Time tmpTime) {
		BidID = tmpBidID;
		AskID = tmpAskID;
		Price = tmpPrice;
		Vol = tmpVol;
		time = tmpTime;
	}

	public double getPrice() {
		return Price;
	}

	public Time getTime() {
		return time;
	}

	public int getVol() {
		return Vol;
	}

	public long getAskID() {
		return AskID;
	}

	public long getBuyID() {
		return BidID;
	}

}
