package Trading_Engine;

import java.sql.Time;

public class OneData {
	private long ID;
	private double Price;
	private int Vol;
	private Time time;
	
	public OneData(long tmpID, double tmpPrice, int tmpVol, Time tmpTime) {
		ID = tmpID;
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

	public void updateValue(long tmpID, double tmpPrice, int tmpVol,
			Time tmpTime) {
		ID = tmpID;
		Price = tmpPrice;
		Vol = tmpVol;
		time = tmpTime;
	}

	public int getVol() {
		return Vol;
	}

	public long getID() {
		return ID;
	}

	public void updateVol(int tmpVol) {
		Vol = tmpVol;
	}

}
