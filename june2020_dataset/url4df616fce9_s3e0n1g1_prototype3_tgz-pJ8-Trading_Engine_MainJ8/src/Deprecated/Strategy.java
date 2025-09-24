package Deprecated;


/**
 * Contains strategy functions.
 * @author Dhruv
 *
 */
public class Strategy {
	
	/**
	 * returnList is a list of the last 10 return.
	 */
	private double[] returnList;
	private int count;
	
	public Strategy () {
		this.returnList = new double[10];
		this.count = 0;
	}
	
	/** 
	 * Adds returns to the returnList.
	 * @return return.
	 */
	public double getReturn ( Trade current, Trade previous) {
		double retrn = (current.getPrice() - previous.getPrice())/previous.getPrice();
		
		if (count >= 9) {
			returnList = resize (returnList);
			returnList[count] = retrn;
		}
		else {
			returnList[count] = retrn;
			count ++;
		}
		
		return retrn;
	}
	
	/**
	 * @return  Average of last "count" number of returns.
	 */
	public double getAverage () {
		
		double sum = 0;
		int i = 0;
		while ( i < count) {
			sum += returnList[i];
			i++;
		}
		return sum/count;
	}
	
	/**
	 * Takes in a full list.
	 * @return list with element position updated.
	 */
	private double[] resize ( double[] list) {
		double[] temp = new double[10];
		for (int y = 0; y< 9; y++) {
			temp[y] = list[y+1];
		}
		return temp;
	}
	
	/**
	 * Returns a trade signal. 1 denotes a buy signal, 2 denotes a sell signal and 0 denotes neither.
	 * @return signal
	 */
	public int getSignal () {
		
		int signal = 0;
		if (this.getAverage() > 0) {
			signal = 1;
		}
		else if (this.getAverage() < 0) {
			signal = 2;
		}
		
		return signal;
	}
}
